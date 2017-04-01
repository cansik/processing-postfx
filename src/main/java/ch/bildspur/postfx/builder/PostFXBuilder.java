package ch.bildspur.postfx.builder;

import ch.bildspur.postfx.PostFXSupervisor;
import processing.core.PGraphics;

/**
 * Created by cansik on 01.04.17.
 */
class PostFXBuilder {
    private PostFXSupervisor supervisor;
    private PostFX fx;

    protected PostFXBuilder(PostFX fx, PostFXSupervisor supervisor)
    {
        this.fx = fx;
        this.supervisor = supervisor;
    }

    public void compose(PGraphics graphics)
    {
        supervisor.compose(graphics);
    }


}
