package model.image;

import model.enums.FlipType;
import model.enums.GreyscaleComponentType;

/**
 * The interface which contains methods for returning properties of an image and methods for
 * creating new images with certain manipulations done.
 */
public interface Image {

  /**
   * Returns the width of this image based on how many pixels wide it is.
   *
   * @return the number of pixels wide the image is
   */
  int getWidth();

  /**
   * Returns the height of this image based on how many pixels tall it is.
   *
   * @return the number of pixels tall the image is
   */
  int getHeight();

  /**
   * Returns the max value that a pixel can go up to when representing its components as an integer.
   *
   * @return the max value that a pixel can go up to when representing its components as an integer
   */
  int getMaxValue();

  /**
   * Returns a deep copy of the 2D array of pixels that make up this image.
   *
   * @return a 2D array of pixels which is a copy of the pixels contained in this image
   */
  Pixel[][] getPixels();

  /**
   * Returns another image where the pixels have been manipulated to create a certain greyscale
   * version of this image based on the specified greyscale type.
   *
   * @param type the type of greyscale to be applied to this image
   * @return another image with the specified greyscale type applied
   * @throws IllegalArgumentException if the greyscale type specified is null
   */
  Image greyscaleComponent(GreyscaleComponentType type) throws IllegalArgumentException;

  /**
   * Returns another image that represents this image flipped, based on the specified flip type.
   *
   * @param type the type of flip to be applied to this image
   * @return another image with the specified flip applied
   * @throws IllegalArgumentException if the flip type specified is null
   */
  Image flip(FlipType type) throws IllegalArgumentException;

  /**
   * Returns another image which is the result of this image being brightened by the specified
   * value. If a specific component of a pixel has reached the maximum value, this component will
   * remain at the maximum value while the others will still increase.
   *
   * @param value the increment for how much the channels of the pixel are increasing by
   * @return another image with the brightness manipulation applied
   * @throws IllegalArgumentException if the specified value to brighten the image by is not a
   *                                  positive integer
   */
  Image brighten(int value) throws IllegalArgumentException;

  /**
   * Returns another image which is the result of this image being darkened by the specified
   * value. If a specific component of a pixel has reached the minimum value, this component will
   * remain at the minimum value while the others will still decrease.
   *
   * @param value the increment for how much the channels of the pixel are decreasing by
   * @return another image with the darken manipulation applied
   * @throws IllegalArgumentException if the specified value to darken the image by is not a
   *                                  positive integer
   */
  Image darken(int value) throws IllegalArgumentException;


  /**
   * Returns another image which is the result of this image being filtered, using the specified
   * kernel.
   *
   * @param kernel the matrix that provides us with the math necessary to apply the filter
   * @return another image with the filter applied
   * @throws IllegalArgumentException if the kernel to apply is null
   */
  Image filter(double[][] kernel) throws IllegalArgumentException;

  /**
   * Returns another image which is the result of this image being transformed, using the specified
   * matrix.
   *
   * @param matrix the matrix that provides us with the math necessary to apply the transformation
   * @return another image with the transformation applied
   * @throws IllegalArgumentException if the transformation to apply is null
   */
  Image colorTransformation(double[][] matrix) throws IllegalArgumentException;

  /**
   * Returns another image which is the result of this image being downsized by the specified
   * percent.
   *
   * @param widthPercent  the percentage that we are downsizing the width by
   * @param heightPercent the percentage that we are downsizing the height by
   * @return another image with the downsizing applied
   * @throws IllegalArgumentException if the percent is not a valid percent to downsize by
   */
  Image downsize(int widthPercent, int heightPercent) throws IllegalArgumentException;
}