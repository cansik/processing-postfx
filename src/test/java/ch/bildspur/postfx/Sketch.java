package ch.bildspur.postfx;

import ch.bildspur.postfx.builder.PostFX;
import ch.bildspur.postfx.pass.BlurPass;
import ch.bildspur.postfx.pass.BrightPass;
import ch.bildspur.postfx.pass.SobelPass;
import ch.bildspur.postfx.pass.VignettePass;
import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;
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

    PImage lenna;

    public void settings() {
        size(OUTPUT_WIDTH, OUTPUT_HEIGHT, P2D);
        PJOGL.profile = 1;
    }

    public void setup() {
        frameRate(FRAME_RATE);

        supervisor = new PostFXSupervisor(this);
        brightPass = new BrightPass(this, 0.3f);
        blurPass = new BlurPass(this, 40, 12f, false);
        sobelPass = new SobelPass(this);

        fx = new PostFX(this);
        fx.preload(VignettePass.class);

        canvas = createGraphics(width, height, P3D);

        // load test image
        lenna = this.loadImage("data/Lenna.png");

        // initialise pass results
        passResult = createGraphics(width, height, P2D);
    }

    public void draw() {
        // clear screen
        background(0);

        canvas.beginDraw();
        canvas.background(55);

        drawChessBoard(canvas, 8);
        drawBackgroundImage(canvas);

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

        // add effect
        /*
        supervisor.render();
        supervisor.pass(sobelPass);
        supervisor.compose();
        */

        fx.render(canvas)
                //.brightnessContrast(0.1f, 1.0f)
                //.bloom(0.8f, 30, 50)
                //.pixelate(20)
                //.rgbSplit(map(mouseX, 0, width, 0, 100))
                .vignette(1, 0)
                //.noise(1, 50)
                //.saturationVibrance(map(mouseX, 0, width, -1, 1), map(mouseY, 0, height, -1, 1))
                .compose(passResult);

        blendMode(BLEND);
        image(canvas, 0, 0, width / 2, height / 2);
        text("Canvas", 10, height / 2 + 20);

        blendMode(BLEND);
        image(passResult, width / 2, 0, width / 2, height / 2);
        text("Result", width / 2 + 10, height / 2 + 20);

        fill(0, 255, 0);
        text("FPS: " + frameRate, 20, 20);
    }

    void drawBackgroundImage(PGraphics pg) {
        pg.image(lenna, 0, 0, pg.width, pg.height);
    }

    void drawChessBoard(PGraphics pg, int amount) {

        float blockX = pg.width / (float) amount;
        float blockY = pg.height / (float) amount;

        for (int i = 0; i < amount; i++) {
            for (int j = 0; j < amount; j++) {
                if ((i + j + 1) % 2 == 0) {
                    pg.fill(255, 255, 255); // white
                } else {
                    pg.fill(0, 0, 0); // black
                }
                pg.rect(i * blockX, j * blockY, (i + 1) * blockX, (j + 1) * blockY);
            }
        }
    }
}
