package controller.commands;

import model.ImageModel;
import model.enums.FlipType;

/**
 * A command to flip the specified image in a specific way and store it by the specified
 * name.
 */
public class Flip extends AbstractImageCommand {

  private final FlipType type;

  /**
   * A constructor which initializes the fields of the flip command object to the specified
   * values.
   *
   * @param type the way that we are flipping the image
   * @param in   the name of the image that we are flipping
   * @param out  the name of the image that we are storing the flipping image by
   */
  public Flip(FlipType type, String in, String out) {
    super(in, out);
    this.type = type;
  }

  @Override
  public void execute(ImageModel model) {
    model.flip(type, in, out);
  }
}
