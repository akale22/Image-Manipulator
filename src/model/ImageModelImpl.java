package model;

import java.util.HashMap;

import model.enums.FlipType;
import model.enums.GreyscaleComponentType;
import model.image.Image;
import model.image.ImageImpl;

/**
 * A class representing an implementation of the model which uses a hashmap to store a map of
 * image names to the images themselves.
 */
// INVARIANT: The hashmap contained within this model implementation cannot ever be null.
public class ImageModelImpl implements ImageModel {

  private final HashMap<String, Image> images;

  /**
   * A constructor which takes in no arguments and instantiates the hashmap to an empty one.
   */
  public ImageModelImpl() {
    this.images = new HashMap<String, Image>();
  }

  @Override
  public Image getImage(String s) throws IllegalArgumentException {
    Image img = this.images.get(s);
    Image imgCopy;

    if (img == null) {
      throw new IllegalArgumentException("This image doesn't exist!");
    } else {
      imgCopy = new ImageImpl(img.getWidth(), img.getHeight(), img.getMaxValue(), img.getPixels());
    }
    return imgCopy;
  }

  @Override
  public void setImage(String name, Image img) {
    if (img == null) {
      throw new IllegalArgumentException("Null image!");
    }
    this.images.put(name, img);
  }

  @Override
  public void greyscaleComponent(GreyscaleComponentType type, String oldFileName,
                                 String newFileName) throws IllegalArgumentException {
    Image img = this.getImage(oldFileName);
    Image newImg = img.greyscaleComponent(type);
    this.setImage(newFileName, newImg);
  }

  @Override
  public void brighten(int value, String oldFileName, String newFileName)
          throws IllegalArgumentException {
    Image img = this.getImage(oldFileName);
    Image newImg = img.brighten(value);
    this.setImage(newFileName, newImg);
  }

  @Override
  public void darken(int value, String oldFileName, String newFileName)
          throws IllegalArgumentException {
    Image img = this.getImage(oldFileName);
    Image newImg = img.darken(value);
    this.setImage(newFileName, newImg);
  }

  @Override
  public void flip(FlipType type, String oldFileName, String newFileName)
          throws IllegalArgumentException {
    Image img = this.getImage(oldFileName);
    Image newImg = img.flip(type);
    this.setImage(newFileName, newImg);
  }

  @Override
  public void blur(String oldFileName, String newFileName) throws IllegalArgumentException {
    Image img = this.getImage(oldFileName);
    double[][] kernel = new double[][]
        {{0.0625, 0.125, 0.0625}, {0.125, 0.25, 0.125}, {0.0625, 0.125, 0.0625}};
    Image newImg = img.filter(kernel);
    this.setImage(newFileName, newImg);
  }

  @Override
  public void sharpen(String oldFileName, String newFileName) throws IllegalArgumentException {
    Image img = this.getImage(oldFileName);
    double[][] kernel = new double[][]
        {{-0.125, -0.125, -0.125, -0.125, -0.125},
                {-0.125, 0.25, 0.25, 0.25, -0.125},
                {-0.125, 0.25, 1, 0.25, -0.125},
                {-0.125, 0.25, 0.25, 0.25, -0.125},
                {-0.125, -0.125, -0.125, -0.125, -0.125}};
    Image newImg = img.filter(kernel);
    this.setImage(newFileName, newImg);
  }

  @Override
  public void greyscaleColorTransformation(String oldFileName, String newFileName) {
    Image img = this.getImage(oldFileName);
    double[][] matrix = new double[][]
        {{0.2126, 0.7152, 0.0722}, {0.2126, 0.7152, 0.0722}, {0.2126, 0.7152, 0.0722}};
    Image newImg = img.colorTransformation(matrix);
    this.setImage(newFileName, newImg);
  }

  @Override
  public void sepia(String oldFileName, String newFileName) {
    Image img = this.getImage(oldFileName);
    double[][] matrix = new double[][]
        {{0.393, 0.769, 0.189}, {0.349, 0.686, 0.168}, {0.272, 0.534, 0.131}};
    Image newImg = img.colorTransformation(matrix);
    this.setImage(newFileName, newImg);
  }

  @Override
  public void downsize(int widthPercent, int heightPercent, String oldFileName,
                       String newFileName) throws IllegalArgumentException {
    Image img = this.getImage(oldFileName);
    Image newImg = img.downsize(widthPercent, heightPercent);
    this.setImage(newFileName, newImg);
  }
}
