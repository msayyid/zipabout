package com.example.zipaboutgui.ui.controller;

import com.example.zipaboutgui.service.RentalService;
import com.example.zipaboutgui.service.Rental;
import com.example.zipaboutgui.ui.util.SceneSwitcher;
import com.example.zipaboutgui.ui.util.Session;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.format.DateTimeFormatter;

/**
 * Controller for the "My Rentals" screen.
 *
 * <p>This screen allows a logged-in user to view their own rental history.</p>
 *
 * <p>The table displays:
 * <ul>
 *   <li>Vehicle model</li>
 *   <li>Vehicle type</li>
 *   <li>Rental start time</li>
 *   <li>Rental end time (or Active if ongoing)</li>
 *   <li>Rental status</li>
 * </ul>
 * </p>
 *
 * <p>Both active and completed rentals are shown.</p>
 */
public class MyRentalsController {

    /**
     * Table displaying all rentals for the currently logged-in user.
     */
    @FXML
    private TableView<Rental> rentalsTable;

    /**
     * Column showing the rented vehicle model.
     */
    @FXML
    private TableColumn<Rental, String> vehicleCol;

    /**
     * Column showing the vehicle type.
     */
    @FXML
    private TableColumn<Rental, String> typeCol;

    /**
     * Column showing the rental start time.
     */
    @FXML
    private TableColumn<Rental, String> startCol;

    /**
     * Column showing the rental end time (or Active if not completed).
     */
    @FXML
    private TableColumn<Rental, String> endCol;

    /**
     * Column showing the rental status.
     */
    @FXML
    private TableColumn<Rental, String> statusCol;

    /**
     * Singleton service providing access to rental data.
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
     * <p>Configures the table columns and populates the table
     * with all rentals belonging to the currently logged-in user.</p>
     */
    @FXML
    public void initialize() {

        // Vehicle model column
        vehicleCol.setCellValueFactory(
                cell -> new javafx.beans.property.SimpleStringProperty(
                        cell.getValue().getVehicle().getModel()
                )
        );

        // Vehicle type column
        typeCol.setCellValueFactory(
                cell -> new javafx.beans.property.SimpleStringProperty(
                        cell.getValue().getVehicle().getVehicleType().toString()
                )
        );

        // Rental start time column
        startCol.setCellValueFactory(cell ->
                new javafx.beans.property.SimpleStringProperty(
                        cell.getValue().getStartTime() != null
                                ? cell.getValue().getStartTime().format(TIME_FORMATTER)
                                : "n/a"
                )
        );

        // Rental end time column
        endCol.setCellValueFactory(cell ->
                new javafx.beans.property.SimpleStringProperty(
                        cell.getValue().getEndTime() != null
                                ? cell.getValue().getEndTime().format(TIME_FORMATTER)
                                : "Active"
                )
        );

        // Rental status column (uses Rental.getStatus())
        statusCol.setCellValueFactory(
                new PropertyValueFactory<>("status")
        );

        // Populate table with rentals for the logged-in user
        rentalsTable.getItems().setAll(
                rentalService.getRentalsForUser(Session.getCurrentUser())
        );
    }

    /**
     * Handles the "Back" button action.
     * Navigates back to the vehicles screen.
     */
    @FXML
    private void handleBack() {
        SceneSwitcher.switchTo("vehicles.fxml");
    }
}
