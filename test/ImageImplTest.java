import org.junit.Before;
import org.junit.Test;

import controller.commands.Load;
import model.ImageModel;
import model.ImageModelImpl;
import model.enums.FlipType;
import model.enums.GreyscaleComponentType;
import model.image.Image;
import model.image.ImageImpl;
import model.image.Pixel;
import model.image.RGBPixel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


/**
 * A JUnit test class for {@link ImageImpl}s.
 */
public class ImageImplTest {

  private ImageModel m;
  private Image family;
  private Image square;

  @Before
  public void init() {
    m = new ImageModelImpl();
    new Load("res/square.ppm", "square").execute(m);
    square = m.getImage("square");
  }

  @Test
  public void testValidConstruction() {
    assertEquals(4, square.getWidth());
    assertEquals(4, square.getHeight());
    assertEquals(255, square.getMaxValue());
    assertNotNull(square.getPixels());
    for (int i = 0; i < square.getWidth(); i++) {
      for (int j = 0; j < square.getHeight(); j++) {
        assertNotNull(square.getPixels()[i][j]);
      }
    }
  }

  // testing for an exception when width is not a positive integer
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidConstruction1() {
    Image img = new ImageImpl(0, 100, 255, new RGBPixel[0][100]);
  }

  // testing for an exception when height is not a positive integer
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidConstruction2() {
    Image img = new ImageImpl(100, 0, 255, new RGBPixel[100][0]);
  }

  // testing for an exception when maxValue is not a positive integer
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidConstruction3() {
    Image img = new ImageImpl(100, 100, -10, new RGBPixel[100][100]);
  }

  // testing for an exception when pixels array is null
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidConstruction4() {
    Image img = new ImageImpl(100, 100, 255, null);
  }

  // testing for an exception when pixels array is not of size width by height and pixels are null
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidConstruction5() {
    Image img = new ImageImpl(100, 100, 255, new RGBPixel[1000][45]);
  }

  // testing the method getWidth on images
  @Test
  public void testGetWidth() {
    new Load("res/family/family.ppm", "family").execute(m);
    family = m.getImage("family");
    assertEquals(200, family.getWidth());
    assertEquals(4, square.getWidth());
  }

  // testing the method getHeight on images
  @Test
  public void testGetHeight() {
    new Load("res/family/family.ppm", "family").execute(m);
    family = m.getImage("family");
    assertEquals(200, family.getHeight());
    assertEquals(4, square.getHeight());
  }

  // testing the method getMaxValue
  @Test
  public void testGetMaxValue() {
    new Load("res/family/family.ppm", "family").execute(m);
    family = m.getImage("family");
    assertEquals(255, family.getMaxValue());
    assertEquals(255, square.getMaxValue());
  }


  // getPixels is being tested within other methods, but this is an additional test to make sure
  // the values are being accessed correctly from the ppm file
  @Test
  public void testGetPixels() {
    // testing the rgb values of random pixels on a small image where we created the values and
    // can read them
    assertEquals(0, square.getPixels()[0][0].getRed());
    assertEquals(0, square.getPixels()[0][0].getGreen());
    assertEquals(0, square.getPixels()[0][0].getBlue());

    assertEquals(255, square.getPixels()[0][3].getRed());
    assertEquals(0, square.getPixels()[0][3].getGreen());
    assertEquals(255, square.getPixels()[0][3].getBlue());

    assertEquals(0, square.getPixels()[2][2].getRed());
    assertEquals(15, square.getPixels()[2][2].getGreen());
    assertEquals(175, square.getPixels()[2][2].getBlue());

    assertEquals(0, square.getPixels()[3][2].getRed());
    assertEquals(0, square.getPixels()[3][2].getGreen());
    assertEquals(0, square.getPixels()[3][2].getBlue());

    assertEquals(255, square.getPixels()[3][3].getRed());
    assertEquals(255, square.getPixels()[3][3].getGreen());
    assertEquals(255, square.getPixels()[3][3].getBlue());
  }

  // testing for an exception when the greyscaleComponent method is passed a null type
  @Test(expected = IllegalArgumentException.class)
  public void testFailedGreyscale() {
    square.greyscaleComponent(null);
  }

