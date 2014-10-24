Button buttonWelcome, buttonGameTime, buttonScattaFoto, buttonResetYes, buttonResetNo;


void setupButton() {
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



  void display() {


    fill(255, 255);
    ellipse(pos.x, pos.y, 290, 290);
    fill(0);
    if (resetMe) {

      text("SI", pos.x, pos.y);
    } else {
      text("NO", pos.x, pos.y);
    }
  }

  void checkCollide(PVector mousePos) {

    float dist=mousePos.dist(pos);
    if (dist<100) {
      myCountArc+=0.01;

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





  void checkCollide(PVector mousePos) {

    float dist=mousePos.dist(pos);
    if (dist<100) {
      myCountArc+=0.01;

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




  void update(PVector mousePos) {
    display();
    checkCollide(mousePos);
  }


  void display() {
    image(but, pos.x, pos.y);
  }


  void checkCollide(PVector mousePos) {

    float dist=mousePos.dist(pos);
    if (dist<100) {
      myCountArc+=0.01;

      if (myCountArc>1)
      { 
        myCountArc=0;
        states(goToStat);
      }


      pushStyle();
      noFill();
      strokeWeight(12);
      stroke(#ea5025);
      arc(pos.x, pos.y, 291, 291, 0, PI*2*myCountArc);
      popStyle();
    } else {
      myCountArc=0;
    }
  }
}
