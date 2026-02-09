package com.example.zipaboutgui.ui.controller;

import com.example.zipaboutgui.service.Rental;
import com.example.zipaboutgui.service.RentalService;
import com.example.zipaboutgui.ui.util.SceneSwitcher;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.time.format.DateTimeFormatter;

/**
 * Controller for the Admin "Active Rentals" screen.
 *
 * <p>This screen provides administrators with a read-only view
 * of all currently active rentals in the system.</p>
 *
 * <p>The admin can monitor:
 * <ul>
 *   <li>Which user is renting</li>
 *   <li>Which vehicle is rented</li>
 *   <li>When the rental started</li>
 *   <li>The current rental status</li>
 * </ul>
 * </p>
 *
 * <p>No modification or control of rentals is allowed from this screen.</p>
 */
public class AdminController {

    /**
     * Table displaying all active rentals.
     */
    @FXML
    private TableView<Rental> rentalsTable;

    /**
     * Column showing the username of the renting user.
     */
    @FXML
    private TableColumn<Rental, String> usernameColumn;

    /**
     * Column showing the full name of the renting user.
     */
    @FXML
    private TableColumn<Rental, String> nameColumn;

    /**
     * Column showing the type of the rented vehicle.
     */
    @FXML
    private TableColumn<Rental, String> typeColumn;

    /**
     * Column showing the model of the rented vehicle.
     */
    @FXML
    private TableColumn<Rental, String> modelColumn;

    /**
     * Column showing the rental start time.
     */
    @FXML
    private TableColumn<Rental, String> startTimeColumn;

    /**
     * Column showing the current rental status.
     */
    @FXML
    private TableColumn<Rental, String> statusColumn;

    /**
     * Singleton service that provides access to rental data.
     */
    private final RentalService rentalService = RentalService.getInstance();

    /**
     * Formatter used to display date-time values consistently in the table.
     */
    private static final DateTimeFormatter TIME_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    /**
     * JavaFX initialization method.
     *
     * <p>Configures the table columns and populates the table
     * with all active rentals retrieved from {@link RentalService}.</p>
     */
    @FXML
    public void initialize() {

        // Username column
        usernameColumn.setCellValueFactory(data ->
                new SimpleStringProperty(
                        data.getValue().getUser().getUsername()
                )
        );

        // User full name column
        nameColumn.setCellValueFactory(data ->
                new SimpleStringProperty(
                        data.getValue().getUser().getName()
                )
        );

        // Vehicle type column
        typeColumn.setCellValueFactory(data ->
                new SimpleStringProperty(
                        data.getValue().getVehicle().getVehicleType()
                )
        );

        // Vehicle model column
        modelColumn.setCellValueFactory(data ->
                new SimpleStringProperty(
                        data.getValue().getVehicle().getModel()
                )
        );

        // Rental start time column
        startTimeColumn.setCellValueFactory(data ->
                new SimpleStringProperty(
                        data.getValue().getStartTime().format(TIME_FORMATTER)
                )
        );

        // Rental status column
        statusColumn.setCellValueFactory(data ->
                new SimpleStringProperty(
                        data.getValue().getStatus().toString()
                )
        );

        // Populate table with active rentals only
        rentalsTable.setItems(
                FXCollections.observableArrayList(
                        rentalService.getActiveRentals()
                )
        );
    }

    /**
     * Handles the "Back" button action.
     * Navigates back to the admin home screen.
     */
    @FXML
    private void handleBack() {
        SceneSwitcher.switchTo("admin_home.fxml");
    }
}
