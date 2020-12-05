import constant.GlobalConstants;
import controller.CommandController;
import controller.InputController;
import controller.impl.CommandPatternController;
import controller.impl.ConsoleInputController;
import controller.impl.FileInputController;
import observers.impl.CommandConsoleOutputObserver;
import service.impl.NearestSlotParkingService;

/**
 * Main class to run the project
 */
public class Main {
    public static void main(String[] args) {
        CommandController commandController = new CommandPatternController(NearestSlotParkingService.getInstance(), new CommandConsoleOutputObserver());
        readFromFile(commandController, args);
    }

    private static void readFromFile(CommandController commandController, String[] args) {
        String filePath = GlobalConstants.INPUT_FILE_NAME;
        if (args.length > 0) {
            filePath = args[0];
        }
        InputController fileInputController = new FileInputController(commandController, filePath);
        fileInputController.readCommand();
    }

    private static void readFromConsole(CommandController commandController) {
        InputController consoleInputController = new ConsoleInputController(commandController);
        consoleInputController.readCommand();
    }
}
