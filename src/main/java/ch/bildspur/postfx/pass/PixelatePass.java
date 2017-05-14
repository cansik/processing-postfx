package ch.bildspur.postfx.pass;

import ch.bildspur.postfx.Supervisor;
import processing.core.PApplet;

/**
 * Created by cansik on 14.05.17.
 */
public class PixelatePass extends BasePass {
    private static final String PASS_NAME = "pixelateFrag";

    private float amount;

    public PixelatePass(PApplet sketch) {
        this(sketch, 10);
    }

    public PixelatePass(PApplet sketch, float amount) {
        super(sketch, PASS_NAME);

        this.amount = amount;
    }

    @Override
    public void prepare(Supervisor supervisor) {
        shader.set("resolution", supervisor.getResolution());
        shader.set("amount", amount);
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }
}
