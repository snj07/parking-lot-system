package service;

import exception.ParkingSystemException;
import model.Vehicle;

import java.util.List;
import java.util.Optional;

/**
 * ParkingLot services commands business logic methods
 */
public interface ParkingLotService {
    void createParkingLot(int capacity);

    Optional<Integer> parkVehicle(Vehicle vehicle) throws ParkingSystemException;

    Optional<Vehicle> leaveParkingLot(int slot) throws ParkingSystemException;

    Optional<Integer> getSlotNumberForCarWithNumber(String registrationNumber) throws ParkingSystemException;

    List<Integer> getSlotNumbersForDriverOfAge(int age);

    List<String> getRegistrationNumbersForDriverOfAge(int age);

    /**
     * Cleans the parking lot DAO
     */
    void cleanParkingLot();

}
