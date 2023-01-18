package controller.inputoutput;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import model.image.Image;
import model.image.ImageImpl;
import model.image.Pixel;
import model.image.RGBPixel;

/**
 * A class representing image IO on .ppm images. A .ppm file is a different type of image than
 * the other three that this application supports so far, so it has a different implementation
 * for loading and saving, and thus overrides the implementation in the abstract class.
 */
public class PPMImageInputOutput extends AbstractImageInputOutput {

  @Override
  public Image load(String imagePath) throws IllegalArgumentException {
    Scanner sc;

    try {
      sc = new Scanner(new FileInputStream(imagePath));
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("Invalid file contents!");
    }

    StringBuilder builder = new StringBuilder();

    //read the file line by line, and populate a string. This will throw away any comment lines
    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (s.charAt(0) != '#') {
        builder.append(s + System.lineSeparator());
      }
    }

    //now set up the scanner to read from the string we just built
    sc = new Scanner(builder.toString());

    String token;

    token = sc.next();
    if (!token.equals("P3")) {
      throw new IllegalArgumentException("Invalid PPM file: plain RAW file should begin with P3!");
    }

    int width = sc.nextInt();
    int height = sc.nextInt();
    int maxValue = sc.nextInt();

    Pixel[][] pixels = new RGBPixel[width][height];

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int r = sc.nextInt();
        int g = sc.nextInt();
        int b = sc.nextInt();
        pixels[j][i] = new RGBPixel(r, g, b);
      }
    }

    Image img = new ImageImpl(width, height, maxValue, pixels);
    return img;
  }


  @Override
  public void save(String filePath, Image img) throws IllegalArgumentException {
    StringBuilder content = new StringBuilder();
    Pixel[][] pixels = img.getPixels();

    content.append("P3" + System.lineSeparator());
    content.append(img.getWidth() + " " + img.getHeight() + System.lineSeparator());
    content.append(img.getMaxValue() + System.lineSeparator());

    for (int i = 0; i < img.getHeight(); i++) {
      for (int j = 0; j < img.getWidth(); j++) {
        content.append(pixels[j][i].getRed() + System.lineSeparator());
        content.append(pixels[j][i].getGreen() + System.lineSeparator());
        content.append(pixels[j][i].getBlue() + System.lineSeparator());
      }
    }

    try {
      FileWriter myWriter = new FileWriter(filePath);
      myWriter.write(content.toString());
      myWriter.close();
    } catch (IOException e) {
      System.out.println("An error occurred while trying to save the code.");
    }
  }

  @Override
  protected String getFileType() {
    return "ppm";
  }
}
