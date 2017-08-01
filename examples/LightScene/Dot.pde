class Dot
{
  PVector location;
  PVector velocity = new PVector();
  PVector acceleration = new PVector();
  PVector target = new PVector();

  float maxforce;    // Maximum steering force
  float maxspeed;    // Maximum speed
  float maxTargetDist;
  float r = 20;

  public Dot()
  {
    location = new PVector(random(0, width), random(0, height));

    float angle = random(TWO_PI);
    velocity = new PVector(cos(angle), sin(angle));

    maxspeed = 2;
    maxforce = 0.03;
    maxTargetDist = 100;
  }

  public void update()
  {
    // Update velocity
    velocity.add(acceleration);
    // Limit speed
    velocity.limit(maxspeed);
    location.add(velocity);

    //find target
    if (target != null && PVector.dist(location, target) < maxTargetDist)
    {
      velocity.add(seek(target));
    }

    // Reset accelertion to 0 each cycle
    acceleration.mult(0);
    borders();
  }

  PVector seek(PVector target) {
    PVector desired = PVector.sub(target, location);
    desired.normalize();
    desired.mult(maxspeed);

    PVector steer = PVector.sub(desired, velocity);
    steer.limit(maxforce); 
    return steer;
  }

  void applyForce(PVector force) {
    acceleration.add(force);
  }

  // Wraparound
  void borders() {
    if (location.x < -r) location.x = width+r;
    if (location.y < -r) location.y = height+r;
    if (location.x > width+r) location.x = -r;
    if (location.y > height+r) location.y = -r;
  }

  public void render()
  {
    pushMatrix();
    translate(location.x, location.y);

    // render dots
    noStroke();
    fill(255, 159, 0, 200);
    ellipse(0, 0, r, r);

    popMatrix();
  }
}