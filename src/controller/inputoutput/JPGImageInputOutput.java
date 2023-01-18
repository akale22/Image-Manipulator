package controller.inputoutput;

/**
 * A class representing image IO on .jpg images.
 */
public class JPGImageInputOutput extends AbstractImageInputOutput {

  @Override
  protected String getFileType() {
    return "jpg";
  }
}
