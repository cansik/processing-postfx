package ch.bildspur.postfx.builder;

import ch.bildspur.postfx.PostFXSupervisor;
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
}
