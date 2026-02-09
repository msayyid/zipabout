package com.example.zipaboutgui.domain.vehicle;

// FIXED: enums now imported from the NEW base package
import com.example.zipaboutgui.domain.enums.MotorType;
import com.example.zipaboutgui.domain.enums.PasType;
import com.example.zipaboutgui.domain.enums.RideMode;

// FIXED: equipment and parts imports updated to new package
import com.example.zipaboutgui.domain.equipment.Equipment;
import com.example.zipaboutgui.domain.parts.Battery;
import com.example.zipaboutgui.domain.parts.Controller;
import com.example.zipaboutgui.domain.parts.Motor;

/**
 * Abstract base class for all electric vehicles in ZipAbout.
 * <p>
 * Extends {@link Vehicle} with electric-specific components ({@link Battery},
 * {@link Motor}, {@link Controller}) and configuration, such as ride mode,
 * PAS type and lighting/throttle options.
 */
public abstract class ElectricVehicle extends Vehicle {

    private final Motor motor;
    private final Controller controller;
    private final Battery battery;

    private double maxSpeedKmH;
    private double rangeKm;
    private RideMode rideMode;
    private MotorType motorType;
    private PasType pasType;
    private boolean lightsIntegrated;
    private boolean hasThrottle;

    /**
     * Creates a new electric vehicle with the given parts and basic details.
     *
     * @param make       manufacturer name
     * @param model      model name
     * @param equipments equipment associated with this vehicle
     * @param battery    battery used by this vehicle
     * @param motor      motor used by this vehicle
     * @param controller controller used by this vehicle
     */
    public ElectricVehicle(String make,
                           String model,
                           Equipment[] equipments,
                           Battery battery,
                           Motor motor,
                           Controller controller) {
        super(make, model, equipments);
        this.battery = battery;
        this.motor = motor;
        this.controller = controller;
    }

    // --- Getters ---

    public Motor getMotor() {
        return motor;
    }

    public Controller getController() {
        return controller;
    }

    public Battery getBattery() {
        return battery;
    }

    public boolean isHasThrottle() {
        return hasThrottle;
    }

    public boolean isLightsIntegrated() {
        return lightsIntegrated;
    }

    public PasType getPasType() {
        return pasType;
    }

    public MotorType getMotorType() {
        return motorType;
    }

    public RideMode getRideMode() {
        return rideMode;
    }

    public double getRangeKm() {
        return rangeKm;
    }

    public double getMaxSpeedKmH() {
        return maxSpeedKmH;
    }

    // --- Setters for configuration ---

    public void setMotorType(MotorType motorType) {
        this.motorType = motorType;
    }

    public void setRideMode(RideMode rideMode) {
        this.rideMode = rideMode;
    }

    public void setPasType(PasType pasType) {
        this.pasType = pasType;
    }

    public void setLightsIntegrated(boolean lightsIntegrated) {
        this.lightsIntegrated = lightsIntegrated;
    }

    public void setHasThrottle(boolean hasThrottle) {
        this.hasThrottle = hasThrottle;
    }

    public void setMaxSpeedKmH(double maxSpeedKmH) {
        this.maxSpeedKmH = maxSpeedKmH;
    }

    public void setRangeKm(double rangeKm) {
        this.rangeKm = rangeKm;
    }

    /**
     * Default implementation for moving an electric vehicle.
     * Subclasses may override to provide more specific behaviour or messages.
     */
    @Override
    public void go() {
        String batteryInfo = (battery != null)
                ? " Battery level: " + battery.getLevel() + "%"
                : "";
        System.out.println(
                getMake() + " " + getModel()
                        + " is moving silently using electric power." + batteryInfo
        );
    }

    /**
     * Default implementation for braking an electric vehicle.
     * Subclasses may override to provide more specific behaviour or messages.
     */
    @Override
    public void brake() {
        System.out.println(
                getMake() + " " + getModel()
                        + " is braking now."
        );
    }
}
