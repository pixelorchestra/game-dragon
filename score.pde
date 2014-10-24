Score score;
PFont fontScore, fontPopUp,fontCountTimerPhoto;


void setupFont() {

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

  void update() {
    compute();
    display();
  }
  void compute() {
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


  void updateScore(int coinValue) {
    myScore+=coinValue;
  }


  void display() {
    ///////////////////////// TEXT //////////////////////////////////
    textFont(fontScore);

    textAlign(CENTER, TOP);
    fill(255);
    text("DRAGHETTO", width/2, 30);
    textAlign(LEFT, TOP);

    text("PUNTEGGIO: "+myScore+"€", width-500, 30);
  }
}
