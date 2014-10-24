
void drawResetHiscore() {
    image(bkg, width/2, height/2);

    fill(255,255);
 
 
     textAlign(CENTER,CENTER);
     textFont(fontScore);

 text("VUOI RESETTARE IL RECORD?",width/2,height/2-300);
  

  PVector hpos = handPos();

 buttonResetYes.update(hpos);
 buttonResetNo.update(hpos);
 

 

  noStroke();
  fill(255, 100);
  ellipse(hpos.x, hpos.y, 100, 100);
}
