import org.junit.Before;
import org.junit.Test;

import controller.commands.Load;
import controller.commands.Save;
import model.ImageModel;
import model.ImageModelImpl;
import model.image.Image;

import static org.junit.Assert.assertEquals;

/**
 * A JUnit test class for some IO.
 */
public class ImageTestingIO {

  private ImageModel m;
  private Image family;


  @Before
  public void init() {
    this.m = new ImageModelImpl();
    new Load("res/family/family.ppm", "family").execute(m);
    family = m.getImage("family");
  }

  // we are testing an invalid file path by making sure that you can't get an image when that
  // filepath doesn't exist
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidFilePath() {
    new Load("thisPathDoesntExist", "image").execute(m);
    m.getImage("image");
  }

  // testing a valid load by checking the properties of an image loaded, which will ensure that
  // the method read the file's contents correctly
  // *** The image is being loaded in the @Before method on line 18 ***
  @Test
  public void testLoad() {
    assertEquals(200, family.getWidth());
    assertEquals(200, family.getHeight());
    assertEquals(255, family.getMaxValue());
    assertEquals(200, family.getPixels().length);

    for (int i = 0; i < family.getPixels().length; i++) {
      assertEquals(200, family.getPixels()[i].length);
    }
  }

  // testing to ensure that each of the pixels has the correct rgb value in a small image when
  // loaded
  @Test
  public void testLoadPPM() {
    new Load("res/Square.ppm", "squarePPM").execute(m);
    Image squarePPM = m.getImage("squarePPM");

    assertEquals(0, squarePPM.getPixels()[0][0].getRed());
    assertEquals(0, squarePPM.getPixels()[0][0].getGreen());
    assertEquals(0, squarePPM.getPixels()[0][0].getBlue());
    assertEquals(0, squarePPM.getPixels()[0][1].getRed());
    assertEquals(0, squarePPM.getPixels()[0][1].getGreen());
    assertEquals(0, squarePPM.getPixels()[0][1].getBlue());
    assertEquals(0, squarePPM.getPixels()[0][2].getRed());
    assertEquals(0, squarePPM.getPixels()[0][2].getGreen());
    assertEquals(0, squarePPM.getPixels()[0][2].getBlue());
    assertEquals(255, squarePPM.getPixels()[0][3].getRed());
    assertEquals(0, squarePPM.getPixels()[0][3].getGreen());
    assertEquals(255, squarePPM.getPixels()[0][3].getBlue());

    assertEquals(100, squarePPM.getPixels()[1][0].getRed());
    assertEquals(0, squarePPM.getPixels()[1][0].getGreen());
    assertEquals(0, squarePPM.getPixels()[1][0].getBlue());
    assertEquals(0, squarePPM.getPixels()[1][1].getRed());
    assertEquals(255, squarePPM.getPixels()[1][1].getGreen());
    assertEquals(175, squarePPM.getPixels()[1][1].getBlue());
    assertEquals(0, squarePPM.getPixels()[1][2].getRed());
    assertEquals(0, squarePPM.getPixels()[1][2].getGreen());
    assertEquals(0, squarePPM.getPixels()[1][2].getBlue());
    assertEquals(0, squarePPM.getPixels()[1][3].getRed());
    assertEquals(0, squarePPM.getPixels()[1][3].getGreen());
    assertEquals(0, squarePPM.getPixels()[1][3].getBlue());

    assertEquals(0, squarePPM.getPixels()[2][0].getRed());
    assertEquals(0, squarePPM.getPixels()[2][0].getGreen());
    assertEquals(0, squarePPM.getPixels()[2][0].getBlue());
    assertEquals(0, squarePPM.getPixels()[2][1].getRed());
    assertEquals(0, squarePPM.getPixels()[2][1].getGreen());
    assertEquals(0, squarePPM.getPixels()[2][1].getBlue());
    assertEquals(0, squarePPM.getPixels()[2][2].getRed());
    assertEquals(15, squarePPM.getPixels()[2][2].getGreen());
    assertEquals(175, squarePPM.getPixels()[2][2].getBlue());
    assertEquals(0, squarePPM.getPixels()[2][3].getRed());
    assertEquals(0, squarePPM.getPixels()[2][3].getGreen());
    assertEquals(0, squarePPM.getPixels()[2][3].getBlue());

    assertEquals(255, squarePPM.getPixels()[3][0].getRed());
    assertEquals(0, squarePPM.getPixels()[3][0].getGreen());
    assertEquals(255, squarePPM.getPixels()[3][0].getBlue());
    assertEquals(0, squarePPM.getPixels()[3][1].getRed());
    assertEquals(0, squarePPM.getPixels()[3][1].getGreen());
    assertEquals(0, squarePPM.getPixels()[3][1].getBlue());
    assertEquals(0, squarePPM.getPixels()[3][2].getRed());
    assertEquals(0, squarePPM.getPixels()[3][2].getGreen());
    assertEquals(0, squarePPM.getPixels()[3][2].getBlue());
    assertEquals(255, squarePPM.getPixels()[3][3].getRed());
    assertEquals(255, squarePPM.getPixels()[3][3].getGreen());
    assertEquals(255, squarePPM.getPixels()[3][3].getBlue());
  }


