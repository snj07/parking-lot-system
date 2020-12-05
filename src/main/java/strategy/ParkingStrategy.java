package strategy;

/**
 * Parking strategy structure for implementation
 */
public interface ParkingStrategy {

    public void addSlot(int slot);

    public int getFreeSlot();

    public int getFreeSlotCount();

    public boolean removeSlot(int slot);


}
