package ch.bildspur.postfx.pass;

import ch.bildspur.postfx.Supervisor;
import processing.core.PApplet;

/**
 * Created by cansik on 15.05.17.
 */
public class DenoisePass extends BasePass {
    private static final String PASS_NAME = "denoiseFrag";

    private float exponent;

    public DenoisePass(PApplet sketch) {
        this(sketch, 50);
    }

    public DenoisePass(PApplet sketch, float exponent) {
        super(sketch, PASS_NAME);

        this.exponent = exponent;
    }

    @Override
    public void prepare(Supervisor supervisor) {
        shader.set("resolution", supervisor.getResolution());
        shader.set("exponent", exponent);
    }

    public float getExponent() {
        return exponent;
    }

    public void setExponent(float exponent) {
        this.exponent = exponent;
    }
}
