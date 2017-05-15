package ch.bildspur.postfx.pass;

import ch.bildspur.postfx.Supervisor;
import processing.core.PApplet;

/**
 * Created by cansik on 15.05.17.
 */
public class SaturationVibrancePass extends BasePass {
    private static final String PASS_NAME = "saturationVibranceFrag";

    private float saturation;
    private float vibrance;

    public SaturationVibrancePass(PApplet sketch) {
        this(sketch, 0.0f, 1.0f);
    }

    public SaturationVibrancePass(PApplet sketch, float saturation, float vibrance) {
        super(sketch, PASS_NAME);

        this.saturation = saturation;
        this.vibrance = vibrance;
    }

    @Override
    public void prepare(Supervisor supervisor) {
        shader.set("saturation", saturation);
        shader.set("vibrance", vibrance);
    }

    public float getSaturation() {
        return saturation;
    }

    public void setSaturation(float saturation) {
        this.saturation = saturation;
    }

    public float getVibrance() {
        return vibrance;
    }

    public void setVibrance(float vibrance) {
        this.vibrance = vibrance;
    }
}
