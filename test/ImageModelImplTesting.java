import org.junit.Before;
import org.junit.Test;

import controller.commands.Load;
import model.ImageModel;
import model.ImageModelImpl;
import model.enums.FlipType;
import model.enums.GreyscaleComponentType;
import model.image.Image;
import model.image.Pixel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * A JUnit test class for {@link ImageModelImpl}s.
 */
public class ImageModelImplTesting {

  private ImageModel m;
  private Image square;

  /*

    Square is our own 4 by 4 pixel image that we created for easier testing, so we can loop
    through all the pixels to test the effects of the void methods

   */


  // the @Before method here also tests the successful load of an image to the hashmap by
  // being able to get them by their name right after
  @Before
  public void init() {
    this.m = new ImageModelImpl();
    new Load("res/square.ppm", "square").execute(m);
    square = m.getImage("square");
  }

  // ensuring that getImage returns a deep copy such that it is not referencing the deep copy
  // created last time it was called, which shows that a new object is created everytime
  @Test
  public void testGetImage() {
    Image imgCopy = m.getImage("square");
    assertFalse(imgCopy == square);
    for (int i = 0; i < square.getWidth(); i++) {
      for (int j = 0; j < square.getHeight(); j++) {
        assertFalse(imgCopy.getPixels()[i][j] == square.getPixels()[i][j]);
      }
    }
  }

  // making sure that an exception is thrown when we try to get an image which doesn't exist in
  // the hashmap
  @Test (expected = IllegalArgumentException.class)
  public void testFailedGetImage() {
    Image imgCopy = m.getImage("banana");
  }

  // creating another model and then setting the same square image in that model. Then getting it
  // and ensuring that the contents are the same, meaning that it's been set with the correct
  // name and the proper contents.
  @Test
  public void testSetImage() {
    ImageModel m2 = new ImageModelImpl();
    m2.setImage("square2", square);
    Image square2 = m2.getImage("square2");

    for (int i = 0; i < square.getWidth(); i++) {
      for (int j = 0; j < square.getHeight(); j++) {
        assertEquals(square2.getPixels()[i][j].getRed(), square.getPixels()[i][j].getRed());
        assertEquals(square2.getPixels()[i][j].getGreen(), square.getPixels()[i][j].getGreen());
        assertEquals(square2.getPixels()[i][j].getBlue(), square.getPixels()[i][j].getBlue());
      }
    }
  }

  // testing for an exception when the image is null
  @Test (expected = IllegalArgumentException.class)
  public void testFailedSetImage() {
    m.setImage("invalid", null);
  }


