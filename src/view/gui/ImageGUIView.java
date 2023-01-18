package view.gui;

import java.awt.event.ActionListener;

import model.image.Image;

/**
 * An interface that represents a GUI view for the Image Processing Application. It contains
 * methods to update what is currently being shown on the screen and render messages to the
 * user or prompt them for more input, amongst other methods.
 */
public interface ImageGUIView {

  /**
   * Displays the GUI.
   */
  void display();

  /**
   * Refreshes the screen to show the most updated visuals.
   */
  void refresh();

  /**
   * Sets the listener for specific components to the given ActionListener.
   *
   * @param listener what we are setting as the listener for certain view components
   * @throws IllegalArgumentException if the listener is null.
   */
  void setListener(ActionListener listener) throws IllegalArgumentException;

  /**
   * Updates the image being shown in the GUI based on the given image.
   *
   * @param img the image to display in the GUI
   * @throws IllegalArgumentException if the image is null
   */
  void updateImage(Image img) throws IllegalArgumentException;

  /**
   * Updates the histogram being shown in the GUI based on the given image.
   *
   * @param img the image to display the histogram for in the GUI
   * @throws IllegalArgumentException if the image is null
   */
  void updateHistogram(Image img) throws IllegalArgumentException;

  /**
   * Gets user input when information is required.
   *
   * @param s the instructions that users are given for input
   * @return the input entered by a user
   * @throws IllegalArgumentException if the instructions given to users is null
   */
  String getUserInput(String s) throws IllegalArgumentException;

  /**
   * Displays an error message if something has gone wrong.
   *
   * @param s the error message to be displayed
   * @throws IllegalArgumentException if the error message to be displayed is null
   */
  void renderErrorMessage(String s) throws IllegalArgumentException;
}
