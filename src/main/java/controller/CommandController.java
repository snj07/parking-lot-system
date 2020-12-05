package controller;

import model.Vehicle;
import observers.CommandObservable;

/**
 * CommandController interface for handling commands from the streams
 */
public interface CommandController extends CommandObservable {
    void runCommand(String singleCommand);

    void generateTicket(int slot, Vehicle vehicle);
}
