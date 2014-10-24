void states(int goToStat) {


  if (goToStat!=0) {

    state=goToStat;
  }


  switch(state) {
  case 1:

    break;
  case 2:
    setupID();


    break;
  case 3:
  
    break;
  case 4: // GAMETIME
    score=new Score(); // init score make a score!
    setupPopUp(); // init the popups
      myCoins = new ArrayList<Coin>(); // init my coin container 


    gameTimer = new Timer(60*1000);   /// 60 secs for milliseconds
    gameTimer.start();

    break;
  case 5:
    saveScore();
    endTimer=new Timer(6*1000);
    endTimer.start();
    break;
    
  case 6:  
    endDemoTimer=new Timer(15*1000);
    endDemoTimer.start();
    break;
  }
}
