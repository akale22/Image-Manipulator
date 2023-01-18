package controller.inputoutput;

/**
 * A class representing image IO on .png images.
 */
public class PNGImageInputOutput extends AbstractImageInputOutput {

  @Override
  protected String getFileType() {
    return "png";
  }
}
