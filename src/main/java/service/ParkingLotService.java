package service;

import exception.ParkingSystemException;
import model.Vehicle;

import java.util.List;
import java.util.Optional;

/**
 * ParkingLot services commands business logic methods
 */
public interface ParkingLotService {
    public void createParkingLot(int capacity);

    public Optional<Integer> parkVehicle(Vehicle vehicle) throws ParkingSystemException;

    public Optional<Vehicle> leaveParkingLot(int slot) throws ParkingSystemException;

    public Optional<Integer> getSlotNumberForCarWithNumber(String registrationNumber) throws ParkingSystemException;

    public List<Integer> getSlotNumbersForDriverOfAge(int age);

    public List<String> getRegistrationNumbersForDriverOfAge(int age);

    /**
     * Cleans the parking lot DAO
     */
    public void cleanParkingLot();

}
