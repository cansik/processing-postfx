package ch.bildspur.postfx.dof;

import peasy.PeasyCam;
import processing.core.PApplet;
import processing.core.PGraphics;

public class DepthOfFieldExample extends PApplet {

    public static void main(String[] args) {
        new DepthOfFieldExample().run();
    }

    public void run() {
        this.runSketch();
    }

    PGraphics canvas;

    PeasyCam cam;

    final int size = 500;

    DepthOfField dof;

    public void settings() {
        size(size * 2, size, P3D);
    }

    public void setup() {
        canvas = createGraphics(size, size, P3D);
        cam = new PeasyCam(this, 1000);

        dof = new DepthOfField(this, canvas.width, canvas.height);
    }

    public void draw() {
        background(0);

        // render 3d
        render(canvas);
        cam.getState().apply(canvas);

        PGraphics result = dof.filter(canvas);

        // show output onto onscreen canvas
        cam.beginHUD();
        image(canvas, 0, 0);
        image(result, size, 0);

        textSize(15);
        text("ORIGINAL", 25, 25);
        text("DEPTH", size + 25, 25);
        text("FPS: " + frameRate, 25, height - 25);
        cam.endHUD();
    }

    void render(PGraphics canvas) {
        float size = 4.0f;

        canvas.beginDraw();
        canvas.background(55);

        canvas.pushMatrix();

        //canvas.translate(canvas.width / 2, canvas.height / 2);
        canvas.rotateX(radians(frameCount % 360));
        canvas.rotateZ(radians(frameCount % 360));

        canvas.noStroke();
        canvas.fill(20, 20, 20);
        canvas.box(100 * size);

        canvas.fill(150, 255, 255);
        canvas.sphere(60 * size);

        canvas.popMatrix();
        canvas.endDraw();
        canvas.endDraw();
    }
}
