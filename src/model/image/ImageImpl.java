package model.image;

import model.enums.FlipType;
import model.enums.GreyscaleComponentType;

/**
 * A class that represents a singular image by its size and pixels that make up the image.
 */
// INVARIANT: The image always has a positive width, height, and maxValue. The 2D array of its
//            pixels are never null and the none of the actual pixels in the 2D array are ever null
//            either. It also enforces that the size of the array of pixels is the same as the
//            width by height.
public class ImageImpl implements Image {

  private final int width;
  private final int height;

  private final int maxValue;
  private final Pixel[][] pixels;

  /**
   * A constructor which sets the fields to the arguments of the constructor to construct an
   * image and ensures that the constraints on the fields are enforced.
   *
   * @param width    the width of the image based on how many pixels wide it is.
   * @param height   the height of this image based on how many pixels tall it is.
   * @param maxValue the maximum value that a channel of a pixel can be represented by
   * @param pixels   the 2D array of pixels making up the image
   * @throws IllegalArgumentException if the constructor is passed a width, height, or maxValue
   *                                  that is not a positive integer, or if the pixels or any of
   *                                  its contents are null, or if the size of the array doesn't
   *                                  match the parameters.
   */
  public ImageImpl(int width, int height, int maxValue, Pixel[][] pixels)
          throws IllegalArgumentException {
    this.checkImageConditions(width, height, maxValue, pixels);
    this.width = width;
    this.height = height;
    this.maxValue = maxValue;
    this.pixels = pixels;
  }

  /**
   * A helper method which enforces the requirements of the constructor.
   *
   * @param width    the width of the image based on how many pixels wide it is.
   * @param height   the height of this image based on how many pixels tall it is.
   * @param maxValue the maximum value that a channel of a pixel can be represented by
   * @param pixels   the 2D array of pixels making up the image
   * @throws IllegalArgumentException if the constructor is passed a width, height, or maxValue
   *                                  that is not a positive integer, or if the pixels or any of
   *                                  its contents are null, or if the 2d array is not of size
   *                                  width by height
   */
  private void checkImageConditions(int width, int height, int maxValue, Pixel[][] pixels)
          throws IllegalArgumentException {
    if (width <= 0 || height <= 0 || maxValue <= 0) {
      throw new IllegalArgumentException("Width and height must both be positive!");
    }
    if (pixels == null) {
      throw new IllegalArgumentException("2D array of pixels cannot be null!");
    }
    for (int i = 0; i < pixels.length; i++) {
      for (int j = 0; j < pixels[0].length; j++) {
        if (pixels[i][j] == null) {
          throw new IllegalArgumentException("Pixels within the 2D array cannot be null!");
        }
      }
    }
  }

  @Override
  public int getWidth() {
    return this.width;
  }

  @Override
  public int getHeight() {
    return this.height;
  }

  @Override
  public int getMaxValue() {
    return this.maxValue;
  }

  @Override
  public Pixel[][] getPixels() {

    Pixel[][] temp = new RGBPixel[this.width][this.height];

    for (int i = 0; i < this.width; i++) {
      for (int j = 0; j < this.height; j++) {
        Pixel p = this.pixels[i][j];
        Pixel newPixel = new RGBPixel(p.getRed(), p.getGreen(), p.getBlue());
        temp[i][j] = newPixel;
      }
    }
    return temp;
  }

  @Override
  public Image greyscaleComponent(GreyscaleComponentType type) {
    if (type == null) {
      throw new IllegalArgumentException("Type cannot be null!");
    }

    Pixel[][] newPixels = new RGBPixel[this.width][this.height];
    int color;

    for (int i = 0; i < this.width; i++) {
      for (int j = 0; j < this.height; j++) {
        Pixel p = this.pixels[i][j];

        switch (type) {
          case RED:
            color = p.getRed();
            break;
          case GREEN:
            color = p.getGreen();
            break;
          case BLUE:
            color = p.getBlue();
            break;
          case VALUE:
            color = this.maxValue(p.getRed(), p.getGreen(), p.getBlue());
            break;
          case INTENSITY:
            color = (int) (Math.round(p.getRed() + p.getBlue() + p.getGreen()) / 3.0);
            break;
          case LUMA:
            color = (int) (Math.round((p.getRed() * 0.2126)
                    + (p.getGreen() * 0.7152)
                    + (p.getBlue() * 0.0722)));
            break;
          default:
            throw new IllegalArgumentException("Null greyscale type!");
        }

        Pixel newPixel = new RGBPixel(color, color, color);
        newPixels[i][j] = newPixel;
      }
    }

    Image newImg = new ImageImpl(this.width, this.height, this.maxValue, newPixels);
    return newImg;
  }

