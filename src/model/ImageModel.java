package model;

import model.enums.FlipType;
import model.enums.GreyscaleComponentType;
import model.image.Image;

/**
 * An interface representing the model for this image application which is able to store multiple
 * images and apply manipulations on these images. It contains methods to observe different
 * images in the application, to deal with input and output, and to manipulate the images and
 * store the new manipulated versions by a different reference.
 */
public interface ImageModel {

  /**
   * Returns a deep copy of the image represented by the given name.
   *
   * @param imageName the specified name that refers to the image desired
   * @return a deep copy of the image represented by this string name
   * @throws IllegalArgumentException if there is no image in the application stored by the
   *                                  specified name when this method is called
   */
  Image getImage(String imageName) throws IllegalArgumentException;

  /**
   * Stores the provided image by the specified name in the model.
   *
   * @param name the name to store the image by
   * @param img  the image to store
   * @throws IllegalArgumentException if the image is null
   */
  void setImage(String name, Image img) throws IllegalArgumentException;

  /**
   * Creates a new image which is the old image brightened by the specified value, and stores it
   * by the specified name.
   *
   * @param value       how much to brighten the image by
   * @param oldFileName the name of the image that we want to brighten
   * @param newFileName the name that we store the newly brightened image by
   * @throws IllegalArgumentException if the image that we want to brighten doesn't exist
   */
  void brighten(int value, String oldFileName, String newFileName) throws IllegalArgumentException;


  /**
   * Creates a new image which is the old image darkened by the specified value, and stores it
   * by the specified name.
   *
   * @param value       how much to darken the image by
   * @param oldFileName the name of the image that we want to darken
   * @param newFileName the name that we store the newly darkened image by
   * @throws IllegalArgumentException if the image that we want to darken doesn't exist
   */
  void darken(int value, String oldFileName, String newFileName) throws IllegalArgumentException;

  /**
   * Creates a new image which is the old image with the specified greyscale applied. This new
   * greyscale image is then stored in the model.
   *
   * @param type        the type of greyscale transformation to apply
   * @param oldFileName the name of the image that we want to apply the greyscale to
   * @param newFileName the name that we store the new greyscale image by
   * @throws IllegalArgumentException if the image that we want to greyscale doesn't exist
   */
  void greyscaleComponent(GreyscaleComponentType type, String oldFileName, String newFileName)
          throws IllegalArgumentException;

  /**
   * Creates a new image which is the old image with the specified flip applied. This new
   * flipped image is then stored in the model.
   *
   * @param type        the type of flip to apply
   * @param oldFileName the name of the image that we want to apply the flip to
   * @param newFileName the name that we store the new flipped image by
   * @throws IllegalArgumentException if the image that we want to flip doesn't exist
   */
  void flip(FlipType type, String oldFileName, String newFileName) throws IllegalArgumentException;

  /**
   * Creates a new image which is the old image blurred. This blurred image is then stored in the
   * model.
   *
   * @param oldFileName the name of the image that we want to apply the filter to
   * @param newFileName the name that we store the new filtered image by
   * @throws IllegalArgumentException if the image that we want to flip doesn't exist
   */
  void blur(String oldFileName, String newFileName) throws IllegalArgumentException;

  /**
   * Creates a new image which is the old image sharpened. This sharpened image is then stored in
   * the model.
   *
   * @param oldFileName the name of the image that we want to apply the filter to
   * @param newFileName the name that we store the new filtered image by
   * @throws IllegalArgumentException if the image that we want to flip doesn't exist
   */
  void sharpen(String oldFileName, String newFileName) throws IllegalArgumentException;

  /**
   * Creates a new image which is the old image with a greyscale color transformation applied.
   * This new greyscaled image is stored in the model.
   *
   * @param oldFileName the name of the image that we want to apply the greyscale to
   * @param newFileName the name that we store the new greyscale image by
   * @throws IllegalArgumentException if the image that we want to greyscale doesn't exist
   */
  void greyscaleColorTransformation(String oldFileName, String newFileName)
          throws IllegalArgumentException;

  /**
   * Creates a new image which is the old image with a sepia color transformation applied.
   * This new sepia image is stored in the model.
   *
   * @param oldFileName the name of the image that we want to apply the sepia to
   * @param newFileName the name that we store the new sepia image by
   * @throws IllegalArgumentException if the image that we want to sepia doesn't exist
   */
  void sepia(String oldFileName, String newFileName) throws IllegalArgumentException;

  /**
   * Creates a new image which is the old image downsized by the specified percent, and stores it
   * by the specified name.
   *
   * @param widthPercent  the percent that we want to downsize the width by
   * @param heightPercent the percent that we want to downsize the height by
   * @param oldFileName   the name of the image that we want to downsize
   * @param newFileName   the name that we store the newly downsized image by
   * @throws IllegalArgumentException if the image that we want to downsize doesn't exist
   */
  void downsize(int widthPercent, int heightPercent, String oldFileName, String newFileName)
          throws IllegalArgumentException;

}
