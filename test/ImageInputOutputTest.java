import org.junit.Before;
import org.junit.Test;

import controller.inputoutput.BMPImageInputOutput;
import controller.inputoutput.ImageInputOutput;
import controller.inputoutput.JPGImageInputOutput;
import controller.inputoutput.PNGImageInputOutput;
import controller.inputoutput.PPMImageInputOutput;
import model.image.Image;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * A JUnit test class for ImageInputOutputs.
 */
public class ImageInputOutputTest {

  private ImageInputOutput ppmIO;
  private ImageInputOutput jpgIO;
  private ImageInputOutput bmpIO;
  private ImageInputOutput pngIO;
  private Image squarePPM;
  private Image squarePNG;
  private Image squareJPG;
  private Image squareBMP;

  @Before
  public void init() {
    ppmIO = new PPMImageInputOutput();
    jpgIO = new JPGImageInputOutput();
    bmpIO = new BMPImageInputOutput();
    pngIO = new PNGImageInputOutput();
  }

  @Test
  public void testLoadAndSaveFromPPM() {
    // load as ppm
    squarePPM = ppmIO.load("res/squareAllFileTypes/square.ppm");

    // save as png
    pngIO.save("res/squareAllFileTypes/square.png", squarePPM);

    // save as jpg
    jpgIO.save("res/squareAllFileTypes/square.jpg", squarePPM);

    // save as bmp
    bmpIO.save("res/squareAllFileTypes/square.bmp", squarePPM);

    // load the png
    squarePNG = pngIO.load("res/squareAllFileTypes/square.png");

    // load the jpg
    squareJPG = jpgIO.load("res/squareAllFileTypes/square.jpg");

    // load the bmp
    squareBMP = bmpIO.load("res/squareAllFileTypes/square.bmp");


    for (int i = 0; i < squarePPM.getWidth(); i++) {
      for (int j = 0; j < squarePPM.getHeight(); j++) {

        assertEquals(squarePNG.getPixels()[i][j].getRed(), squarePPM.getPixels()[i][j].getRed());
        assertTrue(Math.abs(squareJPG.getPixels()[i][j].getRed()
                - squarePPM.getPixels()[i][j].getRed()) < 40);
        assertEquals(squareBMP.getPixels()[i][j].getRed(), squarePPM.getPixels()[i][j].getRed());

        assertEquals(squarePNG.getPixels()[i][j].getGreen(),
                squarePPM.getPixels()[i][j].getGreen());
        assertTrue(Math.abs(squareJPG.getPixels()[i][j].getGreen()
                - squarePPM.getPixels()[i][j].getGreen()) < 40);
        assertEquals(squareBMP.getPixels()[i][j].getGreen(),
                squarePPM.getPixels()[i][j].getGreen());

        assertEquals(squarePNG.getPixels()[i][j].getBlue(), squarePPM.getPixels()[i][j].getBlue());
        assertTrue(Math.abs(squareJPG.getPixels()[i][j].getGreen()
                - squarePPM.getPixels()[i][j].getGreen()) < 40);
        assertEquals(squareBMP.getPixels()[i][j].getBlue(), squarePPM.getPixels()[i][j].getBlue());

      }
    }
  }

  @Test
  public void testLoadAndSaveFromPNG() {
    // load as png
    squarePPM = pngIO.load("res/squareAllFileTypes/square.png");

    // save as ppm
    ppmIO.save("res/squareAllFileTypes/square.ppm", squarePPM);

    // save as jpg
    jpgIO.save("res/squareAllFileTypes/square.jpg", squarePPM);

    // save as bmp
    bmpIO.save("res/squareAllFileTypes/square.bmp", squarePPM);

    // load the png
    squarePNG = ppmIO.load("res/squareAllFileTypes/square.ppm");

    // load the jpg
    squareJPG = jpgIO.load("res/squareAllFileTypes/square.jpg");

    // load the bmp
    squareBMP = bmpIO.load("res/squareAllFileTypes/square.bmp");


    for (int i = 0; i < squarePPM.getWidth(); i++) {
      for (int j = 0; j < squarePPM.getHeight(); j++) {

        assertEquals(squarePPM.getPixels()[i][j].getRed(), squarePNG.getPixels()[i][j].getRed());
        assertTrue(Math.abs(squareJPG.getPixels()[i][j].getRed()
                - squarePNG.getPixels()[i][j].getRed()) < 40);
        assertEquals(squareBMP.getPixels()[i][j].getRed(), squarePNG.getPixels()[i][j].getRed());

        assertEquals(squarePPM.getPixels()[i][j].getGreen(),
                squarePNG.getPixels()[i][j].getGreen());
        assertTrue(Math.abs(squareJPG.getPixels()[i][j].getGreen()
                - squarePNG.getPixels()[i][j].getGreen()) < 40);
        assertEquals(squareBMP.getPixels()[i][j].getGreen(),
                squarePNG.getPixels()[i][j].getGreen());

        assertEquals(squarePPM.getPixels()[i][j].getBlue(), squarePNG.getPixels()[i][j].getBlue());
        assertTrue(Math.abs(squareJPG.getPixels()[i][j].getBlue()
                - squarePNG.getPixels()[i][j].getBlue()) < 40);
        assertEquals(squareBMP.getPixels()[i][j].getBlue(), squarePNG.getPixels()[i][j].getBlue());

      }
    }
  }

