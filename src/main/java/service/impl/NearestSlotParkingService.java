package service.impl;

import dao.ParkingLotDataHandler;
import dao.impl.InMemoryParkingLotHandler;
import enums.ErrorCode;
import exception.ParkingSystemException;
import model.Vehicle;
import service.ParkingLotService;
import strategy.NearestSlotParkingStrategy;
import strategy.ParkingStrategy;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * This class contains business logic method of parking system based on Nearest-Slot-Parking
 */
public class NearestSlotParkingService implements ParkingLotService {
    private static NearestSlotParkingService instance;
    private ParkingLotDataHandler parkingLotDataHandler;

    private NearestSlotParkingService() {

    }

    public static NearestSlotParkingService getInstance() {
        if (instance == null) {
            synchronized (NearestSlotParkingService.class) {
                if (instance == null) {
                    instance = new NearestSlotParkingService();
                }
            }
        }
        return instance;
    }

    @Override
    public void createParkingLot(int capacity) throws ParkingSystemException {
        if (parkingLotDataHandler != null)
            throw new ParkingSystemException(ErrorCode.PARKING_LOT_ALREADY_EXIST.getMessage(), ErrorCode.PARKING_LOT_ALREADY_EXIST);
        if (capacity <= 0) {
            throw new ParkingSystemException(String.format(ErrorCode.INVALID_PARKING_LOT_SIZE.getMessage(),
                    capacity), ErrorCode.INVALID_PARKING_LOT_SIZE);
        }
        try {
            ParkingStrategy parkingStrategy = new NearestSlotParkingStrategy();
            parkingLotDataHandler = InMemoryParkingLotHandler.getInstance(parkingStrategy, capacity);
        } catch (Exception e) {
            throw new ParkingSystemException(ErrorCode.INVALID_PARKING_LOT_SIZE.getMessage(), e);
        }
    }

    @Override
    public Optional<Integer> parkVehicle(Vehicle vehicle) throws ParkingSystemException {

        if (parkingLotDataHandler == null) {
            throw new ParkingSystemException(ErrorCode.NO_PARKING_FOUND.getMessage(), ErrorCode.NO_PARKING_FOUND);
        }

        return Optional.of(parkingLotDataHandler.parkVehicle(vehicle));
    }

    @Override
    public Optional<Vehicle> leaveParkingLot(int slot) throws ParkingSystemException {

        if (parkingLotDataHandler == null) {
            throw new ParkingSystemException(ErrorCode.NO_PARKING_FOUND.getMessage(), ErrorCode.NO_PARKING_FOUND);
        }

        return Optional.of(parkingLotDataHandler.leaveParkingLot(slot));
    }

    @Override
    public Optional<Integer> getSlotNumberForCarWithNumber(String registrationNumber) throws ParkingSystemException {

        if (parkingLotDataHandler == null) {
            throw new ParkingSystemException(ErrorCode.NO_PARKING_FOUND.getMessage(), ErrorCode.NO_PARKING_FOUND);
        }
        Integer bookedSlot = parkingLotDataHandler.getVehicleParkingSlotNumber(registrationNumber);
        if (bookedSlot == null) {
            return Optional.empty();
        }
        return Optional.of(bookedSlot);
    }

    @Override
    public List<Integer> getSlotNumbersForDriverOfAge(int age) {
        if (parkingLotDataHandler == null) {
            throw new ParkingSystemException(ErrorCode.NO_PARKING_FOUND.getMessage(), ErrorCode.NO_PARKING_FOUND);
        }

        return parkingLotDataHandler.getAllSlotsParkedByDriverOfAge(age);

    }

    @Override
    public List<String> getRegistrationNumbersForDriverOfAge(int age) {
        if (parkingLotDataHandler == null) {
            throw new ParkingSystemException(ErrorCode.NO_PARKING_FOUND.getMessage(), ErrorCode.NO_PARKING_FOUND);
        }
        return parkingLotDataHandler.getAllVehiclesParkedByDriverOfAge(age).stream()
                .map(vehicle -> vehicle.getRegistrationNumber())
                .collect(Collectors.toList());
    }

    /**
     * Method to clean singleton objects
     */
    @Override
    public synchronized void cleanParkingLot() {

        parkingLotDataHandler.clearParkingLot();
        instance = null;
    }


}