  /**
   * Helper method which determines the maximum of three integers, the red, green, and blue
   * channels of a pixel.
   *
   * @param red   the red component of a pixel
   * @param green the green component of a pixel
   * @param blue  the blue component of a pixel
   * @return the maximum of the three components of a pixel
   */
  private int maxValue(int red, int green, int blue) {
    int tempMax = Math.max(red, green);
    int max = Math.max(tempMax, blue);
    return max;
  }

  @Override
  public Image brighten(int value) {
    if (value <= 0) {
      throw new IllegalArgumentException("Cannot brighten by a negative number!");
    }

    Pixel[][] newPixels = new RGBPixel[this.width][this.height];
    for (int i = 0; i < this.width; i++) {
      for (int j = 0; j < this.height; j++) {
        Pixel p = this.pixels[i][j];
        int red = Math.min(this.getMaxValue(), (p.getRed() + value));
        int green = Math.min(this.getMaxValue(), (p.getGreen() + value));
        int blue = Math.min(this.getMaxValue(), (p.getBlue() + value));
        Pixel newPixel = new RGBPixel(red, green, blue);
        newPixels[i][j] = newPixel;
      }
    }

    Image newImg = new ImageImpl(this.width, this.height, this.maxValue, newPixels);
    return newImg;
  }

  @Override
  public Image darken(int value) {
    if (value <= 0) {
      throw new IllegalArgumentException("Cannot darken by a negative number!");
    }

    Pixel[][] newPixels = new RGBPixel[this.width][this.height];
    for (int i = 0; i < this.width; i++) {
      for (int j = 0; j < this.height; j++) {
        Pixel p = this.pixels[i][j];
        int red = Math.max(0, (p.getRed() - value));
        int green = Math.max(0, (p.getGreen() - value));
        int blue = Math.max(0, (p.getBlue() - value));
        Pixel newPixel = new RGBPixel(red, green, blue);
        newPixels[i][j] = newPixel;
      }
    }
    Image newImg = new ImageImpl(this.width, this.height, this.maxValue, newPixels);
    return newImg;
  }

  @Override
  public Image flip(FlipType type) {
    if (type == null) {
      throw new IllegalArgumentException("Type cannot be null!");
    }

    Pixel[][] newPixels = this.getPixels();

    switch (type) {
      case VERTICAL:
        for (int i = 0; i < width; i++) {
          for (int j = 0; j < height / 2; j++) {
            Pixel temp = newPixels[i][j];
            newPixels[i][j] = newPixels[i][height - 1 - j];
            newPixels[i][height - 1 - j] = temp;
          }
        }
        break;
      case HORIZONTAL:
        for (int i = 0; i < width / 2; i++) {
          for (int j = 0; j < height; j++) {
            Pixel temp = newPixels[i][j];
            newPixels[i][j] = newPixels[width - 1 - i][j];
            newPixels[width - 1 - i][j] = temp;
          }
        }
        break;
      default:
        throw new IllegalArgumentException("Invalid flip type!");
    }

    Image img = new ImageImpl(this.width, this.height, this.maxValue, newPixels);
    return img;
  }

  @Override
  public Image filter(double[][] kernel) throws IllegalArgumentException {
    if (kernel == null) {
      throw new IllegalArgumentException("Kernel is null!");
    }
    if ((kernel.length != kernel[0].length) || (kernel.length % 2 != 1)) {
      throw new IllegalArgumentException("Kernel must be a square matrix and must have an odd " +
              "length!");
    }

    Pixel[][] newPixels = new RGBPixel[this.width][this.height];

    for (int i = 0; i < this.getWidth(); i++) {
      for (int j = 0; j < this.getHeight(); j++) {
        newPixels[i][j] = this.applyKernelToPixel(i, j, kernel);
      }
    }
    return new ImageImpl(this.width, this.height, this.maxValue, newPixels);
  }

