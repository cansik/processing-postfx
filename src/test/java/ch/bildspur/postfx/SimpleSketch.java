package ch.bildspur.postfx;

import ch.bildspur.postfx.builder.PostFX;
import ch.bildspur.postfx.builder.PostFXBuilder;
import processing.core.PApplet;
import processing.core.PGraphics;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.TreeSet;

/**
 * Created by cansik on 21.03.17.
 */
public class SimpleSketch extends PApplet {
    PostFX fx;
    PGraphics canvas;

    boolean renderOnCanvas = false;

    public static void main(String... args) {
        SimpleSketch sketch = new SimpleSketch();
        sketch.runSketch();
    }

    public void settings() {
        size(720, 720, P3D);
        pixelDensity(1);
    }

    public void setup() {
        fx = new PostFX(this);

        canvas = createGraphics(width, height, P3D);
    }

    public void draw() {
        background(0);

        if (renderOnCanvas) {
            canvas.beginDraw();
            renderExample(canvas);
            canvas.endDraw();
        } else {
            renderExample(g);
        }

        // add effect
        PostFXBuilder fxb;

        if (renderOnCanvas) {
            fxb = fx.render(this.canvas);
        } else {
            fxb = fx.render();
        }

        fxb = fxb.bloom(0.8f, 30, 50);

        background(0);
        if (renderOnCanvas) {
            fxb.compose(canvas);
            image(canvas, 0, 0);
        } else {
            fxb.compose();
        }

        fill(0, 255, 0);
        text("FPS: " + frameRate, 20, 20);
        text("Rendering: " + ((renderOnCanvas) ? "Canvas" : "Screen"), 20, 50);
    }

    void renderExample(PGraphics graphics) {
        // clear screen
        graphics.background(0);

        graphics.fill(255);
        graphics.ellipse(width / 2, height / 2, width * 0.75f, height * 0.75f);

        // render simple cube
        graphics.pushMatrix();

        graphics.translate(width / 2f, height / 2f);
        graphics.rotateX(radians(frameCount % 360));
        graphics.rotateZ(radians(frameCount % 360));

        graphics.noStroke();
        graphics.fill(20, 20, 20);
        graphics.box(100);

        graphics.fill(150, 255, 255);
        graphics.sphere(60);

        graphics.popMatrix();
    }

    @Override
    public void keyPressed() {
        super.keyPressed();

        renderOnCanvas = !renderOnCanvas;
    }

    public static Collection<Field> getAllFields(Class<?> type) {
        TreeSet<Field> fields = new TreeSet<Field>(
                Comparator.comparing(Field::getName).thenComparing(o -> o.getDeclaringClass().getSimpleName()).thenComparing(o -> o.getDeclaringClass().getName()));
        for (Class<?> c = type; c != null; c = c.getSuperclass()) {
            fields.addAll(Arrays.asList(c.getDeclaredFields()));
        }
        return fields;
    }

    public static void printAllFields(Object obj) {
        for (Field field : getAllFields(obj.getClass())) {
            field.setAccessible(true);
            String name = field.getName();
            Object value = null;
            try {
                value = field.get(obj);
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
            System.out.printf("%s %s.%s = %s;\n", value == null ? " " : "*", field.getDeclaringClass().getSimpleName(), name, value);
        }
    }
}
