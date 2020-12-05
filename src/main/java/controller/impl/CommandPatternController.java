package controller.impl;

import constant.CommandConstants;
import constant.OutputMessage;
import controller.CommandController;
import enums.ErrorCode;
import exception.ParkingSystemException;
import model.Car;
import model.Driver;
import model.Vehicle;
import observers.CommandObserver;
import service.ParkingLotService;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * This class is used for handling all the commands from the stream by using Regex pattern matching
 */
public class CommandPatternController implements CommandController {

    private final ParkingLotService parkingLotService;
    private final Set<CommandObserver> commandObserverList;

    public CommandPatternController(ParkingLotService parkingLotService) {
        this.parkingLotService = parkingLotService;
        this.commandObserverList = new HashSet<>();
    }

    public CommandPatternController(ParkingLotService parkingLotService, CommandObserver... observer) {
        this.parkingLotService = parkingLotService;
        this.commandObserverList = new HashSet<CommandObserver>(Arrays.asList(observer));
    }

    /**
     * Executes the single command string based on the key
     *
     * @param singleCommand
     */
    public void runCommand(String singleCommand) {
        String[] command = singleCommand.split(" ");
        String commandKey = command[0];
        try {
            switch (commandKey) {
                case CommandConstants
                        .CREATE_PARKING_LOT:
                    handleCreateParkingLotCommand(singleCommand);
                    break;
                case CommandConstants.PARK_VEHICLE:
                    handleParkVehicleCommand(singleCommand);
                    break;
                case CommandConstants.LEAVE_PARKING_LOT:
                    handleLeaveParkingLotCommand(singleCommand);
                    break;
                case CommandConstants.SLOT_NUMBER_FOR_CAR_WITH_NUMBER:
                    handleGetSlotNumberForCarWithNumberCommand(singleCommand);
                    break;
                case CommandConstants.SLOT_NUMBERS_FOR_DRIVER_OF_AGE:
                    handleGetSlotNumbersForDriverOfAgeCommand(singleCommand);
                    break;
                case CommandConstants.VEHICLE_REGISTRATION_NUMBERS_FOR_DRIVER_OF_AGE:
                    handleGetRegistrationNumbersForDriverOfAgeCommand(singleCommand);
                    break;
                default:
                    throw new ParkingSystemException(String.format(ErrorCode.INVALID_COMMAND_FORMAT.getMessage(), singleCommand), ErrorCode.INVALID_COMMAND_FORMAT);

            }
        } catch (ParkingSystemException e) {
            notifyObserver(e.getMessage());
        } catch (Exception e) {
            // should ideally send error to logs file
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void generateTicket(int slot, Vehicle vehicle) {
        /**
         *  Need More details about ticket generation action
         * */
    }

    public ParkingLotService getParkingLotService() {
        return parkingLotService;
    }

    private void handleGetRegistrationNumbersForDriverOfAgeCommand(String singleCommand) {
        Matcher matcher = matchCommandPattern(CommandConstants.VEHICLE_REGISTRATION_NUMBERS_FOR_DRIVER_OF_AGE, singleCommand);

        int age = Integer.parseInt(matcher.group(2));
        List<String> slotsList = parkingLotService.getRegistrationNumbersForDriverOfAge(age);

        if (slotsList != null && slotsList.size() > 0) {
            notifyObserver(slotsList.stream().map(String::valueOf)
                    .collect(Collectors.joining(",")));
        } else {
            notifyObserver("null");
        }
    }


    private void handleGetSlotNumbersForDriverOfAgeCommand(String singleCommand) {
        Matcher matcher = matchCommandPattern(CommandConstants.SLOT_NUMBERS_FOR_DRIVER_OF_AGE, singleCommand);


        int age = Integer.parseInt(matcher.group(2));
        List<Integer> slotsList = parkingLotService.getSlotNumbersForDriverOfAge(age);

        if (slotsList != null && slotsList.size() > 0) {
            notifyObserver(slotsList.stream().map(String::valueOf)
                    .collect(Collectors.joining(",")));
        } else {
            notifyObserver("null");
        }
    }

    private void handleGetSlotNumberForCarWithNumberCommand(String singleCommand) {
        Matcher matcher = matchCommandPattern(CommandConstants.SLOT_NUMBER_FOR_CAR_WITH_NUMBER, singleCommand);
        String registrationNumber = matcher.group(2);

        Optional<Integer> slot = parkingLotService.getSlotNumberForCarWithNumber(registrationNumber);
        if (slot.isPresent()) {
            notifyObserver(String.valueOf(slot.get()));
        } else {
            notifyObserver("null");
        }
    }

    private void handleLeaveParkingLotCommand(String singleCommand) {
        Matcher matcher = matchCommandPattern(CommandConstants.LEAVE_PARKING_LOT, singleCommand);
        int slot = Integer.parseInt(matcher.group(2));
        Optional<Vehicle> unParkedVehicle = parkingLotService.leaveParkingLot(slot);
        if (unParkedVehicle.isPresent()) {
            notifyObserver(String.format(OutputMessage.VEHICLE_LEAVE_MESSAGE, slot, unParkedVehicle.get().getRegistrationNumber(), unParkedVehicle.get().getDriver().getAge()));
        } else {
            notifyObserver("null");
        }
    }

    private void handleParkVehicleCommand(String singleCommand) {
        Matcher matcher = matchCommandPattern(CommandConstants.PARK_VEHICLE, singleCommand);
        String registrationNumber = matcher.group(2);
        int age = Integer.parseInt(matcher.group(4));
        Vehicle vehicle = new Car(registrationNumber, new Driver(age));
        Optional<Integer> slot = parkingLotService.parkVehicle(vehicle);
        if (slot.isPresent()) {
            notifyObserver(String.format(OutputMessage.VEHICLE_PARKED_MESSAGE, registrationNumber, slot.get()));
            generateTicket(slot.get(), vehicle);
        } else {
            notifyObserver("null");
        }
    }

    private void handleCreateParkingLotCommand(String singleCommand) {
        Matcher matcher = matchCommandPattern(CommandConstants.CREATE_PARKING_LOT, singleCommand);
        int capacity = Integer.parseInt(matcher.group(2));
        parkingLotService.createParkingLot(capacity);
        notifyObserver(String.format(OutputMessage.CREATED_PARKING_LOT_MESSAGE, capacity));
    }

    private Matcher matchCommandPattern(String commandKey, String singleCommand) {
        if (!CommandConstants.getCommandsPatternMap().containsKey(commandKey)) {
            throw new ParkingSystemException(String.format(ErrorCode.NO_COMMAND_FOUND.getMessage(), commandKey), ErrorCode.NO_COMMAND_FOUND);
        }
        Pattern pattern = Pattern.compile(CommandConstants.getCommandsPatternMap().get(commandKey));
        Matcher matcher = pattern.matcher(singleCommand);
        if (!matcher.matches()) {
            throw new ParkingSystemException(String.format(ErrorCode.INVALID_COMMAND_FORMAT.getMessage(), singleCommand), ErrorCode.INVALID_COMMAND_FORMAT);
        }
        return matcher;
    }


    @Override
    public boolean addObserver(CommandObserver observer) {
        return commandObserverList.add(observer);
    }

    @Override
    public boolean removeObserver(CommandObserver observer) {
        return commandObserverList.remove(observer);
    }

    @Override
    public void notifyObserver(String message) {
        if (commandObserverList.size() == 0) return;

        for (CommandObserver observer : commandObserverList) {
            observer.update(message);
        }

    }
}