  /**
   * Applies a filter to every slot at a specific i and j by centering a kernel on a pixel and
   * then looping through the kernel and its corresponding pixels to calculate the components of
   * the new pixel, using the components of the pixels around the one we are centered at.
   *
   * @param i      the row of the pixel we are filtering
   * @param j      the column of the pixel we are filtering
   * @param kernel the matrix that provides us with the math necessary to apply the filter
   * @return a new Pixel at the specified location after the filter has been applied
   */
  private Pixel applyKernelToPixel(int i, int j, double[][] kernel) {
    int red = 0;
    int green = 0;
    int blue = 0;

    int matrixSize = kernel.length;

    for (int r = 0; r < matrixSize; r++) {
      for (int c = 0; c < matrixSize; c++) {

        try {
          red += kernel[r][c] * pixels[i + (r - (matrixSize / 2))][j + (c - (matrixSize / 2))]
                  .getRed();
          green += kernel[r][c] * pixels[i + (r - (matrixSize / 2))][j + (c - (matrixSize / 2))]
                  .getGreen();
          blue += kernel[r][c] * pixels[i + (r - (matrixSize / 2))][j + (c - (matrixSize / 2))]
                  .getBlue();
        } catch (ArrayIndexOutOfBoundsException e) {
          // DO NOTHING if we are trying to access an index out of bounds because that means
          // that that spot doesn't exist and doesn't contribute to the new value of a component
        }
      }
    }
    red = this.enforceConstraints(red);
    green = this.enforceConstraints(green);
    blue = this.enforceConstraints(blue);
    return new RGBPixel(red, green, blue);
  }

  @Override
  public Image colorTransformation(double[][] matrix) {
    if (matrix == null) {
      throw new IllegalArgumentException("Kernel is null!");
    }

    // ensuring that the matrix 3 by 3
    if (matrix.length != 3 || matrix[0].length != 3 || matrix[1].length != 3
            || matrix[2].length != 3) {
      throw new IllegalArgumentException("Kernel must be a square matrix and must have an odd " +
              "length!");
    }

    Pixel[][] newPixels = new RGBPixel[this.width][this.height];

    for (int i = 0; i < this.getWidth(); i++) {
      for (int j = 0; j < this.getHeight(); j++) {
        newPixels[i][j] = this.applyColorTransformationToPixel(i, j, matrix);
      }
    }
    return new ImageImpl(this.width, this.height, this.maxValue, newPixels);
  }

  /**
   * Applies a 3 by 3 matrix to a pixel to apply a color transformation.
   *
   * @param i      the row of the pixel we are filtering
   * @param j      the column of the pixel we are filtering
   * @param matrix the matrix that provides us with the math to get our new components
   * @return a new pixel which has a color transformation applied to the old pixel.
   */
  private Pixel applyColorTransformationToPixel(int i, int j, double[][] matrix) {
    Pixel p = pixels[i][j];

    int red = (int) (Math.round(p.getRed() * matrix[0][0])
            + (p.getGreen() * matrix[0][1])
            + (p.getBlue() * matrix[0][2]));
    int green = (int) (Math.round(p.getRed() * matrix[1][0])
            + (p.getGreen() * matrix[1][1])
            + (p.getBlue() * matrix[1][2]));
    int blue = (int) (Math.round(p.getRed() * matrix[2][0])
            + (p.getGreen() * matrix[2][1])
            + (p.getBlue() * matrix[2][2]));
    red = this.enforceConstraints(red);
    green = this.enforceConstraints(green);
    blue = this.enforceConstraints(blue);
    return new RGBPixel(red, green, blue);
  }

  /**
   * Ensures that a component of a pixel is within the acceptable range by capping its upper
   * and lower limits based on the max value.
   *
   * @param i the value of one of the components of a pixel
   * @return the value to use for the component based on the constraints
   */
  private int enforceConstraints(int i) {
    if (i < 0) {
      return 0;
    } else if (i > this.maxValue) {
      return this.maxValue;
    } else {
      return i;
    }
  }

