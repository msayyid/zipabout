package com.example.zipaboutgui.ui.controller;

import com.example.zipaboutgui.domain.user.User;
import com.example.zipaboutgui.service.Rental;
import com.example.zipaboutgui.service.RentalService;
import com.example.zipaboutgui.ui.util.AdminUserContext;
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
 * Controller for the Admin "User Details" screen.
 *
 * <p>This screen provides administrators with a detailed, read-only
 * view of a selected user's information.</p>
 *
 * <p>Displayed information includes:
 * <ul>
 *   <li>Basic user details (ID, username, role, VIP status)</li>
 *   <li>Loyalty points and completed rentals count</li>
 *   <li>Current active rental (if any)</li>
 *   <li>Past rental history</li>
 * </ul>
 * </p>
 *
 * <p>The selected user is passed using {@link AdminUserContext}.</p>
 */
public class AdminUserDetailsController {

    // --- Basic user info labels ---

    @FXML
    private Label idLabel;

    @FXML
    private Label usernameLabel;

    @FXML
    private Label nameLabel;

    @FXML
    private Label roleLabel;

    @FXML
    private Label vipLabel;

    @FXML
    private Label pointsLabel;

    @FXML
    private Label completedLabel;

    // --- Active rental display ---

    @FXML
    private Label activeRentalLabel;

    // --- Past rentals table ---

    @FXML
    private TableView<Rental> pastRentalsTable;

    @FXML
    private TableColumn<Rental, String> vehicleColumn;

    @FXML
    private TableColumn<Rental, String> startColumn;

    @FXML
    private TableColumn<Rental, String> endColumn;

    @FXML
    private TableColumn<Rental, String> statusColumn;

    /**
     * Singleton service providing access to users and rentals.
     */
    private final RentalService rentalService = RentalService.getInstance();

    /**
     * Formatter for displaying date-time values consistently.
     */
    private static final DateTimeFormatter TIME_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    /**
     * JavaFX initialization method.
     *
     * <p>Loads the selected user from {@link AdminUserContext}
     * and populates all UI fields.</p>
     */
    @FXML
    public void initialize() {

        // Retrieve selected user from context
        User user = AdminUserContext.getSelectedUser();

        // Safety check: if no user is selected, return to users list
        if (user == null) {
            SceneSwitcher.switchTo("admin_users.fxml");
            return;
        }

        // --- Populate basic user info ---
        idLabel.setText(user.getId());
        usernameLabel.setText(user.getUsername());
        nameLabel.setText(user.getName());
        roleLabel.setText(user.getRole().toString());
        vipLabel.setText(user.isVip() ? "Yes" : "No");
        pointsLabel.setText(String.valueOf(user.getLoyaltyPoints()));
        completedLabel.setText(String.valueOf(user.getTotalCompletedRentals()));

        // --- Display active rental (if any) ---
        List<Rental> active = rentalService.getActiveRentalsForUser(user);
        if (active.isEmpty()) {
            activeRentalLabel.setText("None");
        } else {
            Rental r = active.get(0);
            activeRentalLabel.setText(
                    r.getVehicle().getVehicleType()
                            + " - "
                            + r.getVehicle().getModel()
                            + " (since "
                            + r.getStartTime().format(TIME_FORMATTER)
                            + ")"
            );
        }

        // --- Configure past rentals table ---

        vehicleColumn.setCellValueFactory(
                data -> new SimpleStringProperty(
                        data.getValue().getVehicle().getVehicleType()
                                + " - "
                                + data.getValue().getVehicle().getModel()
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

        pastRentalsTable.setItems(
                FXCollections.observableArrayList(
                        rentalService.getPastRentalsForUser(user)
                )
        );
    }

    /**
     * Handles the "Back" button action.
     * Clears the selected user context and returns to the users list.
     */
    @FXML
    private void handleBack() {
        AdminUserContext.clear();
        SceneSwitcher.switchTo("admin_users.fxml");
    }
}
