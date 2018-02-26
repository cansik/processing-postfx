package ch.bildspur.postfx.dof;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PGraphics;
import processing.opengl.FrameBuffer;
import processing.opengl.PGraphics3D;
import processing.opengl.PShader;

public class DepthOfField implements PConstants {
    private PApplet parent;
    private PShader depthShader;
    private int w, h;

    PGraphics depthCanvas;

    float near = 0f;
    float far = 1000f;

    public DepthOfField(PApplet parent, int w, int h) {
        this.parent = parent;
        this.w = w;
        this.h = h;

        depthShader = parent.loadShader("shader/dof/depthFrag.glsl", "shader/dof/depthVert.glsl");
        depthShader.set("near", near);
        depthShader.set("far", far);

        depthCanvas = parent.createGraphics(w, h, P2D);
        //depthCanvas.shader(depthShader);
    }

    public PGraphics filter(PGraphics graphics) {
        if (!graphics.is3D())
            return graphics;

        PGraphics3D scene = (PGraphics3D) graphics;
        FrameBuffer buffer = scene.getFrameBuffer();

        scene.shader(depthShader);
        scene.flush();
        depthCanvas.beginDraw();
        depthCanvas.image(scene, 0, 0);
        depthCanvas.endDraw();
        scene.resetShader();

        return depthCanvas;
    }
}
