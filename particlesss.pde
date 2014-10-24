// The Nature of Code
// THANKS TO Daniel Shiffman
// http://natureofcode.com





/// CLASS PARTICLE SYSTEM

ArrayList<ParticleSystem> systems;
PImage imgParticle;

void setupParticles() {
  systems = new ArrayList<ParticleSystem>();
  imgParticle = loadImage("particle/texture.png");
}

void drawParticles() {

  // run thru every system for every particle
  pushStyle();
  blendMode(ADD);
  //println("system "+systems.size());

  for (ParticleSystem ps : systems) {
    ps.run();
    // ps.addParticle();
  }
  popStyle();

  // if the system is empty (doesn't contain any particle) remove 
  for (int i = systems.size ()-1; i >= 0; i--) {
    ParticleSystem p = systems.get(i);
    if (p.dead()) {
      systems.remove(i);
    }
  }
}


class ParticleSystem {

  ArrayList<Particle> particles;    // An arraylist for all the particles
  PVector origin;        // An origin point for where particles are birthed

  ParticleSystem(int num, PVector v) {
    particles = new ArrayList<Particle>();   // Initialize the arraylist
    origin = v.get();                        // Store the origin point
    for (int i = 0; i < num; i++) {
      particles.add(new Particle(origin));    // Add "num" amount of particles to the arraylist
    }
  }

  void run() {
    for (int i = particles.size ()-1; i >= 0; i--) {
      Particle p = particles.get(i);
      p.run();
      if (p.isDead()) {
        particles.remove(i);
      }
    }
  }

  void addParticle() {
    particles.add(new Particle(origin));
  }

  // A method to test if the particle system still has particles
  boolean dead() {
    if (particles.isEmpty()) {
      return true;
    } else {
      return false;
    }
  }
}





/// CLASS PARTICLE 

class Particle {
  PVector location;
  PVector velocity;
  PVector acceleration;
  float lifespan;
  float scale;


  Particle(PVector l) {
    acceleration = new PVector(random(-.02, .02), random(-.02, .02));
    velocity = new PVector(random(-1, 1), random(-1, 1));
    location = l.get();
    lifespan = 255.0;
    scale=random(1, 2);
  }

  void run() {
    update();
    display();
  }

  // Method to update location
  void update() {
    velocity.add(acceleration);
    location.add(velocity);
    lifespan -= 3.0;
  }

  // Method to display
  void display() {


    tint(#FC6100, lifespan);
    image(imgParticle, location.x, location.y, imgParticle.width*scale, imgParticle.height*scale);
  }

  // Is the particle still useful?
  boolean isDead() {
    if (lifespan < 0.0) {
      return true;
    } else {
      return false;
    }
  }
}