  @Override
  public Image downsize(int widthPercent, int heightPercent) throws IllegalArgumentException {
    if (widthPercent < 0 || widthPercent >= 100 || heightPercent < 0 || heightPercent >= 100) {
      throw new IllegalArgumentException("Percent must be between 0 - 100");
    }

    // calculating the size of the new pixel grid of the downsized image and making a new pixel
    // array of this size
    int newWidth = (int) Math.round((this.width * (100 - widthPercent)) / 100.0);
    int newHeight = (int) Math.round((this.height * (100 - heightPercent)) / 100.0);
    Pixel[][] newPixels = new RGBPixel[newWidth][newHeight];

    // populating the new pixel array
    for (int i = 0; i < newWidth; i++) {
      for (int j = 0; j < newHeight; j++) {
        Pixel p = this.makeDownsizedPixel(i, j, newWidth, newHeight);
        newPixels[i][j] = p;
      }
    }

    return new ImageImpl(newWidth, newHeight, this.maxValue, newPixels);
  }

  /**
   * Creates a pixel with the correct color components based on the current row and column of the
   * pixel in the downsized image.
   *
   * @param i         the row of the pixel's components in the new image we are calculating
   * @param j         the col of the pixel's components in the new image we are calculating
   * @param newWidth  the width of the downsized image
   * @param newHeight the height of the downsized image
   * @return a pixel with the correct components calculated using the pixels around it
   */
  private Pixel makeDownsizedPixel(int i, int j, int newWidth, int newHeight) {
    double originalX = (i / (double) newWidth) * this.width;
    double originalY = (j / (double) newHeight) * this.height;


    // if both the x and y values map directly to integer valued x and y in the original, the
    // return that mapped pixel
    if (originalX % 1 == 0 && originalY % 1 == 0) {
      Pixel oldPixel = this.pixels[(int) originalX][(int) originalY];
      return new RGBPixel(oldPixel.getRed(), oldPixel.getGreen(), oldPixel.getBlue());
    }

    // if either of the original coordinates are floating-point numbers, then use the surrounding
    // pixels to generate the correct component values
    else {
      Pixel oldPixel = averageSurroundingPixels((int) originalX, (int) originalY);
      return oldPixel;
    }
  }

  /**
   * Averages the components of the 4 surrounding pixels of the pixel found at the specified row
   * and column to determine its components.
   *
   * @param i the row of the pixel whose surroundings we are combining
   * @param j the column of the pixel whose surroundings we are combining
   * @return a pixel whose components are those of the surrounding 4 averaged
   */
  private Pixel averageSurroundingPixels(int i, int j) {
    int red = 0;
    int green = 0;
    int blue = 0;
    double average = 0.0;

    try {
      Pixel leftPixel = pixels[i][j - 1];
      red += leftPixel.getRed();
      green += leftPixel.getGreen();
      blue += leftPixel.getBlue();
      average++;
    } catch (IndexOutOfBoundsException e) {
      // DO NOTHING HERE IF THE PIXEL DOESN'T EXIST
    }

    try {
      Pixel rightPixel = pixels[i][j + 1];
      red += rightPixel.getRed();
      green += rightPixel.getGreen();
      blue += rightPixel.getBlue();
      average++;
    } catch (IndexOutOfBoundsException e) {
      // DO NOTHING HERE IF THE PIXEL DOESN'T EXIST
    }

    try {
      Pixel upPixel = pixels[i - 1][j];
      red += upPixel.getRed();
      green += upPixel.getGreen();
      blue += upPixel.getBlue();
      average++;
    } catch (IndexOutOfBoundsException e) {
      // DO NOTHING HERE IF THE PIXEL DOESN'T EXIST
    }

    try {
      Pixel downPixel = pixels[i + 1][j];
      red += downPixel.getRed();
      green += downPixel.getGreen();
      blue += downPixel.getBlue();
      average++;
    } catch (IndexOutOfBoundsException e) {
      // DO NOTHING HERE IF THE PIXEL DOESN'T EXIST
    }

    return new RGBPixel((int) (Math.round(red / average)),
            (int) (Math.round(green / average)),
            (int) (Math.round(blue / average)));
  }
}
