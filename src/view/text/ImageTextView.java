package view.text;

import java.io.IOException;

/**
 * An interface representing a view for the program, capable of displaying information to the
 * user.
 */
public interface ImageTextView {

  /**
   * Render a specific message to the provided data destination.
   *
   * @param message the message to be transmitted
   * @throws IOException if transmission of the message to the provided data destination fails
   */
  void renderMessage(String message) throws IOException;
}

