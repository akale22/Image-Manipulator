package view.gui;

import java.awt.Graphics;

import model.image.Image;

/**
 * An interface to represent a histogram for an image which displays relative frequencies
 * of specific aspects or components of the image. It contains methods to update the histogram's
 * contents to represent a specified image and to physically draw the histogram itself.
 */
public interface Histogram {

  /**
   * Updates the histogram's values to have the correct frequencies based on the image being
   * passed in.
   *
   * @param img the image that the histogram is updating its contents to represent.
   * @throws IllegalArgumentException if the image is null
   */
  void updateHistogram(Image img) throws IllegalArgumentException;

  /**
   * Draws a histogram to accurately represent the relative frequencies for an image, based on
   * the data of an image itself.
   *
   * @param g the Graphics object that we are using to paint
   */
  void paintComponent(Graphics g);
}
