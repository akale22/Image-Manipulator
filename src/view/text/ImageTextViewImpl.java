package view.text;

import java.io.IOException;

/**
 * A class representing an implementation of the text view of an image which displays information
 * to the user through text.
 */
public class ImageTextViewImpl implements ImageTextView {

  private final Appendable out;

  /**
   * Constructor for ImageTextViewImpl that sets the appendable to the parameter.
   */
  public ImageTextViewImpl(Appendable out) {
    if (out == null) {
      throw new IllegalArgumentException("Appendable cannot be null!");
    }

    this.out = out;
  }

  /**
   * Constructor for ImageTextViewImpl that sets the appendable to the console's out.
   */
  public ImageTextViewImpl() {
    this(System.out);
  }

  @Override
  public void renderMessage(String message) throws IOException {
    this.out.append(message);
  }
}
