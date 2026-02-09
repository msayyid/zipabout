package com.example.zipaboutgui.ui.controller;

import com.example.zipaboutgui.ui.util.SceneSwitcher;
import com.example.zipaboutgui.ui.util.Session;
import javafx.fxml.FXML;

/**
 * Controller for the Admin Home screen.
 *
 * <p>This screen acts as the central navigation hub for administrators.</p>
 *
 * <p>From here, an admin can:
 * <ul>
 *   <li>View active rentals</li>
 *   <li>Manage users</li>
 *   <li>Manage vehicles</li>
 *   <li>Log out of the system</li>
 * </ul>
 * </p>
 *
 * <p>The controller contains no business logic and is responsible
 * only for handling navigation actions.</p>
 */
public class AdminHomeController {

    /**
     * Navigates to the Active Rentals screen.
     * This allows administrators to monitor ongoing rentals.
     */
    @FXML
    private void handleActiveRentals() {
        SceneSwitcher.switchTo("admin.fxml");
    }

    /**
     * Logs the current user out of the session
     * and returns to the login screen.
     */
    @FXML
    private void handleLogout() {
        Session.logout();
        SceneSwitcher.switchTo("login.fxml");
    }

    /**
     * Navigates to the Manage Users screen.
     * From there, admins can add, view, and remove users.
     */
    @FXML
    private void handleUsers() {
        SceneSwitcher.switchTo("admin_users.fxml");
    }

    /**
     * Navigates to the Manage Vehicles screen.
     * From there, admins can register and view vehicles.
     */
    @FXML
    private void handleVehicles() {
        SceneSwitcher.switchTo("admin_vehicles.fxml");
    }
}
