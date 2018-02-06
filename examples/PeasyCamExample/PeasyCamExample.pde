import ch.bildspur.postfx.builder.*;
import ch.bildspur.postfx.pass.*;
import ch.bildspur.postfx.*;
import peasy.*;
import peasy.org.apache.commons.math.*;
import peasy.org.apache.commons.math.geometry.*;

PeasyCam cam;

PostFX fx;
void setup() {
  size(500, 500, P3D);

  cam = new PeasyCam(this, 800);
  fx = new PostFX(this);
}

void draw() {
  background(55);

  pushMatrix();
  rotateY(frameCount/50.0);
  rotateX(frameCount/50.0);
  box(200);
  popMatrix();

  cam.beginHUD();
  fx.render()
    .bloom(0.5, 20, 40)
    .compose();
  cam.endHUD();
}