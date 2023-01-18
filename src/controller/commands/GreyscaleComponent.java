package controller.commands;

import model.ImageModel;
import model.enums.GreyscaleComponentType;

/**
 * A command to apply a specific greyscale manipulation to the specified image in a specific way
 * and store it by the specified name.
 */
public class GreyscaleComponent extends AbstractImageCommand {

  private final GreyscaleComponentType type;

  /**
   * A constructor which initializes the fields of the greyscale command object to the specified
   * values.
   *
   * @param type the greyscale type that we are applying
   * @param in   the name of the image that we are applying a greyscale to
   * @param out  the name of the image that we are storing greyscaled image by
   */
  public GreyscaleComponent(GreyscaleComponentType type, String in, String out) {
    super(in, out);
    this.type = type;
  }

  @Override
  public void execute(ImageModel model) {
    model.greyscaleComponent(type, in, out);
  }
}
