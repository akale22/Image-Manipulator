package controller.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import controller.commands.Load;
import controller.commands.Save;
import model.ImageModel;
import model.enums.FlipType;
import model.enums.GreyscaleComponentType;
import view.gui.ImageGUIView;

/**
 * An implementation of a controller for the GUI representation of this program. It has a model
 * and a GUI view, and it is an action listener itself for every part of the GUI view which has
 * action commands. It handles all the potential user input through the GUI view in a proper
 * manner by delegating tasks to the model and displaying things back on the GUI view. This
 * controller only keeps track of one image at a time even though the model has the capability to
 * keep track of many, which is why every image is loaded to the model with a name of "guiImage"
 * and every time a manipulation is done, it is saved with this name, overriding the previous
 * image. Users only have access to the image on the screen and cannot undo or go back to
 * previous iterations, but they can just load in the image again if they'd like to go back.
 */
// INVARIANT: The model and view cannot be null.
public class ImageGUIControllerImpl implements ActionListener, ImageGUIController {

  private final ImageModel model;
  private final ImageGUIView view;

  /**
   * A constructor for a ImageGUIControllerImpl which sets the fields appropriately and then sets
   * itself as the listener to the all the view components that need a listener and the
   * displays the view.
   *
   * @param model the model for this controller
   * @param view  the GUI view for this controller
   * @throws IllegalArgumentException if either the model or the view are null
   */
  public ImageGUIControllerImpl(ImageModel model, ImageGUIView view)
          throws IllegalArgumentException {
    if (model == null || view == null) {
      throw new IllegalArgumentException("Model or view cannot be null!");
    }

    this.model = model;
    this.view = view;
    this.view.setListener(this);
    this.view.display();
  }

  @Override
  public void actionPerformed(ActionEvent event) {
    switch (event.getActionCommand()) {

      //read from the input textfield
      case "load":
        this.loadHelper();
        break;
      case "save":
        this.saveHelper();
        break;
      case "brighten":
        this.brightenHelper();
        break;
      case "darken":
        this.darkenHelper();
        break;
      case "horizontal flip":
        this.flipHelper(FlipType.HORIZONTAL);
        break;
      case "vertical flip":
        this.flipHelper(FlipType.VERTICAL);
        break;
      case "red component":
        this.componentHelper(GreyscaleComponentType.RED);
        break;
      case "green component":
        this.componentHelper(GreyscaleComponentType.GREEN);
        break;
      case "blue component":
        this.componentHelper(GreyscaleComponentType.BLUE);
        break;
      case "value component":
        this.componentHelper(GreyscaleComponentType.VALUE);
        break;
      case "intensity component":
        this.componentHelper(GreyscaleComponentType.INTENSITY);
        break;
      case "luma component":
        this.componentHelper(GreyscaleComponentType.LUMA);
        break;
      case "blur":
        this.blurHelper();
        break;
      case "sharpen":
        this.sharpenHelper();
        break;
      case "greyscale":
        this.greyscaleHelper();
        break;
      case "sepia":
        this.sepiaHelper();
        break;
      case "downsize": {
        this.downsizeHelper();
        break;
      }
      default:
        throw new IllegalArgumentException("Invalid action event!");
    }
  }

  /**
   * Ensures that the view is drawing the proper image and histogram after one has been loaded or
   * manipulated.
   */
  private void updateView() {
    view.updateImage(model.getImage("guiImage"));
    view.updateHistogram(model.getImage("guiImage"));
    view.refresh();
  }

  /**
   * A helper method to display the image and carry out the IO necessary and save the specified
   * image as "guiImage".
   */
  private void loadHelper() {
    final JFileChooser loadFileChooser = new JFileChooser(".");
    FileNameExtensionFilter filter = new FileNameExtensionFilter(
            "PPM, PNG, JPG, & BMP Images", "ppm", "png", "jpg", "bmp");
    loadFileChooser.setFileFilter(filter);
    int loadRetValue = loadFileChooser.showOpenDialog(null);
    if (loadRetValue == JFileChooser.APPROVE_OPTION) {
      File f = loadFileChooser.getSelectedFile();
      new Load(f.getAbsolutePath(), "guiImage").execute(model);
      this.updateView();
    }
  }

  /**
   * A helper method to carry out the IO necessary to save an image.
   */
  private void saveHelper() {
    // checking for a loaded image
    try {
      model.getImage("guiImage");
    } catch (IllegalArgumentException e) {
      view.renderErrorMessage("An image must be loaded before it can be saved!");
      return;
    }

    // trying to save an image with a valid file type
    try {
      final JFileChooser saveFileChooser = new JFileChooser(".");
      int saveRetValue = saveFileChooser.showSaveDialog(null);
      if (saveRetValue == JFileChooser.APPROVE_OPTION) {
        File f = saveFileChooser.getSelectedFile();
        new Save(f.getAbsolutePath(), "guiImage").execute(model);
      }
    }
    catch (IllegalArgumentException e) {
      view.renderErrorMessage("An image must be saved as either a .jpg, .bmp, .png, or .ppm file!");
    }
  }

