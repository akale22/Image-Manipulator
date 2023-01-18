package controller.inputoutput;

import model.image.Image;

/**
 * An interface which contains functionality to deal with input and output, more specifically, in
 * terms of reading an image file and writing an image to a file. Each implementation represents
 * loading and saving a different image type (.png, .jpg. etc).
 */
public interface ImageInputOutput {

  /**
   * Loads an image to the program based on the given file path.
   *
   * @param imagePath the filepath to load the image from
   * @return an image that has been loaded
   * @throws IllegalArgumentException if the filepath doesn't exist or is of incorrect format, or
   *                                  if there are any other issues encountered when loading the
   *                                  image
   */
  Image load(String imagePath) throws IllegalArgumentException;

  /**
   * Saves a specified image to the specified file path.
   *
   * @param filePath the file path that we are saving the image to
   * @param img      the image that we are saving
   * @throws IllegalArgumentException if an IO error occurs while saving or if the image doesn't
   *                                  exist
   */
  void save(String filePath, Image img) throws IllegalArgumentException;
}
