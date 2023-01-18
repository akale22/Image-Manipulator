import java.util.Objects;

import model.ImageModel;
import model.enums.FlipType;
import model.enums.GreyscaleComponentType;
import model.image.Image;

/**
 * A mock model to confirm that the correct methods are being called by the controller.
 */
public class MockModel implements ImageModel {

  private final StringBuilder log;

  /**
   * Instantiates a mock model with a log.
   * @param log the log that we are using to keep track of operations
   */
  public MockModel(StringBuilder log) {
    this.log = Objects.requireNonNull(log);
  }


  @Override
  public Image getImage(String imageName) throws IllegalArgumentException {
    log.append("Image with name " + imageName + " was retrieved.\n");
    return null;
  }

  @Override
  public void setImage(String name, Image img) throws IllegalArgumentException {
    log.append("Image with name " + name + " was set.\n");
  }

  @Override
  public void brighten(int value, String oldFileName, String newFileName) throws
          IllegalArgumentException {
    log.append("Image with name " + oldFileName + " was brightened by " + value + " and saved as " +
            newFileName + ".\n");
  }

  @Override
  public void darken(int value, String oldFileName, String newFileName) throws
          IllegalArgumentException {
    log.append("Image with name " + oldFileName + " was darkened by " + value + " and saved as " +
            newFileName + ".\n");
  }

  @Override
  public void greyscaleComponent(GreyscaleComponentType type, String oldFileName,
                                 String newFileName) throws IllegalArgumentException {
    log.append("Image with name " + oldFileName + " was greyscaled component wise by " + type +
            " and saved as " + newFileName + ".\n");
  }

  @Override
  public void flip(FlipType type, String oldFileName, String newFileName) throws
          IllegalArgumentException {
    log.append("Image with name " + oldFileName + " was flipped " + type + " and saved as " +
            newFileName + ".\n");
  }

  @Override
  public void blur(String oldFileName, String newFileName) throws IllegalArgumentException {
    log.append("Image with name " + oldFileName + " was blurred and saved as " + newFileName +
            ".\n");
  }

  @Override
  public void sharpen(String oldFileName, String newFileName) throws IllegalArgumentException {
    log.append("Image with name " + oldFileName + " was sharpened and saved as " + newFileName +
            ".\n");
  }

  @Override
  public void greyscaleColorTransformation(String oldFileName, String newFileName) {
    log.append("Image with name " + oldFileName + " was greyscaled with a color transformation " +
            "and saved as " + newFileName + ".\n");
  }

  @Override
  public void sepia(String oldFileName, String newFileName) {
    log.append("Image with name " + oldFileName + " has a sepia filter called to it and saved as "
            + newFileName + ".\n");
  }

  @Override
  public void downsize(int widthPercent, int heightPercent, String oldFileName, String newFileName)
          throws IllegalArgumentException {
    log.append("Image with name " + oldFileName + " had its width downsized by " + widthPercent +
            " and its height downsized by " + heightPercent + " and was saved as "
            + newFileName + ".\n");
  }
}