  // testing for an exception when the flip method is passed a null type
  @Test(expected = IllegalArgumentException.class)
  public void testFailedFlip() {
    square.flip(null);
  }

  // testing for an exception when the brighten method is passed a negative number
  @Test(expected = IllegalArgumentException.class)
  public void testFailedBrighten() {
    square.brighten(-68);
  }

  // testing for an exception when the darken method is passed a negative number
  @Test(expected = IllegalArgumentException.class)
  public void testFailedDarken() {
    square.darken(-999);
  }

  // testing the method brighten
  @Test
  public void testBrightenImage() {
    Image brightSquare = square.brighten(20);
    for (int i = 0; i < square.getWidth(); i++) {
      for (int j = 0; j < square.getHeight(); j++) {
        assertEquals(brightSquare.getPixels()[i][j].getRed(),
                Math.min(square.getPixels()[i][j].getRed() + 20, square.getMaxValue()));
        assertEquals(brightSquare.getPixels()[i][j].getGreen(),
                Math.min(square.getPixels()[i][j].getGreen() + 20, square.getMaxValue()));
        assertEquals(brightSquare.getPixels()[i][j].getBlue(),
                Math.min(square.getPixels()[i][j].getBlue() + 20, square.getMaxValue()));
      }
    }
  }

  // testing the darken method
  @Test
  public void testDarkenImage() {
    Image darkSquare = square.darken(20);

    for (int i = 0; i < square.getWidth(); i++) {
      for (int j = 0; j < square.getHeight(); j++) {
        assertEquals(darkSquare.getPixels()[i][j].getRed(),
                Math.max(square.getPixels()[i][j].getRed() - 20, 0));
        assertEquals(darkSquare.getPixels()[i][j].getGreen(),
                Math.max(square.getPixels()[i][j].getGreen() - 20, 0));
        assertEquals(darkSquare.getPixels()[i][j].getBlue(),
                Math.max(square.getPixels()[i][j].getBlue() - 20, 0));
      }
    }
  }

  // testing the greyscale component method with a red greyscale type
  @Test
  public void testRedComponent() {
    Image redSquare = square.greyscaleComponent(GreyscaleComponentType.RED);

    for (int i = 0; i < square.getWidth(); i++) {
      for (int j = 0; j < square.getHeight(); j++) {
        assertEquals(redSquare.getPixels()[i][j].getRed(),
                square.getPixels()[i][j].getRed());
        assertEquals(redSquare.getPixels()[i][j].getGreen(),
                square.getPixels()[i][j].getRed());
        assertEquals(redSquare.getPixels()[i][j].getBlue(),
                square.getPixels()[i][j].getRed());
      }
    }
  }

  // testing the greyscale component method with a green greyscale type
  @Test
  public void testGreenComponent() {
    Image greenSquare = square.greyscaleComponent(GreyscaleComponentType.GREEN);

    for (int i = 0; i < square.getWidth(); i++) {
      for (int j = 0; j < square.getHeight(); j++) {
        assertEquals(greenSquare.getPixels()[i][j].getRed(),
                square.getPixels()[i][j].getGreen());
        assertEquals(greenSquare.getPixels()[i][j].getGreen(),
                square.getPixels()[i][j].getGreen());
        assertEquals(greenSquare.getPixels()[i][j].getBlue(),
                square.getPixels()[i][j].getGreen());
      }
    }
  }

  // testing the greyscale component method with a blue greyscale type
  @Test
  public void testBlueComponent() {
    Image blueSquare = square.greyscaleComponent(GreyscaleComponentType.BLUE);

    for (int i = 0; i < square.getWidth(); i++) {
      for (int j = 0; j < square.getHeight(); j++) {
        assertEquals(blueSquare.getPixels()[i][j].getRed(),
                square.getPixels()[i][j].getBlue());
        assertEquals(blueSquare.getPixels()[i][j].getGreen(),
                square.getPixels()[i][j].getBlue());
        assertEquals(blueSquare.getPixels()[i][j].getBlue(),
                square.getPixels()[i][j].getBlue());
      }
    }
  }

