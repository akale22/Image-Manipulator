package controller.commands;

import controller.inputoutput.BMPImageInputOutput;
import controller.inputoutput.JPGImageInputOutput;
import controller.inputoutput.PNGImageInputOutput;
import controller.inputoutput.PPMImageInputOutput;
import model.ImageModel;
import model.image.Image;

/**
 * A command which loads a specific image by the specified file path into the model.
 */
public class Load extends AbstractImageCommand {

  /**
   * A constructor which initializes the fields of the load command object to the specified
   * values.
   *
   * @param in  the filepath of the image that we want to load in
   * @param out the name that we want to store the loaded image by
   */
  public Load(String in, String out) {
    super(in, out);
  }

  // determines the type of file that we are working on and delegates the loading task to another
  // class
  @Override
  public void execute(ImageModel model) {
    Image img;

    // determining if the filename has enough characters to be valid
    int totalLength = in.length();
    if (totalLength < 5) {
      throw new IllegalArgumentException("Invalid file!");
    }

    String fileType = in.substring(totalLength - 3);
    switch (fileType) {
      case "ppm":
        img = new PPMImageInputOutput().load(in);
        break;
      case "bmp":
        img = new BMPImageInputOutput().load(in);
        break;
      case "jpg":
        img = new JPGImageInputOutput().load(in);
        break;
      case "png":
        img = new PNGImageInputOutput().load(in);
        break;
      default:
        throw new IllegalArgumentException("Invalid file!");
    }

    model.setImage(out, img);
  }
}


