import org.junit.Test;

import java.awt.event.ActionEvent;

import controller.gui.ImageGUIControllerImpl;
import model.ImageModel;
import view.gui.ImageGUIView;

import static org.junit.Assert.assertEquals;

/**
 * Represents the JUnit test class for the ImageGUIController.
 */
public class ImageGUIControllerImplTest {

  private StringBuilder log = new StringBuilder();
  private ImageModel m = new MockModel(log);
  private ImageGUIView v = new MockView();
  private ImageGUIControllerImpl c = new ImageGUIControllerImpl(m, v);

  private ActionEvent e;


  // For testing the controller, we created our own action events that would mimic the
  // different buttons being pressed on the GUI, and then we passed these in as arguments to the
  // controller for the actionPerformed method. We used a mock model to ensure that the
  // controller was calling the correct methods in the model on the correct image and renaming it
  // as the same image by checking the mock's log. We also used a mock view to make things simpler
  // since our actual GUI view would render a GUI with JSwing every time a controller was made with
  // it as the view, and this was unnecessary for testing the interaction between the controller
  // and model.
  @Test
  public void testBlur() {
    e = new ActionEvent("", 0, "blur");
    c.actionPerformed(e);
    String blurCommand = log.toString().split("\n")[0];
    assertEquals("Image with name guiImage was blurred and saved as guiImage.",
            blurCommand);
  }

  @Test
  public void testLoadButtonThroughBrighten() {
    e = new ActionEvent("", 0, "brighten");
    c.actionPerformed(e);
    String loadCommand = log.toString().split("\n")[0];
    assertEquals("Image with name guiImage was retrieved.", loadCommand);
  }

  @Test
  public void testBrightenButton() {
    e = new ActionEvent("", 0, "brighten");
    c.actionPerformed(e);
    String brightenCommand = log.toString().split("\n")[1];
    assertEquals("Image with name guiImage was brightened by 10 and saved as guiImage.",
            brightenCommand);
  }

  @Test
  public void testDarkenButton() {
    e = new ActionEvent("", 0, "darken");
    c.actionPerformed(e);
    String darkenCommand = log.toString().split("\n")[1];
    assertEquals("Image with name guiImage was darkened by 10 and saved as guiImage.",
            darkenCommand);
  }

  @Test
  public void testHorizontalFlip() {
    e = new ActionEvent("", 0, "horizontal flip");
    c.actionPerformed(e);
    String horizontalFlipCommand = log.toString().split("\n")[0];
    assertEquals("Image with name guiImage was flipped HORIZONTAL and saved as guiImage.",
            horizontalFlipCommand);
  }

  @Test
  public void testVerticalFlip() {
    e = new ActionEvent("", 0, "vertical flip");
    c.actionPerformed(e);
    String verticalFlipCommand = log.toString().split("\n")[0];
    assertEquals("Image with name guiImage was flipped VERTICAL and saved as guiImage.",
            verticalFlipCommand);
  }

  @Test
  public void testRedComponent() {
    e = new ActionEvent("", 0, "red component");
    c.actionPerformed(e);
    String redComponentCommand = log.toString().split("\n")[0];
    assertEquals("Image with name guiImage was greyscaled component wise by RED and" +
                    " saved as guiImage.", redComponentCommand);
  }

  @Test
  public void testGreenComponent() {
    e = new ActionEvent("", 0, "green component");
    c.actionPerformed(e);
    String greenComponentCommand = log.toString().split("\n")[0];
    assertEquals("Image with name guiImage was greyscaled component wise by GREEN and" +
            " saved as guiImage.", greenComponentCommand);
  }

  @Test
  public void testBlueComponent() {
    e = new ActionEvent("", 0, "blue component");
    c.actionPerformed(e);
    String blueComponentCommand = log.toString().split("\n")[0];
    assertEquals("Image with name guiImage was greyscaled component wise by BLUE and" +
            " saved as guiImage.", blueComponentCommand);
  }

  @Test
  public void testValueComponent() {
    e = new ActionEvent("", 0, "value component");
    c.actionPerformed(e);
    String valueComponentCommand = log.toString().split("\n")[0];
    assertEquals("Image with name guiImage was greyscaled component wise by VALUE and" +
            " saved as guiImage.", valueComponentCommand);
  }

  @Test
  public void testIntensityComponent() {
    e = new ActionEvent("", 0, "intensity component");
    c.actionPerformed(e);
    String intensityComponentCommand = log.toString().split("\n")[0];
    assertEquals("Image with name guiImage was greyscaled component wise by INTENSITY and" +
            " saved as guiImage.", intensityComponentCommand);
  }

  @Test
  public void testLumaComponent() {
    e = new ActionEvent("", 0, "luma component");
    c.actionPerformed(e);
    String lumaComponentCommand = log.toString().split("\n")[0];
    assertEquals("Image with name guiImage was greyscaled component wise by LUMA and" +
            " saved as guiImage.", lumaComponentCommand);
  }

  @Test
  public void testSharpen() {
    e = new ActionEvent("", 0, "sharpen");
    c.actionPerformed(e);
    String sharpenCommand = log.toString().split("\n")[0];
    assertEquals("Image with name guiImage was sharpened and saved as guiImage.",
            sharpenCommand);
  }

  @Test
  public void testSepia() {
    e = new ActionEvent("", 0, "sepia");
    c.actionPerformed(e);
    String sepiaCommand = log.toString().split("\n")[0];
    assertEquals("Image with name guiImage has a sepia filter called to it and saved as" +
                    " guiImage.", sepiaCommand);
  }

  @Test
  public void testGreyscale() {
    e = new ActionEvent("", 0, "greyscale");
    c.actionPerformed(e);
    String greyscaleCommand = log.toString().split("\n")[0];
    assertEquals("Image with name guiImage was greyscaled with a color transformation " +
            "and saved as guiImage.", greyscaleCommand);
  }

  @Test
  public void testDownsize() {
    e = new ActionEvent("", 0, "downsize");
    c.actionPerformed(e);
    String downsizeCommand = log.toString().split("\n")[1];
    assertEquals("Image with name guiImage had its width downsized by 10 and its height" +
                    " downsized by 10 and was saved as guiImage.", downsizeCommand);
  }
}

