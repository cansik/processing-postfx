package ch.bildspur.postfx;

import processing.core.PApplet;

/**
 * Created by cansik on 21.03.17.
 */
public class Main {

    public static void main(String... args) {
        Sketch sketch = new Sketch();
        PApplet.runSketch(new String[]{"ch.bildspur.postfx.Sketch "}, sketch);
    }
}
