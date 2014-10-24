
ArrayList<PopUp> popupList;
PopUp popup;

String[] messages = { 
  "BASTANO 50€ AL MESE", 
  "RISPARMIARE SI PUÒ", 
  " PIÚ POTENZIALE PER TE"
};  // 




void setupPopUp() {
  popupList = new ArrayList<PopUp>();
}

void drawPopUp() {


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

  void display() {
    life-=1;
    posX-=.6;

    pushStyle();
    tint(255, life);
    image(bkgPopUp, posX, posY);
    popStyle();

    textAlign(CENTER, TOP);
    textFont(fontPopUp);
    fill(255, life);
    text(messages[myIndex], posX+105, posY);
    text("HAI RAGGIUNTO "+ incomingScore+"€", posX+105, posY+35);
  }


  boolean isDead() {
    if (life < 0.0) {
      return true;
    } else {
      return false;
    }
  }
}