  // testing the greyscale component method with a value greyscale type
  @Test
  public void testValueComponent() {
    Image valueSquare = square.greyscaleComponent(GreyscaleComponentType.VALUE);

    for (int i = 0; i < square.getWidth(); i++) {
      for (int j = 0; j < square.getHeight(); j++) {

        int tempMax = Math.max(square.getPixels()[i][j].getRed(),
                square.getPixels()[i][j].getGreen());
        int max = Math.max(square.getPixels()[i][j].getBlue(), tempMax);

        assertEquals(max, valueSquare.getPixels()[i][j].getRed());
        assertEquals(max, valueSquare.getPixels()[i][j].getGreen());
        assertEquals(max, valueSquare.getPixels()[i][j].getBlue());
      }
    }
  }

  // testing the greyscale component method with a intensity greyscale type
  @Test
  public void testIntensityComponent() {
    Image intensitySquare = square.greyscaleComponent(GreyscaleComponentType.INTENSITY);

    for (int i = 0; i < square.getWidth(); i++) {
      for (int j = 0; j < square.getHeight(); j++) {

        int color =
                (int) (Math.round(square.getPixels()[i][j].getRed()
                        + square.getPixels()[i][j].getGreen()
                        + square.getPixels()[i][j].getBlue()) / 3.0);

        assertEquals(color, intensitySquare.getPixels()[i][j].getRed());
        assertEquals(color, intensitySquare.getPixels()[i][j].getGreen());
        assertEquals(color, intensitySquare.getPixels()[i][j].getBlue());
      }
    }
  }

  // testing the greyscale component method with a luma greyscale type
  @Test
  public void testLumaComponent() {
    Image lumaSquare = square.greyscaleComponent(GreyscaleComponentType.LUMA);

    for (int i = 0; i < square.getWidth(); i++) {
      for (int j = 0; j < square.getHeight(); j++) {

        int color =
                (int) (Math.round((square.getPixels()[i][j].getRed() * 0.2126) +
                        (square.getPixels()[i][j].getGreen() * 0.7152) +
                        (square.getPixels()[i][j].getBlue() * 0.0722)));

        assertEquals(color, lumaSquare.getPixels()[i][j].getRed());
        assertEquals(color, lumaSquare.getPixels()[i][j].getGreen());
        assertEquals(color, lumaSquare.getPixels()[i][j].getBlue());
      }
    }
  }

  // testing the flip method with a vertical flip type
  @Test
  public void testFlipVertically() {
    Image verticalSquare = square.flip(FlipType.VERTICAL);

    for (int i = 0; i < square.getWidth(); i++) {
      for (int j = 0; j < square.getHeight(); j++) {

        assertEquals(verticalSquare.getPixels()[i][square.getHeight() - 1 - j].getRed(),
                square.getPixels()[i][j].getRed());
        assertEquals(verticalSquare.getPixels()[i][square.getHeight() - 1 - j].getGreen(),
                square.getPixels()[i][j].getGreen());
        assertEquals(verticalSquare.getPixels()[i][square.getHeight() - 1 - j].getBlue(),
                square.getPixels()[i][j].getBlue());
      }
    }
  }

  // testing the flip method with a horizontal flip type
  @Test
  public void testFlipHorizontally() {
    Image horizontalSquare = square.flip(FlipType.HORIZONTAL);

    for (int i = 0; i < square.getWidth(); i++) {
      for (int j = 0; j < square.getHeight(); j++) {

        assertEquals(horizontalSquare.getPixels()[square.getWidth() - 1 - i][j].getRed(),
                square.getPixels()[i][j].getRed());
        assertEquals(horizontalSquare.getPixels()[square.getWidth() - 1 - i][j].getGreen(),
                square.getPixels()[i][j].getGreen());
        assertEquals(horizontalSquare.getPixels()[square.getWidth() - 1 - i][j].getBlue(),
                square.getPixels()[i][j].getBlue());
      }
    }
  }

