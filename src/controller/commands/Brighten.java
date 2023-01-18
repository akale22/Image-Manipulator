package controller.commands;

import model.ImageModel;

/**
 * A command to brighten the specified image by a specified value and store it by the specified
 * name.
 */
public class Brighten extends AbstractImageCommand {

  private final int value;

  /**
   * A constructor which initializes the fields of the brighten command object to the specified
   * values.
   *
   * @param value how much we are brightening the image by
   * @param in    the name of the image that we are brightening
   * @param out   the name of the image that we are storing the brightened image by
   */
  public Brighten(int value, String in, String out) {
    super(in, out);
    this.value = value;
  }

  @Override
  public void execute(ImageModel model) {
    model.brighten(value, in, out);
  }
}