  // testing for a failed save when the image name doesn't exist
  @Test(expected = IllegalArgumentException.class)
  public void testFailedSave() {
    new Save("res/image.ppm", "thisDOESNTEexist").execute(m);
    m.getImage("thisDOESNTEexist");
  }

  /*
          THE FOLLOWING CODE BELOW COMMENTED OUT WAS WHAT PRODUCED THE IMAGES IN THE PROVIDED
            RES FOLDER AS SPECIFIED IN THE README AND WILL RESAVE THE IMAGES IF RUN AGAIN
            --------------------------------------------------------------------------------
            These tests are commented out so that they don't override the preexisting images in
            the res folder
   */


  //    @Test
  //    public void testBrighten() {
  //
  //      m.brighten(40, "family", "brightFamily");
  //      new Save("res/family/brightFamily.ppm", "brightFamily").execute(m);
  //    }
  //
  //    @Test
  //    public void testDarken() {
  //
  //      m.darken(40, "family", "darkFamily");
  //      new Save("res/family/darkFamily.ppm", "darkFamily").execute(m);
  //    }
  //
  //    @Test
  //    public void testRedGreyscale() {
  //      m.greyscaleComponent(GreyscaleComponentType.RED, "family", "redFamily");
  //      new Save("res/family/redFamily.ppm", "redFamily").execute(m);
  //    }
  //
  //    @Test
  //    public void testGreenGreyscale() {
  //
  //      m.greyscaleComponent(GreyscaleComponentType.GREEN, "family", "greenFamily");
  //      new Save("res/family/greenFamily.ppm", "greenFamily").execute(m);
  //    }
  //
  //    @Test
  //    public void testBlueGreyscale() {
  //
  //      m.greyscaleComponent(GreyscaleComponentType.BLUE, "family", "blueFamily");
  //      new Save("res/family/blueFamily.ppm", "blueFamily").execute(m);
  //    }
  //
  //    @Test
  //    public void testValueGreyscale() {
  //
  //      m.greyscaleComponent(GreyscaleComponentType.VALUE, "family", "valueFamily");
  //      new Save("res/family/valueFamily.ppm", "valueFamily").execute(m);
  //    }
  //
  //    @Test
  //    public void testIntensityGreyscale() {
  //
  //      m.greyscaleComponent(GreyscaleComponentType.INTENSITY, "family", "intensityFamily");
  //      new Save("res/family/intensityFamily.ppm", "intensityFamily").execute(m);
  //    }
  //
  //    @Test
  //    public void testLumaGreyscale() {
  //
  //      m.greyscaleComponent(GreyscaleComponentType.LUMA, "family", "lumaFamily");
  //      new Save("res/family/lumaFamily.ppm", "lumaFamily").execute(m);
  //    }
  //
  //    @Test
  //    public void testFlipVertically() {
  //
  //      m.flip(FlipType.VERTICAL, "family", "verticalFamily");
  //      new Save("res/family/verticalFamily.ppm", "verticalFamily").execute(m);
  //    }
  //
  //    @Test
  //    public void testFlipHorizontally() {
  //
  //      m.flip(FlipType.HORIZONTAL, "family", "horizontalFamily");
  //      new Save("res/family/horizontalFamily.ppm", "horizontalFamily").execute(m);
  //    }
  //
  //  @Test
  //  public void testBlur() {
  //
  //    m.blur("family", "blurredFamily");
  //    new Save("res/family/blurredFamily.ppm", "blurredFamily").execute(m);
  //  }
  //
  //  @Test
  //  public void testSharpen() {
  //
  //    m.sharpen("family", "sharpenedFamily");
  //    new Save("res/family/sharpenedFamily.ppm", "sharpenedFamily").execute(m);
  //  }
  //
  //  @Test
  //  public void testGreyscaleColorTransformation() {
  //
  //    m.greyscaleColorTransformation("family", "greyscaleFamily");
  //    new Save("res/family/greyscaleFamily.ppm", "greyscaleFamily").execute(m);
  //  }
  //
  //  @Test
  //  public void testSepia() {
  //
  //    m.sepia("family", "sepiaFamily");
  //    new Save("res/family/sepiaFamily.ppm", "sepiaFamily").execute(m);
  //  }
  //
  //  @Test
  //  public void testSaveAsPNG() {
  //    new Save("res/family/family.png", "family").execute(m);
  //  }
  //
  //  @Test
  //  public void testSaveAsBMP() {
  //    new Save("res/family/family.bmp", "family").execute(m);
  //  }
  //
  //  @Test
  //  public void testSaveAsJPG() {
  //    new Save("res/family/family.jpg", "family").execute(m);
  //  }
}