  // testing the flip method with two successive flips
  @Test
  public void testFlipHorizontallyAndVertically() {
    Image horizontalSquare = square.flip(FlipType.HORIZONTAL);
    Image doubleFlippedSquare = horizontalSquare.flip(FlipType.VERTICAL);

    for (int i = 0; i < square.getWidth(); i++) {
      for (int j = 0; j < square.getHeight(); j++) {

        assertEquals(doubleFlippedSquare
                        .getPixels()
                        [square.getWidth() - 1 - i][square.getHeight() - 1 - j].getRed(),
                square.getPixels()[i][j].getRed());
        assertEquals(doubleFlippedSquare
                        .getPixels()
                        [square.getWidth() - 1 - i][square.getHeight() - 1 - j].getGreen(),
                square.getPixels()[i][j].getGreen());
        assertEquals(doubleFlippedSquare
                        .getPixels()
                        [square.getWidth() - 1 - i][square.getHeight() - 1 - j].getBlue(),
                square.getPixels()[i][j].getBlue());
      }
    }
  }

  // testing the blur method
  @Test
  public void testBlur() {
    double[][] kernel = new double[][]
        {{0.0625, 0.125, 0.0625}, {0.125, 0.25, 0.125}, {0.0625, 0.125, 0.0625}};
    Image blurSquare = square.filter(kernel);

    for (int i = 0; i < square.getWidth(); i++) {
      for (int j = 0; j < square.getHeight(); j++) {

        int red = 0;
        int green = 0;
        int blue = 0;

        Pixel[][] pixels = square.getPixels();
        int matrixSize = kernel.length;

        // same logic as in the implementation to apply a kernel to every pixel
        for (int r = 0; r < matrixSize; r++) {
          for (int c = 0; c < matrixSize; c++) {

            try {
              red += kernel[r][c] * pixels[i + (r - (matrixSize / 2))][j + (c - (matrixSize / 2))]
                      .getRed();
              green += kernel[r][c] * pixels[i + (r - (matrixSize / 2))][j + (c - (matrixSize / 2))]
                      .getGreen();
              blue += kernel[r][c] * pixels[i + (r - (matrixSize / 2))][j + (c - (matrixSize / 2))]
                      .getBlue();
            }
            catch (ArrayIndexOutOfBoundsException e) { // DO NOTHING
            }
          }
        }
        // Using Math.max and Math.min to the RGB constraints
        red = Math.min(Math.max(red, 0), 255);
        green = Math.min(Math.max(green, 0), 255);
        blue = Math.min(Math.max(blue, 0), 255);

        // checking each component of the blurred image with the manually calculated ones
        assertEquals(blurSquare.getPixels()[i][j].getRed(), red);
        assertEquals(blurSquare.getPixels()[i][j].getGreen(), green);
        assertEquals(blurSquare.getPixels()[i][j].getBlue(), blue);
      }
    }
  }

  // testing the sharpen method with a vertical flip type
  @Test
  public void testSharpen() {
    double[][] kernel = new double[][]
        {{-0.125, -0.125, -0.125, -0.125, -0.125},
                {-0.125, 0.25, 0.25, 0.25, -0.125},
                {-0.125, 0.25, 1, 0.25, -0.125},
                {-0.125, 0.25, 0.25, 0.25, -0.125},
                {-0.125, -0.125, -0.125, -0.125, -0.125}};
    Image sharpenSquare = square.filter(kernel);

    for (int i = 0; i < square.getWidth(); i++) {
      for (int j = 0; j < square.getHeight(); j++) {

        int red = 0;
        int green = 0;
        int blue = 0;

        Pixel[][] pixels = square.getPixels();
        int matrixSize = kernel.length;

        // same logic as in the implementation to apply a kernel to every pixel
        for (int r = 0; r < matrixSize; r++) {
          for (int c = 0; c < matrixSize; c++) {

            try {
              red += kernel[r][c] * pixels[i + (r - (matrixSize / 2))][j + (c - (matrixSize / 2))]
                      .getRed();
              green += kernel[r][c] * pixels[i + (r - (matrixSize / 2))][j + (c - (matrixSize / 2))]
                      .getGreen();
              blue += kernel[r][c] * pixels[i + (r - (matrixSize / 2))][j + (c - (matrixSize / 2))]
                      .getBlue();
            }
            catch (ArrayIndexOutOfBoundsException e) { // DO NOTHING
            }
          }
        }
        // Using Math.max and Math.min to the RGB constraints
        red = Math.min(Math.max(red, 0), 255);
        green = Math.min(Math.max(green, 0), 255);
        blue = Math.min(Math.max(blue, 0), 255);

        // checking each component of the sharpened image with the manually calculated ones
        assertEquals(sharpenSquare.getPixels()[i][j].getRed(), red);
        assertEquals(sharpenSquare.getPixels()[i][j].getGreen(), green);
        assertEquals(sharpenSquare.getPixels()[i][j].getBlue(), blue);
      }
    }
  }

