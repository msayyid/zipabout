package com.example.zipaboutgui.ui.controller;

import com.example.zipaboutgui.domain.enums.Role;
import com.example.zipaboutgui.domain.user.User;
import com.example.zipaboutgui.service.RentalService;
import com.example.zipaboutgui.ui.util.AdminUserContext;
import com.example.zipaboutgui.ui.util.SceneSwitcher;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert;

/**
 * Controller for the Admin "Manage Users" screen.
 *
 * <p>This screen allows administrators to:
 * <ul>
 *   <li>View all standard users</li>
 *   <li>Inspect detailed information for a selected user</li>
 *   <li>Add new users</li>
 *   <li>Remove users (with business constraints)</li>
 * </ul>
 * </p>
 *
 * <p>Administrative users are intentionally excluded from this list.</p>
 */
public class AdminUsersController {

    /**
     * Table displaying all non-admin users.
     */
    @FXML
    private TableView<User> usersTable;

    /**
     * Column showing the user ID.
     */
    @FXML
    private TableColumn<User, String> idColumn;

    /**
     * Column showing the user's full name.
     */
    @FXML
    private TableColumn<User, String> nameColumn;

    /**
     * Column showing the user's role.
     */
    @FXML
    private TableColumn<User, String> roleColumn;

    /**
     * Column showing whether the user has VIP status.
     */
    @FXML
    private TableColumn<User, String> vipColumn;

    /**
     * Column showing the user's loyalty points.
     */
    @FXML
    private TableColumn<User, String> pointsColumn;

    /**
     * Singleton service responsible for managing users and rentals.
     */
    private final RentalService rentalService = RentalService.getInstance();

    /**
     * JavaFX initialization method.
     *
     * <p>Configures the table columns and populates the table
     * with all standard users retrieved from {@link RentalService}.</p>
     */
    @FXML
    public void initialize() {

        // User ID column
        idColumn.setCellValueFactory(
                data -> new SimpleStringProperty(data.getValue().getId())
        );

        // User name column
        nameColumn.setCellValueFactory(
                data -> new SimpleStringProperty(data.getValue().getName())
        );

        // User role column
        roleColumn.setCellValueFactory(
                data -> new SimpleStringProperty(data.getValue().getRole().toString())
        );

        // VIP status column
        vipColumn.setCellValueFactory(
                data -> new SimpleStringProperty(
                        data.getValue().isVip() ? "Yes" : "No"
                )
        );

        // Loyalty points column
        pointsColumn.setCellValueFactory(
                data -> new SimpleStringProperty(
                        String.valueOf(data.getValue().getLoyaltyPoints())
                )
        );

        // Populate table with non-admin users only
        usersTable.setItems(
                FXCollections.observableArrayList(
                        rentalService.getUsers().stream()
                                .filter(user -> user.getRole() == Role.USER)
                                .toList()
                )
        );
    }

    /**
     * Handles the "View Details" button action.
     *
     * <p>Opens a detailed, read-only view of the selected user.</p>
     */
    @FXML
    private void handleViewDetails() {
        User selected = usersTable.getSelectionModel().getSelectedItem();

        if (selected == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setContentText("Please select a user first.");
            alert.showAndWait();
            return;
        }

        // Store selected user in shared context
        AdminUserContext.setSelectedUser(selected);

        // Navigate to user details screen
        SceneSwitcher.switchTo("admin_user_details.fxml");
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
     * Handles the "Add User" button action.
     * Navigates to the add-user form.
     */
    @FXML
    private void handleAddUser() {
        SceneSwitcher.switchTo("admin_add_user.fxml");
    }

    /**
     * Handles the "Remove User" button action.
     *
     * <p>Users can only be removed if:
     * <ul>
     *   <li>They are not admins</li>
     *   <li>They have no active rentals</li>
     * </ul>
     * </p>
     *
     * <p>A confirmation dialog is shown before removal.</p>
     */
    @FXML
    private void handleRemoveUser() {

        User selected = usersTable.getSelectionModel().getSelectedItem();

        if (selected == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setContentText("Please select a user to remove.");
            alert.showAndWait();
            return;
        }

        // Confirmation dialog
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirm Removal");
        confirm.setHeaderText("Remove user: " + selected.getUsername());
        confirm.setContentText(
                "This action cannot be undone.\n\n" +
                        "The user must not have active rentals."
        );

        if (confirm.showAndWait().orElse(ButtonType.CANCEL) != ButtonType.OK) {
            return;
        }

        // Delegate removal logic to RentalService
        boolean removed = rentalService.removeUser(selected);

        if (!removed) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setHeaderText(null);
            error.setContentText(
                    "User cannot be removed.\n" +
                            "Make sure the user has no active rentals."
            );
            error.showAndWait();
            return;
        }

        // Refresh table by removing the deleted user
        usersTable.getItems().remove(selected);
    }
}
