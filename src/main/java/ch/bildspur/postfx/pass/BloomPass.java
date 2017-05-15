package ch.bildspur.postfx.pass;

import ch.bildspur.postfx.Supervisor;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PGraphics;

/**
 * Created by cansik on 15.05.17.
 */
public class BloomPass implements Pass {

    private PApplet sketch;

    private BlurPass blurPass;
    private BrightPass brightPass;

    private int blurSize;
    private float sigma;
    private float threshold;


    public BloomPass(PApplet sketch) {
        this.sketch = sketch;

        blurPass = new BlurPass(sketch);
        brightPass = new BrightPass(sketch);
    }

    public BloomPass(PApplet sketch, float threshold, int blurSize, float sigma) {
        this(sketch);

        this.threshold = threshold;
        this.blurSize = blurSize;
        this.sigma = sigma;
    }

    @Override
    public void prepare(Supervisor supervisor) {
    }

    @Override
    public void apply(Supervisor supervisor) {
        PGraphics np = supervisor.getNextPass();
        PGraphics cp = supervisor.getCurrentPass();

        supervisor.clearPass(np);

        // copy original image to next pass
        np.beginDraw();
        np.blendMode(PConstants.BLEND);
        np.image(cp, 0, 0);
        np.endDraw();

        // use current pass as buffer
        supervisor.clearPass(cp);
        cp.beginDraw();

        // bright pass
        brightPass.setThreshold(threshold);
        brightPass.prepare(supervisor);

        cp.shader(brightPass.shader);
        cp.image(np, 0, 0);
        cp.endDraw();

        // set params
        blurPass.setBlurSize(blurSize);
        blurPass.setSigma(sigma);

        // horizontal pass
        cp.beginDraw();
        blurPass.setHorizontal(true);
        blurPass.prepare(supervisor);

        cp.shader(blurPass.shader);
        cp.image(cp, 0, 0);
        cp.endDraw();

        // vertical pass
        cp.beginDraw();
        blurPass.setHorizontal(false);
        blurPass.prepare(supervisor);

        cp.shader(blurPass.shader);
        cp.image(cp, 0, 0);
        cp.endDraw();

        // screen pass onto next
        np.beginDraw();
        np.blendMode(PConstants.SCREEN);
        np.image(cp, 0, 0);
        np.blendMode(PConstants.BLEND);
        np.endDraw();
    }

    public int getBlurSize() {
        return blurSize;
    }

    public void setBlurSize(int blurSize) {
        this.blurSize = blurSize;
    }

    public float getSigma() {
        return sigma;
    }

    public void setSigma(float sigma) {
        this.sigma = sigma;
    }

    public float getThreshold() {
        return threshold;
    }

    public void setThreshold(float threshold) {
        this.threshold = threshold;
    }
}
