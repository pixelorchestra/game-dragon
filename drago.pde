Drago drago;

class Drago {
  float x=1000;
  float y=950;
  float easing = 0.1;
  float myMouseY;
  float myMouseX, oldMouseX;
  color myColor;
  int direction;
  Animation animation1, animation2, lastAnimation;
  boolean mangiato;
  PImage mangia_dx, mangia_sx;




  Drago() {
    myColor= color(#ffffff);

    animation1 = new Animation("drago/dx/dx_", 124, 0, ".gif");
    animation2 = new Animation("drago/sx/sx_", 124, 0, ".gif");
    lastAnimation = new Animation("drago/sx/sx_", 124, 0, ".gif");

    mangia_dx = loadImage("drago/mangia_dx_00000.gif");
    mangia_sx = loadImage("drago/mangia_sx_00000.gif");

    oldMouseX=0;

    mangiato=false;
  }


  void update( ) {
    compute();
    move();
    display();
    //  tail();
  }



  void compute() {


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

  void move() {

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

  void display() {
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

  void swapColor() {
    mangiato=true;
  //  println("MANGIATO");
    mangiaTimer=new Timer(500);
    mangiaTimer.start();
  }
}
