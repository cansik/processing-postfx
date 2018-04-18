package ch.bildspur.postfx.dof;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PGraphics;
import processing.opengl.PGraphics3D;
import processing.opengl.PShader;

public class DepthOfField implements PConstants {
    private PApplet parent;
    private PShader depthShader;
    private int w, h;

    PGraphics3D depthCanvas;

    float near = 0f;
    float far = 1000f;

    public DepthOfField(PApplet parent, int w, int h) {
        this.parent = parent;
        this.w = w;
        this.h = h;

        depthShader = parent.loadShader("shader/dof/depthFrag.glsl", "shader/dof/depthVert.glsl");
        depthShader.set("near", near);
        depthShader.set("far", far);

        depthCanvas = (PGraphics3D) parent.createGraphics(w, h, P3D);
        //depthCanvas.shader(depthShader);
    }

    public PGraphics filter(PGraphics graphics) {
        if (!graphics.is3D())
            return graphics;

        PGraphics3D scene = (PGraphics3D) graphics;

        //scene.shader(depthShader);
        scene.beginDraw();
        scene.filter(depthShader);
        scene.endDraw();

        depthCanvas.beginDraw();
        //depthCanvas.setMatrix(scene.getMatrix());
        //depthCanvas.image(scene, 0, 0);
        depthCanvas.getFrameBuffer();
        depthCanvas.endDraw();

        return depthCanvas;
    }
}
