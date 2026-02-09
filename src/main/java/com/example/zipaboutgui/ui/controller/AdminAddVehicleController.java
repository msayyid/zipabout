package com.example.zipaboutgui.ui.controller;

import com.example.zipaboutgui.domain.enums.VehicleKind;
import com.example.zipaboutgui.domain.vehicle.Vehicle;
import com.example.zipaboutgui.domain.vehicle.VehicleFactory;
import com.example.zipaboutgui.service.RentalService;
import com.example.zipaboutgui.ui.util.SceneSwitcher;
import javafx.fxml.FXML;
import javafx.scene.control.*;

/**
 * Controller for the "Add Vehicle" screen in the admin interface.
 *
 * <p>This controller allows administrators to register new vehicles
 * in the system through the GUI.</p>
 *
 * <p>Its responsibilities are limited to:
 * <ul>
 *   <li>Collecting vehicle information from the form</li>
 *   <li>Performing basic input validation</li>
 *   <li>Delegating vehicle creation to {@link VehicleFactory}</li>
 *   <li>Registering the vehicle via {@link RentalService}</li>
 *   <li>Handling navigation back to the vehicles list</li>
 * </ul>
 * </p>
 *
 * <p>No rental or business rules are implemented here.</p>
 */
public class AdminAddVehicleController {

    /**
     * ComboBox for selecting the vehicle type (e.g. Bike, E-Bike, Scooter).
     */
    @FXML
    private ComboBox<VehicleKind> typeComboBox;

    /**
     * Text field for entering the vehicle brand.
     */
    @FXML
    private TextField brandField;

    /**
     * Text field for entering the vehicle model.
     */
    @FXML
    private TextField modelField;

    /**
     * Text field for entering the vehicle asset code.
     * This represents a real-world identifier for the vehicle.
     */
    @FXML
    private TextField assetCodeField;

    /**
     * Singleton service responsible for managing users, vehicles, and rentals.
     */
    private final RentalService rentalService = RentalService.getInstance();

    /**
     * Factory used to create vehicles based on their type.
     * This mirrors the factory usage from Sprint 2.
     */
    private final VehicleFactory factory = new VehicleFactory();

    /**
     * JavaFX initialization method.
     *
     * <p>Populates the vehicle type dropdown using the {@link VehicleKind} enum.</p>
     */
    @FXML
    public void initialize() {
        typeComboBox.getItems().setAll(VehicleKind.values());
    }

    /**
     * Handles the "Add Vehicle" button action.
     *
     * <p>This method:
     * <ol>
     *   <li>Reads input values from the form</li>
     *   <li>Validates that all required fields are filled</li>
     *   <li>Creates a new {@link Vehicle} using {@link VehicleFactory}</li>
     *   <li>Assigns an asset code to the vehicle</li>
     *   <li>Registers the vehicle via {@link RentalService}</li>
     *   <li>Navigates back to the vehicles management screen</li>
     * </ol>
     * </p>
     */
    @FXML
    private void handleAddVehicle() {

        // Read form input
        VehicleKind kind = typeComboBox.getValue();
        String brand = brandField.getText();
        String model = modelField.getText();
        String assetCode = assetCodeField.getText();

        // Basic validation
        if (kind == null || brand.isBlank() || model.isBlank() || assetCode.isBlank()) {
            showWarning("All fields must be filled.");
            return;
        }

        // Create a minimal vehicle instance (no equipment or electric parts for now)
        Vehicle vehicle = factory.createVehicle(
                kind,
                brand,
                model,
                new com.example.zipaboutgui.domain.equipment.Equipment[0],
                null,
                null,
                null
        );

        // Assign a real-world asset identifier
        vehicle.assignAssetCode(assetCode);

        // Register the vehicle in the system
        rentalService.registerVehicle(vehicle);

        // Return to the admin vehicles screen
        SceneSwitcher.switchTo("admin_vehicles.fxml");
    }

    /**
     * Handles the "Back" button action.
     * Navigates back to the admin vehicles management screen.
     */
    @FXML
    private void handleBack() {
        SceneSwitcher.switchTo("admin_vehicles.fxml");
    }

    /**
     * Displays a warning dialog with the given message.
     *
     * @param message the warning message to display
     */
    private void showWarning(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
