package observers.impl;

import observers.CommandObserver;

/**
 * CommandController observer for printing the output of the commands
 */
public class CommandConsoleOutputObserver implements CommandObserver {
    @Override
    public void update(String message) {
        System.out.println(message);
    }
}