  // testing every pixel of a self created 4 by 4 pixel image by comparing it to itself after its
  // been brightened
  @Test
  public void testBrightenImage() {
    m.brighten(20, "square", "brightSquare");
    Image brightSquare = m.getImage("brightSquare");

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

  // testing for a fail when we attempt to brighten with a neg number
  @Test(expected = IllegalArgumentException.class)
  public void testBrightenWithNeg() {
    m.brighten(-20, "square", "brightSquare");
  }


  // testing every pixel of a self created 4 by 4 pixel image by comparing it to itself after its
  // been darkened
  @Test
  public void testDarkenImage() {
    m.darken(20, "square", "darkSquare");
    Image darkSquare = m.getImage("darkSquare");

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

  // testing for a fail when we attempt to darken with a neg number
  @Test(expected = IllegalArgumentException.class)
  public void testDarkenWithNeg() {
    m.brighten(-20, "square", "brightSquare");
  }

  // testing the greyscaleComponent method with the red component type
  @Test
  public void testRedComponent() {
    m.greyscaleComponent(GreyscaleComponentType.RED, "square", "redSquare");
    Image redSquare = m.getImage("redSquare");

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

  // testing the greyscaleComponent method with the green component type
  @Test
  public void testGreenComponent() {
    m.greyscaleComponent(GreyscaleComponentType.GREEN, "square", "greenSquare");
    Image greenSquare = m.getImage("greenSquare");

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

  // testing the greyscaleComponent method with the blue component type
  @Test
  public void testBlueComponent() {
    m.greyscaleComponent(GreyscaleComponentType.BLUE, "square", "blueSquare");
    Image blueSquare = m.getImage("blueSquare");

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

  // testing the greyscaleComponent method with the value component type
  @Test
  public void testValueComponent() {
    m.greyscaleComponent(GreyscaleComponentType.VALUE, "square", "valueSquare");
    Image valueSquare = m.getImage("valueSquare");

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

  // testing the greyscaleComponent method with the intensity component type
  @Test
  public void testIntensityComponent() {
    m.greyscaleComponent(GreyscaleComponentType.INTENSITY, "square", "intensitySquare");
    Image intensitySquare = m.getImage("intensitySquare");

    for (int i = 0; i < square.getWidth(); i++) {
      for (int j = 0; j < square.getHeight(); j++) {

        int color =
                (int) (Math.round(square.getPixels()[i][j].getRed()
                        + square.getPixels()[i][j].getGreen() +
                square.getPixels()[i][j].getBlue()) / 3.0);

        assertEquals(color, intensitySquare.getPixels()[i][j].getRed());
        assertEquals(color, intensitySquare.getPixels()[i][j].getGreen());
        assertEquals(color, intensitySquare.getPixels()[i][j].getBlue());
      }
    }
  }

  // testing the greyscaleComponent method with the luma component type
  @Test
  public void testLumaComponent() {
    m.greyscaleComponent(GreyscaleComponentType.LUMA, "square", "lumaSquare");
    Image lumaSquare = m.getImage("lumaSquare");

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

  // testing for an exception greyscaleComponent method with a null component type
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidGreyscale() {
    m.greyscaleComponent(null, "square", "square2");
  }

  // testing the flip method with the vertical flip type
  @Test
  public void testFlipVertically() {
    m.flip(FlipType.VERTICAL, "square", "verticalSquare");
    Image verticalSquare = m.getImage("verticalSquare");

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

  // testing the flip method with the horizontal flip type
  @Test
  public void testFlipHorizontally() {
    m.flip(FlipType.HORIZONTAL, "square", "horizontalSquare");
    Image horizontalSquare = m.getImage("horizontalSquare");

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

  // testing for an exception with an null flip type
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidFlip() {
    m.flip(null, "square", "square2");
  }

  // testing multiple flips
  @Test
  public void testMultipleFlips() {
    m.flip(FlipType.HORIZONTAL, "square", "horizontalSquare");
    m.flip(FlipType.VERTICAL, "horizontalSquare", "doubleFlippedSquare");
    Image doubleFlippedSquare = m.getImage("doubleFlippedSquare");

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

  // testing the method blur
  @Test
  public void testBlur() {
    m.blur("square", "blurredSquare");
    Image blurredSquare = m.getImage("blurredSquare");
    double[][] kernel = new double[][]
        {{0.0625, 0.125, 0.0625}, {0.125, 0.25, 0.125}, {0.0625, 0.125, 0.0625}};

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
        assertEquals(blurredSquare.getPixels()[i][j].getRed(), red);
        assertEquals(blurredSquare.getPixels()[i][j].getGreen(), green);
        assertEquals(blurredSquare.getPixels()[i][j].getBlue(), blue);
      }
    }
  }

  // testing the method sharpen
  @Test
  public void testSharpen() {
    m.sharpen("square", "sharpenSquare");
    Image sharpenSquare = m.getImage("sharpenSquare");
    double[][] kernel = new double[][]
        {{-0.125, -0.125, -0.125, -0.125, -0.125},
                {-0.125, 0.25, 0.25, 0.25, -0.125},
                {-0.125, 0.25, 1, 0.25, -0.125},
                {-0.125, 0.25, 0.25, 0.25, -0.125},
                {-0.125, -0.125, -0.125, -0.125, -0.125}};

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
        assertEquals(sharpenSquare.getPixels()[i][j].getRed(), red);
        assertEquals(sharpenSquare.getPixels()[i][j].getGreen(), green);
        assertEquals(sharpenSquare.getPixels()[i][j].getBlue(), blue);
      }
    }
  }

  // testing the method greyscaleColorTransformation
  @Test
  public void testGreyscaleColorTransformation() {
    double[][] matrix = new double[][]
        {{0.2126, 0.7152, 0.0722}, {0.2126, 0.7152, 0.0722}, {0.2126, 0.7152, 0.0722}};
    m.greyscaleColorTransformation("square", "greyscaleSquare");
    Image greyscaleSquare = m.getImage("greyscaleSquare");

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

  // testing the method sepia
  @Test
  public void testSepia() {
    double[][] matrix = new double[][]
        {{0.393, 0.769, 0.189}, {0.349, 0.686, 0.168}, {0.272, 0.534, 0.131}};
    m.sepia("square", "sepiaSquare");
    Image sepiaSquare = m.getImage("sepiaSquare");

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
}
