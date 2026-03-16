package controller.commands;

import model.ImageModel;
import model.image.Image;

/**
 * Rotates an image by 90 degrees counterclockwise.
 */
public class RotateCounterClockwise extends AbstractImageCommand {

  /**
   * Constructs this command.
   *
   * @param in  the image name to rotate
   * @param out the name to save the rotated image as
   */
  public RotateCounterClockwise(String in, String out) {
    super(in, out);
  }

  @Override
  public void execute(ImageModel model) {
    Image image = model.getImage(in);
    model.setImage(out, ImageTransformUtil.rotate90CounterClockwise(image));
  }
}
