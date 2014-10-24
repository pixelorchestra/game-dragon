import processing.video.*;




Capture cam;
PImage superDrago;

PImage imageCaptured;





void setupCamera() {


  String[] cameras = Capture.list();

  if (cameras.length == 0) {
    println("There are no cameras available for capture.");
    exit();
  } else {
    println("Available cameras:");
    for (int i = 0; i < cameras.length; i++) {
      println(cameras[i]);
    }
  }


  cam = new Capture(this, width, height);
  cam.start();
  superDrago=loadImage("photo/sfondi_foto.png");
  imageCaptured = createImage(1447, 1080, ARGB);



  //   cam.stop();  /// DELETE THIS!!! IS ONLY FOR LAZY DEVOLPER
}

void DrawCamera() {

  if (cam.available()) {
    cam.read();
  }
  pushMatrix();
  scale(-1, 1);
  image(cam, -width/2, height/2);

  popMatrix();
  image(superDrago, width/2, height/2);


  PVector hpos = handPos();


  buttonGameTime.update(hpos);
  buttonScattaFoto.update(hpos);

  if ( photoTimer!=null) {
    if(hpos.x< 400){
    noStroke();
    fill(255, 100);

    ellipse(hpos.x, hpos.y, 100, 100);
    }
 } else{
  noStroke();
    fill(255, 100);

    ellipse(hpos.x, hpos.y, 100, 100);
 
 
 }
  
    drawID();

  drawTimerPhoto();
}

void keyPressed() {
  if (key == 's' ||  key == 'S') {

    save("saved/myimage_"+timestamp()+".png");
  }
}

void saveImageToHD() {
  imageCaptured=get(473, 0, width-473, height);
 // imageCaptured.save("saved/myimage_"+timestamp()+".png");
  
  imageCaptured.save("saved/"+myID+".png");
  rect(0, 0, width, height);
}



import java.util.Calendar;

String timestamp() {
  Calendar now = Calendar.getInstance();
  return String.format("%1$ty-%1$tm-%1$td_%1$tH.%1$tM.%1$tS", now);
}







void drawTimerPhoto() {


  // go haead with timer and check if is finished

  if (photoTimer!=null) {
    int passed=photoTimer.passedTime();

    if (photoTimer.isFinished())    
    {  
      saveImageToHD();
      photoTimer=null;
      states(demoTime);
      buttonScattaFoto.noRepeatStartCounter=true;

      return;
    }

    //  show the timer
    fill(255,100);

    textAlign(CENTER, CENTER);
   
        textFont(fontCountTimerPhoto);

    text(passed,width/2, height/2);

  }
}




import java.util.Random;
Random r = new Random();
String alphabet = "abcdefghilmnopqrstuvz";
String myID;

void setupID() {
  int myRan=int(random(0, 1000));
  myID= ""+genChar()+genChar()+"-"+myRan;
}

char genChar() {
  return alphabet.charAt(r.nextInt(alphabet.length()));
}


void drawID() {
  fill(255);
 
  textAlign(TOP, LEFT);
     textFont(fontPopUp);

  text(myID, 550, 1030);
 
}
