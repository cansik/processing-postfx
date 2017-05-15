package ch.bildspur.postfx.builder;

import ch.bildspur.postfx.PostFXSupervisor;
import ch.bildspur.postfx.pass.Pass;
import processing.core.PApplet;
import processing.core.PGraphics;

/**
 * Basic PostFX api with builder pattern.
 */
public class PostFX {
    PApplet sketch;
    PostFXSupervisor supervisor;
    PostFXBuilder builder;

    /**
     * Create a new PostFX context.
     *
     * @param sketch Processing context.
     */
    public PostFX(PApplet sketch) {
        this.sketch = sketch;
        supervisor = new PostFXSupervisor(sketch);
        builder = new PostFXBuilder(this, supervisor);
    }

    /**
     * @param sketch Processing context.
     * @param width  Width of the pass buffer.
     * @param height Height of the pass buffer.
     */
    public PostFX(PApplet sketch, int width, int height) {
        this.sketch = sketch;
        supervisor = new PostFXSupervisor(sketch, width, height);
        builder = new PostFXBuilder(this, supervisor);
    }

    /**
     * Start rendering a texture.
     *
     * @param graphics Texture used to render.
     * @return PostFXBuilder pattern object.
     */
    public PostFXBuilder render(PGraphics graphics) {
        supervisor.render(graphics);
        return builder;
    }

    /**
     * Start a rendering the screen framebuffer.
     *
     * @return PostFXBuilder pattern object.
     */
    public PostFXBuilder render() {
        supervisor.render();
        return builder;
    }

    /**
     * Re-initialise the framebuffer with a different size.
     *
     * @param sketch Sketch with new size.
     */
    public void setResolution(PApplet sketch) {
        setResolution(sketch.g);
    }

    /**
     * Re-initialise the framebuffer with a different size.
     *
     * @param texture Texture object with new size.
     */
    public void setResolution(PGraphics texture) {
        setResolution(texture.width, texture.height);
    }

    /**
     * Re-initialise the framebuffer with a different size.
     *
     * @param width  Width of the new framebuffer.
     * @param height Height of the new framebuffer.
     */
    public void setResolution(int width, int height) {
        supervisor.setResolution(width, height);
    }

    /**
     * Preload a specific path to avoid delay because of shader compiling.
     *
     * @param type Type of the pass to preload.
     * @param <T>  Type of the pass to preload.
     * @return
     */
    public <T extends Pass> T preload(Class<T> type) {
        return builder.getPass(type);
    }

    /**
     * Clears all cached passes.
     */
    public void clearPassCache() {
        builder.getPasses().clear();
    }
}
