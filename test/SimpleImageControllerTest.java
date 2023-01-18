import org.junit.Before;
import org.junit.Test;

import java.io.StringReader;

import controller.text.ImageTextController;
import controller.text.ImageTextControllerImpl;
import model.ImageModel;
import model.ImageModelImpl;
import model.image.Image;
import model.image.Pixel;
import view.text.ImageTextViewImpl;
import view.text.ImageTextView;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * A JUnit test class for {@link ImageTextControllerImpl}s.
 */
public class SimpleImageControllerTest {

  private ImageModel model;
  private Appendable ap;
  private ImageTextView view;
  private Readable rd;
  private ImageTextController corruptViewController;
  private ImageTextController corruptReadableController;


  @Before
  public void init() {
    model = new ImageModelImpl();
    ap = new StringBuilder();
    Appendable corruptAp = new CorruptAppendable();
    view = new ImageTextViewImpl(ap);
    ImageTextView corruptView = new ImageTextViewImpl(corruptAp);
    rd = new StringReader("");
    Readable corruptRd = new CorruptReadable("this is a corrupt readable");
    ImageTextController controller = new ImageTextControllerImpl(model, view, rd);
    corruptViewController = new ImageTextControllerImpl(model, corruptView, rd);
    corruptReadableController = new ImageTextControllerImpl(model, view, corruptRd);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidConstruction1() {
    ImageTextController c = new ImageTextControllerImpl(model, view, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidConstruction2() {
    ImageTextController c = new ImageTextControllerImpl(model, null, rd);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidConstruction3() {
    ImageTextController c = new ImageTextControllerImpl(null, view, rd);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidConstruction4() {
    ImageTextController c = new ImageTextControllerImpl(model, null, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidConstruction5() {
    ImageTextController c = new ImageTextControllerImpl(null, view, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidConstruction6() {
    ImageTextController c = new ImageTextControllerImpl(null, null, rd);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidConstruction7() {
    ImageTextController c = new ImageTextControllerImpl(null, null, null);
  }

  @Test(expected = IllegalStateException.class)
  public void testCorruptReadable() {
    corruptReadableController.start();
  }

  @Test(expected = IllegalStateException.class)
  public void testCorruptViewWithController() {
    Appendable ap = new CorruptAppendable();
    ImageTextView badView = new ImageTextViewImpl(ap);
    Readable rd = new StringReader("hello");
    ImageTextController c = new ImageTextControllerImpl(model, badView, rd);
    corruptViewController.start();
  }

  // see the imagesToTestSaveFunctionality/ folder within the res/ folder to view the saved image
  // in ppm format at the specified local destination
  @Test
  public void testLoadAndSaveThroughController() {
    Readable readable = new StringReader(
            "load res/Square.ppm square \n" +
                    "vertical-flip square verticalSquare \n" +
                    "save res/imagesToTestSaveFunctionality/saveThroughController.ppm " +
                    "verticalSquare");

    ImageTextController c = new ImageTextControllerImpl(model, view, readable);

    // running the controller start method which will create the new image and save it
    c.start();

    Image square = model.getImage("square");
    Image verticalSquare = model.getImage("verticalSquare");

    // ensuring that the image has been changed properly through controller reading input
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

  @Test
  public void testBrighten() {
    Readable readable = new StringReader(
            "load res/Square.ppm square \n" +
                    "brighten 20 square brightSquare");

    ImageTextController c = new ImageTextControllerImpl(model, view, readable);
    // running the controller start method which will create the new image and save it
    c.start();

    Image square = model.getImage("square");
    Image brightSquare = model.getImage("brightSquare");

    // ensuring that the image has been changed properly through controller reading input
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

  @Test
  public void testDarken() {
    Readable readable = new StringReader(
            "load res/Square.ppm square \n" +
                    "darken 20 square darkSquare");

    ImageTextController c = new ImageTextControllerImpl(model, view, readable);
    // running the controller start method which will create the new image and save it
    c.start();

    Image square = model.getImage("square");
    Image darkSquare = model.getImage("darkSquare");

    // ensuring that the image has been changed properly through controller reading input
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

  @Test
  public void testRedGreyscale() {
    Readable readable = new StringReader(
            "load res/Square.ppm square \n " +
                    "red-component square redSquare");

    ImageTextController c = new ImageTextControllerImpl(model, view, readable);
    // running the controller start method which will create the new image and save it
    c.start();

    Image square = model.getImage("square");
    Image redSquare = model.getImage("redSquare");

    // ensuring that the image has been changed properly through controller reading input
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

  @Test
  public void testGreenGreyscale() {
    Readable readable = new StringReader(
            "load res/Square.ppm square \n " +
                    "green-component square greenSquare");

    ImageTextController c = new ImageTextControllerImpl(model, view, readable);
    // running the controller start method which will create the new image and save it
    c.start();

    Image square = model.getImage("square");
    Image greenSquare = model.getImage("greenSquare");

    // ensuring that the image has been changed properly through controller reading input
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

  @Test
  public void testBlueGreyscale() {
    Readable readable = new StringReader(
            "load res/Square.ppm square \n " +
                    "blue-component square blueSquare");

    ImageTextController c = new ImageTextControllerImpl(model, view, readable);
    // running the controller start method which will create the new image and save it
    c.start();

    Image square = model.getImage("square");
    Image blueSquare = model.getImage("blueSquare");

    // ensuring that the image has been changed properly through controller reading input
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

  @Test
  public void testValueComponent() {
    Readable readable = new StringReader(
            "load res/Square.ppm square \n " +
                    "value-component square valueSquare");

    ImageTextController c = new ImageTextControllerImpl(model, view, readable);
    // running the controller start method which will create the new image and save it
    c.start();

    Image square = model.getImage("square");
    Image valueSquare = model.getImage("valueSquare");

    // ensuring that the image has been changed properly through controller reading input
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

  @Test
  public void testIntensityGreyscale() {
    Readable readable = new StringReader(
            "load res/Square.ppm square \n " +
                    "intensity-component square intensitySquare");

    ImageTextController c = new ImageTextControllerImpl(model, view, readable);
    // running the controller start method which will create the new image and save it
    c.start();

    Image square = model.getImage("square");
    Image intensitySquare = model.getImage("intensitySquare");

    // ensuring that the image has been changed properly through controller reading input
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

  @Test
  public void testLumaGreyscale() {
    Readable readable = new StringReader(
            "load res/Square.ppm square \n " +
                    "luma-component square lumaSquare");

    ImageTextController c = new ImageTextControllerImpl(model, view, readable);
    // running the controller start method which will create the new image and save it
    c.start();

    Image square = model.getImage("square");
    Image lumaSquare = model.getImage("lumaSquare");

    // ensuring that the image has been changed properly through controller reading input
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

  @Test
  public void testFlipVertically() {
    Readable readable = new StringReader(
            "load res/Square.ppm square \n " +
                    "vertical-flip square verticalSquare");

    ImageTextController c = new ImageTextControllerImpl(model, view, readable);
    // running the controller start method which will create the new image and save it
    c.start();

    Image square = model.getImage("square");
    Image verticalSquare = model.getImage("verticalSquare");

    // ensuring that the image has been changed properly through controller reading input
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

  @Test
  public void testFlipHorizontally() {
    Readable readable = new StringReader(
            "load res/Square.ppm square \n " +
                    "horizontal-flip square horizontalSquare");

    ImageTextController c = new ImageTextControllerImpl(model, view, readable);
    // running the controller start method which will create the new image and save it
    c.start();

    Image square = model.getImage("square");
    Image horizontalSquare = model.getImage("horizontalSquare");

    // ensuring that the image has been changed properly through controller reading input
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

  @Test
  public void testFlipHorizontallyAndVertically() {
    Readable readable = new StringReader(
            "load res/Square.ppm square \n " +
                    "horizontal-flip square horizontalSquare\n" +
            "vertical-flip horizontalSquare doubleFlippedSquare");

    ImageTextController c = new ImageTextControllerImpl(model, view, readable);
    // running the controller start method which will create the new image and save it
    c.start();

    Image square = model.getImage("square");
    Image doubleFlippedSquare = model.getImage("doubleFlippedSquare");

    // ensuring that the image has been changed properly through controller reading input
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

  @Test
  public void testDoubleFlipped() {
    Readable readable = new StringReader(
            "load res/Square.ppm square \n " +
                    "horizontal-flip square horizontalSquare\n" +
                    "vertical-flip horizontalSquare doubleFlippedSquare \n" +
                    "save res/doubleFlippedSquare.png doubleFlippedSquare \n" +
                    "load res/doubleFlippedSquare.png doubleFlippedSquareAfterSaving");

    ImageTextController c = new ImageTextControllerImpl(model, view, readable);
    // running the controller start method which will create the new image and save it
    c.start();

    Image square = model.getImage("square");
    Image doubleFlippedSquare = model.getImage("doubleFlippedSquare");

    // ensuring that the image has been changed properly through controller reading input
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

    Image doubleFlippedSquareAfterSaving =
            model.getImage("doubleFlippedSquareAfterSaving");

    // ensuring that the image has been changed properly after it was saved to a different file
    // format and then loaded back again
    for (int i = 0; i < square.getWidth(); i++) {
      for (int j = 0; j < square.getHeight(); j++) {

        assertEquals(doubleFlippedSquareAfterSaving
                        .getPixels()
                        [square.getWidth() - 1 - i][square.getHeight() - 1 - j].getRed(),
                square.getPixels()[i][j].getRed());
        assertEquals(doubleFlippedSquareAfterSaving
                        .getPixels()
                        [square.getWidth() - 1 - i][square.getHeight() - 1 - j].getGreen(),
                square.getPixels()[i][j].getGreen());
        assertEquals(doubleFlippedSquareAfterSaving
                        .getPixels()
                        [square.getWidth() - 1 - i][square.getHeight() - 1 - j].getBlue(),
                square.getPixels()[i][j].getBlue());
      }
    }
  }


  @Test
  public void testErrorInputs() {
    Readable readable = new StringReader(
            "load res/Square.ppm square \n " +
                    "brighten NOT-A-NUMBER square brightSquare\n" +
                    "this is gibberish input don't do anything here\n" +
                    "horizontal-flip square horizontalSquare\n" +
                    "vertical-flip horizontalSquare doubleFlippedSquare");

    ImageTextController c = new ImageTextControllerImpl(model, view, readable);
    // running the controller start method which will create the new image and save it
    c.start();

    Image square = model.getImage("square");
    Image doubleFlippedSquare = model.getImage("doubleFlippedSquare");

    // ensuring that the image has been changed properly through controller reading input
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

    assertEquals("Welcome to the image editor application!\n" +
            "Invalid command sequence: brighten NOT-A-NUMBER square brightSquare\n" +
            "Invalid command sequence: this is gibberish input don't do anything here\n",
            ap.toString());
  }

  @Test
  public void testBlur() {
    Readable readable = new StringReader(
            "load res/Square.ppm square \n " +
                    "blur square blurSquare\n");

    ImageTextController controller = new ImageTextControllerImpl(model, view, readable);
    // running the controller start method which will create the new image and save it
    controller.start();

    Image square = model.getImage("square");
    Image blurSquare = model.getImage("blurSquare");
    double[][] kernel = new double[][]
        {{0.0625, 0.125, 0.0625}, {0.125, 0.25, 0.125}, {0.0625, 0.125, 0.0625}};

    // ensuring that the image has been changed properly through controller reading input
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

  @Test
  public void testSharpen() {
    Readable readable = new StringReader(
            "load res/Square.ppm square \n " +
                    "sharpen square sharpenSquare\n");

    ImageTextController controller = new ImageTextControllerImpl(model, view, readable);
    // running the controller start method which will create the new image and save it
    controller.start();

    Image square = model.getImage("square");
    Image sharpenSquare = model.getImage("sharpenSquare");
    double[][] kernel = new double[][]
        {{-0.125, -0.125, -0.125, -0.125, -0.125},
                {-0.125, 0.25, 0.25, 0.25, -0.125},
                {-0.125, 0.25, 1, 0.25, -0.125},
                {-0.125, 0.25, 0.25, 0.25, -0.125},
                {-0.125, -0.125, -0.125, -0.125, -0.125}};

    // ensuring that the image has been changed properly through controller reading input
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

  @Test
  public void testGreyscale() {
    Readable readable = new StringReader(
            "load res/Square.ppm square \n " +
                    "greyscale square greyscaleSquare\n");

    ImageTextController controller = new ImageTextControllerImpl(model, view, readable);
    // running the controller start method which will create the new image and save it
    controller.start();

    Image square = model.getImage("square");
    Image greyscaleSquare = model.getImage("greyscaleSquare");
    double[][] matrix = new double[][]
        {{0.2126, 0.7152, 0.0722}, {0.2126, 0.7152, 0.0722}, {0.2126, 0.7152, 0.0722}};

    // ensuring that the image has been changed properly through controller reading input
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

  @Test
  public void testSepia() {
    Readable readable = new StringReader(
            "load res/Square.ppm square \n " +
                    "sepia square sepiaSquare\n");

    ImageTextController controller = new ImageTextControllerImpl(model, view, readable);
    // running the controller start method which will create the new image and save it
    controller.start();

    Image square = model.getImage("square");
    Image sepiaSquare = model.getImage("sepiaSquare");
    double[][] matrix = new double[][]
        {{0.393, 0.769, 0.189}, {0.349, 0.686, 0.168}, {0.272, 0.534, 0.131}};

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
        assertEquals(red, sepiaSquare.getPixels()[i][j].getRed());
        assertEquals(green, sepiaSquare.getPixels()[i][j].getGreen());
        assertEquals(blue, sepiaSquare.getPixels()[i][j].getBlue());
      }
    }
  }

  // FOR THIS TEST, SAVING TO A JPG FILE IS A LOSSY COMPRESSION METHOD SO THUS, WE HAD TO TEST
  // THE COMPONENT VALUES WITHIN A RANGE WHEN COMPARING A JPG IMAGE TO A PPM IMAGE
  // testing the loading and saving commands from a ppm file to other file formats
  @Test
  public void testLoadAndSaveFromPPM() {
    Readable readable = new StringReader(
            // loading in a ppm
            "load res/squareAllFileTypes/square.ppm squarePPM \n " +

                    // saving the ppm as the other 3 file types
                    "save res/squareAllFileTypes/square.png squarePPM \n" +
                    "save res/squareAllFileTypes/square.bmp squarePPM \n" +
                    "save res/squareAllFileTypes/square.jpg squarePPM \n" +

                    // loading those 3 saved images back in so that we can compare the image data
                    "load res/squareAllFileTypes/square.png squarePNG \n " +
                    "load res/squareAllFileTypes/square.bmp squareBMP \n " +
                    "load res/squareAllFileTypes/square.jpg squareJPG \n ");

    // running the controller start method which will create the new images and save them, and
    // load them back in
    ImageTextController controller = new ImageTextControllerImpl(model, view, readable);
    controller.start();

    // obtaining the images from the model
    Image squarePNG = model.getImage("squarePNG");
    Image squarePPM = model.getImage("squarePPM");
    Image squareJPG = model.getImage("squareJPG");
    Image squareBMP = model.getImage("squareBMP");

    // comparing the components of each pixel between all the images
    for (int i = 0; i < squarePPM.getWidth(); i++) {
      for (int j = 0; j < squarePPM.getHeight(); j++) {

        assertEquals(squarePNG.getPixels()[i][j].getRed(), squarePPM.getPixels()[i][j].getRed());
        assertTrue(Math.abs(squareJPG.getPixels()[i][j].getRed()
                - squarePPM.getPixels()[i][j].getRed()) < 50);
        assertEquals(squareBMP.getPixels()[i][j].getRed(), squarePPM.getPixels()[i][j].getRed());

        assertEquals(squarePNG.getPixels()[i][j].getGreen(),
                squarePPM.getPixels()[i][j].getGreen());
        assertTrue(Math.abs(squareJPG.getPixels()[i][j].getGreen()
                - squarePPM.getPixels()[i][j].getGreen()) < 50);
        assertEquals(squareBMP.getPixels()[i][j].getGreen(),
                squarePPM.getPixels()[i][j].getGreen());

        assertEquals(squarePNG.getPixels()[i][j].getBlue(), squarePPM.getPixels()[i][j].getBlue());
        assertTrue(Math.abs(squareJPG.getPixels()[i][j].getBlue()
                - squarePPM.getPixels()[i][j].getBlue()) < 50);
        assertEquals(squareBMP.getPixels()[i][j].getBlue(), squarePPM.getPixels()[i][j].getBlue());
      }
    }
  }

  // FOR THIS TEST, SAVING TO A JPG FILE IS A LOSSY COMPRESSION METHOD SO THUS, WE HAD TO TEST
  // THE COMPONENT VALUES WITHIN A RANGE WHEN COMPARING A JPG IMAGE TO A PNG IMAGE
  // testing the loading and saving commands from a png file to other file formats
  @Test
  public void testLoadAndSaveFromPNG() {
    Readable readable = new StringReader(
            // loading in a png
            "load res/squareAllFileTypes/square.png squarePNG \n " +
                   // saving the png as the other 3 file types
                   "save res/squareAllFileTypes/square.ppm squarePNG \n" +
                    "save res/squareAllFileTypes/square.bmp squarePNG \n" +
                    "save res/squareAllFileTypes/square.jpg squarePNG \n" +

                    // loading those 3 saved images back in so that we can compare the image data
                    "load res/squareAllFileTypes/square.ppm squarePPM \n " +
                    "load res/squareAllFileTypes/square.bmp squareBMP \n " +
                    "load res/squareAllFileTypes/square.jpg squareJPG \n ");

    // running the controller start method which will create the new images and save them and
    // load them back in
    ImageTextController controller = new ImageTextControllerImpl(model, view, readable);
    controller.start();

    // obtaining the images from the model
    Image squarePNG = model.getImage("squarePNG");
    Image squarePPM = model.getImage("squarePPM");
    Image squareJPG = model.getImage("squareJPG");
    Image squareBMP = model.getImage("squareBMP");

    // comparing the components of each pixel between all the images
    for (int i = 0; i < squareJPG.getWidth(); i++) {
      for (int j = 0; j < squareJPG.getHeight(); j++) {

        assertEquals(squarePPM.getPixels()[i][j].getRed(),
                squarePNG.getPixels()[i][j].getRed());
        assertEquals(squareBMP.getPixels()[i][j].getRed(), squarePNG.getPixels()[i][j].getRed());
        assertTrue(Math.abs(squareJPG.getPixels()[i][j].getRed()
                - squarePNG.getPixels()[i][j].getRed()) < 50);

        assertEquals(squarePPM.getPixels()[i][j].getGreen(),
                squarePNG.getPixels()[i][j].getGreen());
        assertEquals(squareBMP.getPixels()[i][j].getGreen(),
                squarePNG.getPixels()[i][j].getGreen());
        assertTrue(Math.abs(squareJPG.getPixels()[i][j].getGreen()
                - squarePNG.getPixels()[i][j].getGreen()) < 50);

        assertEquals(squarePPM.getPixels()[i][j].getBlue(), squarePNG.getPixels()[i][j].getBlue());
        assertEquals(squareBMP.getPixels()[i][j].getBlue(), squarePNG.getPixels()[i][j].getBlue());
        assertTrue(Math.abs(squareJPG.getPixels()[i][j].getBlue()
                - squarePNG.getPixels()[i][j].getBlue()) < 50);
      }
    }
  }


  // FOR THIS TEST, SAVING TO A JPG FILE IS A LOSSY COMPRESSION METHOD SO THUS, WE HAD TO TEST
  // THE COMPONENT VALUES WITHIN A RANGE WHEN COMPARING A JPG IMAGE TO A BMP IMAGE
  // testing the loading and saving commands from a bmp file to other file formats
  @Test
  public void testLoadAndSaveFromBMP() {
    Readable readable = new StringReader(
            // loading in a bmp
            "load res/squareAllFileTypes/square.bmp squareBMP \n " +

                    // saving the bmp as the other 3 file types
                    "save res/squareAllFileTypes/square.ppm squareBMP \n" +
                    "save res/squareAllFileTypes/square.png squareBMP \n" +
                    "save res/squareAllFileTypes/square.jpg squareBMP \n" +

                    // loading those 3 saved images back in so that we can compare the image data
                    "load res/squareAllFileTypes/square.ppm squarePPM \n " +
                    "load res/squareAllFileTypes/square.png squarePNG \n " +
                    "load res/squareAllFileTypes/square.jpg squareJPG \n ");

    // running the controller start method which will create the new images and save them and
    // load them back in
    ImageTextController controller = new ImageTextControllerImpl(model, view, readable);
    controller.start();

    // obtaining the images from the model
    Image squarePNG = model.getImage("squarePNG");
    Image squarePPM = model.getImage("squarePPM");
    Image squareJPG = model.getImage("squareJPG");
    Image squareBMP = model.getImage("squareBMP");

    // comparing the components of each pixel between all the images
    for (int i = 0; i < squareBMP.getWidth(); i++) {
      for (int j = 0; j < squareBMP.getHeight(); j++) {

        assertEquals(squarePNG.getPixels()[i][j].getRed(), squareBMP.getPixels()[i][j].getRed());
        assertTrue(Math.abs(squareJPG.getPixels()[i][j].getRed()
                - squareBMP.getPixels()[i][j].getRed()) < 50);
        assertEquals(squarePPM.getPixels()[i][j].getRed(), squareBMP.getPixels()[i][j].getRed());

        assertEquals(squarePNG.getPixels()[i][j].getGreen(),
                squareBMP.getPixels()[i][j].getGreen());
        assertTrue(Math.abs(squareJPG.getPixels()[i][j].getGreen()
                - squareBMP.getPixels()[i][j].getGreen()) < 50);
        assertEquals(squarePPM.getPixels()[i][j].getGreen(),
                squareBMP.getPixels()[i][j].getGreen());

        assertEquals(squarePNG.getPixels()[i][j].getBlue(), squareBMP.getPixels()[i][j].getBlue());
        assertTrue(Math.abs(squareJPG.getPixels()[i][j].getBlue()
                - squareBMP.getPixels()[i][j].getBlue()) < 50);
        assertEquals(squarePPM.getPixels()[i][j].getBlue(), squareBMP.getPixels()[i][j].getBlue());
      }
    }
  }


  // since we are never saving an image as a jpg here (the process that compresses the pixels),
  // just loading it, we can directly compare its component values to the other image types and
  // don't need to use a range testing the loading and saving commands from a jpg file
  @Test
  public void testLoadAndSaveFromJPG() {
    Readable readable = new StringReader(
            // loading in a jpg
            "load res/squareAllFileTypes/square.jpg squareJPG \n " +

                    // saving the jpg as the other 3 file types
                    "save res/squareAllFileTypes/square.ppm squareJPG \n" +
                    "save res/squareAllFileTypes/square.png squareJPG \n" +
                    "save res/squareAllFileTypes/square.bmp squareJPG \n" +

                    // loading those 3 saved images back in so that we can compare the image data
                    "load res/squareAllFileTypes/square.ppm squarePPM \n " +
                    "load res/squareAllFileTypes/square.png squarePNG \n " +
                    "load res/squareAllFileTypes/square.bmp squareBMP \n ");

    // running the controller start method which will create the new images and save them and
    // load them back in
    ImageTextController controller = new ImageTextControllerImpl(model, view, readable);
    controller.start();

    // obtaining the images from the model
    Image squarePNG = model.getImage("squarePNG");
    Image squarePPM = model.getImage("squarePPM");
    Image squareJPG = model.getImage("squareJPG");
    Image squareBMP = model.getImage("squareBMP");

    // comparing the components of each pixel between all the images
    for (int i = 0; i < squareJPG.getWidth(); i++) {
      for (int j = 0; j < squareJPG.getHeight(); j++) {

        assertEquals(squarePNG.getPixels()[i][j].getRed(), squareJPG.getPixels()[i][j].getRed());
        assertEquals(squarePPM.getPixels()[i][j].getRed(), squareJPG.getPixels()[i][j].getRed());
        assertEquals(squareBMP.getPixels()[i][j].getRed(), squareJPG.getPixels()[i][j].getRed());

        assertEquals(squarePNG.getPixels()[i][j].getGreen(),
                squareJPG.getPixels()[i][j].getGreen());
        assertEquals(squarePPM.getPixels()[i][j].getGreen(),
                squareJPG.getPixels()[i][j].getGreen());
        assertEquals(squareBMP.getPixels()[i][j].getGreen(),
                squareJPG.getPixels()[i][j].getGreen());

        assertEquals(squarePNG.getPixels()[i][j].getBlue(), squareJPG.getPixels()[i][j].getBlue());
        assertEquals(squarePPM.getPixels()[i][j].getBlue(), squareJPG.getPixels()[i][j].getBlue());
        assertEquals(squareBMP.getPixels()[i][j].getBlue(), squareJPG.getPixels()[i][j].getBlue());
      }
    }
  }

}