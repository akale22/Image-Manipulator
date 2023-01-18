package controller.inputoutput;

/**
 * A class representing image IO on .bmp images.
 */
public class BMPImageInputOutput extends AbstractImageInputOutput {

  @Override
  protected String getFileType() {
    return "bmp";
  }
}
