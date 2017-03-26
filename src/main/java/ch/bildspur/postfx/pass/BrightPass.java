package ch.bildspur.postfx.pass;

import processing.core.PApplet;

/**
 * Created by cansik on 27.03.17.
 */
public class BrightPass extends BasePass {
    private static final String PASS_NAME = "brightPassFrag";

    private float threshold;

    public BrightPass(PApplet sketch, float threshold) {
        super(sketch, PASS_NAME);

        this.threshold = threshold;
    }

    @Override
    public void prepare() {
        shader.set("brightPassThreshold", threshold);
    }

    public float getThreshold() {
        return threshold;
    }

    public void setThreshold(float threshold) {
        this.threshold = threshold;
    }
}
