package controller.commands;

import model.ImageModel;

/**
 * A command to apply a sepia color transformation the specified image and store it by the
 * specified name.
 */
public class Sepia extends AbstractImageCommand {

  /**
   * A constructor which initializes the fields of the sepia command object to the specified
   * values.
   *
   * @param in  the name of the image that we are applying a sepia transformation t
   * @param out the name of the image that we are storing the sepia transformed image by
   */
  public Sepia(String in, String out) {
    super(in, out);
  }

  @Override
  public void execute(ImageModel model) {
    model.sepia(in, out);
  }
}
