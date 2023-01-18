import java.awt.event.ActionListener;

import model.image.Image;
import view.gui.ImageGUIView;

/**
 * Represents the MockView class. It is a mock for the view class in which the methods do not do
 * anything, but is rather useful for testing.
 */
public class MockView implements ImageGUIView {

  @Override
  public void setListener(ActionListener listener) {
    return;
  }

  @Override
  public void updateImage(Image img) {
    return;
  }

  @Override
  public void updateHistogram(Image img) {
    return;
  }

  @Override
  public void display() {
    return;
  }

  @Override
  public void refresh() {
    return;
  }

  @Override
  public String getUserInput(String s) {
    // using a default 10 as a user input for the manipulations that need more information, such
    // as brighten, darken, and downsize
    return "10";
  }

  @Override
  public void renderErrorMessage(String s) {
    return;
  }
}
