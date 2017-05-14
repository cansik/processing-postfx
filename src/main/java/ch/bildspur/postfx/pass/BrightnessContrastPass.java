package ch.bildspur.postfx.pass;

import ch.bildspur.postfx.Supervisor;
import processing.core.PApplet;

/**
 * Created by cansik on 14.05.17.
 */
public class BrightnessContrastPass extends BasePass {
    private static final String PASS_NAME = "brightnessContrastFrag";

    private float brightness;
    private float contrast;

    public BrightnessContrastPass(PApplet sketch) {
        this(sketch, 0.0f, 1.0f);
    }

    public BrightnessContrastPass(PApplet sketch, float brightness, float contrast) {
        super(sketch, PASS_NAME);

        this.brightness = brightness;
        this.contrast = contrast;
    }

    @Override
    public void prepare(Supervisor supervisor) {
        shader.set("brightness", brightness);
        shader.set("contrast", contrast);
    }

    public float getBrightness() {
        return brightness;
    }

    public void setBrightness(float brightness) {
        this.brightness = brightness;
    }

    public float getContrast() {
        return contrast;
    }

    public void setContrast(float contrast) {
        this.contrast = contrast;
    }
}
