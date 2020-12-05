package model;

import java.util.Objects;

/**
 * Vehicle data model for storing vehicle's data
 */
public abstract class Vehicle {
    private String registrationNumber;
    private Driver driver;


    public Vehicle(String registrationNumber, Driver driver) {
        this.registrationNumber = registrationNumber;
        this.driver = driver;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vehicle vehicle = (Vehicle) o;
        return registrationNumber.equals(vehicle.registrationNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(registrationNumber);
    }
}
