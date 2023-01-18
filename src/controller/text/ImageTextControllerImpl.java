package controller.text;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;

import controller.commands.Blur;
import controller.commands.Brighten;
import controller.commands.Darken;
import controller.commands.Flip;
import controller.commands.GreyscaleColorTransformation;
import controller.commands.GreyscaleComponent;
import controller.commands.ImageCommand;
import controller.commands.Load;
import controller.commands.Save;
import controller.commands.Sepia;
import controller.commands.Sharpen;
import model.ImageModel;
import model.enums.FlipType;
import model.enums.GreyscaleComponentType;
import view.text.ImageTextView;

/**
 * A simple controller implementation which uses the model and view and a specific readable to
 * handle input and delegate tasks. It also contains a map of potential commands.
 */
// INVARIANT: The model, view, readable, and map of known commands can never be null.
public class ImageTextControllerImpl implements ImageTextController {

  private final ImageModel model;
  private final ImageTextView view;
  private final Readable rd;
  private final Map<String, Function<Scanner, ImageCommand>> knownCommands;

  /**
   * A constructor which initializes the field of the simple controller and ensures that none of
   * the fields are null.
   *
   * @param model the model for the controller for performing operations
   * @param view  the view for the controller for displaying information to the user
   * @param rd    the readable to interpret input from
   * @throws IllegalArgumentException if the model, view, or readable are null.
   */
  public ImageTextControllerImpl(ImageModel model, ImageTextView view, Readable rd)
          throws IllegalArgumentException {

    if (model == null || view == null || rd == null) {
      throw new IllegalArgumentException("Model, view, or readable cannot be null!");
    }

    this.model = model;
    this.view = view;
    this.rd = rd;
    this.knownCommands = new HashMap<>();

    this.setCommands();
  }

  /**
   * A helper method which adds all the known commands to the map of commands.
   */
  private void setCommands() {
    knownCommands.put("load", s -> new Load(s.next(), s.next()));
    knownCommands.put("save", s -> new Save(s.next(), s.next()));
    knownCommands.put("red-component",
        s -> new GreyscaleComponent(GreyscaleComponentType.RED, s.next(), s.next()));
    knownCommands.put("green-component",
        s -> new GreyscaleComponent(GreyscaleComponentType.GREEN, s.next(), s.next()));
    knownCommands.put("blue-component",
        s -> new GreyscaleComponent(GreyscaleComponentType.BLUE, s.next(), s.next()));
    knownCommands.put("value-component",
        s -> new GreyscaleComponent(GreyscaleComponentType.VALUE, s.next(), s.next()));
    knownCommands.put("intensity-component",
        s -> new GreyscaleComponent(GreyscaleComponentType.INTENSITY, s.next(), s.next()));
    knownCommands.put("luma-component",
        s -> new GreyscaleComponent(GreyscaleComponentType.LUMA, s.next(), s.next()));
    knownCommands.put("vertical-flip", s -> new Flip(FlipType.VERTICAL, s.next(), s.next()));
    knownCommands.put("horizontal-flip", s -> new Flip(FlipType.HORIZONTAL, s.next(), s.next()));
    knownCommands.put("brighten", s -> new Brighten(s.nextInt(), s.next(), s.next()));
    knownCommands.put("darken", s -> new Darken(s.nextInt(), s.next(), s.next()));
    knownCommands.put("blur", s -> new Blur(s.next(), s.next()));
    knownCommands.put("sharpen", s -> new Sharpen(s.next(), s.next()));
    knownCommands.put("greyscale", s -> new GreyscaleColorTransformation(s.next(), s.next()));
    knownCommands.put("sepia", s -> new Sepia(s.next(), s.next()));
  }

  @Override
  public void start() throws IllegalStateException {
    Scanner sc = new Scanner(this.rd);
    ImageCommand c;
    this.displayStartingScreen();

    // while the scanner has more input
    while (sc.hasNext()) {
      String next = sc.next();

      // ignoring lines that represent comments
      if (next.charAt(0) == '#') {
        sc.nextLine();
      }

      // dealing with quitting the application
      if (next.equalsIgnoreCase("q") || next.equalsIgnoreCase("quit")) {
        this.quit();
        return;
      }

      // dealing with loading, saving, or image manipulation commands
      Function<Scanner, ImageCommand> cmd = knownCommands.getOrDefault(next, null);

      // if the cmd is null, then the command was not found in map of known commands
      if (cmd == null) {
        this.renderInvalidMessageAndSkipLine(next + sc.nextLine());
      }

      // if the command is found, then try to apply the function object and execute the command
      else {
        try {
          c = cmd.apply(sc);
          c.execute(model);
        } catch (Exception e) {
          this.renderInvalidMessageAndSkipLine(next + " " + sc.nextLine());
        }
      }
    }
  }

  /**
   * A helper method which displays a welcome message to the user.
   *
   * @throws IllegalStateException if transmission of the welcome message fails
   */
  private void displayStartingScreen() throws IllegalStateException {
    try {
      this.view.renderMessage("Welcome to the image editor application!" + System.lineSeparator());
    } catch (IOException ioe) {
      throw new IllegalStateException("Transmission of the starting information to the view " +
              "failed!");
    }
  }

  /**
   * A helper method which displays a quit message to the user.
   *
   * @throws IllegalStateException if transmission of the quit message fails
   */
  private void quit() throws IllegalStateException {
    try {
      this.view.renderMessage("Application quit!" + System.lineSeparator());
    } catch (IOException e) {
      throw new IllegalStateException("Transmission of the quitting information to the view " +
              "failed!");
    }
  }


  /**
   * Renders a specific invalid message to the queue and clears the line so that the rest of the
   * input on that line in the scanner is ignored.
   *
   * @param currLine the line representing the invalid command
   * @throws IllegalStateException if the transmission of the message failed
   */
  private void renderInvalidMessageAndSkipLine(String currLine)
          throws IllegalStateException {
    try {
      this.view.renderMessage("Invalid command sequence: " + currLine + System.lineSeparator());
    } catch (IOException e) {
      throw new IllegalStateException("Transmission to the view failed!");
    }
  }
}
