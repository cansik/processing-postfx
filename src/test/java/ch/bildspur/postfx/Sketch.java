package ch.bildspur.postfx;

import ch.bildspur.postfx.builder.PostFX;
import ch.bildspur.postfx.pass.BlurPass;
import ch.bildspur.postfx.pass.BrightPass;
import ch.bildspur.postfx.pass.SobelPass;
import processing.core.PApplet;
import processing.core.PGraphics;
import processing.opengl.PJOGL;

/**
 * Created by cansik on 21.03.17.
 */
public class Sketch extends PApplet {
    public final static int OUTPUT_WIDTH = 640;
    public final static int OUTPUT_HEIGHT = 480;

    public final static int FRAME_RATE = 60;

    PGraphics canvas;
    PGraphics passResult;

    PostFXSupervisor supervisor;
    BrightPass brightPass;
    BlurPass blurPass;
    SobelPass sobelPass;

    PostFX fx;

    public void settings() {
        size(OUTPUT_WIDTH, OUTPUT_HEIGHT, P3D);
        PJOGL.profile = 1;
    }

    public void setup() {
        frameRate(FRAME_RATE);

        supervisor = new PostFXSupervisor(this);
        brightPass = new BrightPass(this, 0.3f);
        blurPass = new BlurPass(this, 40, 12f, false);
        sobelPass = new SobelPass(this);

        fx = new PostFX(this);

        canvas = createGraphics(width, height, P3D);

        // initialise pass results
        passResult = createGraphics(width, height, P2D);
    }

    public void draw() {
        // clear screen
        background(0);

        canvas.beginDraw();
        canvas.background(55);

        // render simple cube
        canvas.pushMatrix();

        canvas.translate(width / 2, height / 2);
        canvas.rotateX(radians(frameCount % 360));
        canvas.rotateZ(radians(frameCount % 360));

        canvas.noStroke();
        //fill(124, 238, 206);
        canvas.fill(20, 20, 20);
        canvas.box(100);

        canvas.fill(150, 255, 255);
        //canvas.fill(255);
        canvas.sphere(60);

        canvas.popMatrix();
        canvas.endDraw();

        // add effects
        /*
        supervisor.render(canvas);
        supervisor.pass(sobelPass);
        supervisor.compose(passResult);
        */

        fx.render(canvas)
                .brightnessContrast(0.0f, 1.0f)
                .compose(passResult);

        blendMode(BLEND);
        image(canvas, 0, 0, width / 2, height / 2);
        text("Canvas", 10, height / 2 + 20);
        blendMode(SCREEN);
        image(passResult, width / 2, 0, width / 2, height / 2);
        text("Result", width / 2 + 10, height / 2 + 20);


        fill(0, 255, 0);
        text("FPS: " + frameRate, 20, 20);
    }

}