  /**
   * A helper method for carrying out the brighten functionality.
   */
  private void brightenHelper() {
    // checking for a loaded image
    try {
      model.getImage("guiImage");
    } catch (IllegalArgumentException e) {
      view.renderErrorMessage("An image must be loaded before it can be brightened!");
      return;
    }

    // trying to get the increment and then parsing it to an int
    String increment = view.getUserInput("Enter brightening increment: ");
    int value;
    try {
      value = Integer.parseInt(increment);
    } catch (NumberFormatException e) {
      view.renderErrorMessage("Brightness increment must be a positive integer!");
      return;
    }

    // attempting to brighten with the integer, which will only work of it is positive
    try {
      model.brighten(value, "guiImage", "guiImage");
      this.updateView();
    }
    catch (IllegalArgumentException e) {
      view.renderErrorMessage("Brighten increment must be a positive integer!");
    }
  }

  /**
   * A helper method for carrying out the darken functionality.
   */
  private void darkenHelper() {
    // checking for a loaded image
    try {
      model.getImage("guiImage");
    } catch (IllegalArgumentException e) {
      view.renderErrorMessage("An image must be loaded before it can be darkened!");
      return;
    }

    // trying to get the increment and then parsing it to an int
    String increment = view.getUserInput("Enter darkening increment: ");
    int value;
    try {
      value = Integer.parseInt(increment);
    } catch (NumberFormatException e) {
      view.renderErrorMessage("Darkening increment must be a positive integer!");
      return;
    }

    // attempting to darken with the integer, which will only work of it is positive
    try {
      model.darken(value, "guiImage", "guiImage");
      this.updateView();
    }
    catch (IllegalArgumentException e) {
      view.renderErrorMessage("Darken increment must be a positive integer!");
    }
  }

  /**
   * A helper method for carrying out the flip functionality.
   *
   * @param type the type of flip
   */
  private void flipHelper(FlipType type) {
    // attempting to flip and rendering a message if the image hasn't been loaded
    try {
      model.flip(type, "guiImage", "guiImage");
      this.updateView();
    } catch (IllegalArgumentException e) {
      view.renderErrorMessage("An image must be loaded before it can be flipped!");
    }
  }

  /**
   * A helper method for carrying out the greyscale component functionality.
   *
   * @param type the type of component
   */
  private void componentHelper(GreyscaleComponentType type) {
    // attempting to greyscale component an image and rendering a message if the image hasn't been
    // loaded
    try {
      model.greyscaleComponent(type, "guiImage", "guiImage");
      this.updateView();
    } catch (IllegalArgumentException e) {
      view.renderErrorMessage("An image must be loaded before it can be greyscaled by a " +
              "component!");
    }
  }

  /**
   * A helper method for carrying out the blur functionality.
   */
  private void blurHelper() {
    // attempting to blur an image and rendering a message if the image hasn't been loaded
    try {
      model.blur("guiImage", "guiImage");
      this.updateView();
    } catch (IllegalArgumentException e) {
      view.renderErrorMessage("An image must be loaded before it can be blurred!");
    }
  }

  /**
   * A helper method for carrying out the sharpen functionality.
   */
  private void sharpenHelper() {
    // attempting to sharpen an image and rendering a message if the image hasn't been loaded
    try {
      model.sharpen("guiImage", "guiImage");
      this.updateView();
    } catch (IllegalArgumentException e) {
      view.renderErrorMessage("An image must be loaded before it can be sharpened!");
    }
  }

  /**
   * A helper method for carrying out the greyscale functionality.
   */
  private void greyscaleHelper() {
    // attempting to greyscale an image and rendering a message if the image hasn't been loaded
    try {
      model.greyscaleColorTransformation("guiImage", "guiImage");
      this.updateView();
    } catch (IllegalArgumentException e) {
      view.renderErrorMessage("An image must be loaded before it can be greyscaled!");
    }
  }

  /**
   * A helper method for carrying out the sepia functionality.
   */
  private void sepiaHelper() {
    // attempting to sepia an image and rendering a message if the image hasn't been loaded
    try {
      model.sepia("guiImage", "guiImage");
      this.updateView();
    } catch (IllegalArgumentException e) {
      view.renderErrorMessage("An image must be loaded before it can be sepia-ed!");
    }
  }

  /**
   * A helper method for carrying out the downsize functionality.
   */
  private void downsizeHelper() {
    // checking for a loaded image
    try {
      model.getImage("guiImage");
    } catch (IllegalArgumentException e) {
      view.renderErrorMessage("An image must be loaded before it can be downsized!");
      return;
    }

    // trying to get the two downsizing increments and then parsing it to an int
    String s1 = view.getUserInput("Enter a percent to decrease the width by: ");
    String s2 = view.getUserInput("Enter a percent to decrease the height by: ");
    int widthPercent;
    int heightPercent;
    try {
      widthPercent = Integer.parseInt(s1);
      heightPercent = Integer.parseInt(s2);
    } catch (NumberFormatException e) {
      view.renderErrorMessage("Downsizing percents must be a positive integer!");
      return;
    }

    // attempting to downsize with the integers, which will only work of if they're both positive
    try {
      model.downsize(widthPercent, heightPercent, "guiImage", "guiImage");
      this.updateView();
    }
    catch (IllegalArgumentException e) {
      view.renderErrorMessage("Downsizing increments must be a positive integer between 0 and 100" +
              " (including 0)!");
    }
  }
}