  // testing the colorTransformation method with a greyscale matrix passed in
  @Test
  public void testGreyscaleColorTransformation() {
    double[][] matrix = new double[][]
        {{0.2126, 0.7152, 0.0722}, {0.2126, 0.7152, 0.0722}, {0.2126, 0.7152, 0.0722}};
    Image greyscaleSquare = square.colorTransformation(matrix);

    for (int i = 0; i < square.getWidth(); i++) {
      for (int j = 0; j < square.getHeight(); j++) {

        Pixel p = square.getPixels()[i][j];
        int red = (int) (Math.round(p.getRed() * matrix[0][0])
                + (p.getGreen() * matrix[0][1])
                + (p.getBlue() * matrix[0][2]));
        int green = (int) (Math.round(p.getRed() * matrix[1][0])
                + (p.getGreen() * matrix[1][1])
                + (p.getBlue() * matrix[1][2]));
        int blue = (int) (Math.round(p.getRed() * matrix[2][0])
                + (p.getGreen() * matrix[2][1])
                + (p.getBlue() * matrix[2][2]));

        // Using Math.max and Math.min to the RGB constraints
        red = Math.min(Math.max(red, 0), 255);
        green = Math.min(Math.max(green, 0), 255);
        blue = Math.min(Math.max(blue, 0), 255);

        // checking each component of the greyscale image with the manually calculated ones
        assertEquals(red, greyscaleSquare.getPixels()[i][j].getRed());
        assertEquals(green, greyscaleSquare.getPixels()[i][j].getGreen());
        assertEquals(blue, greyscaleSquare.getPixels()[i][j].getBlue());
      }
    }
  }

  // testing the colorTransformation method with a sepia matrix passed in
  @Test
  public void testSepia() {
    double[][] matrix = new double[][]
        {{0.393, 0.769, 0.189}, {0.349, 0.686, 0.168}, {0.272, 0.534, 0.131}};
    Image sepiaSquare = square.colorTransformation(matrix);

    for (int i = 0; i < square.getWidth(); i++) {
      for (int j = 0; j < square.getHeight(); j++) {

        Pixel p = square.getPixels()[i][j];
        int red = (int) (Math.round(p.getRed() * matrix[0][0])
                + (p.getGreen() * matrix[0][1])
                + (p.getBlue() * matrix[0][2]));
        int green = (int) (Math.round(p.getRed() * matrix[1][0])
                + (p.getGreen() * matrix[1][1])
                + (p.getBlue() * matrix[1][2]));
        int blue = (int) (Math.round(p.getRed() * matrix[2][0])
                + (p.getGreen() * matrix[2][1])
                + (p.getBlue() * matrix[2][2]));

        // Using Math.max and Math.min to the RGB constraints
        red = Math.min(Math.max(red, 0), 255);
        green = Math.min(Math.max(green, 0), 255);
        blue = Math.min(Math.max(blue, 0), 255);

        // checking each component of the sepia image with the manually calculated ones
        assertEquals(red, sepiaSquare.getPixels()[i][j].getRed());
        assertEquals(green, sepiaSquare.getPixels()[i][j].getGreen());
        assertEquals(blue, sepiaSquare.getPixels()[i][j].getBlue());
      }
    }
  }

  @Test (expected = IllegalArgumentException.class)
  public void testNullKernelForFilter() {
    square.filter(null);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testNonSquareKernelForFilter() {
    square.filter(new double[3][5]);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testEvenSizedKernelForFilter() {
    square.filter(new double[4][4]);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testNullMatrixForColorTransformation() {
    square.colorTransformation(null);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testInvalidMatrixSizeForColorTransformation() {
    square.colorTransformation(new double[3][4]);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testInvalidMatrixSizeForColorTransformation2() {
    square.colorTransformation(new double[4][3]);
  }
}