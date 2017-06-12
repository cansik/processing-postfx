package ch.bildspur.postfx.pass;

import ch.bildspur.postfx.Supervisor;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PGraphics;
import processing.opengl.PShader;

import java.net.URL;
import java.nio.file.Paths;

/**
 * Created by cansik on 27.03.17.
 */
public abstract class BasePass implements Pass {
    PShader shader;
    PApplet sketch;

    public BasePass(PApplet sketch, String passName) {
        this.sketch = sketch;
        shader = sketch.loadShader(Paths.get(getLibPath(), "shader", passName + ".glsl").toString());
    }

    @Override
    public void prepare(Supervisor supervisor) {
    }

    @Override
    public void apply(Supervisor supervisor) {
        PGraphics pass = supervisor.getNextPass();
        supervisor.clearPass(pass);

        pass.beginDraw();
        pass.shader(shader);
        pass.image(supervisor.getCurrentPass(), 0, 0);
        pass.endDraw();
    }

    private String getLibPath() {
        URL url = this.getClass().getResource(BasePass.class.getSimpleName() + ".class");
        if (url != null) {
            // Convert URL to string, taking care of spaces represented by the "%20"
            // string.
            String path = url.toString().replace("%20", " ");

            if (!path.contains(".jar"))
                return sketch.sketchPath();

            int n0 = path.indexOf('/');

            int n1 = -1;

            // read jar file name
            String fullJarPath = BasePass.class.getProtectionDomain()
                    .getCodeSource()
                    .getLocation()
                    .getPath();

            if (PApplet.platform == PConstants.WINDOWS) {
                // remove leading slash in windows path
                fullJarPath = fullJarPath.substring(1);
            }

            String jar = Paths.get(fullJarPath).getFileName().toString();

            n1 = path.indexOf(jar);
            if (PApplet.platform == PConstants.WINDOWS) {
                // remove leading slash in windows path
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

    public PShader getShader() {
        return shader;
    }
}
