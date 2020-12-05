package controller.impl;

import controller.CommandController;
import controller.InputController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


/**
 * This class handles inputs from console
 */

public class ConsoleInputController extends InputController {
    public ConsoleInputController(CommandController commandController) {
        super(commandController);
    }

    @Override
    public void readCommand() {
        printInstruction();
        try (BufferedReader bufferReader = new BufferedReader(new InputStreamReader(System.in))) {
            String input;
            while (true) {
                input = bufferReader.readLine().trim();
                if (input.equalsIgnoreCase("exit")) {
                    break;
                }
                commandController.runCommand(input);
            }
        } catch (IOException e) {
            System.out.println("Error occurred while reading");
        }

    }

    private void printInstruction() {
        System.out.println("Please enter command in single line or send \"exit\" to exit from terminal");
    }
}
