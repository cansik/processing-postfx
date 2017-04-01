import ch.bildspur.postfx.builder.*;
import ch.bildspur.postfx.pass.*;
import ch.bildspur.postfx.*;

PostFXSupervisor supervisor;
SobelPass sobelPass;
NegatePass negatePass;

PGraphics canvas;

void setup()
{
  size(500, 500, P2D);

  // create supervisor and load shaders
  supervisor = new PostFXSupervisor(this);
  sobelPass = new SobelPass(this); 
  negatePass = new NegatePass();

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

  // add white ring around sphere
  blendMode(BLEND);
  supervisor.render(canvas);
  supervisor.pass(sobelPass);
  supervisor.pass(negatePass);
  supervisor.compose();
}