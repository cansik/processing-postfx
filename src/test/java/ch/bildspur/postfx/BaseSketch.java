package ch.bildspur.postfx;

import ch.bildspur.postfx.builder.PostFX;
import ch.bildspur.postfx.pass.BlurPass;
import ch.bildspur.postfx.pass.BrightPass;
import ch.bildspur.postfx.pass.SobelPass;
import ch.bildspur.postfx.pass.VignettePass;
import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;

/**
 * Created by cansik on 21.03.17.
 */
public class BaseSketch extends PApplet {
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
    PImage hdrImage;

    public static void main(String... args) {
        BaseSketch sketch = new BaseSketch();
        sketch.runSketch();
    }

    public void settings() {
        size(OUTPUT_WIDTH, OUTPUT_HEIGHT, P2D);
        pixelDensity(2);
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
        hdrImage = this.loadImage("data/hdr.jpg");

        // initialise pass results
        passResult = createGraphics(width, height, P2D);
    }

    public void draw() {
        // clear screen
        background(0);

        canvas.beginDraw();
        canvas.background(55);

        //drawChessBoard(canvas, 8);
        drawHDRImage(canvas);

        // render simple cube
        canvas.pushMatrix();

        canvas.translate(width / 2f, height / 2f);
        canvas.rotateX(radians(frameCount % 360));
        canvas.rotateZ(radians(frameCount % 360));

        canvas.noStroke();
        canvas.fill(20, 20, 20);
        canvas.box(100);

        canvas.fill(150, 255, 255);
        canvas.sphere(60);

        canvas.popMatrix();
        canvas.endDraw();

        // add effect
        fx.render(canvas)
                //.brightnessContrast(0.1f, 1.0f)
                //.bloom(0.8f, 30, 50)
                //.vignette(1, 0)
                //.binaryGlitch(0.5f)
                .toneMapping(1.2f)
                .exposure(1.0f)
                .compose(passResult);

        blendMode(BLEND);
        image(canvas, 0, 0, width / 2f, height / 2f);
        text("Canvas", 10, height / 2f + 20);

        blendMode(BLEND);
        image(passResult, width / 2f, 0, width / 2f, height / 2f);
        text("Result", width / 2f + 10, height / 2f + 20);

        fill(0, 255, 0);
        text("FPS: " + frameRate, 20, 20);
    }

    void drawBackgroundImage(PGraphics pg) {
        pg.image(lenna, 0, 0, pg.width, pg.height);
    }

    void drawHDRImage(PGraphics pg) {
        pg.image(hdrImage, 0, 0, pg.width, pg.height);
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
