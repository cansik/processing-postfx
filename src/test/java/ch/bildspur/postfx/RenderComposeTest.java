package ch.bildspur.postfx;

import processing.core.*;

public class RenderComposeTest extends PApplet {

    public static void main(String... args) {
        RenderComposeTest sketch = new RenderComposeTest();
        sketch.runSketch();
    }

    private PMatrix3D originalMatrix;
    PGraphics canvas;

    int strokeCount = 200;

    @Override
    public void settings() {
        super.settings();
        size(500, 500, P3D);
        pixelDensity(2);
    }

    public void setup() {

        if (this.g.is3D())
            originalMatrix = this.getMatrix((PMatrix3D) null);
        else
            originalMatrix = new PMatrix3D();

        canvas = createGraphics(width, height, P2D);
    }

    public void draw() {
        // draw something onto the screen
        pushMatrix();
        translate(width / 2, height / 2);
        for (int i = 0; i < strokeCount; i++) {
            noFill();
            stroke(0, 255, 0);
            strokeWeight(1);
            line(width / (float) strokeCount * i,
                    height / (float) strokeCount * i,
                    width, height / 2);
        }
        popMatrix();

        // copy screen to canvas
        canvas.beginDraw();
        canvas.resetShader();
        g.endDraw();
        canvas.image((PImage) g, 0, 0);
        g.beginDraw();
        canvas.endDraw();

        background(0);
        rotate((float) (PI * 0.3));

        beginHUD();
        image(canvas, 0, 0);
        endHUD();
    }

    private void beginHUD() {
        this.g.pushMatrix();
        this.g.hint(PConstants.DISABLE_DEPTH_TEST);
        // Load the identity matrix.
        this.g.resetMatrix();
        // Apply the original Processing transformation matrix.
        this.g.applyMatrix(originalMatrix);
    }

    private void endHUD() {
        this.g.hint(PConstants.ENABLE_DEPTH_TEST);
        this.g.popMatrix();
    }
}
