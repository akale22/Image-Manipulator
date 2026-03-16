package controller.commands;

import model.image.Image;
import model.image.ImageImpl;
import model.image.Pixel;
import model.image.RGBPixel;

/**
 * A utility class with reusable image transformations used by command implementations.
 */
public final class ImageTransformUtil {

  private ImageTransformUtil() {
    // utility class
  }

  /**
   * Rotates the given image by 90 degrees clockwise.
   *
   * @param image the image to rotate
   * @return a rotated image
   */
  public static Image rotate90Clockwise(Image image) {
    if (image == null) {
      throw new IllegalArgumentException("Image cannot be null!");
    }

    Pixel[][] oldPixels = image.getPixels();
    int width = image.getWidth();
    int height = image.getHeight();
    Pixel[][] newPixels = new RGBPixel[height][width];

    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        Pixel p = oldPixels[x][y];
        int newX = height - 1 - y;
        int newY = x;
        newPixels[newX][newY] = new RGBPixel(p.getRed(), p.getGreen(), p.getBlue());
      }
    }

    return new ImageImpl(height, width, image.getMaxValue(), newPixels);
  }

  /**
   * Rotates the given image by 90 degrees counterclockwise.
   *
   * @param image the image to rotate
   * @return a rotated image
   */
  public static Image rotate90CounterClockwise(Image image) {
    if (image == null) {
      throw new IllegalArgumentException("Image cannot be null!");
    }

    Pixel[][] oldPixels = image.getPixels();
    int width = image.getWidth();
    int height = image.getHeight();
    Pixel[][] newPixels = new RGBPixel[height][width];

    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        Pixel p = oldPixels[x][y];
        int newX = y;
        int newY = width - 1 - x;
        newPixels[newX][newY] = new RGBPixel(p.getRed(), p.getGreen(), p.getBlue());
      }
    }

    return new ImageImpl(height, width, image.getMaxValue(), newPixels);
  }

  /**
   * Performs per-channel contrast stretching by mapping each channel min->0 and max->255.
   *
   * @param image the image to adjust
   * @return an image with stretched contrast
   */
  public static Image autoContrast(Image image) {
    if (image == null) {
      throw new IllegalArgumentException("Image cannot be null!");
    }

    Pixel[][] oldPixels = image.getPixels();
    int width = image.getWidth();
    int height = image.getHeight();

    int minRed = image.getMaxValue();
    int minGreen = image.getMaxValue();
    int minBlue = image.getMaxValue();
    int maxRed = 0;
    int maxGreen = 0;
    int maxBlue = 0;

    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        Pixel p = oldPixels[x][y];
        minRed = Math.min(minRed, p.getRed());
        minGreen = Math.min(minGreen, p.getGreen());
        minBlue = Math.min(minBlue, p.getBlue());
        maxRed = Math.max(maxRed, p.getRed());
        maxGreen = Math.max(maxGreen, p.getGreen());
        maxBlue = Math.max(maxBlue, p.getBlue());
      }
    }

    Pixel[][] newPixels = new RGBPixel[width][height];
    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        Pixel p = oldPixels[x][y];
        int newRed = stretchComponent(p.getRed(), minRed, maxRed, image.getMaxValue());
        int newGreen = stretchComponent(p.getGreen(), minGreen, maxGreen, image.getMaxValue());
        int newBlue = stretchComponent(p.getBlue(), minBlue, maxBlue, image.getMaxValue());
        newPixels[x][y] = new RGBPixel(newRed, newGreen, newBlue);
      }
    }

    return new ImageImpl(width, height, image.getMaxValue(), newPixels);
  }

  private static int stretchComponent(int value, int min, int max, int maxValue) {
    if (max == min) {
      return value;
    }

    return (int) Math.round(((value - min) / (double) (max - min)) * maxValue);
  }
}
