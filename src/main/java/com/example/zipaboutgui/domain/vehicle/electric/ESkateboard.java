package com.example.zipaboutgui.domain.vehicle.electric;

//import main.java.roehampton.msayyid.zipabout.domain.equipment.Equipment;
//import main.java.roehampton.msayyid.zipabout.domain.parts.Battery;
//import main.java.roehampton.msayyid.zipabout.domain.parts.Controller;
//import main.java.roehampton.msayyid.zipabout.domain.parts.Motor;
//import main.java.roehampton.msayyid.zipabout.domain.user.User;
//import main.java.roehampton.msayyid.zipabout.domain.vehicle.ElectricVehicle;
import com.example.zipaboutgui.domain.equipment.*;
import com.example.zipaboutgui.domain.parts.*;
import com.example.zipaboutgui.domain.user.*;
import com.example.zipaboutgui.domain.vehicle.ElectricVehicle;
/**
 * Electric skateboard in the ZipAbout system.
 * <p>
 * Extends {@link ElectricVehicle} and provides electric-skateboard-specific
 * movement messages and a detailed {@link #printDetails()} implementation.
 */
public class ESkateboard extends ElectricVehicle {

    /**
     * Creates a new electric skateboard with the given make, model, equipment and parts.
     *
     * @param make       manufacturer name
     * @param model      model name
     * @param equipments equipment associated with this electric skateboard
     * @param battery    battery used by this skateboard
     * @param motor      motor used by this skateboard
     * @param controller controller used by this skateboard
     */
    public ESkateboard(String make,
                       String model,
                       Equipment[] equipments,
                       Battery battery,
                       Motor motor,
                       Controller controller) {
        super(make, model, equipments, battery, motor, controller);
    }

    @Override
    public String getVehicleType() {
        return "Electric Skateboard";
    }

    @Override
    public void go() {
        System.out.println(
                getMake() + " " + getModel()
                        + " accelerates as the rider leans forward and applies throttle."
        );
    }

    @Override
    public void brake() {
        System.out.println(
                getMake() + " " + getModel()
                        + " uses regenerative braking to slow down smoothly."
        );
    }

    @Override
    public void printDetails() {
        System.out.println("=== Electric Skateboard ===");
        System.out.println("Make: " + getMake());
        System.out.println("Model: " + getModel());

        // Battery info
        System.out.println("Battery chemistry: " + getBattery().getChemistry());
        System.out.println("Battery: " + getBattery().getCapacityWh() + " Wh ("
                + getBattery().getCurrentChargePercent() + "%)");

        // Motor info
        System.out.println("Motor power: " + getMotor().getPowerWatts() + " W");

        // Controller info
        System.out.println("Controller firmware: " + getController().getFirmwareVersion());

        // Equipment list
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
}
