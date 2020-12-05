package controller.impl;

import controller.CommandController;
import controller.InputController;
import utils.FileUtils;

/**
 * This class handles inputs from File
 */
public class FileInputController extends InputController {

    private final FileUtils fileUtils;
    private final String filePath;

    public FileInputController(CommandController commandController, String filePath) {
        super(commandController);
        this.fileUtils = new FileUtils();
        this.filePath = filePath;
    }

    @Override
    public void readCommand() {
        executeCommandsFromFile();
    }

    private void executeCommandsFromFile() {
        try {
            String commandsFromFile = fileUtils.readFile(filePath);
            if (commandsFromFile != null && !commandsFromFile.equals("")) {
                String[] commandsArr = commandsFromFile.split("\n");
                for (String singleCommand : commandsArr) {
                    commandController.runCommand(singleCommand);
                }
            }
        } catch (Exception e) {
            System.out.format("Error occurred while reading file : %s", e.getMessage());
        }
    }
}
