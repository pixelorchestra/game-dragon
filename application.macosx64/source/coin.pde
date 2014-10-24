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
    int myRan=int(random(0, 2));

    animation1 = new Animation("coin3d/2_euro/coin2euro_", 90, myRan, ".gif");

    myValue=2;
  }

  void update( ) {
    compute();
    display();
  }

  void compute() {
    posY+=gravity;
  }
  void display() {
    // fill(#ff6600);
    //  ellipse(posX, posY, 50, 50);
    animation1.display(posX, posY);


    // text(""+myValue, posX, posY-10);
  }





  boolean isDead() {
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
    int myRan=int(random(0, 2));
    myValue=5;

    animation1 = new Animation("coin3d/5_euro/coin5euro_", 90, myRan, ".gif");
  }

  void display() {
    // fill(#ff6600);
    //  ellipse(posX, posY, 50, 50);
    animation1.display(posX, posY);


    // text(""+myValue, posX, posY-10);
  }
}

class euro10 extends Coin {
  Animation animation1;

  euro10() {
    int myRan=int(random(0, 2));
    myValue=10;

    animation1 = new Animation("coin3d/10_euro/coin10euro_", 90, myRan, ".gif");
  }

  void display() {
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
    int myRan=int(random(0, 2));
    myValue=25;

    animation1 = new Animation("coin3d/25_euro/coin25euro_", 90, myRan, ".gif");
    gravity=9;
  }
  
  void compute() {
    posY+=gravity;
  }
  

  void display() {
    // fill(#ff6600);
    //  ellipse(posX, posY, 50, 50);
    animation1.display(posX, posY);


    // text(""+myValue, posX, posY-10);
  }
}
