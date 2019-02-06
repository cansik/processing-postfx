package ch.bildspur.postfx;

import ch.bildspur.postfx.builder.PostFX;
import processing.core.PApplet;

/**
 * Created by cansik on 21.03.17.
 */
public class SimpleSketch extends PApplet {
    PostFX fx;

    public static void main(String... args) {
        SimpleSketch sketch = new SimpleSketch();
        sketch.runSketch();
    }

    public void settings() {
        size(720, 720, P3D);
        pixelDensity(2);
    }

    public void setup() {
        fx = new PostFX(this);
    }

    public void draw() {
        // clear screen
        background(0);

        fill(255);
        ellipse(width / 2, height / 2, width * 0.75f, height * 0.75f);

        // render simple cube
        pushMatrix();

        translate(width / 2f, height / 2f);
        rotateX(radians(frameCount % 360));
        rotateZ(radians(frameCount % 360));

        noStroke();
        fill(20, 20, 20);
        box(100);

        fill(150, 255, 255);
        sphere(60);

        popMatrix();

        // add effect
        fx.render()
                .bloom(0.8f, 30, 50)
                .toneMapping(1.2f)
                .compose();

        fill(0, 255, 0);
        text("FPS: " + frameRate, 20, 20);
    }
}
