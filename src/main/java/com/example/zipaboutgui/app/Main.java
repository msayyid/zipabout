package com.example.zipaboutgui.app;

//import main.java.roehampton.msayyid.zipabout.domain.enums.BatteryChemistry;
//import main.java.roehampton.msayyid.zipabout.domain.enums.BrakeType;
//import main.java.roehampton.msayyid.zipabout.domain.enums.EquipmentType;
//import main.java.roehampton.msayyid.zipabout.domain.enums.FrameType;
//import main.java.roehampton.msayyid.zipabout.domain.enums.SuspensionType;
//import main.java.roehampton.msayyid.zipabout.domain.enums.VehicleKind;
import com.example.zipaboutgui.domain.enums.*;
//import main.java.roehampton.msayyid.zipabout.domain.equipment.Equipment;
import com.example.zipaboutgui.domain.equipment.Equipment;
//import main.java.roehampton.msayyid.zipabout.domain.parts.Battery;
//import main.java.roehampton.msayyid.zipabout.domain.parts.Controller;
//import main.java.roehampton.msayyid.zipabout.domain.parts.Motor;
import com.example.zipaboutgui.domain.parts.*;
//import main.java.roehampton.msayyid.zipabout.domain.user.User;
import com.example.zipaboutgui.domain.user.User;
//import main.java.roehampton.msayyid.zipabout.domain.vehicle.Vehicle;
//import main.java.roehampton.msayyid.zipabout.domain.vehicle.VehicleFactory;
//import main.java.roehampton.msayyid.zipabout.domain.vehicle.electric.EBike;
import com.example.zipaboutgui.domain.vehicle.*;
//import main.java.roehampton.msayyid.zipabout.service.MaintenanceObserver;
//import main.java.roehampton.msayyid.zipabout.service.NotificationObserver;
//import main.java.roehampton.msayyid.zipabout.service.RentalService;
import com.example.zipaboutgui.domain.vehicle.electric.EBike;
import com.example.zipaboutgui.service.*;

/**
 * Entry point for the ZipAbout console demo application.
 * <p>
 * This class:
 * <ul>
 *     <li>Creates equipment, parts and vehicles using a {@link VehicleFactory},</li>
 *     <li>Registers users and vehicles with the {@link RentalService} singleton,</li>
 *     <li>Demonstrates booking, double-booking prevention, releasing,</li>
 *     <li>Tests loyalty points and VIP status,</li>
 *     <li>Triggers maintenance checks via observers,</li>
 *     <li>Prints summaries of rentals, users, and vehicles.</li>
 * </ul>
 */
public class Main {

