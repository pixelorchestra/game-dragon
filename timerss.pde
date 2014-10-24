// AGAIN  THANKS TO Daniel Shiffman


Timer gameTimer, endTimer, photoTimer, mangiaTimer,endDemoTimer;





class Timer {

  int savedTime; // When Timer started
  int totalTime; // How long Timer should last


  Timer()
  {
  }
  Timer(int tempTotalTime) {
    totalTime = tempTotalTime;
  }

  // Starting the timer
  void start() {
    // When the timer starts it stores the current time in milliseconds.
    savedTime = millis();
  }

  // The function isFinished() returns true if 5,000 ms have passed. 
  // The work of the timer is farmed out to this method.
  boolean isFinished() { 
    // Check how much time has passed
    int passedTime = millis()- savedTime;
    if (passedTime > totalTime) {
      return true;
    } else {
      return false;
    }
  }

  int passedTime() {
    int passedTime = millis()- savedTime;
    int tempme=(totalTime-passedTime)/1000;

    return tempme;
  }
}
