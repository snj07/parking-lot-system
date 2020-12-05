package controller;

import model.Vehicle;
import observers.CommandObservable;

/**
 * CommandController interface for handling commands from the streams
 */
public interface CommandController extends CommandObservable {
    public void runCommand(String singleCommand);
    public void generateTicket(int slot, Vehicle vehicle);
}
