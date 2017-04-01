package ch.bildspur.postfx.pass;

import ch.bildspur.postfx.Supervisor;

/**
 * Created by cansik on 26.03.17.
 */
public interface Pass {
    void prepare(Supervisor supervisor);

    void apply(Supervisor supervisor);
}
