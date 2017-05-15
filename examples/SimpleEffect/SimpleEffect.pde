import ch.bildspur.postfx.builder.*;
import ch.bildspur.postfx.pass.*;
import ch.bildspur.postfx.*;

PostFX fx;

void setup()
{
  size(500, 500, P3D);

  fx = new PostFX(this);  
}

void draw()
{
  // draw a simple rotating cube around a sphere
  background(55);

  pushMatrix();

  translate(width / 2, height / 2);
  rotateX(radians(frameCount % 360));
  rotateZ(radians(frameCount % 360));

  noStroke();
  fill(20, 20, 20);
  box(100);

  fill(150, 255, 255);
  sphere(60);

  popMatrix();

  // add bloom filter
  fx.render()
    .sobel()
    .bloom(0.5, 20, 30)
    .compose();
}