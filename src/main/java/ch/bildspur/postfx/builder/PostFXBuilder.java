package ch.bildspur.postfx.builder;

import ch.bildspur.postfx.PostFXSupervisor;
import ch.bildspur.postfx.pass.*;
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

    private Map<String, Pass> passes;

    protected PostFXBuilder(PostFX fx, PostFXSupervisor supervisor) {
        this.fx = fx;
        this.supervisor = supervisor;

        passes = new HashMap<>();
    }

    private <T extends BasePass> T getPass(Class<T> type) {
        if (passes.containsKey(type.getName()))
            return (T) passes.get(type.getName());

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

        // add pass
        passes.put(type.getName(), pass);

        return pass;
    }

    public void compose() {
        supervisor.compose();
    }

    public void compose(PGraphics graphics) {
        supervisor.compose(graphics);
    }

    public PostFXBuilder blur(int blurSize, float sigma) {
        blur(blurSize, sigma, false);
        blur(blurSize, sigma, true);
        return this;
    }

    public PostFXBuilder blur(int blurSize, float sigma, boolean horizontal) {
        BlurPass pass = getPass(BlurPass.class);

        pass.setBlurSize(blurSize);
        pass.setSigma(sigma);
        pass.setHorizontal(horizontal);

        supervisor.pass(pass);
        return this;
    }

    public PostFXBuilder brightPass(float threshold) {
        BrightPass pass = getPass(BrightPass.class);

        pass.setThreshold(threshold);

        supervisor.pass(pass);
        return this;
    }

    public PostFXBuilder sobel() {
        SobelPass pass = getPass(SobelPass.class);
        supervisor.pass(pass);
        return this;
    }

    public PostFXBuilder toon() {
        ToonPass pass = getPass(ToonPass.class);
        supervisor.pass(pass);
        return this;
    }
}
