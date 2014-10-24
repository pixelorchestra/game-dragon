void drawWelcome() {
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
