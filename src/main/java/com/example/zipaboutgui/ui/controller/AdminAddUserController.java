package com.example.zipaboutgui.ui.controller;

import com.example.zipaboutgui.domain.enums.Role;
import com.example.zipaboutgui.domain.user.User;
import com.example.zipaboutgui.service.RentalService;
import com.example.zipaboutgui.ui.util.SceneSwitcher;
import javafx.fxml.FXML;
import javafx.scene.control.*;

/**
 * Controller for the "Add User" screen in the admin interface.
 *
 * <p>This controller allows an administrator to create new
 * standard (non-admin) users via the GUI.</p>
 *
 * <p>It is responsible only for:
 * <ul>
 *   <li>Collecting input from the form</li>
 *   <li>Performing basic validation</li>
 *   <li>Delegating user creation to {@link RentalService}</li>
 *   <li>Navigating back to the users list</li>
 * </ul>
 * </p>
 *
 * <p>No business logic is implemented here.</p>
 */
public class AdminAddUserController {

    /**
     * Text field for displaying or entering a user ID.
     * <p>Currently, IDs are generated automatically.</p>
     */
    @FXML
    private TextField idField;

    /**
     * Text field for entering the user's full name.
     */
    @FXML
    private TextField nameField;

    /**
     * Password field for entering the user's password.
     */
    @FXML
    private PasswordField passwordField;

    /**
     * Text field for entering the user's username
     * (used as the login identity).
     */
    @FXML
    private TextField usernameField;

    /**
     * Reference to the singleton RentalService.
     * Used to register newly created users.
     */
    private final RentalService rentalService = RentalService.getInstance();


//    @FXML
//    private void handleAddUser() {
//
//        String id = idField.getText();
//        String name = nameField.getText();
//        String password = passwordField.getText();
//
//        if (id.isBlank() || name.isBlank() || password.isBlank()) {
//            showWarning("All fields must be filled.");
//            return;
//        }
//
//        User user = new User(id, name, password, Role.USER);
//        rentalService.registerUser(user);
//
//        SceneSwitcher.switchTo("admin_users.fxml");
//    }

    /**
     * Handles the "Add User" button action.
     *
     * <p>This method:
     * <ol>
     *   <li>Generates a simple user ID</li>
     *   <li>Reads input values from the form</li>
     *   <li>Validates required fields</li>
     *   <li>Creates a new {@link User} object</li>
     *   <li>Registers the user via {@link RentalService}</li>
     *   <li>Returns to the admin users screen</li>
     * </ol>
     * </p>
     */
    @FXML
    private void handleAddUser() {

        // Generate a simple ID based on current user count
        String id = "U" + (rentalService.getUsers().size() + 1);

        // Read form input
        String username = usernameField.getText();
        String name = nameField.getText();
        String password = passwordField.getText();

        // Basic validation
        if (username.isBlank() || name.isBlank() || password.isBlank()) {
            showWarning("All fields must be filled.");
            return;
        }

        // Create a new standard user (admin creation is restricted)
        User user = new User(
                id,
                username,
                name,
                password,
                Role.USER
        );

        // Register user in the system
        rentalService.registerUser(user);

        // Return to the users list
        SceneSwitcher.switchTo("admin_users.fxml");
    }

    /**
     * Handles the "Back" button action.
     * Navigates back to the admin users screen.
     */
    @FXML
    private void handleBack() {
        SceneSwitcher.switchTo("admin_users.fxml");
    }

    /**
     * Displays a warning dialog with the given message.
     *
     * @param message the message to display to the user
     */
    private void showWarning(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