    public static void main(String[] args) {

        // --- Equipment sets -------------------------------------------------
        Equipment helmetM = new Equipment(EquipmentType.HELMET, "ABS plastic", "M");
        Equipment helmetL = new Equipment(EquipmentType.HELMET, "ABS plastic", "L");
        Equipment glovesM = new Equipment(EquipmentType.GLOVE, "Leather", "M");
        Equipment pads    = new Equipment(EquipmentType.KNEEPAD, "Foam", "Universal");

        Equipment[] safetySet1 = {helmetM, glovesM};
        Equipment[] safetySet2 = {helmetL, pads};
        Equipment[] basicSet   = {helmetM};

        // --- Electric parts -------------------------------------------------
        Battery battery1 = new Battery(400, 100, true, BatteryChemistry.LI_ION);
        Battery battery2 = new Battery(350, 80, false);
        Battery battery3 = new Battery(500, 65, true);

        Motor motor1 = new Motor(250);
        Motor motor2 = new Motor(300);
        Motor motor3 = new Motor(350);

        Controller controller1 = new Controller("v1.0");
        Controller controller2 = new Controller("v2.1");
        Controller controller3 = new Controller("v3.0");

        // --- Vehicle factory ------------------------------------------------
        VehicleFactory factory = new VehicleFactory();

        System.out.println("\n--- Creating vehicles via factory ---");

        // Electric bikes
        EBike ebike1 = (EBike) factory.createVehicle(
                VehicleKind.E_BIKE,
                "Giant", "Explore E+",
                safetySet1, battery1, motor1, controller1
        );
        ebike1.assignAssetCode("EB-001");

        EBike ebike2 = (EBike) factory.createVehicle(
                VehicleKind.E_BIKE,
                "Trek", "Powerfly 4",
                safetySet2, battery3, motor3, controller3
        );
        ebike2.assignAssetCode("EB-002");

        // tweak EB-002 to be more MTB / sporty
        ebike2.setFrameType(FrameType.MTB);
        ebike2.setSuspensionType(SuspensionType.FULL);
        ebike2.setAssistLevel(4);
        ebike2.setBrakeType(BrakeType.HYDRAULIC_DISC);
        ebike2.setWheelSizeInch(29.0);
        ebike2.setTireWidthMm(55);
        ebike2.setGearCount(11);

        // Electric scooters
        Vehicle scooter1 = factory.createVehicle(
                VehicleKind.E_SCOOTER,
                "Xiaomi", "Pro 2",
                safetySet1, battery2, motor2, controller2
        );
        scooter1.assignAssetCode("ES-001");

        Vehicle scooter2 = factory.createVehicle(
                VehicleKind.E_SCOOTER,
                "Segway-Ninebot", "Max G30",
                basicSet, battery3, motor3, controller3
        );
        scooter2.assignAssetCode("ES-002");

        // Electric skateboard
        Vehicle eskate1 = factory.createVehicle(
                VehicleKind.E_SKATEBOARD,
                "Boosted", "Mini X",
                safetySet1, battery2, motor2, controller2
        );
        eskate1.assignAssetCode("ESK-001");

        // Segway
        Vehicle segway1 = factory.createVehicle(
                VehicleKind.SEGWAY,
                "Segway", "i2 SE",
                safetySet2, battery3, motor3, controller3
        );
        segway1.assignAssetCode("SG-001");

        // Non-electric bikes
        Vehicle bike1 = factory.createVehicle(
                VehicleKind.BIKE,
                "Trek", "FX 1",
                safetySet1, null, null, null
        );
        bike1.assignAssetCode("BK-001");

        Vehicle bike2 = factory.createVehicle(
                VehicleKind.BIKE,
                "Specialized", "Sirrus 2.0",
                basicSet, null, null, null
        );
        bike2.assignAssetCode("BK-002");

        // Kick scooter
        Vehicle kick1 = factory.createVehicle(
                VehicleKind.KICK_SCOOTER,
                "Oxelo", "Town 7XL",
                safetySet2, null, null, null
        );
        kick1.assignAssetCode("KS-001");

        // Skateboard
        Vehicle skate1 = factory.createVehicle(
                VehicleKind.SKATEBOARD,
                "Element", "Section",
                basicSet, null, null, null
        );
        skate1.assignAssetCode("SB-001");

        Vehicle[] allVehicles = {
                ebike1, ebike2,
                scooter1, scooter2,
                eskate1, segway1,
                bike1, bike2,
                kick1, skate1
        };

        // --- Users ----------------------------------------------------------
        User alice = new User("U001", "Alice Smith");
        User bob   = new User("U002", "Bob Johnson");
        User carol = new User("U003", "Carol Lee");
        User dave  = new User("U004", "David Brown");
        User eve   = new User("U005", "Eve Davis");

        User[] users = {alice, bob, carol, dave, eve};

        // --- Rental service (singleton) ------------------------------------
        RentalService rentalService = RentalService.getInstance();

        // Register observers (Observer pattern)
        NotificationObserver notificationObserver = new NotificationObserver();
        MaintenanceObserver maintenanceObserver   = new MaintenanceObserver();
        rentalService.addObserver(notificationObserver);
        rentalService.addObserver(maintenanceObserver);

        // --- Register users and vehicles -----------------------------------
        System.out.println("\n--- Registering users ---");
        for (User u : users) {
            rentalService.registerUser(u);
        }

        System.out.println("\n--- Registering vehicles ---");
        for (Vehicle v : allVehicles) {
            rentalService.registerVehicle(v);
        }

        // --- Booking demo ---------------------------------------------------
        System.out.println("\n--- Booking demo ---");
        rentalService.bookVehicle(alice, ebike1);   // should succeed
        rentalService.bookVehicle(bob, ebike1);     // should fail (already booked by Alice)
        rentalService.bookVehicle(bob, scooter1);   // succeed
        rentalService.bookVehicle(carol, bike1);    // succeed
        rentalService.bookVehicle(dave, segway1);   // succeed
        rentalService.bookVehicle(eve, kick1);      // succeed
        // leave ebike2, scooter2, eskate1, bike2, skate1 free initially

        // --- Polymorphism test: go() + brake() -----------------------------
        System.out.println("\n--- Polymorphism test: go() and brake() ---");
        for (Vehicle v : allVehicles) {
            v.go();
            v.brake();
            System.out.println();
        }

        // --- Release some rentals to trigger COMPLETED + loyalty + observers
        System.out.println("\n--- Completing some rentals ---");
        rentalService.releaseVehicle(alice, ebike1);   // 1 completed for Alice, EB-001
        rentalService.releaseVehicle(bob, scooter1);   // 1 completed for Bob, ES-001

        // --- Loyalty test: Alice makes more rentals on EB-002 --------------
        System.out.println("\n--- Loyalty test: Alice makes more rentals on EB-002 ---");
        for (int i = 0; i < 5; i++) {  // 5 rentals
            rentalService.bookVehicle(alice, ebike2);
            rentalService.releaseVehicle(alice, ebike2);
        }

        System.out.println("\n--- Alice after loyalty test ---");
        alice.printDetails();
        alice.redeemFreeRide();
        alice.printDetails();

        // --- Maintenance threshold test: force EB-002 to hit 10 rentals ----
        System.out.println("\n--- Maintenance threshold test on EB-002 ---");
        // At this point EB-002 already has 5 completed rentals from the loop above.
        // We'll add 5 more to reach 10 and trigger the maintenance threshold.
        for (int i = 0; i < 5; i++) {
            rentalService.bookVehicle(alice, ebike2);
            rentalService.releaseVehicle(alice, ebike2);
        }

        // --- Active rentals summary ----------------------------------------
        System.out.println("\n--- Active rentals ---");
        var activeRentals = rentalService.getActiveRentals();
        System.out.println("Number of active rentals: " + activeRentals.size());

        // --- All rentals with status + times + duration --------------------
        System.out.println("\n--- All rentals summary ---");
//        rentalService.printAllRentals();

        // --- Final user details --------------------------------------------
        System.out.println("\n--- User details ---");
        for (User u : users) {
            u.printDetails();
        }

        // --- Final vehicle details -----------------------------------------
        System.out.println("\n--- Vehicle details ---");
        for (Vehicle v : allVehicles) {
            v.printDetails();
        }

        // --- Maintenance observer message ----------------------------------
        System.out.println("\n--- Maintenance Usage Summary (from observer) ---");
        maintenanceObserver.printUsageSummary();

        System.out.println("hello world, mate");
    }
}
