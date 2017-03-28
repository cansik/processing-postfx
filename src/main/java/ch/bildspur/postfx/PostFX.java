package ch.bildspur.postfx;

/**
 * Created by Florian Bruggisser on 21.03.17.
 */
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PGraphics;
import processing.opengl.PShader;

import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PostFX
{
    public final static String VERSION = "1.0";

    private PApplet sketch;

    private final int PASS_NUMBER = 2;

    private int width;
    private int height;
    private int[] resolution;

    private Path shaderPath;

    private int passIndex = -1;

    // shaders
    private PShader brightPassShader;
    private PShader blurShader;
    private PShader sobelShader;
    private PShader toonShader;

    // frameBuffer
    private PGraphics[] passBuffers;

    public PostFX(PApplet sketch)
    {
        this(sketch, sketch.width, sketch.height);
    }

    public PostFX(PApplet sketch, int width, int height)
    {
        this.sketch = sketch;
        this.width = width;
        this.height = height;

        shaderPath = Paths.get(getLibPath(), "shader");

        resolution = new int[] {width, height};

        // init temp pass buffer
        passBuffers = new PGraphics[PASS_NUMBER];
        for (int i = 0; i < passBuffers.length; i++)
        {
            passBuffers[i] = sketch.createGraphics(width, height, PApplet.P2D);
            passBuffers[i].noSmooth();
        }

        // load shaders
        loadShaders();
    }

    private void loadShaders()
    {
        brightPassShader = sketch.loadShader(Paths.get(shaderPath.toString(), "brightPassFrag.glsl").toString());
        blurShader = sketch.loadShader(Paths.get(shaderPath.toString(), "blurFrag.glsl").toString());
        sobelShader = sketch.loadShader(Paths.get(shaderPath.toString(), "sobelFrag.glsl").toString());
        toonShader = sketch.loadShader(Paths.get(shaderPath.toString(), "toonFrag.glsl").toString());
    }

    private void increasePass()
    {
        passIndex = (passIndex + 1) % passBuffers.length;
    }

    private PGraphics getNextPass()
    {
        int nextIndex = (passIndex + 1) % passBuffers.length;
        return passBuffers[nextIndex];
    }

    private PGraphics getCurrentPass()
    {
        return passBuffers[passIndex];
    }

    private void clearPass(PGraphics pass)
    {
        // clear pass buffer
        pass.beginDraw();
        pass.background(0, 0);
        pass.resetShader();
        pass.endDraw();
    }

    public PostFX filter(PGraphics pg)
    {
        PGraphics pass = getNextPass();
        clearPass(pass);

        pass.beginDraw();
        pass.image(pg, 0, 0);
        pass.endDraw();

        increasePass();
        return this;
    }

    public void close(PGraphics result)
    {
        clearPass(result);

        result.beginDraw();
        result.image(getCurrentPass(), 0, 0);
        result.endDraw();
    }

    public PostFX brightPass(float luminanceTreshold)
    {
        PGraphics pass = getNextPass();
        clearPass(pass);

        brightPassShader.set("brightPassThreshold", luminanceTreshold);

        pass.beginDraw();
        pass.shader(brightPassShader);
        pass.image(getCurrentPass(), 0, 0);
        pass.endDraw();

        increasePass();

        return this;
    }

    public PostFX blur(int blurSize, float sigma, boolean horizontal)
    {
        PGraphics pass = getNextPass();
        clearPass(pass);

        blurShader.set("blurSize", blurSize);
        blurShader.set("sigma", sigma);
        blurShader.set("horizontalPass", horizontal ? 1 : 0);

        pass.beginDraw();
        pass.shader(blurShader);
        pass.image(getCurrentPass(), 0, 0);
        pass.endDraw();

        increasePass();

        return this;
    }

    public PostFX sobel()
    {
        PGraphics pass = getNextPass();
        clearPass(pass);

        sobelShader.set("resolution", resolution);

        pass.beginDraw();
        pass.shader(sobelShader);
        pass.image(getCurrentPass(), 0, 0);
        pass.endDraw();

        increasePass();

        return this;
    }

    public PostFX toon()
    {
        PGraphics pass = getNextPass();
        clearPass(pass);

        toonShader.set("resolution", resolution);

        pass.beginDraw();
        pass.shader(toonShader);
        pass.image(getCurrentPass(), 0, 0);
        pass.endDraw();

        increasePass();

        return this;
    }

    private String getLibPath() {
        URL url = this.getClass().getResource("PostFX.class");
        if (url != null) {
            // Convert URL to string, taking care of spaces represented by the "%20"
            // string.
            String path = url.toString().replace("%20", " ");
            int n0 = path.indexOf('/');

            int n1 = -1;


            n1 = path.indexOf("ProcessingPostFX.jar");
            if (PApplet.platform == PConstants.WINDOWS) { //platform Windows
                // In Windows, path string starts with "jar file/C:/..."
                // so the substring up to the first / is removed.
                n0++;
            }


            if ((-1 < n0) && (-1 < n1)) {
                return path.substring(n0, n1);
            } else {
                return sketch.sketchPath();
            }
        }
        return sketch.sketchPath();
    }
}
