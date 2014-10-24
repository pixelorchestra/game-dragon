import de.voidplus.leapmotion.*;

LeapMotion leap;
ArrayList<Hand>  myHands; 
PVector hand_position;


void setupLeap() {

  leap = new LeapMotion(this);
  myHands = new ArrayList<Hand>(); // init my coin container 
  hand_position=new PVector(0, 0);
}

PVector handPos() {

  if (leap.hasHands()) {    // if leap detect at least 1 hand
    myHands = leap.getHands();  // get all hands
    Hand p = myHands.get(0);  // p now is my hand
    hand_position    = p.getPosition(); // hand_position is my hand position

      //  ellipse(hand_position.x,hand_position.y,50,50);  // draw ellipse for debug
  } 
  return hand_position;   // return hand position
}

boolean hasHand() {
  if (leap.hasHands()) 
  {
    return true;
  } else {
    return false;
  }
}

void drawLeap() {

  int fps = leap.getFrameRate();


  //println(leap.countHands());

  // HANDS



  for (Hand hand : leap.getHands ()) {
    hand.draw();
    int     hand_id          = hand.getId();
    hand_position    = hand.getPosition();
    PVector hand_stabilized  = hand.getStabilizedPosition();
    PVector hand_direction   = hand.getDirection();
    PVector hand_dynamics    = hand.getDynamics();
    float   hand_roll        = hand.getRoll();
    float   hand_pitch       = hand.getPitch();
    float   hand_yaw         = hand.getYaw();
    float   hand_time        = hand.getTimeVisible();
    PVector sphere_position  = hand.getSpherePosition();
    float   sphere_radius    = hand.getSphereRadius();
    // println(hand.length() );
    // FINGERS
    for (Finger finger : hand.getFingers ()) {

      // Basics
      finger.draw();
      int     finger_id         = finger.getId();
      PVector finger_position   = finger.getPosition();
      PVector finger_stabilized = finger.getStabilizedPosition();
      PVector finger_velocity   = finger.getVelocity();
      PVector finger_direction  = finger.getDirection();
      float   finger_time       = finger.getTimeVisible();

      // Touch Emulation
      int     touch_zone        = finger.getTouchZone();
      float   touch_distance    = finger.getTouchDistance();

      switch(touch_zone) {
      case -1: // None
        break;
      case 0: // Hovering
        // println("Hovering (#"+finger_id+"): "+touch_distance);
        break;
      case 1: // Touching
        // println("Touching (#"+finger_id+")");
        break;
      }
    }

    // TOOLS
    for (Tool tool : hand.getTools ()) {

      // Basics
      tool.draw();
      int     tool_id           = tool.getId();
      PVector tool_position     = tool.getPosition();
      PVector tool_stabilized   = tool.getStabilizedPosition();
      PVector tool_velocity     = tool.getVelocity();
      PVector tool_direction    = tool.getDirection();
      float   tool_time         = tool.getTimeVisible();

      // Touch Emulation
      int     touch_zone        = tool.getTouchZone();
      float   touch_distance    = tool.getTouchDistance();

      switch(touch_zone) {
      case -1: // None
        break;
      case 0: // Hovering
        // println("Hovering (#"+tool_id+"): "+touch_distance);
        break;
      case 1: // Touching
        // println("Touching (#"+tool_id+")");
        break;
      }
    }
  }

  // DEVICES
  // for(Device device : leap.getDevices()){
  //   float device_horizontal_view_angle = device.getHorizontalViewAngle();
  //   float device_verical_view_angle = device.getVerticalViewAngle();
  //   float device_range = device.getRange();
  // }
}

void leapOnInit() {
  // println("Leap Motion Init");
}
void leapOnConnect() {
  // println("Leap Motion Connect");
}
void leapOnFrame() {
  // println("Leap Motion Frame");
}
void leapOnDisconnect() {
  // println("Leap Motion Disconnect");
}
void leapOnExit() {
  // println("Leap Motion Exit");
}
