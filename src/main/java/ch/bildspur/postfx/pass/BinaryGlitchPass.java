package ch.bildspur.postfx.pass;

import ch.bildspur.postfx.Supervisor;
import processing.core.PApplet;

public class BinaryGlitchPass extends BasePass {
    private static final String PASS_NAME = "binaryGlitchFrag";

    private float strength;

    public BinaryGlitchPass(PApplet sketch) {
        this(sketch, 0.5f);
    }

    public BinaryGlitchPass(PApplet sketch, float strength) {
        super(sketch, PASS_NAME);
        this.strength = strength;
    }

    @Override
    public void prepare(Supervisor supervisor) {
        shader.set("strength", strength);
    }

    public float getStrength() {
        return strength;
    }

    public void setStrength(float strength) {
        this.strength = strength;
    }
}
