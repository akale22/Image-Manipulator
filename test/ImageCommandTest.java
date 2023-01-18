import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

import controller.commands.Blur;
import controller.commands.Brighten;
import controller.commands.Darken;
import controller.commands.Flip;
import controller.commands.GreyscaleColorTransformation;
import controller.commands.GreyscaleComponent;
import controller.commands.Load;
import controller.commands.Save;
import controller.commands.Sepia;
import controller.commands.Sharpen;
import model.ImageModel;
import model.ImageModelImpl;
import model.enums.FlipType;
import model.enums.GreyscaleComponentType;
import model.image.Image;
import model.image.Pixel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


/**
 * A JUnit test class for Image Commands.
 */
public class ImageCommandTest {

  private ImageModel m;
  private Image square;
  private Brighten brighten;
  private Darken darken;
  private Flip flip;
  private GreyscaleComponent greyscaleComponent;
  private Load load;
  private Save save;


  // loading is tested within the init method as well
  @Before
  public void init() {
    m = new ImageModelImpl();
    load = new Load("res/Square.ppm", "square");
    load.execute(m);
    square = m.getImage("square");
  }

  // test the brighten command
  @Test
  public void testBrighten() {
    brighten = new Brighten(10, "square", "brightSquare");
    brighten.execute(m);
    Image brightSquare = m.getImage("brightSquare");

    for (int i = 0; i < square.getWidth(); i++) {
      for (int j = 0; j < square.getHeight(); j++) {
        assertEquals(brightSquare.getPixels()[i][j].getRed(),
                Math.min(square.getPixels()[i][j].getRed() + 10, square.getMaxValue()));
        assertEquals(brightSquare.getPixels()[i][j].getGreen(),
                Math.min(square.getPixels()[i][j].getGreen() + 10, square.getMaxValue()));
        assertEquals(brightSquare.getPixels()[i][j].getBlue(),
                Math.min(square.getPixels()[i][j].getBlue() + 10, square.getMaxValue()));
      }
    }
  }

