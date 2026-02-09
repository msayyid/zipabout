package com.example.zipaboutgui.domain.vehicle.non_electric;

//import main.java.roehampton.msayyid.zipabout.domain.equipment.Equipment;
//import main.java.roehampton.msayyid.zipabout.domain.vehicle.Vehicle;
import com.example.zipaboutgui.domain.equipment.*;
import com.example.zipaboutgui.domain.vehicle.*;

/**
 * Non-electric kick scooter in the ZipAbout system.
 * <p>
 * Extends {@link Vehicle} with simple, human-readable behaviour for
 * movement and braking, and a detailed {@link #printDetails()} implementation.
 */
public class KickScooter extends Vehicle {

    /**
     * Creates a new kick scooter with the given make, model and equipment.
     *
     * @param make       manufacturer name
     * @param model      model name
     * @param equipments equipment associated with this kick scooter
     */
    public KickScooter(String make, String model, Equipment[] equipments) {
        super(make, model, equipments);
    }

    @Override
    public void go() {
        System.out.println(
                getMake() + " " + getModel()
                        + " starts moving as the rider kicks off the ground."
        );
    }

    @Override
    public void brake() {
        System.out.println(
                getMake() + " " + getModel()
                        + " is braking using the rear foot brake."
        );
    }

    @Override
    public void printDetails() {
        System.out.println("=== Kick Scooter ===");
        System.out.println("Make: " + getMake());
        System.out.println("Model: " + getModel());

        // Equipment information
        if (!getEquipments().isEmpty()) {
            System.out.println("Equipment:");
            for (Equipment e : getEquipments()) {
                System.out.println(" - " + e.getType() + " (" + e.getSize() + ")");
            }
        }

        // Booking state
        System.out.println("Available: " + (isAvailable() ? "Yes" : "No"));
        if (!isAvailable() && getCurrentUser() != null) {
            System.out.println("Currently rented by: " + getCurrentUser().getName());
        }

        System.out.println("----------------------------------------");
    }

    @Override
    public String getVehicleType() {
        return "Kick Scooter";
    }
}
