package com.example.zipaboutgui.ui.controller;

import com.example.zipaboutgui.domain.user.User;
import com.example.zipaboutgui.domain.vehicle.Vehicle;
import com.example.zipaboutgui.service.Rental;
import com.example.zipaboutgui.service.RentalService;
import com.example.zipaboutgui.ui.util.SceneSwitcher;
import com.example.zipaboutgui.ui.util.Session;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

/**
 * Controller for the main Vehicles screen (user-facing).
 *
 * <p>This screen allows a logged-in user to:
 * <ul>
 *   <li>View all available vehicles</li>
 *   <li>Inspect vehicle details</li>
 *   <li>Book a vehicle (if allowed)</li>
 *   <li>Release their active rental</li>
 *   <li>Navigate to their rental history</li>
 * </ul>
 * </p>
 *
 * <p>All business rules are enforced via {@link RentalService}.
 * This controller only coordinates UI state and user feedback.</p>
 */
public class VehiclesController {

    /**
     * Singleton service providing access to vehicles and rentals.
     */
    private final RentalService rentalService = RentalService.getInstance();

    // -------------------------------
    // Table and columns
    // -------------------------------

    @FXML
    private TableView<Vehicle> vehiclesTable;

    @FXML
    private TableColumn<Vehicle, String> idColumn;

    @FXML
    private TableColumn<Vehicle, String> typeColumn;

    @FXML
    private TableColumn<Vehicle, String> statusColumn;

    // -------------------------------
    // Detail labels
    // -------------------------------

    @FXML
    private Label idLabel;

    @FXML
    private Label typeLabel;

    @FXML
    private Label modelLabel;

    @FXML
    private Label statusLabel;

    // -------------------------------
    // Action buttons
    // -------------------------------

    @FXML
    private Button bookButton;

    @FXML
    private Button releaseButton;

    @FXML
    private Label sessionLabel;

    @FXML
    private Label userInfoLabel;

    // -------------------------------
    // Tooltips for disabled buttons
    // -------------------------------

    private final Tooltip bookTooltip = new Tooltip();
    private final Tooltip releaseTooltip = new Tooltip();

    /**
     * JavaFX initialization method.
     *
     * <p>Configures table columns, tooltips, session display,
     * selection listeners, and populates the vehicle list.</p>
     */
    @FXML
    public void initialize() {

        /* -------------------------------
           Session info
         -------------------------------- */
        User currentUser = Session.getCurrentUser();
        if (currentUser != null) {
            updateSessionLabel();

//            sessionLabel.setText(
//                    "Logged in as: " + currentUser.getName()
//                            + " (ID: " + currentUser.getId() + ")"
//                            + " | Loyalty points: " + currentUser.getLoyaltyPoints()
//                            + " | Completed rentals: " + currentUser.getTotalCompletedRentals()
//            );
        }

        /* -------------------------------
           Tooltips
         -------------------------------- */
        bookButton.setTooltip(bookTooltip);
        releaseButton.setTooltip(releaseTooltip);

        /* -------------------------------
           Table columns
         -------------------------------- */

        // Shortened ID for readability
        idColumn.setCellValueFactory(data -> {
            String id = data.getValue().getId();
            String shortId = id.length() > 8
                    ? id.substring(0, 8) + "…"
                    : id;
            return new SimpleStringProperty(shortId);
        });

        // Vehicle type
        typeColumn.setCellValueFactory(
                data -> new SimpleStringProperty(
                        data.getValue().getVehicleType()
                )
        );

        // Availability status
        statusColumn.setCellValueFactory(
                data -> new SimpleStringProperty(
                        data.getValue().isAvailable()
                                ? "AVAILABLE"
                                : "BOOKED"
                )
        );

        /* -------------------------------
           Populate table
         -------------------------------- */
        vehiclesTable.setItems(
                FXCollections.observableArrayList(
                        rentalService.getVehicles()
                )
        );

        /* -------------------------------
           Selection listener
         -------------------------------- */
        vehiclesTable.getSelectionModel()
                .selectedItemProperty()
                .addListener((obs, oldVehicle, selectedVehicle) -> {

                    // Reset UI if nothing is selected
                    if (selectedVehicle == null) {
                        idLabel.setText("ID: -");
                        typeLabel.setText("Type: -");
                        modelLabel.setText("Model: -");
                        statusLabel.setText("Status: -");

                        bookButton.setDisable(true);
                        releaseButton.setDisable(true);

                        bookTooltip.setText("");
                        releaseTooltip.setText("");
                        return;
                    }

                    // Update details panel
                    idLabel.setText("ID: " + selectedVehicle.getId());
                    typeLabel.setText("Type: " + selectedVehicle.getVehicleType());
                    modelLabel.setText("Model: " + selectedVehicle.getModel());
                    statusLabel.setText(
                            "Status: " +
                                    (selectedVehicle.isAvailable()
                                            ? "AVAILABLE"
                                            : "BOOKED")
                    );

                    // Determine active rental for this vehicle
                    Rental activeRental = null;
                    for (Rental r : rentalService.getActiveRentals()) {
                        if (r.getVehicle().equals(selectedVehicle)) {
                            activeRental = r;
                            break;
                        }
                    }

                    boolean bookedByCurrentUser =
                            activeRental != null
                                    && activeRental.getUser().equals(currentUser);

                    boolean hasActiveRental =
                            rentalService.userHasActiveRental(currentUser);

                    /* -------------------------------
                       Book rules + tooltip
                     -------------------------------- */
                    if (!selectedVehicle.isAvailable()) {
                        bookButton.setDisable(true);
                        bookTooltip.setText("This vehicle is already booked.");
                    } else if (hasActiveRental) {
                        bookButton.setDisable(true);
                        bookTooltip.setText("You already have an active rental.");
                    } else {
                        bookButton.setDisable(false);
                        bookTooltip.setText("Book this vehicle.");
                    }

                    /* -------------------------------
                       Release rules + tooltip
                     -------------------------------- */
                    if (selectedVehicle.isAvailable()) {
                        releaseButton.setDisable(true);
                        releaseTooltip.setText("This vehicle is not currently rented.");
                    } else if (!bookedByCurrentUser) {
                        releaseButton.setDisable(true);
                        releaseTooltip.setText("You can only release your own rental.");
                    } else {
                        releaseButton.setDisable(false);
                        releaseTooltip.setText("Release this vehicle.");
                    }
                });
    }