  // test the darken command
  @Test
  public void testDarken() {
    darken = new Darken(20, "square", "darkSquare");
    darken.execute(m);
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

  // test the greyscaleComponent command with a red greyscale type
  @Test
  public void testRedComponent() {
    greyscaleComponent = new GreyscaleComponent(GreyscaleComponentType.RED, "square", "redSquare");
    greyscaleComponent.execute(m);
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

  // test the greyscaleComponent command with a green greyscale type
  @Test
  public void testGreenComponent() {
    greyscaleComponent =
            new GreyscaleComponent(GreyscaleComponentType.GREEN, "square", "greenSquare");
    greyscaleComponent.execute(m);
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

  // test the greyscaleComponent command with a blue greyscale type
  @Test
  public void testBlueComponent() {
    greyscaleComponent =
            new GreyscaleComponent(GreyscaleComponentType.BLUE, "square", "blueSquare");
    greyscaleComponent.execute(m);
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

  // test the greyscaleComponent command with a value greyscale type
  @Test
  public void testValueComponent() {
    greyscaleComponent =
            new GreyscaleComponent(GreyscaleComponentType.VALUE, "square", "valueSquare");
    greyscaleComponent.execute(m);
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

  // test the greyscaleComponent command with a intensity greyscale type
  @Test
  public void testIntensityComponent() {
    greyscaleComponent =
            new GreyscaleComponent(GreyscaleComponentType.INTENSITY, "square", "intensitySquare");
    greyscaleComponent.execute(m);
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

  // test the greyscaleComponent command with a luma greyscale type
  @Test
  public void testLumaComponent() {
    greyscaleComponent =
            new GreyscaleComponent(GreyscaleComponentType.LUMA, "square", "lumaSquare");
    greyscaleComponent.execute(m);
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

  // test for an exception with greyscaleComponent command for a null greyscale type
  @Test(expected = IllegalArgumentException.class)
  public void testFailedGreyscaleComponent() {
    greyscaleComponent =
            new GreyscaleComponent(null, "square", "greyscaleSquare");
    greyscaleComponent.execute(m);
  }

  // test for an exception with flip command for a null flip type
  @Test(expected = IllegalArgumentException.class)
  public void testFailedFlip() {
    flip = new Flip(null, "square", "flipSquare");
    flip.execute(m);
  }

  // test for an exception with brighten command for a negative increment
  @Test(expected = IllegalArgumentException.class)
  public void testFailedBrighten() {
    brighten = new Brighten(-10, "square", "brightSquare");
    brighten.execute(m);
  }

  // test for an exception with darken command for a negative increment
  @Test(expected = IllegalArgumentException.class)
  public void testFailedDarken() {
    darken = new Darken(-10, "square", "darkSquare");
    darken.execute(m);
  }

  // test the command flip with a vertical flip type
  @Test
  public void testFlipVertically() {
    flip = new Flip(FlipType.VERTICAL, "square", "verticalSquare");
    flip.execute(m);
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

  // test the command flip with a horizontal flip type
  @Test
  public void testFlipHorizontally() {
    flip = new Flip(FlipType.HORIZONTAL, "square", "horizontalSquare");
    flip.execute(m);
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

  // test the save command
  @Test
  public void testSave() {
    m.greyscaleComponent(GreyscaleComponentType.RED, "square", "redSquare");
    Image redSquare = m.getImage("redSquare");
    Save save = new Save("res/imagesToTestSaveFunctionality/saveCommand.ppm", "redSquare");

    // ensuring that the correct greyscale have been applied to the pixels before saving
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

    // saving the image to a directory inside the res folder
    save.execute(m);
  }

  // test the save as a ppm to ensure that it is writing to the file properly
  @Test
  public void testWritingToAPPMFileCorrectly() throws FileNotFoundException {
    Scanner sc;

    sc = new Scanner(new FileInputStream("res/imagesToTestSaveFunctionality/saveCommand.ppm"));

    StringBuilder builder = new StringBuilder();

    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      builder.append(s + System.lineSeparator());
    }

    String s = builder.toString();
    String[] ppmFileContents = s.split("\n");
    // checking to ensure that it has P3 on the first line, followed by width and height on the
    // next, and the max value on the line after that
    assertEquals("P3", ppmFileContents[0]);
    assertEquals("4 4", ppmFileContents[1]);
    assertEquals("255", ppmFileContents[2]);

    // checking to ensure that the first few pixels are transferred to the ppm file properly
    assertEquals("0", ppmFileContents[3]);
    assertEquals("0", ppmFileContents[4]);
    assertEquals("0", ppmFileContents[5]);
    assertEquals("100", ppmFileContents[6]);
    assertEquals("100", ppmFileContents[7]);
    assertEquals("100", ppmFileContents[8]);
  }


  // testing the blur command
  @Test
  public void testBlur() {
    Blur blur = new Blur("square", "blurredSquare");
    blur.execute(m);
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
            } catch (ArrayIndexOutOfBoundsException e) { // DO NOTHING
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

  // testing the sharpen command
  @Test
  public void testSharpen() {
    Sharpen sharpen = new Sharpen("square", "sharpenedImage");
    sharpen.execute(m);
    Image sharpenedImage = m.getImage("sharpenedImage");

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
            } catch (ArrayIndexOutOfBoundsException e) { // DO NOTHING
            }
          }
        }
        // Using Math.max and Math.min to the RGB constraints
        red = Math.min(Math.max(red, 0), 255);
        green = Math.min(Math.max(green, 0), 255);
        blue = Math.min(Math.max(blue, 0), 255);

        // checking each component of the blurred image with the manually calculated ones
        assertEquals(sharpenedImage.getPixels()[i][j].getRed(), red);
        assertEquals(sharpenedImage.getPixels()[i][j].getGreen(), green);
        assertEquals(sharpenedImage.getPixels()[i][j].getBlue(), blue);
      }
    }
  }

  // testing the greyscaleColorTransformation command
  @Test
  public void testGreyscaleColorTransformation() {
    double[][] matrix = new double[][]
        {{0.2126, 0.7152, 0.0722}, {0.2126, 0.7152, 0.0722}, {0.2126, 0.7152, 0.0722}};
    GreyscaleColorTransformation greyscaleColorTransformation =
            new GreyscaleColorTransformation("square", "greySquare");
    greyscaleColorTransformation.execute(m);
    Image greySquare = m.getImage("greySquare");

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
        assertEquals(red, greySquare.getPixels()[i][j].getRed());
        assertEquals(green, greySquare.getPixels()[i][j].getGreen());
        assertEquals(blue, greySquare.getPixels()[i][j].getBlue());
      }
    }
  }

  // testing the sepia command
  @Test
  public void testSepia() {
    Sepia sepia = new Sepia("square", "sepiaSquare");
    sepia.execute(m);
    Image sepiaSquare = m.getImage("sepiaSquare");

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

        // checking each component of the sepia image with the manually calculated ones
        assertEquals(red, sepiaSquare.getPixels()[i][j].getRed());
        assertEquals(green, sepiaSquare.getPixels()[i][j].getGreen());
        assertEquals(blue, sepiaSquare.getPixels()[i][j].getBlue());
      }
    }
  }


