import org.junit.Before;
import org.junit.Test;

import model.image.Pixel;
import model.image.RGBPixel;

import static org.junit.Assert.assertEquals;


/**
 * A JUnit test class for {@link RGBPixel}s.
 */
public class RGBPixelTest {

  private Pixel red;
  private Pixel orange;
  private Pixel yellow;
  private Pixel green;
  private Pixel turquoise;
  private Pixel blue;
  private Pixel purple;
  private Pixel pink;
  private Pixel white;
  private Pixel grey;
  private Pixel black;
  private Pixel random;

  @Before
  public void initPixel() {
    red = new RGBPixel(255, 0, 0);
    orange = new RGBPixel(255, 128, 0);
    yellow = new RGBPixel(255, 255, 0);
    green = new RGBPixel(0, 255, 0);
    turquoise = new RGBPixel(0, 255, 255);
    blue = new RGBPixel(0, 0, 255);
    purple = new RGBPixel(127, 0, 255);
    pink = new RGBPixel(255, 192, 203);
    white = new RGBPixel(255, 255, 255);
    grey = new RGBPixel(128, 128, 128);
    black = new RGBPixel(0, 0, 0);
    random = new RGBPixel(45, 173, 89);
  }

  // a test to check to see if the fields of a pixel have been correctly initialized after a
  // valid construction
  @Test
  public void testProperConstruction() {
    assertEquals(255, this.red.getRed());
    assertEquals(0, this.red.getGreen());
    assertEquals(0, this.red.getBlue());
  }

  // checking for an exception when the red component is less than 0
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidConstruction1() {
    Pixel pixel = new RGBPixel(-1000, 100, 100);
  }

  // checking for an exception when the red component is greater than 255
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidConstruction2() {
    Pixel pixel = new RGBPixel(100000, 100, 100);
  }

  // checking for an exception when the green component is less than 0
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidConstruction3() {
    Pixel pixel = new RGBPixel(100, -1000, 100);
  }

  // checking for an exception when the green component is greater than 255
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidConstruction4() {
    Pixel pixel = new RGBPixel(100, 256, 100);
  }

  // checking for an exception when the blue component is less than 0
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidConstruction5() {
    Pixel pixel = new RGBPixel(100, 100, -1000);
  }

  // checking for an exception when the blue component is greater than 255
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidConstruction6() {
    Pixel pixel = new RGBPixel(100, 100, 1000);
  }

  // testing the method getRed on different pixels
  @Test
  public void testGetRed() {
    assertEquals(255, this.red.getRed());
    assertEquals(255, this.orange.getRed());
    assertEquals(255, this.yellow.getRed());
    assertEquals(0, this.green.getRed());
    assertEquals(0, this.turquoise.getRed());
    assertEquals(0, this.blue.getRed());
    assertEquals(127, this.purple.getRed());
    assertEquals(255, this.pink.getRed());
    assertEquals(255, this.white.getRed());
    assertEquals(128, this.grey.getRed());
    assertEquals(0, this.black.getRed());
    assertEquals(45, this.random.getRed());
  }

  // testing the method getGreen on different pixels
  @Test
  public void testGetGreen() {
    assertEquals(0, this.red.getGreen());
    assertEquals(128, this.orange.getGreen());
    assertEquals(255, this.yellow.getGreen());
    assertEquals(255, this.green.getGreen());
    assertEquals(255, this.turquoise.getGreen());
    assertEquals(0, this.blue.getGreen());
    assertEquals(0, this.purple.getGreen());
    assertEquals(192, this.pink.getGreen());
    assertEquals(255, this.white.getGreen());
    assertEquals(128, this.grey.getGreen());
    assertEquals(0, this.black.getGreen());
    assertEquals(173, this.random.getGreen());
  }

  // testing the method getBlue on different pixels
  @Test
  public void testGetBlue() {
    assertEquals(0, this.red.getBlue());
    assertEquals(0, this.orange.getBlue());
    assertEquals(0, this.yellow.getBlue());
    assertEquals(0, this.green.getBlue());
    assertEquals(255, this.turquoise.getBlue());
    assertEquals(255, this.blue.getBlue());
    assertEquals(255, this.purple.getBlue());
    assertEquals(203, this.pink.getBlue());
    assertEquals(255, this.white.getBlue());
    assertEquals(128, this.grey.getBlue());
    assertEquals(0, this.black.getBlue());
    assertEquals(89, this.random.getBlue());
  }
}