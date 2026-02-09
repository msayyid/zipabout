package com.example.zipaboutgui.ui.controller;

import com.example.zipaboutgui.domain.vehicle.Vehicle;
import com.example.zipaboutgui.service.RentalService;
import com.example.zipaboutgui.ui.util.AdminVehicleContext;
import com.example.zipaboutgui.ui.util.SceneSwitcher;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * Controller for the Admin "Manage Vehicles" screen.
 *
 * <p>This screen allows administrators to view all registered vehicles
 * in the system and inspect their current status.</p>
 *
 * <p>From this screen, admins can:
 * <ul>
 *   <li>View a list of all vehicles</li>
 *   <li>See availability status (available or booked)</li>
 *   <li>Open a detailed view of a selected vehicle</li>
 *   <li>Add new vehicles</li>
 * </ul>
 * </p>
 *
 * <p>No rental or booking actions are allowed from this screen.</p>
 */
public class AdminVehiclesController {

    /**
     * Table displaying all vehicles in the system.
     */
    @FXML
    private TableView<Vehicle> vehiclesTable;

    /**
     * Column showing the vehicle ID.
     */
    @FXML
    private TableColumn<Vehicle, String> idColumn;

    /**
     * Column showing the vehicle type.
     */
    @FXML
    private TableColumn<Vehicle, String> typeColumn;

    /**
     * Column showing the vehicle model.
     */
    @FXML
    private TableColumn<Vehicle, String> modelColumn;

    /**
     * Column showing the vehicle availability status.
     */
    @FXML
    private TableColumn<Vehicle, String> statusColumn;

    /**
     * Singleton service providing access to vehicles and rentals.
     */
    private final RentalService rentalService = RentalService.getInstance();

    /**
     * JavaFX initialization method.
     *
     * <p>Configures the table columns and populates the table
     * with all vehicles retrieved from {@link RentalService}.</p>
     */
    @FXML
    public void initialize() {

        // Vehicle ID column
        idColumn.setCellValueFactory(
                data -> new SimpleStringProperty(data.getValue().getId())
        );

        // Vehicle type column
        typeColumn.setCellValueFactory(
                data -> new SimpleStringProperty(data.getValue().getVehicleType())
        );

        // Vehicle model column
        modelColumn.setCellValueFactory(
                data -> new SimpleStringProperty(data.getValue().getModel())
        );

        // Availability status column
        statusColumn.setCellValueFactory(
                data -> new SimpleStringProperty(
                        data.getValue().isAvailable() ? "AVAILABLE" : "BOOKED"
                )
        );

        // Populate table with all vehicles
        vehiclesTable.setItems(
                FXCollections.observableArrayList(
                        rentalService.getVehicles()
                )
        );
    }

    /**
     * Handles the "Add Vehicle" button action.
     * Navigates to the add-vehicle form.
     */
    @FXML
    private void handleAddVehicle() {
        SceneSwitcher.switchTo("admin_add_vehicle.fxml");
    }

    /**
     * Handles the "Back" button action.
     * Navigates back to the admin home screen.
     */
    @FXML
    private void handleBack() {
        SceneSwitcher.switchTo("admin_home.fxml");
    }

    /**
     * Handles the "View Details" button action.
     *
     * <p>Opens a detailed, read-only view of the selected vehicle.</p>
     */
    @FXML
    private void handleViewDetails() {
        Vehicle selected =
                vehiclesTable.getSelectionModel().getSelectedItem();

        // Safety check: do nothing if no vehicle is selected
        if (selected == null) return;

        // Store selected vehicle in shared context
        AdminVehicleContext.setSelectedVehicle(selected);

        // Navigate to vehicle details screen
        SceneSwitcher.switchTo("admin_vehicle_details.fxml");
    }
}
