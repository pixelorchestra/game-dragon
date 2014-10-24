void gameTime() {

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

    if (r < 0.6) {    
      myCoins.add(new Coin()); //   value 2 euro
    } else if (r < 0.7) {
      myCoins.add(new euro5()); // value 5 euro
    } else if (r < 0.9) {
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
