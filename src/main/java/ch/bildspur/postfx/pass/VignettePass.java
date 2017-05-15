package ch.bildspur.postfx.pass;

import ch.bildspur.postfx.Supervisor;
import processing.core.PApplet;

/**
 * Created by cansik on 15.05.17.
 */
public class VignettePass extends BasePass {
    private static final String PASS_NAME = "vignetteFrag";

    private float amount;
    private float falloff;

    public VignettePass(PApplet sketch) {
        this(sketch, 1, 0);
    }

    public VignettePass(PApplet sketch, float amount, float falloff) {
        super(sketch, PASS_NAME);

        this.amount = amount;
        this.falloff = falloff;
    }

    @Override
    public void prepare(Supervisor supervisor) {
        shader.set("amount", amount);
        shader.set("falloff", falloff);
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public float getFalloff() {
        return falloff;
    }

    public void setFalloff(float falloff) {
        this.falloff = falloff;
    }
}
