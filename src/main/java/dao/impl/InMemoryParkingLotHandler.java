package dao.impl;

import dao.ParkingLotDataHandler;
import enums.ErrorCode;
import exception.ParkingSystemException;
import model.Vehicle;
import strategy.ParkingStrategy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

/**
 * ParkingLot Singleton DAO for handling data in-memory
 */
public class InMemoryParkingLotHandler implements ParkingLotDataHandler {

    private static InMemoryParkingLotHandler instance = null;
    private final ParkingStrategy parkingStrategy;
    private final Map<Integer, Vehicle> parkingDataMap;
    private final ReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();

    /**
     * Private constructor for singleton instance of class
     *
     * @param parkingStrategy - Parking strategy for parking of vehicles
     * @param capacity        - capacity of parking lot
     */
    private InMemoryParkingLotHandler(ParkingStrategy parkingStrategy, int capacity) {
        this.parkingStrategy = parkingStrategy;
        this.parkingDataMap = new HashMap<>();
        this.addSlotsToParkingLot(capacity);
    }

    /**
     * Method to create singleton instance
     *
     * @param parkingStrategy - Parking strategy for parking of vehicles
     * @param capacity        - capacity of parking lot
     * @return - singleton instance of ParkingLot DAO
     */
    public static InMemoryParkingLotHandler getInstance(ParkingStrategy parkingStrategy, int capacity) {
        if (instance == null) {
            synchronized (InMemoryParkingLotHandler.class) {
                if (instance == null) {
                    instance = new InMemoryParkingLotHandler(parkingStrategy, capacity);
                }
            }
        }
        return instance;
    }

    private void addSlotsToParkingLot(int capacity) {
        try {
            reentrantReadWriteLock.writeLock().lock();
            for (int i = 1; i <= capacity; i++) {
                this.parkingStrategy.addSlot(i);
            }

        } finally {
            reentrantReadWriteLock.writeLock().unlock();
        }

    }

    @Override
    public int parkVehicle(Vehicle vehicle) {
        try {
            reentrantReadWriteLock.writeLock().lock();
            // vehicle is already parked with same number
            if (isVehicleAlreadyParked(vehicle)) {
                throw new ParkingSystemException(String.format(ErrorCode.VEHICLE_ALREADY_EXIST.getMessage(), vehicle.getRegistrationNumber()), ErrorCode.VEHICLE_ALREADY_EXIST);
            }
            // No free parking slot available
            if (parkingStrategy.getFreeSlotCount() == 0) {
                throw new ParkingSystemException(ErrorCode.NO_FREE_SLOT_FOUND.getMessage(), ErrorCode.NO_FREE_SLOT_FOUND);
            }
            int availableSlot = parkingStrategy.getFreeSlot();
            parkingStrategy.removeSlot(availableSlot);
            parkingDataMap.put(availableSlot, vehicle);
            return availableSlot;

        } finally {
            reentrantReadWriteLock.writeLock().unlock();
        }

    }

    public boolean isVehicleAlreadyParked(Vehicle newVehicle) {
        return parkingDataMap.values().stream()
                .filter(vehicle -> vehicle.getRegistrationNumber().equals(newVehicle.getRegistrationNumber()))
                .findAny()
                .isPresent();
    }

    @Override
    public Vehicle leaveParkingLot(int slot) {
        try {
            reentrantReadWriteLock.writeLock().lock();
            if (!parkingDataMap.containsKey(slot)) {
                throw new ParkingSystemException(String.format(ErrorCode.INVALID_SLOT_TO_LEAVE.getMessage(), slot), ErrorCode.INVALID_SLOT_TO_LEAVE);
            }
            Vehicle vehicle = parkingDataMap.remove(slot);
            parkingStrategy.addSlot(slot);
            return vehicle;

        } finally {
            reentrantReadWriteLock.writeLock().unlock();
        }
    }

    @Override
    public List<Vehicle> getAllVehiclesParkedByDriverOfAge(int age) {
        return parkingDataMap.values().stream()
                .filter(vehicle -> vehicle.getDriver().getAge() == age)
                .collect(Collectors.toList());
    }

    @Override
    public Integer getVehicleParkingSlotNumber(String registrationNumber) {
        return parkingDataMap.entrySet().stream()
                .filter(vehicleEntry -> vehicleEntry.getValue().getRegistrationNumber().equals(registrationNumber))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Integer> getAllSlotsParkedByDriverOfAge(int age) {
        return parkingDataMap.entrySet().stream()
                .filter(vehicleEntry -> vehicleEntry.getValue().getDriver().getAge() == age)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    @Override
    public void clearParkingLot() {
        instance = null;
    }
}
