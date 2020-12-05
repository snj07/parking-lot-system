package constant;

import java.util.HashMap;
import java.util.Map;


/**
 * Command constants for ParkingLot system
 */
public class CommandConstants {
    // Create a new parking lot commands
    public static final String CREATE_PARKING_LOT = "create_parking_lot";
    public static final String PARK_VEHICLE = "park";
    public static final String LEAVE_PARKING_LOT = "leave";

    // Query Parking lot command
    public static final String SLOT_NUMBERS_FOR_DRIVER_OF_AGE = "slot_numbers_for_driver_of_age";
    public static final String SLOT_NUMBER_FOR_CAR_WITH_NUMBER = "slot_number_for_car_with_number";
    public static final String VEHICLE_REGISTRATION_NUMBERS_FOR_DRIVER_OF_AGE = "vehicle_registration_number_for_driver_of_age";

    // regex mapping for all the commands
    static private final Map<String, String> commandsPatternMap = new HashMap<>();

    static {
        commandsPatternMap.put(CommandConstants.CREATE_PARKING_LOT, "(create_parking_lot) (\\d+)");
        commandsPatternMap.put(CommandConstants.PARK_VEHICLE, "(park) ([A-Za-z]{2}-\\d{2}-\\w{2}-\\w{4}) (driver_age) (\\d+)");
        commandsPatternMap.put(CommandConstants.LEAVE_PARKING_LOT, "(leave) (\\d+)");
        commandsPatternMap.put(CommandConstants.SLOT_NUMBERS_FOR_DRIVER_OF_AGE, "(slot_numbers_for_driver_of_age) (\\d+)");
        commandsPatternMap.put(CommandConstants.SLOT_NUMBER_FOR_CAR_WITH_NUMBER, "(slot_number_for_car_with_number) ([A-Za-z]{2}-\\d{2}-\\w{2}-\\w{4})");
        commandsPatternMap.put(CommandConstants.VEHICLE_REGISTRATION_NUMBERS_FOR_DRIVER_OF_AGE, "(vehicle_registration_number_for_driver_of_age) (\\d+)");

    }

    public static Map<String, String> getCommandsPatternMap() {
        return commandsPatternMap;
    }
}
