package ch.bildspur.postfx.pass;

import processing.core.PApplet;

/**
 * Created by cansik on 15.05.17.
 */
public class GrayScalePass extends BasePass {
    private static final String PASS_NAME = "grayScaleFrag";

    public GrayScalePass(PApplet sketch) {
        super(sketch, PASS_NAME);
    }
}
