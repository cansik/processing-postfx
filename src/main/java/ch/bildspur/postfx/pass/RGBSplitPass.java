package ch.bildspur.postfx.pass;

import ch.bildspur.postfx.Supervisor;
import processing.core.PApplet;

/**
 * Created by cansik on 15.05.17.
 */
public class RGBSplitPass extends BasePass {
    private static final String PASS_NAME = "rgbSplitFrag";

    private float delta;

    public RGBSplitPass(PApplet sketch) {
        this(sketch, 10);
    }

    public RGBSplitPass(PApplet sketch, float delta) {
        super(sketch, PASS_NAME);

        this.delta = delta;
    }

    @Override
    public void prepare(Supervisor supervisor) {
        shader.set("resolution", supervisor.getResolution());
        shader.set("delta", delta);
    }

    public float getDelta() {
        return delta;
    }

    public void setDelta(float delta) {
        this.delta = delta;
    }
}
