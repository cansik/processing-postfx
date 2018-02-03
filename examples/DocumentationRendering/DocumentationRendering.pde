import ch.bildspur.postfx.builder.*;
import ch.bildspur.postfx.pass.*;
import ch.bildspur.postfx.*;
import java.util.Set;

PostFXSupervisor supervisor;
PImage lenna;
PImage lichtenStein;
PGraphics canvas;
PGraphics buffer;
PGraphics baseBuffer;
HashMap<String, Pass> passes;

final int renderSize = 256;
final int widthCount = 5;
int heightCount;

PFont font;

void setup()
{
  size(1280, 768, P2D);

  supervisor = new PostFXSupervisor(this, renderSize, renderSize);

  // load image to modify
  lenna = loadImage("data/Lenna.png");
  lichtenStein = loadImage("data/Lichtenstein.png");
  font = createFont("Arial Bold", 16);

  // load all passes
  passes = new HashMap<String, Pass>();

  //addPass(new BinaryGlitchPass(this));
  addPass(new BloomPass(this, 0.7, 10, 20));
  addPass(new BlurPass(this, 10, 20, true));
  addPass(new BrightnessContrastPass(this, -0.3, 1.5));
  addPass(new BrightPass(this, 0.6));
  addPass(new ChromaticAberrationPass(this));
  addPass(new DenoisePass(this, 25));
  addPass(new GrayScalePass(this));
  addPass(new InvertPass(this));
  //addPass(new LUTPass(this));
  addPass(new NoisePass(this, 0.1, 10));
  addPass(new PixelatePass(this, 50));
  addPass(new RGBSplitPass(this, 50));
  addPass(new SaturationVibrancePass(this, -0.5, 1.0));
  addPass(new SobelPass(this));
  addPass(new ToonPass(this));
  addPass(new VignettePass(this, 0.8, 0.3));

  println(passes.size());

  heightCount = (int)Math.ceil(passes.size() / (float)widthCount);
  baseBuffer = createGraphics(renderSize, renderSize, P2D);
  buffer = createGraphics(renderSize, renderSize, P2D);
  canvas = createGraphics(renderSize * widthCount, renderSize * heightCount, P2D);

  noLoop();
}

void draw()
{
  background(0);
  image(createExamples(lenna, "lennaRendering"), 0, 0);
  image(createExamples(lichtenStein, "lichtensteinRendering"), 0, 0);
}

PGraphics createExamples(PImage baseImage, String renderName)
{
  canvas.beginDraw();
  canvas.background(0);
  canvas.endDraw();

  baseBuffer.beginDraw();
  baseBuffer.image(baseImage, 0, 0, renderSize, renderSize);
  baseBuffer.endDraw();

  Object[] keys = passes.keySet().toArray();
  int index = 0;
  for (int y = 0; y < heightCount; y++)
  {
    for (int x = 0; x < widthCount; x++)
    {
      String name = (String)keys[index++];
      Pass pass = passes.get(name);

      // render current pass
      supervisor.render(baseBuffer);
      supervisor.pass(pass);
      supervisor.compose(buffer);

      // draw onto canvas
      canvas.beginDraw();
      canvas.image(buffer, x * renderSize, y * renderSize);

      // draw names
      canvas.textFont(font);
      canvas.fill(255);
      canvas.stroke(255);
      canvas.text(name, x * renderSize + 5, y * renderSize + 20);
      canvas.endDraw();
    }
  }

  canvas.save("renderings/" + renderName + ".png");
  canvas.save("renderings/" + renderName + ".jpg");
  return canvas;
}

void addPass(Pass p)
{
  passes.put(p.getClass().getSimpleName(), p);
}