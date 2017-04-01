import ch.bildspur.postfx.builder.*;
import ch.bildspur.postfx.pass.*;
import ch.bildspur.postfx.*;

PostFX fx;

PGraphics canvas;

void setup()
{
  size(500, 500, P2D);

  fx = new PostFX(this);  
  canvas = createGraphics(width, height, P3D);
}

void draw()
{
  // draw a simple rotating cube around a sphere
  canvas.beginDraw();
  canvas.background(55);

  canvas.pushMatrix();

  canvas.translate(width / 2, height / 2);
  canvas.rotateX(radians(frameCount % 360));
  canvas.rotateZ(radians(frameCount % 360));

  canvas.noStroke();
  canvas.fill(20, 20, 20);
  canvas.box(100);

  canvas.fill(150, 255, 255);
  canvas.sphere(60);

  canvas.popMatrix();
  canvas.endDraw();
  
  blendMode(BLEND);
  image(canvas, 0, 0);

  // add bloom filter
  blendMode(SCREEN);
  fx.render(canvas)
    .brightPass(0.5)
    .blur(20, 50)
    .compose();
}