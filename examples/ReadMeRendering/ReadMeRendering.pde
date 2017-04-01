import ch.bildspur.postfx.builder.*;
import ch.bildspur.postfx.pass.*;
import ch.bildspur.postfx.*;

PostFXSupervisor supervisor;
SobelPass sobelPass;
NegatePass negatePass;

PGraphics canvas;
PGraphics sobelOutput;
PGraphics toonOutput;
PGraphics bloomOutput;

PostFX fx;

void setup()
{
  size(800, 200, P2D);

  // create supervisor and load shaders
  fx = new PostFX(this, 200, 200);
  supervisor = new PostFXSupervisor(this);

  sobelPass = new SobelPass(this); 
  negatePass = new NegatePass();

  canvas = createGraphics(200, 200, P3D);

  sobelOutput = createGraphics(200, 200, P2D);
  toonOutput = createGraphics(200, 200, P2D);
  bloomOutput = createGraphics(200, 200, P2D);
}

void draw()
{
  // draw a simple rotating cube around a sphere
  canvas.beginDraw();
  canvas.background(55);

  canvas.pushMatrix();

  canvas.translate(200 / 2, 200 / 2);
  canvas.rotateX(radians(frameCount % 360));
  canvas.rotateZ(radians(frameCount % 360));

  canvas.noStroke();
  canvas.fill(20, 20, 20);
  canvas.box(80);

  canvas.fill(150, 255, 255);
  canvas.sphere(50);

  canvas.popMatrix();
  canvas.endDraw();

  // draw original
  image(canvas, 0, 0);

  // create sobel negate
  supervisor.render(canvas);
  supervisor.pass(sobelPass);
  supervisor.pass(negatePass);
  supervisor.compose(sobelOutput);
  image(sobelOutput, 200, 0);

  // create bloom
  image(canvas, 400, 0);

  blendMode(SCREEN);
  fx.render(canvas)
    .brightPass(0.5)
    .blur(20, 50)
    .compose(bloomOutput);
  image(bloomOutput, 400, 0);
  blendMode(BLEND);

  // create toon
  fx.render(canvas)
    .toon()
    .compose(toonOutput);
  image(toonOutput, 600, 0);
  
  saveFrame("render-######.png");
}