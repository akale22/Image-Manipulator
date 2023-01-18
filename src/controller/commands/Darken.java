package controller.commands;


import model.ImageModel;

/**
 * A command to darken the specified image by a specified value and store it by the specified
 * name.
 */
public class Darken extends AbstractImageCommand {

  private final int value;

  /**
   * A constructor which initializes the fields of the darken command object to the specified
   * values.
   *
   * @param value how much we are darkening the image by
   * @param in    the name of the image that we are darkening
   * @param out   the name of the image that we are storing the darkened image by
   */
  public Darken(int value, String in, String out) {
    super(in, out);
    this.value = value;
  }

  @Override
  public void execute(ImageModel model) {
    model.darken(value, in, out);
  }
}