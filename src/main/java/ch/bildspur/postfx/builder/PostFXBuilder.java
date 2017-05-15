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
 * PostFX builder pattern.
 */
public class PostFXBuilder {
    private PostFXSupervisor supervisor;
    private PostFX fx;

    private Map<String, Pass> passes;

    PostFXBuilder(PostFX fx, PostFXSupervisor supervisor) {
        this.fx = fx;
        this.supervisor = supervisor;

        passes = new HashMap<>();
    }

    <T extends Pass> T getPass(Class<T> type) {
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

    Map<String, Pass> getPasses() {
        return passes;
    }

    /**
     * Compose and finalize rendering onto sketch texture.
     */
    public void compose() {
        supervisor.compose();
    }

    /**
     * Compose and finalize rendering.
     *
     * @param graphics Texture to render onto.
     */
    public void compose(PGraphics graphics) {
        supervisor.compose(graphics);
    }

    /**
     * Run a custom pass on the texture.
     *
     * @param pass Custom pass to be run.
     * @return Builder object.
     */
    public PostFXBuilder custom(Pass pass) {
        supervisor.pass(pass);
        return this;
    }

    /**
     * Run a blur pass on the texture.
     *
     * @param blurSize Size of the blur.
     * @param sigma    Sigma of the blur.
     * @return Builder object.
     */
    public PostFXBuilder blur(int blurSize, float sigma) {
        blur(blurSize, sigma, false);
        blur(blurSize, sigma, true);
        return this;
    }

    /**
     * Run a blur pass on the texture.
     *
     * @param blurSize   Size of the blur.
     * @param sigma      Sigma of the blur.
     * @param horizontal Indicates if the pass runs horizontal or vertical.
     * @return Builder object.
     */
    public PostFXBuilder blur(int blurSize, float sigma, boolean horizontal) {
        BlurPass pass = getPass(BlurPass.class);

        pass.setBlurSize(blurSize);
        pass.setSigma(sigma);
        pass.setHorizontal(horizontal);

        supervisor.pass(pass);
        return this;
    }

    /**
     * Run a bright pass pass on the texture.
     *
     * @param threshold Threshold of the brightness.
     * @return Builder object.
     */
    public PostFXBuilder brightPass(float threshold) {
        BrightPass pass = getPass(BrightPass.class);

        pass.setThreshold(threshold);

        supervisor.pass(pass);
        return this;
    }

    /**
     * Run a sobel edge detection pass on the texture.
     *
     * @return Builder object.
     */
    public PostFXBuilder sobel() {
        SobelPass pass = getPass(SobelPass.class);
        supervisor.pass(pass);
        return this;
    }

    /**
     * Run a toon pass on the texture.
     *
     * @return Builder object.
     */
    public PostFXBuilder toon() {
        ToonPass pass = getPass(ToonPass.class);
        supervisor.pass(pass);
        return this;
    }

    /**
     * Run an invert pass on the texture.
     *
     * @return Builder object.
     */
    public PostFXBuilder invert() {
        InvertPass pass = getPass(InvertPass.class);
        supervisor.pass(pass);
        return this;
    }

    /**
     * Run a brightness and contrast correction pass on the texture.
     *
     * @param brightness Amount of brightness to add to the picture (default 0.0).
     * @param contrast   Contrast of the image (default 1.0).
     * @return Builder object.
     */
    public PostFXBuilder brightnessContrast(float brightness, float contrast) {
        BrightnessContrastPass pass = getPass(BrightnessContrastPass.class);

        pass.setBrightness(brightness);
        pass.setContrast(contrast);

        supervisor.pass(pass);
        return this;
    }

    /**
     * Run a pixel effect pass on the texture.
     *
     * @param amount Amount of the pixel effect.
     * @return Builder object.
     */
    public PostFXBuilder pixelate(float amount) {
        PixelatePass pass = getPass(PixelatePass.class);

        pass.setAmount(amount);

        supervisor.pass(pass);
        return this;
    }

    /**
     * Run a chromatic aberration effect pass on the texture.
     *
     * @return Builder object.
     */
    public PostFXBuilder chromaticAberration() {
        ChromaticAberrationPass pass = getPass(ChromaticAberrationPass.class);
        supervisor.pass(pass);
        return this;
    }

    /**
     * Run a grayscale pass on the texture.
     *
     * @return Builder object.
     */
    public PostFXBuilder grayScale() {
        GrayScalePass pass = getPass(GrayScalePass.class);
        supervisor.pass(pass);
        return this;
    }

    /**
     * Run a bloom effect on the texture.
     *
     * @param threshold Luminance threshold.
     * @param blurSize  Size of the blur.
     * @param sigma     Sigma of the blur.
     * @return Builder object.
     */
    public PostFXBuilder bloom(float threshold, int blurSize, float sigma) {
        BloomPass pass = getPass(BloomPass.class);

        pass.setThreshold(threshold);
        pass.setBlurSize(blurSize);
        pass.setSigma(sigma);

        supervisor.pass(pass);
        return this;
    }

    /**
     * Run a RGB split pass on the texture.
     *
     * @param delta Delta of the rgb split effect.
     * @return Builder object.
     */
    public PostFXBuilder rgbSplit(float delta) {
        RGBSplitPass pass = getPass(RGBSplitPass.class);

        pass.setDelta(delta);

        supervisor.pass(pass);
        return this;
    }

    /**
     * Run a vignette pass on the texture.
     *
     * @param amount  Amount of the vignette effect.
     * @param falloff Fall off of the vignette effect.
     * @return Builder object.
     */
    public PostFXBuilder vignette(float amount, float falloff) {
        VignettePass pass = getPass(VignettePass.class);

        pass.setAmount(amount);
        pass.setFalloff(falloff);

        supervisor.pass(pass);
        return this;
    }

    /**
     * Run a noise pass on the texture.
     *
     * @param amount Amount of the noise effect.
     * @param speed  Speed of the noise effect.
     * @return Builder object.
     */
    public PostFXBuilder noise(float amount, float speed) {
        NoisePass pass = getPass(NoisePass.class);

        pass.setAmount(amount);
        pass.setSpeed(speed);

        supervisor.pass(pass);
        return this;
    }

    /**
     * Run a denoise pass on the texture.
     *
     * @param exponent Exponent of the denoise effect.
     * @return Builder object.
     */
    public PostFXBuilder denoise(float exponent) {
        DenoisePass pass = getPass(DenoisePass.class);

        pass.setExponent(exponent);

        supervisor.pass(pass);
        return this;
    }

    /**
     * Run a saturation and vibrance correction pass on the texture.
     *
     * @param saturation Amount of saturation to add to the picture (default 0.0).
     * @param vibrance   Vibrance of the colors (default 0.0).
     * @return Builder object.
     */
    public PostFXBuilder saturationVibrance(float saturation, float vibrance) {
        SaturationVibrancePass pass = getPass(SaturationVibrancePass.class);

        pass.setSaturation(saturation);
        pass.setVibrance(vibrance);

        supervisor.pass(pass);
        return this;
    }
}