  // FOR THIS TEST, SAVING TO A JPG FILE IS A LOSSY COMPRESSION METHOD SO THUS, WE HAD TO TEST
  // THE COMPONENT VALUES WITHIN A RANGE WHEN COMPARING A JPG IMAGE TO A PPM IMAGE
  // testing the loading and saving commands from a ppm file
  @Test
  public void testLoadAndSaveFromPPM() {
    // load as ppm
    load = new Load("res/squareAllFileTypes/square.ppm", "squarePPM");
    load.execute(m);
    Image squarePPM = m.getImage("squarePPM");

    // save as png
    save = new Save("res/squareAllFileTypes/square.png", "squarePPM");
    save.execute(m);

    // save as jpg
    save = new Save("res/squareAllFileTypes/square.jpg", "squarePPM");
    save.execute(m);

    // save as bmp
    save = new Save("res/squareAllFileTypes/square.bmp", "squarePPM");
    save.execute(m);

    // load the png
    load = new Load("res/squareAllFileTypes/square.png", "squarePNG");
    load.execute(m);
    Image squarePNG = m.getImage("squarePNG");

    // load the jpg
    load = new Load("res/squareAllFileTypes/square.jpg", "squareJPG");
    load.execute(m);
    Image squareJPG = m.getImage("squareJPG");

    // load the bmp
    load = new Load("res/squareAllFileTypes/square.bmp", "squareBMP");
    load.execute(m);
    Image squareBMP = m.getImage("squareBMP");


    for (int i = 0; i < square.getWidth(); i++) {
      for (int j = 0; j < square.getHeight(); j++) {

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
        assertTrue(Math.abs(squareJPG.getPixels()[i][j].getBlue()
                - squarePPM.getPixels()[i][j].getBlue()) < 40);
        assertEquals(squareBMP.getPixels()[i][j].getBlue(), squarePPM.getPixels()[i][j].getBlue());

      }
    }
  }


  // FOR THIS TEST, SAVING TO A JPG FILE IS A LOSSY COMPRESSION METHOD SO THUS, WE HAD TO TEST
  // THE COMPONENT VALUES WITHIN A RANGE WHEN COMPARING A JPG IMAGE TO A PNG IMAGE
  // testing the loading and saving commands from a png file
  @Test
  public void testLoadAndSaveFromPNG() {
    // load as ppm
    load = new Load("res/squareAllFileTypes/square.png", "squarePNG");
    load.execute(m);
    Image squarePNG = m.getImage("squarePNG");

    // save as ppm
    save = new Save("res/squareAllFileTypes/square.ppm", "squarePNG");
    save.execute(m);

    // save as jpg
    save = new Save("res/squareAllFileTypes/square.jpg", "squarePNG");
    save.execute(m);

    // save as bmp
    save = new Save("res/squareAllFileTypes/square.bmp", "squarePNG");
    save.execute(m);

    // load the ppm
    load = new Load("res/squareAllFileTypes/square.ppm", "squarePPM");
    load.execute(m);
    Image squarePPM = m.getImage("squarePPM");

    // load the jpg
    load = new Load("res/squareAllFileTypes/square.jpg", "squareJPG");
    load.execute(m);
    Image squareJPG = m.getImage("squareJPG");

    // load the bmp
    load = new Load("res/squareAllFileTypes/square.bmp", "squareBMP");
    load.execute(m);
    Image squareBMP = m.getImage("squareBMP");


    for (int i = 0; i < square.getWidth(); i++) {
      for (int j = 0; j < square.getHeight(); j++) {

        assertEquals(squarePPM.getPixels()[i][j].getRed(),
                squarePNG.getPixels()[i][j].getRed());
        assertEquals(squareBMP.getPixels()[i][j].getRed(), squarePNG.getPixels()[i][j].getRed());
        assertTrue(Math.abs(squareJPG.getPixels()[i][j].getRed()
                - squarePNG.getPixels()[i][j].getRed()) < 40);

        assertEquals(squarePPM.getPixels()[i][j].getGreen(),
                squarePNG.getPixels()[i][j].getGreen());
        assertEquals(squareBMP.getPixels()[i][j].getGreen(),
                squarePNG.getPixels()[i][j].getGreen());
        assertTrue(Math.abs(squareJPG.getPixels()[i][j].getGreen()
                - squarePNG.getPixels()[i][j].getGreen()) < 40);

        assertEquals(squarePPM.getPixels()[i][j].getBlue(), squarePNG.getPixels()[i][j].getBlue());
        assertEquals(squareBMP.getPixels()[i][j].getBlue(), squarePNG.getPixels()[i][j].getBlue());
        assertTrue(Math.abs(squareJPG.getPixels()[i][j].getBlue()
                - squarePNG.getPixels()[i][j].getBlue()) < 40);
      }
    }
  }

