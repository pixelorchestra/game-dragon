import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.*; 
import processing.video.*; 
import java.util.Calendar; 
import java.util.Random; 
import de.voidplus.leapmotion.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class game_v11 extends PApplet {

/**
 game
 */


//  my states 

int state;

int welcomepage=1;
int scattafoto=2;
int resetHighScore=3;
int gameTime=4;
int endScreen=5;
int demoTime=6;



PImage bkg, bkg_welcome, bkg_fine, bkg_pre_game; // my background image





public void setup() {
  size(1920, 1080, P3D); 
  noStroke();  
  frameRate(60);
  noCursor();
  imageMode(CENTER );
  frame.setTitle("GAME");

  drago=new Drago();   // init drago make a drago!
  score=new Score(); // init score make a score!
  myCoins = new ArrayList<Coin>(); // init my coin container 


  state=3;  // let's start with the resetHighScoree 3
 

  bkg=loadImage("sfondo/sfondo.jpg");
  bkg_welcome=loadImage("sfondo/sfondo_welcome.jpg");
    bkg_fine=loadImage("sfondo/sfondo_fine.jpg");
    bkg_pre_game=loadImage("sfondo/sfondo_pre_game.jpg");




  setupParticles(); // setup particle system 
  setupCamera(); // setup and print all avaiable cameras
  setupID();  // generate an unique 
  setupLeap(); // init leap device
  setupButton();
  setupFont();
  setupPopUp();
  setupXml();
}

public void draw() { 
  frame.setTitle(str(frameRate));  // set the title of window as the framerate NEVER GO DOWN 60fps!!! or you'll be erased from the earth surface

  switch(state) {
  case 1:
    drawWelcome();
    break;
  case 2:
    DrawCamera();
    break;
  case 3:
  drawResetHiscore();
    break;
  case 4: // GAMETIME
    gameTime();
    score.update();
    break;
  case 5:
    drawEnd();
    break;
  
  case 6:
    drawDemoTime();
    break;
  }
}
// Class for animating a sequence of GIFs

class Animation {
  PImage[] images;
  int imageCount;
  int frame;
  int offSet;
   String ext;   
  
  Animation(String imagePrefix, int count, int _offSet, String _ext) {
    imageCount = count;
    images = new PImage[imageCount];
    ext=_ext;

    for (int i = 0; i < imageCount; i++) {
      // Use nf() to number format 'i' into five digits
      String filename = imagePrefix + nf(i, 5) + ext;
      images[i] = loadImage(filename);
    }
    offSet=_offSet;
    
  }

  public void display(float xpos, float ypos) {
    frame = (frame+1+offSet) % imageCount;
    image(images[frame], xpos, ypos);
  }
  public void stopMe(float xpos, float ypos) {
    image(images[frame], xpos, ypos);
  }
  
  public int getWidth() {
    return images[0].width;
  }
}





Capture cam;
PImage superDrago;

PImage imageCaptured;





public void setupCamera() {


  String[] cameras = Capture.list();

  if (cameras.length == 0) {
    println("There are no cameras available for capture.");
    exit();
  } else {
    println("Available cameras:");
    for (int i = 0; i < cameras.length; i++) {
      println(cameras[i]);
    }
  }


  cam = new Capture(this, width, height);
  cam.start();
  superDrago=loadImage("photo/sfondi_foto.png");
  imageCaptured = createImage(1447, 1080, ARGB);



  //   cam.stop();  /// DELETE THIS!!! IS ONLY FOR LAZY DEVOLPER
}

public void DrawCamera() {

  if (cam.available()) {
    cam.read();
  }
  pushMatrix();
  scale(-1, 1);
  image(cam, -width/2, height/2);

  popMatrix();
  image(superDrago, width/2, height/2);


  PVector hpos = handPos();


  buttonGameTime.update(hpos);
  buttonScattaFoto.update(hpos);

  if ( photoTimer!=null) {
    if(hpos.x< 400){
    noStroke();
    fill(255, 100);

    ellipse(hpos.x, hpos.y, 100, 100);
    }
 } else{
  noStroke();
    fill(255, 100);

    ellipse(hpos.x, hpos.y, 100, 100);
 
 
 }
  
    drawID();

  drawTimerPhoto();
}

public void keyPressed() {
  if (key == 's' ||  key == 'S') {

    save("saved/myimage_"+timestamp()+".png");
  }
}

public void saveImageToHD() {
  imageCaptured=get(473, 0, width-473, height);
 // imageCaptured.save("saved/myimage_"+timestamp()+".png");
  
  imageCaptured.save("saved/"+myID+".png");
  rect(0, 0, width, height);
}





public String timestamp() {
  Calendar now = Calendar.getInstance();
  return String.format("%1$ty-%1$tm-%1$td_%1$tH.%1$tM.%1$tS", now);
}







public void drawTimerPhoto() {


  // go haead with timer and check if is finished

  if (photoTimer!=null) {
    int passed=photoTimer.passedTime();

    if (photoTimer.isFinished())    
    {  
      saveImageToHD();
      photoTimer=null;
      states(demoTime);
      buttonScattaFoto.noRepeatStartCounter=true;

      return;
    }

    //  show the timer
    fill(255,100);

    textAlign(CENTER, CENTER);
   
        textFont(fontCountTimerPhoto);

    text(passed,width/2, height/2);

  }
}





Random r = new Random();
String alphabet = "abcdefghilmnopqrstuvz";
String myID;

public void setupID() {
  int myRan=PApplet.parseInt(random(0, 1000));
  myID= ""+genChar()+genChar()+"-"+myRan;
}

public char genChar() {
  return alphabet.charAt(r.nextInt(alphabet.length()));
}


public void drawID() {
  fill(255);
 
  textAlign(TOP, LEFT);
     textFont(fontPopUp);

  text(myID, 550, 1030);
 
}


LeapMotion leap;
ArrayList<Hand>  myHands; 
PVector hand_position;


public void setupLeap() {

  leap = new LeapMotion(this);
  myHands = new ArrayList<Hand>(); // init my coin container 
  hand_position=new PVector(0, 0);
}

public PVector handPos() {

  if (leap.hasHands()) {    // if leap detect at least 1 hand
    myHands = leap.getHands();  // get all hands
    Hand p = myHands.get(0);  // p now is my hand
    hand_position    = p.getPosition(); // hand_position is my hand position

      //  ellipse(hand_position.x,hand_position.y,50,50);  // draw ellipse for debug
  } 
  return hand_position;   // return hand position
}

public boolean hasHand() {
  if (leap.hasHands()) 
  {
    return true;
  } else {
    return false;
  }
}

public void drawLeap() {

  int fps = leap.getFrameRate();


  //println(leap.countHands());

  // HANDS



  for (Hand hand : leap.getHands ()) {
    hand.draw();
    int     hand_id          = hand.getId();
    hand_position    = hand.getPosition();
    PVector hand_stabilized  = hand.getStabilizedPosition();
    PVector hand_direction   = hand.getDirection();
    PVector hand_dynamics    = hand.getDynamics();
    float   hand_roll        = hand.getRoll();
    float   hand_pitch       = hand.getPitch();
    float   hand_yaw         = hand.getYaw();
    float   hand_time        = hand.getTimeVisible();
    PVector sphere_position  = hand.getSpherePosition();
    float   sphere_radius    = hand.getSphereRadius();
    // println(hand.length() );
    // FINGERS
    for (Finger finger : hand.getFingers ()) {

      // Basics
      finger.draw();
      int     finger_id         = finger.getId();
      PVector finger_position   = finger.getPosition();
      PVector finger_stabilized = finger.getStabilizedPosition();
      PVector finger_velocity   = finger.getVelocity();
      PVector finger_direction  = finger.getDirection();
      float   finger_time       = finger.getTimeVisible();

      // Touch Emulation
      int     touch_zone        = finger.getTouchZone();
      float   touch_distance    = finger.getTouchDistance();

      switch(touch_zone) {
      case -1: // None
        break;
      case 0: // Hovering
        // println("Hovering (#"+finger_id+"): "+touch_distance);
        break;
      case 1: // Touching
        // println("Touching (#"+finger_id+")");
        break;
      }
    }

    // TOOLS
    for (Tool tool : hand.getTools ()) {

      // Basics
      tool.draw();
      int     tool_id           = tool.getId();
      PVector tool_position     = tool.getPosition();
      PVector tool_stabilized   = tool.getStabilizedPosition();
      PVector tool_velocity     = tool.getVelocity();
      PVector tool_direction    = tool.getDirection();
      float   tool_time         = tool.getTimeVisible();

      // Touch Emulation
      int     touch_zone        = tool.getTouchZone();
      float   touch_distance    = tool.getTouchDistance();

      switch(touch_zone) {
      case -1: // None
        break;
      case 0: // Hovering
        // println("Hovering (#"+tool_id+"): "+touch_distance);
        break;
      case 1: // Touching
        // println("Touching (#"+tool_id+")");
        break;
      }
    }
  }

  // DEVICES
  // for(Device device : leap.getDevices()){
  //   float device_horizontal_view_angle = device.getHorizontalViewAngle();
  //   float device_verical_view_angle = device.getVerticalViewAngle();
  //   float device_range = device.getRange();
  // }
}

public void leapOnInit() {
  // println("Leap Motion Init");
}
public void leapOnConnect() {
  // println("Leap Motion Connect");
}
public void leapOnFrame() {
  // println("Leap Motion Frame");
}
public void leapOnDisconnect() {
  // println("Leap Motion Disconnect");
}
public void leapOnExit() {
  // println("Leap Motion Exit");
}
Button buttonWelcome, buttonGameTime, buttonScattaFoto, buttonResetYes, buttonResetNo;


public void setupButton() {
  buttonResetYes = new buttonReset(new PVector (width/2-200, height/2), true);  // position of button, goto states
  buttonResetNo = new buttonReset(new PVector (width/2+200, height/2), false);  // position of button, goto states

  buttonWelcome = new Button(new PVector (683, 817), scattafoto);  // position of button, goto states
  buttonGameTime = new Button(new PVector (225, 240), demoTime);  // position of button, goto states
  buttonScattaFoto = new ScattaFoto(new PVector (226, 832));  // position of button, goto states
}




class buttonReset extends Button

{

  boolean resetMe;

  buttonReset(PVector _pos, boolean _resetMe) {
    but=loadImage("buttons/button.png");

    pos=_pos;
    myCountArc=0;
    goToStat=0;
    resetMe=_resetMe;
  }



  public void display() {


    fill(255, 255);
    ellipse(pos.x, pos.y, 290, 290);
    fill(0);
    if (resetMe) {

      text("SI", pos.x, pos.y);
    } else {
      text("NO", pos.x, pos.y);
    }
  }

  public void checkCollide(PVector mousePos) {

    float dist=mousePos.dist(pos);
    if (dist<100) {
      myCountArc+=0.01f;

      if (myCountArc>1)
      { 
        myCountArc=0;

        if (resetMe) {
          resetScore();

          states(1);
        } else {
          states(1);
        }
      }


      pushStyle();
      noFill();
      strokeWeight(10);
      stroke(0);
      arc(pos.x, pos.y, 290, 290, 0, PI*2*myCountArc);
      popStyle();
    } else {
      myCountArc=0;
    }
  }
}





class ScattaFoto extends Button

{

  ScattaFoto(PVector _pos) {
    pos=_pos;
    but=loadImage("buttons/button.png");
    myCountArc=0;
    goToStat=0;
    noRepeatStartCounter=true;
  }





  public void checkCollide(PVector mousePos) {

    float dist=mousePos.dist(pos);
    if (dist<100) {
      myCountArc+=0.01f;

      if (myCountArc>1 && noRepeatStartCounter )
      { 
        //myCountArc=0;
        photoTimer=new Timer(5*1000);
        photoTimer.start();
        noRepeatStartCounter=false;
      }


      pushStyle();
      noFill();
      strokeWeight(10);
      stroke(255);
      arc(pos.x, pos.y, 290, 290, 0, PI*2*myCountArc);
      popStyle();
    } else {
      myCountArc=0;
    }
  }
}


class Button {

  PVector pos;
  PImage but;
  float myCountArc;
  int goToStat;
  String trigger;
  boolean noRepeatStartCounter;


  // must declare a default constructor if not you get an error when you extend this

  Button() {
  }

  // constructor to have a new destination status

  Button(PVector _pos, int _goToStat) {
    pos=_pos;
    but=loadImage("buttons/button.png");
    myCountArc=0;
    goToStat=_goToStat;
  }




  public void update(PVector mousePos) {
    display();
    checkCollide(mousePos);
  }


  public void display() {
    image(but, pos.x, pos.y);
  }


  public void checkCollide(PVector mousePos) {

    float dist=mousePos.dist(pos);
    if (dist<100) {
      myCountArc+=0.01f;

      if (myCountArc>1)
      { 
        myCountArc=0;
        states(goToStat);
      }


      pushStyle();
      noFill();
      strokeWeight(12);
      stroke(0xffea5025);
      arc(pos.x, pos.y, 291, 291, 0, PI*2*myCountArc);
      popStyle();
    } else {
      myCountArc=0;
    }
  }
}
ArrayList<Coin> myCoins;




class Coin {
  float posX;
  float posY=-10;
  float gravity;
  PVector pos;
  Animation animation1;
  int myValue;

  Coin() {
    posX=random(10, width-10);
    gravity=random(1, 5);
    pos=new PVector(0, 0);
    int myRan=PApplet.parseInt(random(0, 2));

    animation1 = new Animation("coin3d/2_euro/coin2euro_", 90, myRan, ".gif");

    myValue=2;
  }

  public void update( ) {
    compute();
    display();
  }

  public void compute() {
    posY+=gravity;
  }
  public void display() {
    // fill(#ff6600);
    //  ellipse(posX, posY, 50, 50);
    animation1.display(posX, posY);


    // text(""+myValue, posX, posY-10);
  }





  public boolean isDead() {
    if (posY > height+300) {
      return true;
    } else {
      return false;
    }
  }
}



class euro5 extends Coin {
  Animation animation1;

  euro5() {
    int myRan=PApplet.parseInt(random(0, 2));
    myValue=5;

    animation1 = new Animation("coin3d/5_euro/coin5euro_", 90, myRan, ".gif");
  }

  public void display() {
    // fill(#ff6600);
    //  ellipse(posX, posY, 50, 50);
    animation1.display(posX, posY);


    // text(""+myValue, posX, posY-10);
  }
}

class euro10 extends Coin {
  Animation animation1;

  euro10() {
    int myRan=PApplet.parseInt(random(0, 2));
    myValue=10;

    animation1 = new Animation("coin3d/10_euro/coin10euro_", 90, myRan, ".gif");
  }

  public void display() {
    // fill(#ff6600);
    //  ellipse(posX, posY, 50, 50);
    animation1.display(posX, posY);


    // text(""+myValue, posX, posY-10);
  }
}


class euro25 extends Coin {
  Animation animation1;
  float gravity;


  euro25() {
    int myRan=PApplet.parseInt(random(0, 2));
    myValue=25;

    animation1 = new Animation("coin3d/25_euro/coin25euro_", 90, myRan, ".gif");
    gravity=9;
  }
  
  public void compute() {
    posY+=gravity;
  }
  

  public void display() {
    // fill(#ff6600);
    //  ellipse(posX, posY, 50, 50);
    animation1.display(posX, posY);


    // text(""+myValue, posX, posY-10);
  }
}

public void drawDemoTime() {
  image(bkg_pre_game, width/2, height/2);


  // go haead with timer and check if is finished
  int passed=endDemoTimer.passedTime();
  
  


  if (endDemoTimer.isFinished()) {  
    states(gameTime);

    return;
  }



  PVector hpos = handPos();

  drago.update();
  
  fill(255);
    textFont(fontScore);


  textAlign(CENTER);

  text("MUOVI LA MANO PER SPOSTARE IL DRAGHETTO    ", width/2, 400 );
  text("IL GIOCO INIZIER\u00c0 TRA "+passed+" SECONDI", width/2, 460);


  noStroke();
  fill(255, 100);
  ellipse(hpos.x, hpos.y, 100, 100);
}
Drago drago;

class Drago {
  float x=1000;
  float y=950;
  float easing = 0.1f;
  float myMouseY;
  float myMouseX, oldMouseX;
  int myColor;
  int direction;
  Animation animation1, animation2, lastAnimation;
  boolean mangiato;
  PImage mangia_dx, mangia_sx;




  Drago() {
    myColor= color(0xffffffff);

    animation1 = new Animation("drago/dx/dx_", 124, 0, ".gif");
    animation2 = new Animation("drago/sx/sx_", 124, 0, ".gif");
    lastAnimation = new Animation("drago/sx/sx_", 124, 0, ".gif");

    mangia_dx = loadImage("drago/mangia_dx_00000.gif");
    mangia_sx = loadImage("drago/mangia_sx_00000.gif");

    oldMouseX=0;

    mangiato=false;
  }


  public void update( ) {
    compute();
    move();
    display();
    //  tail();
  }



  public void compute() {


    if (!hasHand()) {
      direction=0;

      myMouseX=1000;
      myMouseY=950;
      return;
    }


    PVector myMouse=handPos();

    myMouseX=map(myMouse.x, 0, 1700, 0, width);

    myMouseY=map(myMouse.y, 0, 1000, 0, height);



    if ((oldMouseX-myMouseX)>1) {
      direction=1;
    }
    if ((oldMouseX-myMouseX)<-1  ) {
      direction=-1;
    }

    if ((oldMouseX-myMouseX)>-1 &&(oldMouseX-myMouseX)<1) {
      direction=0;
    }

    oldMouseX=x;

    if (myMouseY<400) {
      myMouseY=400;
    }
  }

  public void move() {

    float targetX = myMouseX;
    float dx = targetX - x;
    if (abs(dx) > 1) {
      x += dx * easing;
    }

    float targetY = myMouseY;
    float dy = targetY - y;
    if (abs(dy) > 1) {
      y += dy * easing;
    }
  }

  public void display() {
    if (direction==1 ) {
      if (!mangiato) {
        animation2.display(x, y);
      } else {
        image(mangia_sx, x, y);
        if (mangiaTimer.isFinished()) mangiato=false;
      }
      lastAnimation=animation2;
    }
    if (direction==-1 ) {
      if (!mangiato) {
        animation1.display(x, y);
      } else {
        image(mangia_dx, x, y);
        if (mangiaTimer.isFinished()) mangiato=false;
      }
      lastAnimation=animation1;
    }

    if (direction==0 ) {
        lastAnimation.display(x, y);

        

      }
    
  }

  public void swapColor() {
    mangiato=true;
  //  println("MANGIATO");
    mangiaTimer=new Timer(500);
    mangiaTimer.start();
  }
}
boolean showTopScoreGreetings=false;

public void drawEnd() {
  image(bkg_fine, width/2, height/2);

  // go haead with timer and check if is finished
  int passed=endTimer.passedTime();

  if (endTimer.isFinished()) {  
    states(welcomepage);

    return;
  }

  PVector hpos = handPos();

  drago.update();

  textAlign(CENTER, TOP);

  if (showTopScoreGreetings) {

    fill(0xffea5025);


    text("CONGRATULAZIONI!!", width/2, height/2);

    text("HA TOTALIZZATO IL NUOVO RECORD!!", width/2, height/2+60);
    text(score.myScore+"\u20ac", width/2, height/2+120);
  } else {

    fill(255);

    text("GRAZIE PER AVER GIOCATO", width/2, height/2);

    text("HA TOTALIZZATO "+score.myScore+"\u20ac", width/2, height/2+60);
    
    //text(score.myScore+"\u20ac", width/2, height/2+120);


    //text("PUNTEGGIO: "+score.myScore+"\u20ac", width/2, height/2+180);
  }


  noStroke();
  fill(255, 100);
  ellipse(hpos.x, hpos.y, 100, 100);
}
public void gameTime() {

  image(bkg, width/2, height/2);
  //    drawLeap();     // DRAW LEAP DOTS


  // go haead with timer and check if is finished
  int passed=gameTimer.passedTime();

  if (gameTimer.isFinished())    
  {  
    states(endScreen);
    //setupXml();
    return;
  }




  //  show the timer
  fill(255);
  textAlign(LEFT, TOP);
  text("TEMPO: "+passed, 100, 30);




  //  update the coins position and if they are outside remove from the list

  Iterator<Coin> it = myCoins.iterator();
  while (it.hasNext ()) {
    Coin p = it.next();
    p.update();    /// update the coin position

    if (p.isDead()) {

      it.remove();  /// if the coin reach the bottom remove from the list
    }
  }


  //  if my list contains less than 5 coins add another to the screen 


  if (myCoins.size()<5) {

    float r = random(1);

    if (r < 0.6f) {    
      myCoins.add(new Coin()); //   value 2 euro
    } else if (r < 0.7f) {
      myCoins.add(new euro5()); // value 5 euro
    } else if (r < 0.9f) {
      myCoins.add(new euro10()); //value 10 euro
    } else {
      myCoins.add(new euro25()); // value 25 euro
    }
  }


        drago.update();   // update the drago 
        drawParticles();  // draw particles if any
        drawPopUp();      // draw popups if any




  // TO BE REWRITTEN WITH ITERATOR

  // check collision between drago and coins
  PVector posDrago=new PVector(drago.x, drago.y); // drago position

  for (int i = myCoins.size ()-1; i >= 0; i--) {
    Coin p = myCoins.get(i);

    PVector posCoin=new PVector(p.posX, p.posY); // 

    float myDist=posDrago.dist(posCoin);

    if (myDist<66) {
      drago.swapColor();
      score.updateScore(p.myValue); // add coin value to the main score
      systems.add(new ParticleSystem(15, new PVector(posDrago.x, posDrago.y)));
      myCoins.remove(i);
      return;
    }
  }
}
// The Nature of Code
// THANKS TO Daniel Shiffman
// http://natureofcode.com





/// CLASS PARTICLE SYSTEM

ArrayList<ParticleSystem> systems;
PImage imgParticle;

public void setupParticles() {
  systems = new ArrayList<ParticleSystem>();
  imgParticle = loadImage("particle/texture.png");
}

public void drawParticles() {

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

  public void run() {
    for (int i = particles.size ()-1; i >= 0; i--) {
      Particle p = particles.get(i);
      p.run();
      if (p.isDead()) {
        particles.remove(i);
      }
    }
  }

  public void addParticle() {
    particles.add(new Particle(origin));
  }

  // A method to test if the particle system still has particles
  public boolean dead() {
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
    acceleration = new PVector(random(-.02f, .02f), random(-.02f, .02f));
    velocity = new PVector(random(-1, 1), random(-1, 1));
    location = l.get();
    lifespan = 255.0f;
    scale=random(1, 2);
  }

  public void run() {
    update();
    display();
  }

  // Method to update location
  public void update() {
    velocity.add(acceleration);
    location.add(velocity);
    lifespan -= 3.0f;
  }

  // Method to display
  public void display() {


    tint(0xffFC6100, lifespan);
    image(imgParticle, location.x, location.y, imgParticle.width*scale, imgParticle.height*scale);
  }

  // Is the particle still useful?
  public boolean isDead() {
    if (lifespan < 0.0f) {
      return true;
    } else {
      return false;
    }
  }
}

ArrayList<PopUp> popupList;
PopUp popup;

String[] messages = { 
  "BASTANO 50\u20ac AL MESE", 
  "RISPARMIARE SI PU\u00d2", 
  " PI\u00da POTENZIALE PER TE"
};  // 




public void setupPopUp() {
  popupList = new ArrayList<PopUp>();
}

public void drawPopUp() {


  for (PopUp ps : popupList) {
    ps.display();
  }

  // if the system is empty (doesn't contain any particle) remove 
  for (int i = popupList.size ()-1; i >= 0; i--) {
    PopUp p = popupList.get(i);
    if (p.isDead()) {
      popupList.remove(i);
    }
  }
}




class PopUp {
  PImage bkgPopUp;
  float life;
  String msg;
  float posY=200;
  float posX=1700;
  int myIndex;
  int incomingScore;

  PopUp(int _myIndex, int _incomingScore) {
    bkgPopUp=loadImage("messages/popup2.png");
    life=300;
    myIndex = _myIndex;
    incomingScore=_incomingScore;
  }

  public void display() {
    life-=1;
    posX-=.6f;

    pushStyle();
    tint(255, life);
    image(bkgPopUp, posX, posY);
    popStyle();

    textAlign(CENTER, TOP);
    textFont(fontPopUp);
    fill(255, life);
    text(messages[myIndex], posX+105, posY);
    text("HAI RAGGIUNTO "+ incomingScore+"\u20ac", posX+105, posY+35);
  }


  public boolean isDead() {
    if (life < 0.0f) {
      return true;
    } else {
      return false;
    }
  }
}

public void drawResetHiscore() {
    image(bkg, width/2, height/2);

    fill(255,255);
 
 
     textAlign(CENTER,CENTER);
     textFont(fontScore);

 text("VUOI RESETTARE IL RECORD?",width/2,height/2-300);
  

  PVector hpos = handPos();

 buttonResetYes.update(hpos);
 buttonResetNo.update(hpos);
 

 

  noStroke();
  fill(255, 100);
  ellipse(hpos.x, hpos.y, 100, 100);
}
Score score;
PFont fontScore, fontPopUp,fontCountTimerPhoto;


public void setupFont() {

  fontScore = createFont("GrilledCheeseBTNToasted", 55);
  fontPopUp = createFont("GrilledCheeseBTNToasted", 30);
  fontCountTimerPhoto = createFont("GrilledCheeseBTNToasted", 500);

  textFont(fontScore);

  //  textMode(SHAPE);
}

class Score {

  int myScore;
  boolean flag10euro, flag50euro, flag100euro;

  Score() {
    myScore=0;
    flag10euro=true;
    flag50euro=true;
    flag100euro=true;
  }

  public void update() {
    compute();
    display();
  }
  public void compute() {
    if (myScore>50 && myScore<100 && flag10euro) {
      popupList.add(new PopUp(0,myScore));
      flag10euro=false;
    }
    if (myScore>100 && myScore<200 && flag50euro) {
      popupList.add(new PopUp(1,myScore));
      flag50euro=false;
    }
    if (myScore>200 && myScore<250 && flag100euro) {
      popupList.add(new PopUp(2,myScore));
      flag100euro=false;
    }
  }


  public void updateScore(int coinValue) {
    myScore+=coinValue;
  }


  public void display() {
    ///////////////////////// TEXT //////////////////////////////////
    textFont(fontScore);

    textAlign(CENTER, TOP);
    fill(255);
    text("DRAGHETTO", width/2, 30);
    textAlign(LEFT, TOP);

    text("PUNTEGGIO: "+myScore+"\u20ac", width-500, 30);
  }
}
public void states(int goToStat) {


  if (goToStat!=0) {

    state=goToStat;
  }


  switch(state) {
  case 1:

    break;
  case 2:
    setupID();


    break;
  case 3:
  
    break;
  case 4: // GAMETIME
    score=new Score(); // init score make a score!
    setupPopUp(); // init the popups
      myCoins = new ArrayList<Coin>(); // init my coin container 


    gameTimer = new Timer(60*1000);   /// 60 secs for milliseconds
    gameTimer.start();

    break;
  case 5:
    saveScore();
    endTimer=new Timer(6*1000);
    endTimer.start();
    break;
    
  case 6:  
    endDemoTimer=new Timer(15*1000);
    endDemoTimer.start();
    break;
  }
}
// AGAIN  THANKS TO Daniel Shiffman


Timer gameTimer, endTimer, photoTimer, mangiaTimer,endDemoTimer;





class Timer {

  int savedTime; // When Timer started
  int totalTime; // How long Timer should last


  Timer()
  {
  }
  Timer(int tempTotalTime) {
    totalTime = tempTotalTime;
  }

  // Starting the timer
  public void start() {
    // When the timer starts it stores the current time in milliseconds.
    savedTime = millis();
  }

  // The function isFinished() returns true if 5,000 ms have passed. 
  // The work of the timer is farmed out to this method.
  public boolean isFinished() { 
    // Check how much time has passed
    int passedTime = millis()- savedTime;
    if (passedTime > totalTime) {
      return true;
    } else {
      return false;
    }
  }

  public int passedTime() {
    int passedTime = millis()- savedTime;
    int tempme=(totalTime-passedTime)/1000;

    return tempme;
  }
}
public void drawWelcome() {
  image(bkg_welcome, width/2, height/2);


  PVector hpos = handPos();


  buttonWelcome.update(hpos);
  noStroke();
  fill(255, 100);
  ellipse(hpos.x, hpos.y, 100, 100);

  fill(255);
      textFont(fontScore);

  textAlign(TOP, CENTER);
  text("RECORD "+topScore, 1500, 80);
}
XML xml;

int topScore;

public void setupXml() {

  xml = loadXML("xml/data.xml");  // load data from file
  XML[] children = xml.getChildren("score"); // search for children score

  for (int i = 0; i < children.length; i++) {
    int id =PApplet.parseInt( children[i].getContent());  // in this case i have only one children



    topScore=id;  // set topscore as the value read from the file (id)
  }
}

public void saveScore( ) {

  if (score.myScore>topScore) {  // if the score is grater than topscore
    XML firstChild = xml.getChild("score"); 
    firstChild.setContent(""+score.myScore); // set the firstchild as myscore
    saveXML(xml, "xml/data.xml"); // save the data
    topScore=score.myScore; // update the topscore
    showTopScoreGreetings=true;
  }else{
    showTopScoreGreetings=false;
  }
}


public void resetScore( ) {

    XML firstChild = xml.getChild("score"); 
    firstChild.setContent(""+0); // set the firstchild as myscore
    saveXML(xml, "xml/data.xml"); // save the data
    showTopScoreGreetings=false;
    setupXml();

}
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "--full-screen", "--bgcolor=#080000", "--hide-stop", "game_v11" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
