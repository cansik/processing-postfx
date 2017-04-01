package ch.bildspur.postfx.builder;

import ch.bildspur.postfx.PostFXSupervisor;
import ch.bildspur.postfx.pass.BasePass;
import ch.bildspur.postfx.pass.BlurPass;
import ch.bildspur.postfx.pass.Pass;
import processing.core.PApplet;
import processing.core.PGraphics;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by cansik on 01.04.17.
 */
public class PostFXBuilder {
    private PostFXSupervisor supervisor;
    private PostFX fx;

    private Map<Class<Pass>, Pass> passes;

    protected PostFXBuilder(PostFX fx, PostFXSupervisor supervisor) {
        this.fx = fx;
        this.supervisor = supervisor;

        passes = new HashMap<>();
    }

    private <T extends BasePass> T getPass(Class<T> type) {
        if (passes.containsKey(type))
            return (T) passes.get(type);

        // initialize class lazy and use constructor zero
        T pass = null;

        try {
            Constructor<?> constructor = type.getConstructor(PApplet.class);
            pass = (T) constructor.newInstance(fx.sketch);
        } catch (NoSuchMethodException
                | IllegalAccessException
                | InstantiationException
                | InvocationTargetException e) {
            e.printStackTrace();
        }

        return pass;
    }

    public void compose(PGraphics graphics) {
        supervisor.compose(graphics);
    }

    public PostFXBuilder blur(int blurSize, float sigma) {
        BlurPass pass = getPass(BlurPass.class);

        pass.setBlurSize(blurSize);
        pass.setSigma(sigma);

        supervisor.pass(pass);
        return this;
    }
}
