package controller.commands;

import model.ImageModel;

/**
 * An abstract class containing fields common to all commands.
 */
public abstract class AbstractImageCommand implements ImageCommand {

  protected String in;
  protected String out;

  /**
   * A constructor which initializes the two fields to the parameters passed to the constructors.
   *
   * @param in  the file that we are manipulating or the path that we are loading from or saving to
   * @param out the name of the image that we are manipulating or loading/saving
   */
  protected AbstractImageCommand(String in, String out) {
    this.in = in;
    this.out = out;
  }

  @Override
  public abstract void execute(ImageModel model);
}
