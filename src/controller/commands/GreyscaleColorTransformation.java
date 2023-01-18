package controller.commands;

import model.ImageModel;

/**
 * A command to apply a greyscale color transformation the specified image and store it by the
 * specified name.
 */
public class GreyscaleColorTransformation extends AbstractImageCommand {

  /**
   * A constructor which initializes the fields of the greyscale color transformation command object
   * to the specified values.
   *
   * @param in  the name of the image that we are applying the greyscale color transformation to
   * @param out the name of the image that we are storing the greyscaled image by
   */
  public GreyscaleColorTransformation(String in, String out) {
    super(in, out);
  }

  @Override
  public void execute(ImageModel model) {
    model.greyscaleColorTransformation(in, out);
  }
}
