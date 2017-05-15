package ch.bildspur.postfx;

import ch.bildspur.postfx.pass.Pass;
import processing.core.PApplet;
import processing.core.PGraphics;

import java.nio.file.Path;

/**
 * Handles ping-pong pass buffer.
 */
public class PostFXSupervisor implements Supervisor {
    private PApplet sketch;

    private final int PASS_NUMBER = 2;

    private int width;
    private int height;
    private int[] resolution = new int[2];

    private Path shaderPath;

    private int passIndex = -1;

    // frameBuffer
    private PGraphics[] passBuffers;

    /**
     * Create a new ping-pong pass buffer.
     *
     * @param sketch Processing context. Sketch size will be used.
     */
    public PostFXSupervisor(PApplet sketch) {
        this(sketch, sketch.width, sketch.height);
    }

    /**
     * Create a new ping-pong pass buffer.
     *
     * @param sketch Processing context.
     * @param width  Width of the pass buffer.
     * @param height Height of the pass buffer.
     */
    public PostFXSupervisor(PApplet sketch, int width, int height) {
        this.sketch = sketch;
        setResolution(width, height);
    }

    private void increasePass() {
        passIndex = (passIndex + 1) % passBuffers.length;
    }

    /**
     * Set new resolution and re-init buffer.
     *
     * @param width  New width of the pass buffer.
     * @param height New height of the pass buffer.
     */
    @Override
    public void setResolution(int width, int height) {
        this.width = width;
        this.height = height;

        // init temp pass buffer
        passBuffers = new PGraphics[PASS_NUMBER];
        for (int i = 0; i < passBuffers.length; i++) {
            passBuffers[i] = sketch.createGraphics(width, height, PApplet.P2D);
            passBuffers[i].noSmooth();
        }

        resolution = new int[]{width, height};
    }

    /**
     * Returns pass buffer resolution.
     *
     * @return Int array as shader uniform.
     */
    @Override
    public int[] getResolution() {
        return resolution;
    }


    /**
     * Start a new multi-pass rendering with the screen framebuffer.
     */
    public void render() {
        sketch.g.endDraw();
        render(sketch.g);
        sketch.g.beginDraw();
    }

    /**
     * Start a new multi-pass rendering.
     *
     * @param graphics Texture used as input.
     */
    @Override
    public void render(PGraphics graphics) {
        PGraphics pass = getNextPass();
        clearPass(pass);

        pass.beginDraw();
        pass.image(graphics, 0, 0);
        pass.endDraw();

        increasePass();
    }

    /**
     * Apply pass to texture.
     *
     * @param pass Pass to apply.
     */
    @Override
    public void pass(Pass pass) {
        pass.prepare(this);
        pass.apply(this);
        increasePass();
    }

    /**
     * Compose and finalize rendering onto sketch texture.
     */
    public void compose() {
        sketch.g.image(getCurrentPass(), 0, 0);
    }

    /**
     * Compose and finalize rendering.
     *
     * @param graphics Texture to render onto.
     */
    @Override
    public void compose(PGraphics graphics) {
        clearPass(graphics);

        graphics.beginDraw();
        graphics.image(getCurrentPass(), 0, 0);
        graphics.endDraw();
    }

    /**
     * Get next pass of the pass buffer.
     *
     * @return Next pass of the pass buffer.
     */
    @Override
    public PGraphics getNextPass() {
        int nextIndex = (passIndex + 1) % passBuffers.length;
        return passBuffers[nextIndex];
    }

    /**
     * Get current pass of the pass buffer.
     *
     * @return Current pass of the pass buffer.
     */
    @Override
    public PGraphics getCurrentPass() {
        return passBuffers[passIndex];
    }

    /**
     * Clear a pass (black with alpha).
     *
     * @param pass Pass to clear.
     */
    @Override
    public void clearPass(PGraphics pass) {
        // clear pass buffer
        pass.beginDraw();
        pass.background(0, 0);
        pass.resetShader();
        pass.endDraw();
    }
}
