import ch.bildspur.postfx.builder.*;
import ch.bildspur.postfx.pass.*;
import ch.bildspur.postfx.*;

PostFX fx;

Dot[] dots = new Dot[100];

PVector mouse = new PVector(mouseX, mouseY);

void setup()
{
  size(800, 600, P2D);
  smooth(8);

  fx = new PostFX(this);

  // init dots
  for (int i = 0; i < dots.length; i++)
    dots[i] = new Dot();
}

void draw()
{
  background(20);

  // render dots
  for (int i = 0; i < dots.length; i++)
  {
    Dot dot = dots[i];

    dot.update();
    dot.render();
  }

  // apply postfx
  fx.render()
    .bloom(0.2, 20, 40.0)
    .noise(0.05, 0.2)
    .compose();

  // draw fps
  surface.setTitle("Light Scene [FPS: " + String.format("%.2f", frameRate) + "]");
}

void mousePressed()
{
  for (int i = 0; i < dots.length; i++)
    dots[i].target = mouse;
}

void mouseMoved()
{
  mouse.x = mouseX;
  mouse.y = mouseY;
}

void mouseReleased()
{
  for (int i = 0; i < dots.length; i++)
    dots[i].target = null;
}