    /**
     * Handles the "Book" button action.
     *
     * <p>Shows confirmation, delegates booking to {@link RentalService},
     * refreshes UI, and displays success feedback.</p>
     */
    @FXML
    private void handleBook() {

        Vehicle selectedVehicle =
                vehiclesTable.getSelectionModel().getSelectedItem();

        if (selectedVehicle == null) return;

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirm Booking");
        confirm.setHeaderText("Book this vehicle?");
        confirm.setContentText(
                selectedVehicle.getVehicleType()
                        + " - " + selectedVehicle.getModel()
        );

        if (confirm.showAndWait().orElse(ButtonType.CANCEL)
                != ButtonType.OK) {
            return;
        }

        rentalService.bookVehicle(
                Session.getCurrentUser(),
                selectedVehicle
        );

        vehiclesTable.refresh();
        updateDetailsAndButtons(selectedVehicle);
        updateSessionLabel();

        Alert info = new Alert(Alert.AlertType.INFORMATION);
        info.setTitle("Booking Confirmed");
        info.setHeaderText("Vehicle booked successfully");
        info.setContentText(
                selectedVehicle.getVehicleType()
                        + " - " + selectedVehicle.getModel()
        );
        info.showAndWait();
    }

    /**
     * Handles the "Release" button action.
     *
     * <p>Shows confirmation, delegates release to {@link RentalService},
     * refreshes UI, updates loyalty info, and shows feedback.</p>
     */
    @FXML
    private void handleRelease() {

        Vehicle selectedVehicle =
                vehiclesTable.getSelectionModel().getSelectedItem();

        if (selectedVehicle == null) return;

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirm Release");
        confirm.setHeaderText("Release this vehicle?");
        confirm.setContentText(
                selectedVehicle.getVehicleType()
                        + " - " + selectedVehicle.getModel()
        );

        if (confirm.showAndWait().orElse(ButtonType.CANCEL)
                != ButtonType.OK) {
            return;
        }

        rentalService.releaseVehicle(
                Session.getCurrentUser(),
                selectedVehicle
        );

        vehiclesTable.refresh();
        updateDetailsAndButtons(selectedVehicle);
        updateSessionLabel();

        Alert info = new Alert(Alert.AlertType.INFORMATION);
        info.setTitle("Rental Completed");
        info.setHeaderText("Vehicle released successfully");
        info.setContentText(
                selectedVehicle.getVehicleType()
                        + " - " + selectedVehicle.getModel()
                        + "\nLoyalty points updated."
        );
        info.showAndWait();
    }

    /**
     * Updates the details panel and button states for a selected vehicle.
     */
    private void updateDetailsAndButtons(Vehicle selected) {
        if (selected == null) {
            idLabel.setText("ID: -");
            typeLabel.setText("Type: -");
            modelLabel.setText("Model: -");
            statusLabel.setText("Status: -");

            bookButton.setDisable(true);
            releaseButton.setDisable(true);
            return;
        }

        idLabel.setText("ID: " + selected.getId());
        typeLabel.setText("Type: " + selected.getVehicleType());
        modelLabel.setText("Model: " + selected.getModel());
        statusLabel.setText(
                "Status: " + (selected.isAvailable()
                        ? "AVAILABLE"
                        : "BOOKED")
        );

        bookButton.setDisable(!selected.isAvailable());
        releaseButton.setDisable(selected.isAvailable());
    }

    /**
     * Logs out the current user and returns to the login screen.
     */
    @FXML
    private void handleBack() {
        Session.logout();
        SceneSwitcher.switchTo("login.fxml");
    }

    /**
     * Navigates to the "My Rentals" screen.
     */
    @FXML
    private void handleMyRentals() {
        SceneSwitcher.switchTo("my_rentals.fxml");
    }

    /**
     * Updates the session label with user and loyalty information.
     */
    private void updateSessionLabel() {
        User currentUser = Session.getCurrentUser();
        if (currentUser != null) {
            sessionLabel.setText(
                    "Logged in as: " + currentUser.getName()
                            + " (ID: " + currentUser.getId() + ")"
                            + " | Loyalty points: " + currentUser.getLoyaltyPoints()
                            + " | Completed rentals: " + currentUser.getTotalCompletedRentals()
            );
        }
    }

//    @FXML
//    private void handleAdminView() {
//        SceneSwitcher.switchTo("admin.fxml");
//    }
}
