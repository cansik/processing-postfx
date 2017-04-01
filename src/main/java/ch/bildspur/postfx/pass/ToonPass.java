package ch.bildspur.postfx.pass;

import ch.bildspur.postfx.Supervisor;
import processing.core.PApplet;

/**
 * Created by cansik on 01.04.17.
 */
public class ToonPass extends BasePass {
    private static final String PASS_NAME = "toonFrag";

    public ToonPass(PApplet sketch) {
        super(sketch, PASS_NAME);
    }

    @Override
    public void prepare(Supervisor supervisor) {
        shader.set("resolution", supervisor.getResolution());
    }
}
