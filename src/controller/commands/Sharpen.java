package controller.commands;

import model.ImageModel;

/**
 * A command to sharpen the specified image and store it by the specified name.
 */
public class Sharpen extends AbstractImageCommand {

  /**
   * A constructor which initializes the fields of the sharpen command object to the specified
   * values.
   *
   * @param in  the name of the image that we are sharpening
   * @param out the name of the image that we are storing the sharpened image by
   */
  public Sharpen(String in, String out) {
    super(in, out);
  }


  @Override
  public void execute(ImageModel model) {
    model.sharpen(in, out);
  }
}
