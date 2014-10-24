XML xml; //<>//

int topScore;

void setupXml() {

  xml = loadXML("xml/data.xml");  // load data from file
  XML[] children = xml.getChildren("score"); // search for children score

  for (int i = 0; i < children.length; i++) {
    int id =int( children[i].getContent());  // in this case i have only one children



    topScore=id;  // set topscore as the value read from the file (id)
  }
}

void saveScore( ) {

  if (score.myScore>topScore) {  // if the score is grater than topscore
    XML firstChild = xml.getChild("score"); 
    firstChild.setContent(""+score.myScore); // set the firstchild as myscore
    saveXML(xml, "xml/data.xml"); // save the data
    topScore=score.myScore; // update the topscore
    showTopScoreGreetings=true;
  }else{
    showTopScoreGreetings=false;
  }
}


void resetScore( ) {

    XML firstChild = xml.getChild("score"); 
    firstChild.setContent(""+0); // set the firstchild as myscore
    saveXML(xml, "xml/data.xml"); // save the data
    showTopScoreGreetings=false;
    setupXml();

}
