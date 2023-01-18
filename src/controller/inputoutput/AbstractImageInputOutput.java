package controller.inputoutput;


import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import model.image.Image;
import model.image.ImageImpl;
import model.image.Pixel;
import model.image.RGBPixel;

/**
 * An abstract class that contains implementations of methods similar to all ImageReaderWriters.
 * The implementations of the methods in the ImageInputOutput interface here are applicable to
 * most of the types of an image.
 */
public abstract class AbstractImageInputOutput implements ImageInputOutput {

  @Override
  public Image load(String imagePath) throws IllegalArgumentException {
    BufferedImage buffImg;
    Image img;

    try {
      buffImg = ImageIO.read(new File(imagePath));
    } catch (IOException ioe) {
      throw new IllegalArgumentException("Unable to read image");
    }

    int height = buffImg.getHeight();
    int width = buffImg.getWidth();
    int maxValue = 255;

    Pixel[][] pixels = new RGBPixel[width][height];

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {

        //Retrieving contents of a pixel
        int pixel = buffImg.getRGB(j, i);

        //Creating a Color object from pixel value
        Color color = new Color(pixel);

        //Retrieving the R G B values
        int r = color.getRed();
        int g = color.getGreen();
        int b = color.getBlue();
        pixels[j][i] = new RGBPixel(r, g, b);
      }
    }

    img = new ImageImpl(width, height, maxValue, pixels);
    return img;
  }

  @Override
  public void save(String filePath, Image img) throws IllegalArgumentException {
    Pixel[][] pixels = img.getPixels();

    BufferedImage image =
            new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
    for (int i = 0; i < img.getHeight(); i++) {
      for (int j = 0; j < img.getWidth(); j++) {
        Pixel p = pixels[j][i];
        int r = p.getRed();
        int g = p.getGreen();
        int b = p.getBlue();

        Color c = new Color(r, g, b);
        int color = c.getRGB();

        image.setRGB(j, i, color);
      }
    }
    try {
      ImageIO.write(image, this.getFileType(), new File(filePath));
    } catch (IOException ioe) {
      throw new IllegalArgumentException("Unable to write to a file!");
    }
  }

  /**
   * Returns a string representing the filetype of the image that we are handling IO on.
   *
   * @return a string representing the filetype we are working with
   */
  protected abstract String getFileType();
}