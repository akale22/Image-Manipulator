package controller.commands;

import model.ImageModel;

/**
 * An interface representing the operations that can be done on a model.
 */
public interface ImageCommand {

  /**
   * Executes the specified command on the model.
   *
   * @param model the model that we are performing an action on.
   */
  void execute(ImageModel model);
}
