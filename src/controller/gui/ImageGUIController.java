package controller.gui;

import java.awt.event.ActionEvent;

/**
 * This interface represents a controller for a GUI which interprets user input through the
 * GUI and then delegates tasks to other components of the application.
 */
public interface ImageGUIController {

  /**
   * Determines what task to perform / delegate to the other parts of the application based on
   * the specified action event and its command.
   *
   * @param e the action event taking place
   */
  void actionPerformed(ActionEvent e);
}
