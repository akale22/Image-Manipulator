package view.gui;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JScrollPane;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import model.image.Image;
import model.image.Pixel;

/**
 * An implementation of an ImageGUIView which uses JSwing components to put together the specific
 * GUI. This GUI consists of buttons that deal with IO on images, buttons for manipulations on
 * images, and sections to display an image and a histogram representing the component
 * information of that image. If an image is bigger than the space allotted, then the area will
 * become scrollable so a user can see all parts of their image.
 */
// INVARIANT: The buttons, panels, and every other component from JSwing will always never be
// null and the layout of the screen will not change since the fields are final.
public class ImageGUIViewImpl extends JFrame implements ImageGUIView {

  private final JButton loadButton;
  private final JButton saveButton;
  private final JButton brightenButton;
  private final JButton darkenButton;
  private final JButton horizontalFlipButton;
  private final JButton verticalFlipButton;
  private final JButton redComponentButton;
  private final JButton greenComponentButton;
  private final JButton blueComponentButton;
  private final JButton valueComponentButton;
  private final JButton intensityComponentButton;
  private final JButton lumaComponentButton;
  private final JButton blurButton;
  private final JButton sharpenButton;
  private final JButton greyscaleButton;
  private final JButton sepiaButton;
  private final JButton downsizeButton;
  private final JPanel mainPanel;
  private final JLabel imageLabel;
  private final HistogramImpl histogram;


  /**
   * Constructor which puts together the components of the GUI.
   */
  public ImageGUIViewImpl() {
    super();

    // sets basic properties of the gui itself
    this.setTitle("Image Processor");
    this.setSize(1000, 800);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // creating the mainPanel
    mainPanel = new JPanel();
    mainPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
    this.add(mainPanel);

    // creating the button panel
    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
    mainPanel.add(buttonPanel);

    // creating the io button panel
    JPanel ioButtonPanel = new JPanel();
    ioButtonPanel.setLayout(new GridLayout(2, 1));
    ioButtonPanel.setBorder(BorderFactory.createTitledBorder("File"));
    buttonPanel.add(ioButtonPanel);

    // creating the manipulation button panel
    JPanel manipulationButtonPanel = new JPanel();
    manipulationButtonPanel.setLayout(new GridLayout(15, 1));
    manipulationButtonPanel.setBorder(BorderFactory.createTitledBorder("Manipulations"));
    buttonPanel.add(manipulationButtonPanel);

    // adding the load button
    loadButton = new JButton("load");
    loadButton.setActionCommand("load");
    ioButtonPanel.add(loadButton);

    // adding the save button
    saveButton = new JButton("save");
    saveButton.setActionCommand("save");
    ioButtonPanel.add(saveButton);

    // adding the brighten button
    brightenButton = new JButton("brighten");
    brightenButton.setActionCommand("brighten");
    manipulationButtonPanel.add(brightenButton);

    // adding the darken button
    darkenButton = new JButton("darken");
    darkenButton.setActionCommand("darken");
    manipulationButtonPanel.add(darkenButton);

    // adding the horizontal flip button
    horizontalFlipButton = new JButton("horizontal flip");
    horizontalFlipButton.setActionCommand("horizontal flip");
    manipulationButtonPanel.add(horizontalFlipButton);

    // adding the vertical flip button
    verticalFlipButton = new JButton("vertical flip");
    verticalFlipButton.setActionCommand("vertical flip");
    manipulationButtonPanel.add(verticalFlipButton);

    // adding the red component button
    redComponentButton = new JButton("red component");
    redComponentButton.setActionCommand("red component");
    manipulationButtonPanel.add(redComponentButton);

    // adding the green component button
    greenComponentButton = new JButton("green component");
    greenComponentButton.setActionCommand("green component");
    manipulationButtonPanel.add(greenComponentButton);

    // adding the blue component button
    blueComponentButton = new JButton("blue component");
    blueComponentButton.setActionCommand("blue component");
    manipulationButtonPanel.add(blueComponentButton);

    // adding the value component button
    valueComponentButton = new JButton("value component");
    valueComponentButton.setActionCommand("value component");
    manipulationButtonPanel.add(valueComponentButton);

    // adding the intensity component button
    intensityComponentButton = new JButton("intensity component");
    intensityComponentButton.setActionCommand("intensity component");
    manipulationButtonPanel.add(intensityComponentButton);

    // adding the luma component button
    lumaComponentButton = new JButton("luma component");
    lumaComponentButton.setActionCommand("luma component");
    manipulationButtonPanel.add(lumaComponentButton);

    // adding the blur button
    blurButton = new JButton("blur");
    blurButton.setActionCommand("blur");
    manipulationButtonPanel.add(blurButton);

    // adding the sharpen button
    sharpenButton = new JButton("sharpen");
    sharpenButton.setActionCommand("sharpen");
    manipulationButtonPanel.add(sharpenButton);

    // adding the greyscale button
    greyscaleButton = new JButton("greyscale");
    greyscaleButton.setActionCommand("greyscale");
    manipulationButtonPanel.add(greyscaleButton);

    // adding the sepia button
    sepiaButton = new JButton("sepia");
    sepiaButton.setActionCommand("sepia");
    manipulationButtonPanel.add(sepiaButton);

    // adding the downsize button
    downsizeButton = new JButton("downsize");
    downsizeButton.setActionCommand("downsize");
    manipulationButtonPanel.add(downsizeButton);

    // making a panel for the image and the histogram
    JPanel mediaPanel = new JPanel();
    mediaPanel.setLayout(new BoxLayout(mediaPanel, BoxLayout.Y_AXIS));
    mainPanel.add(mediaPanel);

    // creating a image panel
    JPanel imagePanel = new JPanel();
    imagePanel.setBorder(BorderFactory.createTitledBorder("Image"));
    mediaPanel.add(imagePanel);

    // creating an imageScrollPanel and adding a label to it
    JPanel imageScrollPanel = new JPanel();
    imageLabel = new JLabel();
    imageScrollPanel.add(imageLabel);

    // creating an image scroll pane and decorating the imageScrollPanel to be scrollable
    JScrollPane imageScrollPane = new JScrollPane(imageScrollPanel);
    imagePanel.add(imageScrollPane);
    imageScrollPane.setPreferredSize(new Dimension(600, 300));

    // creating a histogramPanel
    JPanel histogramPanel = new JPanel();
    histogramPanel.setBorder(BorderFactory.createTitledBorder("Histogram"));
    mediaPanel.add(histogramPanel);

    // creating a histogram
    histogram = new HistogramImpl();
    histogramPanel.add(histogram);
  }

