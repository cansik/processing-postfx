package ch.bildspur.postfx.pass;

import ch.bildspur.postfx.Supervisor;
import processing.core.PApplet;

/**
 * Created by cansik on 14.05.17.
 */
public class ToneMappingPass extends BasePass {
    private static final String PASS_NAME = "toneMappingFrag";

    private float gamma;

    public ToneMappingPass(PApplet sketch) {
        this(sketch, 2.2f);
    }

    public ToneMappingPass(PApplet sketch, float gamma) {
        super(sketch, PASS_NAME);

        this.gamma = gamma;
    }

    @Override
    public void prepare(Supervisor supervisor) {
        shader.set("gamma", gamma);
    }

    public float getGamma() {
        return gamma;
    }

    public void setGamma(float gamma) {
        this.gamma = gamma;
    }
}
