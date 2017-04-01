package ch.bildspur.postfx.builder;

import ch.bildspur.postfx.PostFXSupervisor;
import processing.core.PApplet;
import processing.core.PGraphics;

/**
 * Created by cansik on 01.04.17.
 */
public class PostFX {
    PApplet sketch;
    PostFXSupervisor supervisor;
    PostFXBuilder builder;

    public PostFX(PApplet sketch) {
        this.sketch = sketch;
        supervisor = new PostFXSupervisor(sketch);
        builder = new PostFXBuilder(this, supervisor);
    }

    public PostFX(PApplet sketch, int width, int height) {
        this.sketch = sketch;
        supervisor = new PostFXSupervisor(sketch, width, height);
        builder = new PostFXBuilder(this, supervisor);
    }

    public PostFXBuilder render(PGraphics graphics) {
        supervisor.render(graphics);
        return builder;
    }
}