  @Override
  public void display() {
    this.setVisible(true);
  }

  @Override
  public void refresh() {
    this.repaint();
  }

  @Override
  public void setListener(ActionListener listener) {
    if (listener == null) {
      throw new IllegalArgumentException("Listener cannot be null!");
    }

    loadButton.addActionListener(listener);
    saveButton.addActionListener(listener);
    brightenButton.addActionListener(listener);
    darkenButton.addActionListener(listener);
    horizontalFlipButton.addActionListener(listener);
    verticalFlipButton.addActionListener(listener);
    redComponentButton.addActionListener(listener);
    greenComponentButton.addActionListener(listener);
    blueComponentButton.addActionListener(listener);
    valueComponentButton.addActionListener(listener);
    intensityComponentButton.addActionListener(listener);
    lumaComponentButton.addActionListener(listener);
    blurButton.addActionListener(listener);
    sharpenButton.addActionListener(listener);
    greyscaleButton.addActionListener(listener);
    sepiaButton.addActionListener(listener);
    downsizeButton.addActionListener(listener);
  }

  @Override
  public void updateImage(Image img) throws IllegalArgumentException {
    if (img == null) {
      throw new IllegalArgumentException("Image cannot be null!");
    }

    Pixel[][] pixels = img.getPixels();
    BufferedImage image =
            new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);

    // populating the buffered image with the correct values
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

    // setting the icon of the image label so that it now displays the image
    imageLabel.setIcon(new ImageIcon(image));
  }

  @Override
  public void updateHistogram(Image img) throws IllegalArgumentException {
    if (img == null) {
      throw new IllegalArgumentException("Image cannot be null!");
    }

    histogram.updateHistogram(img);
  }

  @Override
  public String getUserInput(String s) {
    if (s == null) {
      throw new IllegalArgumentException("Instruction on what to enter is null!");
    }

    return JOptionPane.showInputDialog(s);
  }

  @Override
  public void renderErrorMessage(String s) {
    if (s == null) {
      throw new IllegalArgumentException("Error message is null!");
    }

    JOptionPane.showMessageDialog(mainPanel, s);
  }
}





