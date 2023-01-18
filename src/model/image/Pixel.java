package model.image;

/**
 * An interface to represent functionality that we have on a pixel, which is a small component
 * that makes up images.
 */
public interface Pixel {

  /**
   * Returns the red component of this pixel.
   *
   * @return the red component of this pixel.
   */
  int getRed();

  /**
   * Returns the green component of this pixel.
   *
   * @return the green component of this pixel.
   */
  int getGreen();

  /**
   * Returns the blue component of this pixel.
   *
   * @return the blue component of this pixel.
   */
  int getBlue();
}