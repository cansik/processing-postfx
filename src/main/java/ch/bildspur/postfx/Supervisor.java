package ch.bildspur.postfx;

import ch.bildspur.postfx.pass.Pass;
import processing.core.PApplet;
import processing.core.PGraphics;

/**
 * Created by cansik on 26.03.17.
 */
public interface Supervisor {
    void setResolution(int width, int height);

    int[] getResolution();

    void render(PGraphics graphics);

    void pass(Pass pass);

    void compose(PGraphics graphics);

    PGraphics getNextPass();

    PGraphics getCurrentPass();

    void clearPass(PGraphics pass);
}
