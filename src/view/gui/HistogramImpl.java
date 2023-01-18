package view.gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Color;
import java.util.Arrays;

import javax.swing.JPanel;

import model.image.Image;
import model.image.Pixel;

/**
 * An implementation of a histogram, which is a JPanel, and uses arrays to keep track of the
 * relative frequencies of the red, green, blue, and intensity values. It keeps track of the
 * frequencies of these specific values from 0-255, which are the possible values for each of
 * these components, and draws the histogram onto itself (a JPanel).
 */
public class HistogramImpl extends JPanel implements Histogram {

  private final int[] xCoords;
  private final int[] redValues;
  private final int[] greenValues;
  private final int[] blueValues;
  private final int[] intensityValues;

  /**
   * A constructor which sets the size of all the arrays that are fields to be 256 and sets the
   * size of this panel as well.
   */
  public HistogramImpl() {
    super();
    this.setPreferredSize(new Dimension(600, 300));

    this.xCoords = new int[256];
    this.redValues = new int[256];
    this.greenValues = new int[256];
    this.blueValues = new int[256];
    this.intensityValues = new int[256];
  }

  @Override
  public void updateHistogram(Image img) throws IllegalArgumentException {
    //ensuring that the image is not null
    if (img == null) {
      throw new IllegalArgumentException("Image cannot be null!");
    }

    // setting all the elements of the xCoordinates array to be equally spaced among the width
    this.setXCoords();

    // RESETTING ALL THE FREQUENCIES OF THE ARRAYS TO 0
    Arrays.fill(redValues, 0);
    Arrays.fill(greenValues, 0);
    Arrays.fill(blueValues, 0);
    Arrays.fill(intensityValues, 0);

    // populating the bins of the histogram by going through every pixel of the image and adding
    // to the correct classification
    Pixel[][] pixels = img.getPixels();
    for (int i = 0; i < img.getWidth(); i++) {
      for (int j = 0; j < img.getHeight(); j++) {
        Pixel p = pixels[i][j];
        int red = p.getRed();
        int green = p.getGreen();
        int blue = p.getBlue();
        int intensity = (int) (Math.round(p.getRed() + p.getBlue() + p.getGreen()) / 3.0);

        redValues[red] += 1;
        greenValues[green] += 1;
        blueValues[blue] += 1;
        intensityValues[intensity] += 1;
      }
    }

    // adjusting the frequencies accordingly
    this.adjustFrequencies();
  }

  /**
   * A helper method which sets the contents of the xCoords array to equally space the 256 points
   * amongst the width.
   */
  private void setXCoords() {

    // multiplying i by a scale factor to get the x value of every point to ensure that the 256
    // points are equally spaced through the width
    for (int i = 0; i < 256; i++) {
      xCoords[i] = (int) (Math.round((this.getWidth() / 256.0) * i));
    }
  }

  /**
   * Adjusts the frequencies to ensure that the greatest one will always be at the top of the
   * histogram and that the relative frequencies stay intact.
   */
  private void adjustFrequencies() {
    double max = this.getMaxFrequency();

    // calculating a scale factor to adjust every frequency by to ensure that they fit within the
    // height of the screen and always have the same relative height
    double scaleFactor = this.getHeight() / max;

    // looping through every value, adjusting by the scale factor, and then subtracting these
    // values from the height of the panel to ensure that the histogram is shown right side up,
    // since java starts at the top for y = 0 and then moves down as y increases by default
    for (int i = 0; i < 256; i++) {
      redValues[i] = (int) (redValues[i] * scaleFactor);
      redValues[i] = this.getHeight() - redValues[i];
      greenValues[i] = (int) (greenValues[i] * scaleFactor);
      greenValues[i] = this.getHeight() - greenValues[i];
      blueValues[i] = (int) (blueValues[i] * scaleFactor);
      blueValues[i] = this.getHeight() - blueValues[i];
      intensityValues[i] = (int) (intensityValues[i] * scaleFactor);
      intensityValues[i] = this.getHeight() - intensityValues[i];
    }
  }

  /**
   * Calculates the maximum frequency amongst any value for the components represented in the
   * histogram: red, green, blue, or intensity.
   */
  private double getMaxFrequency() {
    double max = 0.0;

    for (int i = 0; i < 256; i++) {
      max = Math.max(max, redValues[i]);
      max = Math.max(max, blueValues[i]);
      max = Math.max(max, greenValues[i]);
      max = Math.max(max, intensityValues[i]);
    }

    return max;
  }

  /**
   * This method paints lines onto the JPanel to represent the histogram. It draws a polyline,
   * which basically draws a line through all the points represented by (xCoords[i], ___Values[i])
   * for each specific component in the histogram. The result is one line for each of the red,
   * green, blue, and intensity frequencies.
   *
   * @param g the Graphics object that we are using to paint
   */
  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);

    g.setColor(Color.RED);
    g.drawPolyline(xCoords, redValues, 256);
    g.setColor(Color.GREEN);
    g.drawPolyline(xCoords, greenValues, 256);
    g.setColor(Color.BLUE);
    g.drawPolyline(xCoords, blueValues, 256);
    g.setColor(Color.BLACK);
    g.drawPolyline(xCoords, intensityValues, 256);
  }
}