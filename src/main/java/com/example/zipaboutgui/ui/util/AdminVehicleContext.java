package com.example.zipaboutgui.ui.util;

import com.example.zipaboutgui.domain.vehicle.Vehicle;

/**
 * Shared context used to pass a selected {@link Vehicle}
 * between admin-related screens.
 *
 * <p>This class provides a simple, static storage mechanism
 * to allow controllers to share the currently selected vehicle
 * without being tightly coupled.</p>
 *
 * <p>Typical usage flow:
 * <ul>
 *   <li>AdminVehiclesController sets the selected vehicle</li>
 *   <li>AdminVehicleDetailsController reads it</li>
 *   <li>The context is cleared when navigating back</li>
 * </ul>
 * </p>
 *
 * <p>This mirrors the pattern used for users
 * ({@link AdminUserContext}).</p>
 */
public class AdminVehicleContext {

    /**
     * Holds the currently selected vehicle.
     */
    private static Vehicle selectedVehicle;

    /**
     * Stores the selected vehicle in the shared context.
     *
     * @param vehicle the vehicle selected in the admin vehicles table
     */
    public static void setSelectedVehicle(Vehicle vehicle) {
        selectedVehicle = vehicle;
    }

    /**
     * Retrieves the currently selected vehicle.
     *
     * @return the selected vehicle, or {@code null} if none is set
     */
    public static Vehicle getSelectedVehicle() {
        return selectedVehicle;
    }

    /**
     * Clears the stored vehicle from the context.
     *
     * <p>This should be called when leaving
     * the vehicle details screen.</p>
     */
    public static void clear() {
        selectedVehicle = null;
    }
}
