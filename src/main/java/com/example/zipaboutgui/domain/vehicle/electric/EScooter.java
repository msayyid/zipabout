package com.example.zipaboutgui.domain.vehicle.electric;

//import main.java.roehampton.msayyid.zipabout.domain.enums.MotorType;
//import main.java.roehampton.msayyid.zipabout.domain.enums.RideMode;
//import main.java.roehampton.msayyid.zipabout.domain.enums.ScooterType;
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
import com.example.zipaboutgui.domain.enums.*;

/**
 * Represents an electric scooter in the ZipAbout fleet.
 * <p>
 * Extends {@link ElectricVehicle} and adds scooter-specific properties such as
 * {@link ScooterType} and default configuration (rear hub motor, throttle, lights, ride mode).
 */
public class EScooter extends ElectricVehicle {

    /** The specific scooter category (commuter, off-road, compact). */
    private ScooterType scooterType;

    /**
     * Creates a new {@link EScooter} with the provided components.
     *
     * @param make       manufacturer name
     * @param model      model name
     * @param equipments safety / riding equipment included
     * @param battery    battery pack used by this scooter
     * @param motor      electric motor for propulsion
     * @param controller controller unit managing electronic behaviour
     * @param scooterType the type or category of scooter
     * @param rideMode    the electric ride mode (Eco, Normal, Sport, etc.)
     */
    public EScooter(String make,
                    String model,
                    Equipment[] equipments,
                    Battery battery,
                    Motor motor,
                    Controller controller,
                    ScooterType scooterType,
                    RideMode rideMode) {

        super(make, model, equipments, battery, motor, controller);

        this.scooterType = scooterType;

        // Default configuration for electric scooters
        setMotorType(MotorType.HUB_REAR);
        setRideMode(rideMode);
        setHasThrottle(true);
        setLightsIntegrated(true);
    }

    @Override
    public String getVehicleType() {
        return "Electric Scooter";
    }

    /**
     * Returns the scooter subtype (commuter / off-road etc.).
     */
    public ScooterType getScooterType() {
        return scooterType;
    }

    /**
     * Updates the scooter subtype.
     */
    public void setScooterType(ScooterType scooterType) {
        this.scooterType = scooterType;
    }

    @Override
    public void go() {
        System.out.println(
                getMake() + " " + getModel()
                        + " starts moving as the rider presses the throttle."
        );
    }

    @Override
    public void brake() {
        System.out.println(
                getMake() + " " + getModel()
                        + " is braking using scooter brakes."
        );
    }

    @Override
    public void printDetails() {
        System.out.println("=== Electric Scooter ===");
        System.out.println("Make: " + getMake());
        System.out.println("Model: " + getModel());
        System.out.println("Scooter type: " + scooterType);
        System.out.println("Ride mode: " + getRideMode());
        System.out.println("Motor type: " + getMotorType());

        // Battery information
        System.out.println("Battery: " + getBattery().getCapacityWh() + " Wh ("
                + getBattery().getCurrentChargePercent() + "%), chemistry: "
                + getBattery().getChemistry());

        // Motor information
        System.out.println("Motor power: " + getMotor().getPowerWatts() + " W");

        // Controller information
        System.out.println("Controller firmware: " + getController().getFirmwareVersion());

        // Equipment list
        if (!getEquipments().isEmpty()) {
            System.out.println("Equipment:");
            for (var e : getEquipments()) {
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
