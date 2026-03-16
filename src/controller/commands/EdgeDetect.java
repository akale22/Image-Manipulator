package controller.commands;

import model.ImageModel;

/**
 * Applies a Laplacian edge-detection kernel to the image.
 */
public class EdgeDetect extends AbstractImageCommand {

  /**
   * Constructs this command.
   *
   * @param in  the image name to detect edges on
   * @param out the name for the edge-detected image
   */
  public EdgeDetect(String in, String out) {
    super(in, out);
  }

  @Override
  public void execute(ImageModel model) {
    double[][] kernel = new double[][]
        {{-1, -1, -1},
         {-1, 8, -1},
         {-1, -1, -1}};

    model.setImage(out, model.getImage(in).filter(kernel));
  }
}
