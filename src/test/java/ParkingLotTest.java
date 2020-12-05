import constant.ErrorMessage;
import constant.OutputMessage;
import controller.CommandController;
import controller.impl.CommandPatternController;
import exception.ParkingSystemException;
import model.Car;
import model.Driver;
import observers.impl.CommandConsoleOutputObserver;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.impl.NearestSlotParkingService;
import utils.FileUtils;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;


public class ParkingLotTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;
    private NearestSlotParkingService nearestSlotParkingService;

    @BeforeEach
    public void init() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
        nearestSlotParkingService = NearestSlotParkingService.getInstance();
    }

    @AfterEach
    public void cleanUp() {
        System.setOut(originalOut);
        System.setErr(originalErr);

        nearestSlotParkingService.cleanParkingLot();
        nearestSlotParkingService = null;
    }

    @Test
    public void testCreateParkingLot() {
        CommandController commandController = new CommandPatternController(nearestSlotParkingService, new CommandConsoleOutputObserver());
        commandController.runCommand("create_parking_lot 6");

        String expected = FileUtils.buildExpectedString(String.format(OutputMessage.CREATED_PARKING_LOT_MESSAGE, 6));
        assertTrue(expected.equals(outContent.toString()));
    }


    @Test
    public void testParkingCapacity() {
        nearestSlotParkingService.createParkingLot(3);
        nearestSlotParkingService.parkVehicle(new Car("KA-01-AA-1234", new Driver(21)));
        nearestSlotParkingService.parkVehicle(new Car("KA-01-AB-1234", new Driver(22)));
        nearestSlotParkingService.parkVehicle(new Car("KA-01-AC-1234", new Driver(23)));
        assertThrows(ParkingSystemException.class, () -> nearestSlotParkingService.parkVehicle(new Car("KA-01-AD-1234", new Driver(21))),
                ErrorMessage.NO_FREE_SLOT_FOUND);
        nearestSlotParkingService.cleanParkingLot();
    }

    @Test
    public void testSameVehicleParking()   {
        nearestSlotParkingService.createParkingLot(3);
        nearestSlotParkingService.parkVehicle(new Car("KA-01-AA-1234", new Driver(21)));
        nearestSlotParkingService.parkVehicle(new Car("KA-01-AB-1234", new Driver(22)));
        assertThrows(ParkingSystemException.class, () -> nearestSlotParkingService.parkVehicle(new Car("KA-01-AA-1234", new Driver(21))),
                String.format(ErrorMessage.VEHICLE_ALREADY_EXIST, "KA-01-AA-1234"));
    }

    @Test
    public void testNearestSlotParkingAfterLeave()   {
        nearestSlotParkingService.createParkingLot(6);
        nearestSlotParkingService.parkVehicle(new Car("KA-01-AA-1234", new Driver(21)));
        nearestSlotParkingService.parkVehicle(new Car("KA-01-AB-1234", new Driver(22)));
        nearestSlotParkingService.parkVehicle(new Car("KA-01-AC-1234", new Driver(22)));
        nearestSlotParkingService.parkVehicle(new Car("KA-01-AD-1234", new Driver(22)));
        nearestSlotParkingService.leaveParkingLot(2);

        assertEquals(2, nearestSlotParkingService.parkVehicle(new Car("KA-01-AE-1234", new Driver(22))).get().intValue());
    }

    @Test
    public void testCommandSet1(){

        CommandController commandController = new CommandPatternController(nearestSlotParkingService, new CommandConsoleOutputObserver());
        commandController.runCommand("create_parking_lot 6");
        commandController.runCommand("park KA-01-HH-1234 driver_age 21");
        commandController.runCommand("park PB-01-HH-1234 driver_age 21");
        commandController.runCommand("slot_numbers_for_driver_of_age 21");
        commandController.runCommand("park PB-01-TG-2341 driver_age 40");
        commandController.runCommand("slot_number_for_car_with_number PB-01-HH-1234");
        commandController.runCommand("leave 2");
        commandController.runCommand("park HR-29-TG-3098 driver_age 39");
        commandController.runCommand("vehicle_registration_number_for_driver_of_age 18");

        String outputSet1 = "Created parking of 6 slots\n" +
                "Car with vehicle registration number \"KA-01-HH-1234\" has been parked at slot number 1\n" +
                "Car with vehicle registration number \"PB-01-HH-1234\" has been parked at slot number 2\n" +
                "1,2\n" +
                "Car with vehicle registration number \"PB-01-TG-2341\" has been parked at slot number 3\n" +
                "2\n" +
                "Slot number 2 vacated, the car with vehicle registration number \"PB-01-HH-1234\" left the space, the driver of the car was of age 21\n" +
                "Car with vehicle registration number \"HR-29-TG-3098\" has been parked at slot number 2\n" +
                "null\n";
        String expected = FileUtils.buildExpectedString(outputSet1);
        assertTrue(expected.equals(outContent.toString()));
    }

    @Test
    public void testCommandSet2()  {

        CommandController commandController = new CommandPatternController(nearestSlotParkingService, new CommandConsoleOutputObserver());

        String commandSet = "park KA-01-HH-1234 driver_age 21\n" +
                "create_parking_lot 6\n" +
                "park PB-01-HA-1234 driver_age 24\n" +
                "park PB-01-HB-1234 driver_age 24\n" +
                "park PB-01-HC-1234 driver_age 21\n" +
                "park PB-01-HD-1234 driver_age 21\n" +
                "park PB-01-HE-1234 driver_age 21\n" +
                "park PB-01-HF-1234 driver_age 21\n" +
                "slot_numbers_for_driver_of_age 22\n" +
                "park PB-01-TG-2341 driver_age 40\n" +
                "slot_number_for_car_with_number PB-01-HH-1234\n" +
                "leave 3\n" +
                "park PB-01-TG-2341 driver_age 41\n" +
                "park PB-01-TG-1341 driver_age 42\n" +
                "slot_number_for_car_with_number PB-01-HB-1234\n" +
                "park PB-01-HB-1234\n" +
                "park PB-01-HF-1234 driver_age 21\n" +
                "leave 1\n" +
                "park PB-01-HF-1234 driver_age 21\n" +
                "vehicle_registration_number_for_driver_of_age 21\n" +
                "slot_numbers_for_driver_of_age 21\n" +
                "slot_numbers_for_driver_of_age 24\n" +
                "create_parking_lot 6";
        String[] commandsArr = commandSet.split("\n");
        for (String singleCommand : commandsArr) {
            commandController.runCommand(singleCommand);
        }


        String outputSet = "Could not find any parking lot\n" +
                "Created parking of 6 slots\n" +
                "Car with vehicle registration number \"PB-01-HA-1234\" has been parked at slot number 1\n" +
                "Car with vehicle registration number \"PB-01-HB-1234\" has been parked at slot number 2\n" +
                "Car with vehicle registration number \"PB-01-HC-1234\" has been parked at slot number 3\n" +
                "Car with vehicle registration number \"PB-01-HD-1234\" has been parked at slot number 4\n" +
                "Car with vehicle registration number \"PB-01-HE-1234\" has been parked at slot number 5\n" +
                "Car with vehicle registration number \"PB-01-HF-1234\" has been parked at slot number 6\n" +
                "null\n" +
                "Could not find any free parking slot\n" +
                "null\n" +
                "Slot number 3 vacated, the car with vehicle registration number \"PB-01-HC-1234\" left the space, the driver of the car was of age 21\n" +
                "Car with vehicle registration number \"PB-01-TG-2341\" has been parked at slot number 3\n" +
                "Could not find any free parking slot\n" +
                "2\n" +
                "Invalid command : park PB-01-HB-1234\n" +
                "Vehicle with registration number PB-01-HF-1234 already exists\n" +
                "Slot number 1 vacated, the car with vehicle registration number \"PB-01-HA-1234\" left the space, the driver of the car was of age 24\n" +
                "Vehicle with registration number PB-01-HF-1234 already exists\n" +
                "PB-01-HD-1234,PB-01-HE-1234,PB-01-HF-1234\n" +
                "4,5,6\n" +
                "2\n" +
                "ParkingLot already exists\n";
        String expected = FileUtils.buildExpectedString(outputSet);
        assertTrue(expected.equals(outContent.toString()));
    }


}


