package strategy;

import java.util.TreeSet;

/**
 * Parking Strategy for handling allocation or de-allocation of slot
 */
public class NearestSlotParkingStrategy implements ParkingStrategy {
    private final TreeSet<Integer> freeSlots;

    public NearestSlotParkingStrategy() {
        freeSlots = new TreeSet<Integer>();
    }

    @Override
    public void addSlot(int i) {
        freeSlots.add(i);
    }

    @Override
    public int getFreeSlotCount() {
        return freeSlots.size();
    }

    @Override
    public int getFreeSlot() {
        return freeSlots.first();
    }

    @Override
    public boolean removeSlot(int availableSlot) {
        return freeSlots.remove(availableSlot);
    }
}
