package ch.bildspur.postfx.pass;

import ch.bildspur.postfx.Supervisor;
import processing.core.PApplet;

/**
 * Created by cansik on 27.03.17.
 */
public class BlurPass extends BasePass {
    private static final String PASS_NAME = "blurFrag";

    private int blurSize;
    private float sigma;
    private boolean horizontal;

    public BlurPass(PApplet sketch)
    {
        this(sketch, 10, 10, false);
    }

    public BlurPass(PApplet sketch, int blurSize, float sigma, boolean horizontal) {
        super(sketch, PASS_NAME);

        this.blurSize = blurSize;
        this.sigma = sigma;
        this.horizontal = horizontal;
    }

    @Override
    public void prepare(Supervisor supervisor) {
        shader.set("blurSize", blurSize);
        shader.set("sigma", sigma);
        shader.set("horizontalPass", horizontal ? 1 : 0);
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

    public boolean isHorizontal() {
        return horizontal;
    }

    public void setHorizontal(boolean horizontal) {
        this.horizontal = horizontal;
    }
}
