package com.example.zipaboutgui.app;

import com.example.zipaboutgui.domain.enums.EquipmentType;
import com.example.zipaboutgui.domain.enums.Role;
import com.example.zipaboutgui.domain.enums.VehicleKind;
import com.example.zipaboutgui.domain.equipment.Equipment;
import com.example.zipaboutgui.domain.parts.Battery;
import com.example.zipaboutgui.domain.parts.Controller;
import com.example.zipaboutgui.domain.parts.Motor;
import com.example.zipaboutgui.domain.vehicle.Vehicle;
import com.example.zipaboutgui.domain.vehicle.VehicleFactory;
import javafx.application.Application;
import javafx.stage.Stage;

import com.example.zipaboutgui.ui.util.SceneSwitcher;
import com.example.zipaboutgui.service.RentalService;
import com.example.zipaboutgui.domain.user.User;

/**
 * Entry point for the ZipAbout JavaFX application.
 *
 * <p>This class bootstraps the GUI version of the system.
 * It is intentionally separate from the CLI entry point,
 * which remains unchanged from Sprint 2.</p>
 *
 * <p>Responsibilities:
 * <ul>
 *   <li>Initialize shared services</li>
 *   <li>Seed temporary data for GUI testing</li>
 *   <li>Set up the primary JavaFX stage</li>
 *   <li>Load the initial login screen</li>
 * </ul>
 * </p>
 */
public class ZipAboutApp extends Application {

    /**
     * JavaFX lifecycle method.
     * This is called automatically when the application starts.
     *
     * @param primaryStage the main window (stage) for the application
     */
    @Override
    public void start(Stage primaryStage) {

        // Ensure vehicles exist before the UI loads
        // This avoids empty tables when launching the GUI
        RentalService.getInstance().seedVehiclesIfEmpty();

        // Seed temporary users for GUI testing ONLY
        // These users are not persisted and are safe to remove later
        seedTestUsers();

        // Store the stage globally so scenes can be switched
        SceneSwitcher.setStage(primaryStage);

        // Load the login screen as the initial view
        SceneSwitcher.switchTo("login.fxml");

        // Configure window properties
        primaryStage.setTitle("ZipAbout - Vehicle Rental");
        primaryStage.setWidth(700);
        primaryStage.setHeight(580);
        primaryStage.setResizable(true);

        primaryStage.show();
    }

    /**
     * Seeds temporary users for GUI testing.
     *
     * <p>This replaces real authentication for Sprint 3
     * and allows quick testing of user and admin flows.</p>
     *
     * <p>These users exist only in memory.</p>
     */
    private void seedTestUsers() {
        RentalService rentalService = RentalService.getInstance();

        rentalService.registerUser(
                new User("01", "user1", "User 1", "user1", Role.USER)
        );

        rentalService.registerUser(
                new User("02", "user2", "User 2", "user2", Role.USER)
        );

        rentalService.registerUser(
                new User("A1", "admin", "Admin", "admin", Role.ADMIN)
        );
    }

    /**
     * Standard Java entry point.
     * Delegates control to JavaFX.
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Seeds example vehicles for testing purposes.
     *
     * <p>NOTE:
     * This method is currently unused in the GUI version.
     * Vehicles are instead seeded via
     * {@link RentalService#seedVehiclesIfEmpty()}.</p>
     *
     * <p>It is kept intentionally as:
     * <ul>
     *   <li>a reference for vehicle creation</li>
     *   <li>a bridge to the Sprint 2 CLI logic</li>
     *   <li>a fallback for manual testing</li>
     * </ul>
     * </p>
     */
    private void seedTestVehicles() {

        RentalService rentalService = RentalService.getInstance();
        VehicleFactory factory = new VehicleFactory();

        // --- Minimal equipment ---
        Equipment helmet = new Equipment(
                EquipmentType.HELMET, "ABS plastic", "M"
        );
        Equipment[] basicSet = { helmet };

        // --- Minimal electric parts ---
        Battery battery = new Battery(400, 100, true);
        Motor motor = new Motor(250);
        Controller controller = new Controller("v1.0");

        // --- Create vehicles via factory (same as CLI) ---

        Vehicle ebike = factory.createVehicle(
                VehicleKind.E_BIKE,
                "Giant", "Explore E+",
                basicSet, battery, motor, controller
        );
        ebike.assignAssetCode("EB-001");

        Vehicle scooter = factory.createVehicle(
                VehicleKind.E_SCOOTER,
                "Xiaomi", "Pro 2",
                basicSet, battery, motor, controller
        );
        scooter.assignAssetCode("ES-001");

        Vehicle bike = factory.createVehicle(
                VehicleKind.BIKE,
                "Trek", "FX 1",
                basicSet, null, null, null
        );
        bike.assignAssetCode("BK-001");

        Vehicle skateboard = factory.createVehicle(
                VehicleKind.SKATEBOARD,
                "Element", "Section",
                basicSet, null, null, null
        );
        skateboard.assignAssetCode("SB-001");

        // --- Register vehicles ---
        rentalService.registerVehicle(ebike);
        rentalService.registerVehicle(scooter);
        rentalService.registerVehicle(bike);
        rentalService.registerVehicle(skateboard);
    }
}
