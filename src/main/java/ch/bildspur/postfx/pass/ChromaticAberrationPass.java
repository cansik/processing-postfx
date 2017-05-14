package ch.bildspur.postfx.pass;

import ch.bildspur.postfx.Supervisor;
import processing.core.PApplet;

/**
 * Created by cansik on 14.05.17.
 */
public class ChromaticAberrationPass extends BasePass {
    private static final String PASS_NAME = "chromaticAberrationFrag";

    public ChromaticAberrationPass(PApplet sketch) {
        super(sketch, PASS_NAME);
    }

    @Override
    public void prepare(Supervisor supervisor) {
        shader.set("resolution", supervisor.getResolution());
    }
}
