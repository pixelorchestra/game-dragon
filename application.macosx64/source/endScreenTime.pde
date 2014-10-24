boolean showTopScoreGreetings=false;

void drawEnd() {
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

    fill(#ea5025);


    text("CONGRATULAZIONI!!", width/2, height/2);

    text("HA TOTALIZZATO IL NUOVO RECORD!!", width/2, height/2+60);
    text(score.myScore+"€", width/2, height/2+120);
  } else {

    fill(255);

    text("GRAZIE PER AVER GIOCATO", width/2, height/2);

    text("HA TOTALIZZATO "+score.myScore+"€", width/2, height/2+60);
    
    //text(score.myScore+"€", width/2, height/2+120);


    //text("PUNTEGGIO: "+score.myScore+"€", width/2, height/2+180);
  }


  noStroke();
  fill(255, 100);
  ellipse(hpos.x, hpos.y, 100, 100);
}