  // FOR THIS TEST, SAVING TO A JPG FILE IS A LOSSY COMPRESSION METHOD SO THUS, WE HAD TO TEST
  // THE COMPONENT VALUES WITHIN A RANGE WHEN COMPARING A JPG IMAGE TO A BMP IMAGE
  // testing the loading and saving commands from a bmp file
  @Test
  public void testLoadAndSaveFromBMP() {
    // load as ppm
    load = new Load("res/squareAllFileTypes/square.bmp", "squareBMP");
    load.execute(m);
    Image squareBMP = m.getImage("squareBMP");

    // save as png
    save = new Save("res/squareAllFileTypes/square.png", "squareBMP");
    save.execute(m);

    // save as jpg
    save = new Save("res/squareAllFileTypes/square.jpg", "squareBMP");
    save.execute(m);

    // save as ppm
    save = new Save("res/squareAllFileTypes/square.ppm", "squareBMP");
    save.execute(m);

    // load the png
    load = new Load("res/squareAllFileTypes/square.png", "squarePNG");
    load.execute(m);
    Image squarePNG = m.getImage("squarePNG");

    // load the jpg
    load = new Load("res/squareAllFileTypes/square.jpg", "squareJPG");
    load.execute(m);
    Image squareJPG = m.getImage("squareJPG");

    // load the ppm
    load = new Load("res/squareAllFileTypes/square.ppm", "squarePPM");
    load.execute(m);
    Image squarePPM = m.getImage("squarePPM");


    for (int i = 0; i < square.getWidth(); i++) {
      for (int j = 0; j < square.getHeight(); j++) {

        assertEquals(squarePNG.getPixels()[i][j].getRed(), squareBMP.getPixels()[i][j].getRed());
        assertTrue(Math.abs(squareJPG.getPixels()[i][j].getRed()
                - squareBMP.getPixels()[i][j].getRed()) < 40);
        assertEquals(squarePPM.getPixels()[i][j].getRed(), squareBMP.getPixels()[i][j].getRed());

        assertEquals(squarePNG.getPixels()[i][j].getGreen(),
                squareBMP.getPixels()[i][j].getGreen());
        assertTrue(Math.abs(squareJPG.getPixels()[i][j].getGreen()
                - squareBMP.getPixels()[i][j].getGreen()) < 40);
        assertEquals(squarePPM.getPixels()[i][j].getGreen(),
                squareBMP.getPixels()[i][j].getGreen());

        assertEquals(squarePNG.getPixels()[i][j].getBlue(), squareBMP.getPixels()[i][j].getBlue());
        assertTrue(Math.abs(squareJPG.getPixels()[i][j].getBlue()
                - squareBMP.getPixels()[i][j].getBlue()) < 50);
        assertEquals(squarePPM.getPixels()[i][j].getBlue(), squareBMP.getPixels()[i][j].getBlue());


      }
    }
  }

  // testing the loading and saving commands from a jpg file
  @Test
  public void testLoadAndSaveFromJPG() {
    // load as jpg
    load = new Load("res/squareAllFileTypes/square.jpg", "squareJPG");
    load.execute(m);
    Image squareJPG = m.getImage("squareJPG");

    // save as png
    save = new Save("res/squareAllFileTypes/square.png", "squareJPG");
    save.execute(m);

    // save as ppm
    save = new Save("res/squareAllFileTypes/square.ppm", "squareJPG");
    save.execute(m);

    // save as bmp
    save = new Save("res/squareAllFileTypes/square.bmp", "squareJPG");
    save.execute(m);

    // load the png
    load = new Load("res/squareAllFileTypes/square.png", "squarePNG");
    load.execute(m);
    Image squarePNG = m.getImage("squarePNG");

    // load the ppm
    load = new Load("res/squareAllFileTypes/square.ppm", "squarePPM");
    load.execute(m);
    Image squarePPM = m.getImage("squarePPM");

    // load the bmp
    load = new Load("res/squareAllFileTypes/square.bmp", "squareBMP");
    load.execute(m);
    Image squareBMP = m.getImage("squareBMP");


    for (int i = 0; i < square.getWidth(); i++) {
      for (int j = 0; j < square.getHeight(); j++) {

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

  // testing for an exception where the name of the file isn't at least 5 characters long for load
  @Test(expected = IllegalArgumentException.class)
  public void testLoadSmallFileName() {
    load = new Load(".jpg", "square");
    load.execute(m);
  }

  // testing for an exception where the type is invalid for load
  @Test(expected = IllegalArgumentException.class)
  public void testLoadInvalidFileType() {
    load = new Load("res/squareAllFileTypes/square.abc", "square");
    load.execute(m);
  }

  // testing for an exception where the name of the file isn't at least 5 characters long for save
  @Test(expected = IllegalArgumentException.class)
  public void testSaveSmallFileName() {
    save = new Save(".jpg", "square");
    save.execute(m);
  }

  // testing for an exception where the type is invalid for save
  @Test(expected = IllegalArgumentException.class)
  public void testSaveInvalidFileType() {
    save = new Save("res/squareAllFileTypes/square.abc", "square");
    save.execute(m);
  }

}
