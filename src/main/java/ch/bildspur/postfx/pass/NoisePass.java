package ch.bildspur.postfx.pass;

import ch.bildspur.postfx.Supervisor;
import processing.core.PApplet;

/**
 * Created by cansik on 15.05.17.
 */
public class NoisePass extends BasePass {
    private static final String PASS_NAME = "noiseFrag";

    private float amount;
    private float speed;

    public NoisePass(PApplet sketch) {
        this(sketch, 1, 50);
    }

    public NoisePass(PApplet sketch, float amount, float speed) {
        super(sketch, PASS_NAME);

        this.amount = amount;
        this.speed = speed;
    }

    @Override
    public void prepare(Supervisor supervisor) {
        shader.set("time", sketch.millis() / 1000f);
        shader.set("amount", amount);
        shader.set("speed", speed);
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }
}
