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

import java.util.*;



void setup() {
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

void draw() { 
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
