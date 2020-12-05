package strategy;

/**
 * Parking strategy structure for implementation
 */
public interface ParkingStrategy {

    void addSlot(int slot);

    int getFreeSlot();

    int getFreeSlotCount();

    boolean removeSlot(int slot);


}
