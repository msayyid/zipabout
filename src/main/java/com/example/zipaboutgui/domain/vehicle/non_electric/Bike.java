package com.example.zipaboutgui.domain.vehicle.non_electric;

//import main.java.roehampton.msayyid.zipabout.domain.equipment.Equipment;
//import main.java.roehampton.msayyid.zipabout.domain.vehicle.Vehicle;
import com.example.zipaboutgui.domain.equipment.*;
import com.example.zipaboutgui.domain.vehicle.*;

/**
 * Non-electric bike in the ZipAbout system.
 * <p>
 * Extends {@link Vehicle} with simple, human-readable behaviour for
 * movement and braking, and a concise {@link #printDetails()} implementation.
 */
public class Bike extends Vehicle {

    /**
     * Creates a new bike with the given make, model and equipment.
     *
     * @param make       manufacturer name
     * @param model      model name
     * @param equipments equipment associated with this bike
     */
    public Bike(String make, String model, Equipment[] equipments) {
        super(make, model, equipments);
    }

    @Override
    public void go() {
        System.out.println(
                getMake() + " " + getModel()
                        + " starts moving - the rider is pedalling."
        );
    }

    @Override
    public void brake() {
        System.out.println(
                getMake() + " " + getModel()
                        + " is braking using mechanical brakes."
        );
    }

    @Override
    public void printDetails() {
        System.out.println("=== Bike ===");
        System.out.println("Make: " + getMake());
        System.out.println("Model: " + getModel());

        // Booking state
        System.out.println("Available: " + (isAvailable() ? "Yes" : "No"));
        if (!isAvailable() && getCurrentUser() != null) {
            System.out.println("Currently rented by: " + getCurrentUser().getName());
        }

        System.out.println("----------------------------------------");
    }

    @Override
    public String getVehicleType() {
        return "Bike";
    }
}
