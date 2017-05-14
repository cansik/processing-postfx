package ch.bildspur.postfx.pass;

import ch.bildspur.postfx.Supervisor;
import processing.core.PApplet;

/**
 * Created by cansik on 14.05.17.
 */
public class PixelatePass extends BasePass {
    private static final String PASS_NAME = "pixelateFrag";

    private float level;

    public PixelatePass(PApplet sketch) {
        this(sketch, 10);
    }

    public PixelatePass(PApplet sketch, int level) {
        super(sketch, PASS_NAME);

        this.level = level;
    }

    @Override
    public void prepare(Supervisor supervisor) {
        shader.set("resolution", supervisor.getResolution());
        shader.set("level", level);
    }

    public float getLevel() {
        return level;
    }

    public void setLevel(float level) {
        this.level = level;
    }
}
