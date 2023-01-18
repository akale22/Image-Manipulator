package controller.commands;

import model.ImageModel;

/**
 * A command to blur the specified image and store it by the specified name.
 */
public class Blur extends AbstractImageCommand {

  /**
   * A constructor which initializes the fields of the blur command object to the specified
   * values.
   *
   * @param in  the name of the image that we are blurring
   * @param out the name of the image that we are storing the blurred image by
   */
  public Blur(String in, String out) {
    super(in, out);
  }

  @Override
  public void execute(ImageModel model) {
    model.blur(in, out);
  }
}
