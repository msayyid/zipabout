package com.example.zipaboutgui.ui.controller;

import com.example.zipaboutgui.domain.user.User;
import com.example.zipaboutgui.service.RentalService;
import com.example.zipaboutgui.ui.util.SceneSwitcher;
import com.example.zipaboutgui.ui.util.Session;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.StringConverter;

/**
 * Controller for the Login screen.
 *
 * <p>This controller handles user authentication for the GUI version
 * of the ZipAbout system.</p>
 *
 * <p>Users authenticate by:
 * <ul>
 *   <li>Selecting a username from a dropdown</li>
 *   <li>Entering the corresponding password</li>
 * </ul>
 * </p>
 *
 * <p>After successful login, users are redirected based on their role:
 * <ul>
 *   <li>Admins → Admin Home</li>
 *   <li>Regular users → Vehicles screen</li>
 * </ul>
 * </p>
 */
public class LoginController {

    /**
     * ComboBox containing all registered users.
     * Users are displayed by username.
     */
    @FXML
    private ComboBox<User> userComboBox;

    /**
     * Password field for entering the user's password.
     */
    @FXML
    private PasswordField passwordField;

    /**
     * Singleton service providing access to registered users.
     */
    private final RentalService rentalService = RentalService.getInstance();

    /**
     * JavaFX initialization method.
     *
     * <p>Populates the user dropdown and configures it
     * to display usernames instead of object references.</p>
     */
    @FXML
    public void initialize() {

        // Populate ComboBox with all registered users
        userComboBox.getItems().addAll(rentalService.getUsers());

        // Display username instead of toString()
        userComboBox.setConverter(new StringConverter<>() {

            @Override
            public String toString(User user) {
                return user == null ? "" : user.getUsername();
            }

            @Override
            public User fromString(String string) {
                // Not required because selection is from a fixed list
                return null;
            }
        });
    }

    /**
     * Handles the Login button action.
     *
     * <p>This method:
     * <ol>
     *   <li>Validates user selection and password input</li>
     *   <li>Checks credentials</li>
     *   <li>Stores the logged-in user in {@link Session}</li>
     *   <li>Redirects the user based on their role</li>
     * </ol>
     * </p>
     */
    @FXML
    private void handleLogin() {

        User selectedUser = userComboBox.getValue();
        String password = passwordField.getText();

        // Basic validation
        if (selectedUser == null || password == null || password.isBlank()) {
            showWarning("Please select a username and enter a password.");
            return;
        }

        // Password validation
        if (!selectedUser.getPassword().equals(password)) {
            showWarning("Incorrect password.");
            return;
        }

        // Store logged-in user in session
        Session.loginUser(selectedUser);

        // Navigate based on role
        if (selectedUser.isAdmin()) {
            SceneSwitcher.switchTo("admin_home.fxml");
        } else {
            SceneSwitcher.switchTo("vehicles.fxml");
        }
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
