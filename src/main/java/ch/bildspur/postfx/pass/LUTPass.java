package ch.bildspur.postfx.pass;

import processing.core.PApplet;

public class LUTPass extends BasePass {
    private static final String PASS_NAME = "lutFrag";

    public LUTPass(PApplet sketch) {
        super(sketch, PASS_NAME);
    }
}
