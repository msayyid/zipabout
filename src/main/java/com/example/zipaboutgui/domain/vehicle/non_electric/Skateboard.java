package com.example.zipaboutgui.domain.vehicle.non_electric;

//import main.java.roehampton.msayyid.zipabout.domain.equipment.Equipment;
//import main.java.roehampton.msayyid.zipabout.domain.vehicle.Vehicle;
import com.example.zipaboutgui.domain.equipment.*;
import com.example.zipaboutgui.domain.vehicle.*;
/**
 * Non-electric skateboard in the ZipAbout system.
 * <p>
 * Extends {@link Vehicle} with simple, human-readable behaviour for
 * movement and braking, and a detailed {@link #printDetails()} implementation.
 */
public class Skateboard extends Vehicle {

    /**
     * Creates a new skateboard with the given make, model and equipment.
     *
     * @param make       manufacturer name
     * @param model      model name
     * @param equipments equipment associated with this skateboard
     */
    public Skateboard(String make, String model, Equipment[] equipments) {
        super(make, model, equipments);
    }

    @Override
    public void go() {
        System.out.println(
                getMake() + " " + getModel()
                        + " starts rolling as the rider pushes off."
        );
    }

    @Override
    public void brake() {
        System.out.println(
                getMake() + " " + getModel()
                        + " is slowing down as the rider foot-brakes."
        );
    }

    @Override
    public void printDetails() {
        System.out.println("=== Skateboard ===");
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
        return "Skateboard";
    }
}