  @Test
  public void testLoadAndSaveFromBMP() {
    // load as png
    squareBMP = bmpIO.load("res/squareAllFileTypes/square.bmp");

    // save as ppm
    ppmIO.save("res/squareAllFileTypes/square.ppm", squareBMP);

    // save as jpg
    jpgIO.save("res/squareAllFileTypes/square.jpg", squareBMP);

    // save as png
    pngIO.save("res/squareAllFileTypes/square.png", squareBMP);

    // load the ppm
    squarePPM = ppmIO.load("res/squareAllFileTypes/square.ppm");

    // load the jpg
    squareJPG = jpgIO.load("res/squareAllFileTypes/square.jpg");

    // load the png
    squarePNG = pngIO.load("res/squareAllFileTypes/square.png");


    for (int i = 0; i < squarePPM.getWidth(); i++) {
      for (int j = 0; j < squarePPM.getHeight(); j++) {

        assertEquals(squarePPM.getPixels()[i][j].getRed(), squareBMP.getPixels()[i][j].getRed());
        assertTrue(Math.abs(squareJPG.getPixels()[i][j].getRed()
                - squareBMP.getPixels()[i][j].getRed()) < 40);
        assertEquals(squarePNG.getPixels()[i][j].getRed(), squareBMP.getPixels()[i][j].getRed());

        assertEquals(squarePPM.getPixels()[i][j].getGreen(),
                squareBMP.getPixels()[i][j].getGreen());
        assertTrue(Math.abs(squareJPG.getPixels()[i][j].getGreen()
                - squareBMP.getPixels()[i][j].getGreen()) < 40);
        assertEquals(squarePNG.getPixels()[i][j].getGreen(),
                squareBMP.getPixels()[i][j].getGreen());

        assertEquals(squarePPM.getPixels()[i][j].getBlue(), squareBMP.getPixels()[i][j].getBlue());
        assertEquals(squarePNG.getPixels()[i][j].getBlue(), squareBMP.getPixels()[i][j].getBlue());

      }
    }
  }

  @Test
  public void testLoadAndSaveFromJPG() {
    // load as jpg
    squareJPG = jpgIO.load("res/squareAllFileTypes/square.jpg");

    // save as ppm
    ppmIO.save("res/squareAllFileTypes/square.ppm", squareJPG);

    // save as bmp
    bmpIO.save("res/squareAllFileTypes/square.bmp", squareJPG);

    // save as png
    pngIO.save("res/squareAllFileTypes/square.png", squareJPG);

    // load the ppm
    squarePPM = ppmIO.load("res/squareAllFileTypes/square.ppm");

    // load the bmp
    squareBMP = bmpIO.load("res/squareAllFileTypes/square.bmp");

    // load the png
    squarePNG = pngIO.load("res/squareAllFileTypes/square.png");


    for (int i = 0; i < squarePPM.getWidth(); i++) {
      for (int j = 0; j < squarePPM.getHeight(); j++) {

        assertEquals(squarePPM.getPixels()[i][j].getRed(), squareJPG.getPixels()[i][j].getRed());
        assertEquals(squarePNG.getPixels()[i][j].getRed(), squareJPG.getPixels()[i][j].getRed());
        assertEquals(squareBMP.getPixels()[i][j].getRed(), squareJPG.getPixels()[i][j].getRed());

        assertEquals(squarePPM.getPixels()[i][j].getGreen(),
                squareJPG.getPixels()[i][j].getGreen());
        assertEquals(squarePNG.getPixels()[i][j].getGreen(),
                squareJPG.getPixels()[i][j].getGreen());
        assertEquals(squareBMP.getPixels()[i][j].getGreen(),
                squareJPG.getPixels()[i][j].getGreen());

        assertEquals(squarePPM.getPixels()[i][j].getBlue(), squareJPG.getPixels()[i][j].getBlue());
        assertEquals(squarePNG.getPixels()[i][j].getBlue(), squareJPG.getPixels()[i][j].getBlue());
        assertEquals(squareBMP.getPixels()[i][j].getBlue(), squareJPG.getPixels()[i][j].getBlue());

      }
    }
  }
}