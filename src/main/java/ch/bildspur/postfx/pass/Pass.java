package ch.bildspur.postfx.pass;

import ch.bildspur.postfx.Supervisor;
import processing.core.PGraphics;

/**
 * Created by cansik on 26.03.17.
 */
public interface Pass {
    void prepare();

    void apply(Supervisor supervisor);
}
