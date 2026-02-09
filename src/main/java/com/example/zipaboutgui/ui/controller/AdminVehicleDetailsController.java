package com.example.zipaboutgui.ui.controller;

import com.example.zipaboutgui.domain.vehicle.Vehicle;
import com.example.zipaboutgui.service.Rental;
import com.example.zipaboutgui.service.RentalService;
import com.example.zipaboutgui.ui.util.AdminVehicleContext;
import com.example.zipaboutgui.ui.util.SceneSwitcher;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Controller for the Admin "Vehicle Details" screen.
 *
 * <p>This screen provides administrators with a detailed, read-only
 * view of a selected vehicle.</p>
 *
 * <p>Displayed information includes:
 * <ul>
 *   <li>Basic vehicle details (ID, type, model, status)</li>
 *   <li>Current active rental (if any)</li>
 *   <li>Past rental history for the vehicle</li>
 * </ul>
 * </p>
 *
 * <p>The selected vehicle is passed using {@link AdminVehicleContext}.</p>
 */
public class AdminVehicleDetailsController {

    // --- Basic vehicle info labels ---

    @FXML
    private Label idLabel;

    @FXML
    private Label typeLabel;

    @FXML
    private Label modelLabel;

    @FXML
    private Label statusLabel;

    // --- Active rental display ---

    @FXML
    private Label activeRentalLabel;

    // --- Past rentals table ---

    @FXML
    private TableView<Rental> pastRentalsTable;

    @FXML
    private TableColumn<Rental, String> userColumn;

    @FXML
    private TableColumn<Rental, String> startColumn;

    @FXML
    private TableColumn<Rental, String> endColumn;

    @FXML
    private TableColumn<Rental, String> statusColumn;

    /**
     * Singleton service providing access to vehicles and rentals.
     */
    private final RentalService rentalService = RentalService.getInstance();

    /**
     * Formatter used to display date-time values consistently.
     */
    private static final DateTimeFormatter TIME_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    /**
     * JavaFX initialization method.
     *
     * <p>Loads the selected vehicle from {@link AdminVehicleContext}
     * and populates all UI fields.</p>
     */
    @FXML
    public void initialize() {

        // Retrieve selected vehicle from context
        Vehicle vehicle = AdminVehicleContext.getSelectedVehicle();

        // Safety check: if no vehicle is selected, return to vehicles list
        if (vehicle == null) {
            SceneSwitcher.switchTo("admin_vehicles.fxml");
            return;
        }

        /* -------------------------------
           Basic vehicle information
         -------------------------------- */
        idLabel.setText(vehicle.getId());
        typeLabel.setText(vehicle.getVehicleType());
        modelLabel.setText(vehicle.getModel());
        statusLabel.setText(
                vehicle.isAvailable() ? "AVAILABLE" : "BOOKED"
        );

        /* -------------------------------
           Active rental (if any)
         -------------------------------- */
        Rental active = rentalService.getActiveRentalForVehicle(vehicle);
        if (active == null) {
            activeRentalLabel.setText("None");
        } else {
            activeRentalLabel.setText(
                    active.getUser().getUsername()
                            + " (" + active.getUser().getName() + ")"
                            + " since "
                            + active.getStartTime().format(TIME_FORMATTER)
            );
        }

        /* -------------------------------
           Past rentals table configuration
         -------------------------------- */
        userColumn.setCellValueFactory(
                data -> new SimpleStringProperty(
                        data.getValue().getUser().getUsername()
                                + " (" + data.getValue().getUser().getName() + ")"
                )
        );

        startColumn.setCellValueFactory(
                data -> new SimpleStringProperty(
                        data.getValue().getStartTime().format(TIME_FORMATTER)
                )
        );

        endColumn.setCellValueFactory(
                data -> new SimpleStringProperty(
                        data.getValue().getEndTime() != null
                                ? data.getValue().getEndTime().format(TIME_FORMATTER)
                                : "n/a"
                )
        );

        statusColumn.setCellValueFactory(
                data -> new SimpleStringProperty(
                        data.getValue().getStatus().toString()
                )
        );

        // Load past rentals for the selected vehicle
        List<Rental> pastRentals =
                rentalService.getPastRentalsForVehicle(vehicle);

        pastRentalsTable.setItems(
                FXCollections.observableArrayList(pastRentals)
        );
    }

    /**
     * Handles the "Back" button action.
     * Clears the selected vehicle context and returns to the vehicles list.
     */
    @FXML
    private void handleBack() {
        AdminVehicleContext.clear();
        SceneSwitcher.switchTo("admin_vehicles.fxml");
    }
}
