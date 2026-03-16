package controller.commands;

import model.ImageModel;
import model.image.Image;

/**
 * Automatically stretches contrast for all channels independently.
 */
public class AutoContrast extends AbstractImageCommand {

  /**
   * Constructs this command.
   *
   * @param in  the image name to adjust
   * @param out the name for the contrast-adjusted image
   */
  public AutoContrast(String in, String out) {
    super(in, out);
  }

  @Override
  public void execute(ImageModel model) {
    Image image = model.getImage(in);
    model.setImage(out, ImageTransformUtil.autoContrast(image));
  }
}
