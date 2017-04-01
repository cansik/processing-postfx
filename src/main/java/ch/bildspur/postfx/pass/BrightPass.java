package ch.bildspur.postfx.pass;

import ch.bildspur.postfx.Supervisor;
import processing.core.PApplet;

/**
 * Created by cansik on 27.03.17.
 */
public class BrightPass extends BasePass {
    private static final String PASS_NAME = "brightPassFrag";

    private float threshold;

    public BrightPass(PApplet sketch) {
        this(sketch, 0.5f);
    }

    public BrightPass(PApplet sketch, float threshold) {
        super(sketch, PASS_NAME);

        this.threshold = threshold;
    }

    @Override
    public void prepare(Supervisor supervisor) {
        shader.set("brightPassThreshold", threshold);
    }

    public float getThreshold() {
        return threshold;
    }

    public void setThreshold(float threshold) {
        this.threshold = threshold;
    }
}
