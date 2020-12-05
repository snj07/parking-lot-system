package dao;

import model.Vehicle;

import java.util.List;

/**
 * Parking lot DAO structure
 */
public interface ParkingLotDataHandler {


    int parkVehicle(Vehicle vehicle);

    Vehicle leaveParkingLot(int slot);

    List<Vehicle> getAllVehiclesParkedByDriverOfAge(int age);

    Integer getVehicleParkingSlotNumber(String registrationNumber);

    List<Integer> getAllSlotsParkedByDriverOfAge(int age);

    /**
     * cleans the ParkingLot DAO object completely
     */
    void clearParkingLot();
}
