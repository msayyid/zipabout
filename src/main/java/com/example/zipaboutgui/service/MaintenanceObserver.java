package com.example.zipaboutgui.service;

//import main.java.roehampton.msayyid.zipabout.domain.vehicle.Vehicle;
import com.example.zipaboutgui.domain.vehicle.Vehicle;

import java.util.HashMap;
import java.util.Map;

/**
 * Observer that tracks how many times each vehicle has been rented.
 * <p>
 * When a vehicle reaches a configured number of completed rentals, this observer
 * prints a maintenance reminder. It implements the {@link RentalObserver} interface
 * and is notified by {@link RentalService} whenever a rental is completed.
 */
public class MaintenanceObserver implements RentalObserver {

    /**
     * Number of completed rentals after which a maintenance check is recommended.
     */
    private static final int SERVICE_THRESHOLD = 10;

    /**
     * Tracks the number of completed rentals per vehicle (keyed by vehicle ID).
     */
    private final Map<String, Integer> usageCountByVehicleId = new HashMap<>();

    @Override
    public void onRentalCompleted(Rental rental) {
        Vehicle vehicle = rental.getVehicle();
        String vehicleId = vehicle.getId();

        int newCount = usageCountByVehicleId.getOrDefault(vehicleId, 0) + 1;
        usageCountByVehicleId.put(vehicleId, newCount);

        System.out.println(
                "[MAINTENANCE] " + vehicle.getVehicleType() + " " + vehicle.getModel()
                        + " now has " + newCount + " completed rentals."
        );

        if (newCount == SERVICE_THRESHOLD) {
            System.out.println(
                    "[MAINTENANCE] Vehicle "
                            + (vehicle.getAssetCode() != null ? vehicle.getAssetCode() + " " : "")
                            + "(" + vehicle.getModel() + ") has reached "
                            + SERVICE_THRESHOLD + " rentals. Schedule a maintenance check."
            );
        }
    }

    /**
     * Prints a summary of how many completed rentals each vehicle has had,
     * based on the usage counters tracked by this observer.
     */
    public void printUsageSummary() {
        System.out.println("\n=== Maintenance Usage Summary ===");
        if (usageCountByVehicleId.isEmpty()) {
            System.out.println("No completed rentals yet.");
            return;
        }
        for (Map.Entry<String, Integer> entry : usageCountByVehicleId.entrySet()) {
            String vehicleId = entry.getKey();
            int count = entry.getValue();
            System.out.println("- Vehicle ID: " + vehicleId
                    + " | Completed rentals: " + count);
        }
        System.out.println("----------------------------------------");
    }
}
