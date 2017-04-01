package ch.bildspur.postfx;

import ch.bildspur.postfx.pass.Pass;
import processing.core.PApplet;
import processing.core.PGraphics;

import java.nio.file.Path;

/**
 * Created by cansik on 26.03.17.
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

    public PostFXSupervisor(PApplet sketch)
    {
        this(sketch, sketch.width, sketch.height);
    }

    public PostFXSupervisor(PApplet sketch, int width, int height)
    {
        this.sketch = sketch;
        setResolution(width, height);

        // init temp pass buffer
        passBuffers = new PGraphics[PASS_NUMBER];
        for (int i = 0; i < passBuffers.length; i++)
        {
            passBuffers[i] = sketch.createGraphics(width, height, PApplet.P2D);
            passBuffers[i].noSmooth();
        }
    }

    private void increasePass()
    {
        passIndex = (passIndex + 1) % passBuffers.length;
    }

    @Override
    public void setResolution(int width, int height) {
        this.width = width;
        this.height = height;

        resolution = new int[]{width, height};
    }

    @Override
    public int[] getResolution() {
        return resolution;
    }

    @Override
    public void render(PGraphics graphics) {
        PGraphics pass = getNextPass();
        clearPass(pass);

        pass.beginDraw();
        pass.image(graphics, 0, 0);
        pass.endDraw();

        increasePass();
    }

    @Override
    public void pass(Pass pass) {
        pass.prepare(this);
        pass.apply(this);
        increasePass();
    }

    public void compose() {
        //todo: not working at the moment

        //clearPass(sketch.g);
        sketch.g.beginDraw();
        sketch.g.image(getCurrentPass(), 0, 0);
        sketch.g.endDraw();
    }

    @Override
    public void compose(PGraphics graphics) {
        clearPass(graphics);

        graphics.beginDraw();
        graphics.image(getCurrentPass(), 0, 0);
        graphics.endDraw();
    }

    @Override
    public PGraphics getNextPass() {
        int nextIndex = (passIndex + 1) % passBuffers.length;
        return passBuffers[nextIndex];
    }

    @Override
    public PGraphics getCurrentPass() {
        return passBuffers[passIndex];
    }

    @Override
    public void clearPass(PGraphics pass) {
        // clear pass buffer
        pass.beginDraw();
        pass.background(0, 0);
        pass.resetShader();
        pass.endDraw();
    }
}
