package dao;

import model.Vehicle;

import java.util.List;

/**
 * Parking lot DAO structure
 */
public interface ParkingLotDataHandler {


    public int parkVehicle(Vehicle vehicle);

    public Vehicle leaveParkingLot(int slot);

    public List<Vehicle> getAllVehiclesParkedByDriverOfAge(int age);

    public Integer getVehicleParkingSlotNumber(String registrationNumber);

    public List<Integer> getAllSlotsParkedByDriverOfAge(int age);

    /**
     * cleans the ParkingLot DAO object completely
     */
    public void clearParkingLot();
}
