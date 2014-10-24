
void drawDemoTime() {
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
  text("IL GIOCO INIZIERÀ TRA "+passed+" SECONDI", width/2, 460);


  noStroke();
  fill(255, 100);
  ellipse(hpos.x, hpos.y, 100, 100);
}
