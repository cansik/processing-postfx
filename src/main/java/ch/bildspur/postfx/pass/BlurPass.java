package ch.bildspur.postfx.pass;

import processing.core.PApplet;

/**
 * Created by cansik on 27.03.17.
 */
public class BlurPass extends BasePass {
    private static final String PASS_NAME = "blurFrag";

    private int blurSize;
    private float sigma;
    private boolean horizonatal;

    public BlurPass(PApplet sketch, int blurSize, float sigma, boolean horizonatal) {
        super(sketch, PASS_NAME);

        this.blurSize = blurSize;
        this.sigma = sigma;
        this.horizonatal = horizonatal;
    }

    @Override
    public void prepare() {
        shader.set("blurSize", blurSize);
        shader.set("sigma", sigma);
        shader.set("horizontalPass", horizonatal ? 1 : 0);
    }
}
