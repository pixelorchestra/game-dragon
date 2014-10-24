// Class for animating a sequence of GIFs

class Animation {
  PImage[] images;
  int imageCount;
  int frame;
  int offSet;
   String ext;   
  
  Animation(String imagePrefix, int count, int _offSet, String _ext) {
    imageCount = count;
    images = new PImage[imageCount];
    ext=_ext;

    for (int i = 0; i < imageCount; i++) {
      // Use nf() to number format 'i' into five digits
      String filename = imagePrefix + nf(i, 5) + ext;
      images[i] = loadImage(filename);
    }
    offSet=_offSet;
    
  }

  void display(float xpos, float ypos) {
    frame = (frame+1+offSet) % imageCount;
    image(images[frame], xpos, ypos);
  }
  void stopMe(float xpos, float ypos) {
    image(images[frame], xpos, ypos);
  }
  
  int getWidth() {
    return images[0].width;
  }
}
