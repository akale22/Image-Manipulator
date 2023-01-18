package controller.commands;

import controller.inputoutput.BMPImageInputOutput;
import controller.inputoutput.JPGImageInputOutput;
import controller.inputoutput.PNGImageInputOutput;
import controller.inputoutput.PPMImageInputOutput;
import model.ImageModel;
import model.image.Image;

/**
 * A command which saves a specific image to the specified file path.
 */
public class Save extends AbstractImageCommand {

  /**
   * A constructor which initializes the fields of the save command object to the specified
   * values.
   *
   * @param fileName  the filepath that we want to save the image to
   * @param imageName the name of the image that we want to save
   */
  public Save(String fileName, String imageName) {
    super(fileName, imageName);
  }

  // determines the type of file that we are working on and delegates the saving task to another
  // class
  @Override
  public void execute(ImageModel model) {
    Image img = model.getImage(out);

    // determining if the filename has enough characters to be valid
    int totalLength = in.length();
    if (totalLength < 5) {
      throw new IllegalArgumentException("Invalid file!");
    }

    String fileType = in.substring(totalLength - 3);
    switch (fileType) {
      case "ppm":
        new PPMImageInputOutput().save(in, img);
        break;
      case "bmp":
        new BMPImageInputOutput().save(in, img);
        break;
      case "jpg":
        new JPGImageInputOutput().save(in, img);
        break;
      case "png":
        new PNGImageInputOutput().save(in, img);
        break;
      default:
        throw new IllegalArgumentException("Invalid filename!");
    }
  }
}

