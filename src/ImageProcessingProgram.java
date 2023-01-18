import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;

import controller.gui.ImageGUIController;
import controller.gui.ImageGUIControllerImpl;
import controller.text.ImageTextController;
import controller.text.ImageTextControllerImpl;
import model.ImageModel;
import model.ImageModelImpl;
import view.gui.ImageGUIView;
import view.gui.ImageGUIViewImpl;
import view.text.ImageTextView;
import view.text.ImageTextViewImpl;

/**
 * The class to run and use the image manipulator.
 */
public class ImageProcessingProgram {

  /**
   * A method for running the program.
   *
   * @param args the command-line arguments for this program.
   */
  public static void main(String[] args) {

    ImageModel m = new ImageModelImpl();
    Readable rd;

    // if there are command-line arguments correctly specifying that a file should be taken in as
    // input, then pass that file's contents to the controller as the readable, using the text
    // view and the text controller
    if (args.length == 2 && args[0].equals("-file")) {
      File initialFile = new File(args[1]);
      InputStream input;

      try {
        input = new FileInputStream(initialFile);
      } catch (FileNotFoundException e) {
        throw new IllegalArgumentException("File not found!");
      }
      rd = new InputStreamReader(input);
      ImageTextView v = new ImageTextViewImpl();
      ImageTextController controller = new ImageTextControllerImpl(m, v, rd);
      controller.start();
    }

    // if the command-line arguments are "text", then allow interactive text scripting using the
    // text view and text controller
    else if (args.length == 1 && args[0].equals("-text")) {
      ImageTextView v = new ImageTextViewImpl();
      rd = new InputStreamReader(System.in);
      ImageTextController controller = new ImageTextControllerImpl(m, v, rd);
      controller.start();
    }

    // if there are no command-line arguments, then run the program allowing interactive entry of
    // script commands
    else if (args.length == 0) {
      ImageGUIView viewGUI = new ImageGUIViewImpl();
      ImageGUIController controllerGUI = new ImageGUIControllerImpl(m, viewGUI);
    }

    // display an error message when the command-line arguments don't specify one of the ways to
    // use the program
    else {
      System.out.println("Invalid command-line arguments have been specified!");
    }
  }
}

