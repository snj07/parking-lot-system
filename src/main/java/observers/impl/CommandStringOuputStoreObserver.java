package observers.impl;

import observers.CommandObserver;

/**
 * CommandController observer for storing the output of the commands (for testing)
 */
public class CommandStringOuputStoreObserver implements CommandObserver {
    private final StringBuilder stringBuilder;

    public CommandStringOuputStoreObserver() {
        stringBuilder = new StringBuilder();
    }


    @Override
    public void update(String message) {
        stringBuilder.append(message + "\n");
    }
}
