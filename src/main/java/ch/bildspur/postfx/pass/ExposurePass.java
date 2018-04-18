package ch.bildspur.postfx.pass;

import ch.bildspur.postfx.Supervisor;
import processing.core.PApplet;

/**
 * Created by cansik on 27.03.17.
 */
public class ExposurePass extends BasePass {
    private static final String PASS_NAME = "exposureFrag";

    private float exposure;

    public ExposurePass(PApplet sketch) {
        this(sketch, 0.0f);
    }

    public ExposurePass(PApplet sketch, float exposure) {
        super(sketch, PASS_NAME);

        this.exposure = exposure;
    }

    @Override
    public void prepare(Supervisor supervisor) {
        shader.set("exposure", exposure);
    }

    public float getExposure() {
        return exposure;
    }

    public void setExposure(float exposure) {
        this.exposure = exposure;
    }
}
