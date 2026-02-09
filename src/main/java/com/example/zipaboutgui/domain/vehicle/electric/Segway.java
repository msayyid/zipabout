package com.example.zipaboutgui.domain.vehicle.electric;

//import main.java.roehampton.msayyid.zipabout.domain.equipment.Equipment;
import com.example.zipaboutgui.domain.equipment.*;
//import main.java.roehampton.msayyid.zipabout.domain.parts.Battery;
//import main.java.roehampton.msayyid.zipabout.domain.parts.Controller;
//import main.java.roehampton.msayyid.zipabout.domain.parts.Motor;
import com.example.zipaboutgui.domain.parts.*;
//import main.java.roehampton.msayyid.zipabout.domain.user.User;
//import main.java.roehampton.msayyid.zipabout.domain.vehicle.ElectricVehicle;
import com.example.zipaboutgui.domain.user.*;
import com.example.zipaboutgui.domain.vehicle.ElectricVehicle;

/**
 * Electric Segway vehicle in the ZipAbout system.
 * <p>
 * Extends {@link ElectricVehicle} and provides Segway-specific
 * movement messages and a detailed {@link #printDetails()} implementation.
 */
public class Segway extends ElectricVehicle {

    /**
     * Creates a new Segway with the given make, model, equipment and electric parts.
     *
     * @param make       manufacturer name
     * @param model      model name
     * @param equipments equipment associated with this Segway
     * @param battery    battery used by this Segway
     * @param motor      motor used by this Segway
     * @param controller controller used by this Segway
     */
    public Segway(String make,
                  String model,
                  Equipment[] equipments,
                  Battery battery,
                  Motor motor,
                  Controller controller) {
        super(make, model, equipments, battery, motor, controller);
    }

    @Override
    public void go() {
        System.out.println(
                getMake() + " " + getModel()
                        + " starts gliding forward as the rider leans."
        );
    }

    @Override
    public void brake() {
        System.out.println(
                getMake() + " " + getModel()
                        + " is slowing down as the rider leans back."
        );
    }

    @Override
    public void printDetails() {
        System.out.println("=== Segway ===");
        System.out.println("Make: " + getMake());
        System.out.println("Model: " + getModel());

        // Electric parts
        System.out.println("Battery chemistry: " + getBattery().getChemistry());
        System.out.println("Battery: " + getBattery().getCapacityWh() + " Wh ("
                + getBattery().getCurrentChargePercent() + "%)");
        System.out.println("Motor power: " + getMotor().getPowerWatts() + " W");
        System.out.println("Controller firmware: " + getController().getFirmwareVersion());

        // Equipment information
        if (!getEquipments().isEmpty()) {
            System.out.println("Equipment:");
            for (Equipment e : getEquipments()) {
                System.out.println(" - " + e.getType() + " (" + e.getSize() + ")");
            }
        }

        // Booking state
        boolean available = isAvailable();
        User currentUser = getCurrentUser();

        System.out.println("Available: " + (available ? "Yes" : "No"));
        if (!available && currentUser != null) {
            System.out.println("Currently rented by: " + currentUser.getName());
        }

        System.out.println("----------------------------------------");
    }

    @Override
    public String getVehicleType() {
        return "Segway";
    }
}
