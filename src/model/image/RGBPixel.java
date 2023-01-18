package model.image;

/**
 * A class representing a pixel with red, green, and blue components.
 */
// INVARIANT: An RGBPixel must always have their red, green, and blue fields greater than or
//             equal to 0 and less than or equal to 255, and this is enforced by the constructor
//             and all the methods that mutate a pixel.
public class RGBPixel implements Pixel {

  private final int red;
  private final int green;
  private final int blue;

  /**
   * A constructor which instantiates the pixel's fields to its arguments and ensures that they
   * are within the proper range for an RGB pixel.
   *
   * @param red   the red component of the pixel
   * @param green the green component of the pixel
   * @param blue  the blue component of the pixel
   * @throws IllegalArgumentException if any of the values are greater than the max allowed number
   *                                  or less than the minimum allowed number for rgb values
   */
  public RGBPixel(int red, int green, int blue) throws IllegalArgumentException {
    if (red < 0 || red > 255 || green < 0 || green > 255 || blue < 0 || blue > 255) {
      throw new IllegalArgumentException("RGB values must be between 0 and 255!");
    }
    this.red = red;
    this.green = green;
    this.blue = blue;
  }

  @Override
  public int getRed() {
    return this.red;
  }

  @Override
  public int getGreen() {
    return this.green;
  }

  @Override
  public int getBlue() {
    return this.blue;
  }
}