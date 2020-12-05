package controller;

/**
 * InputController class for handling the input from various streams like File, Console
 */
public abstract class InputController {

    protected CommandController commandController;

    public InputController(CommandController commandController) {
        this.commandController = commandController;
    }

    public CommandController getCommandController() {
        return commandController;
    }

    public abstract void readCommand();
}
