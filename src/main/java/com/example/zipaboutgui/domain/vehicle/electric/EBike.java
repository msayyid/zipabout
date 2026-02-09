package com.example.zipaboutgui.domain.vehicle.electric;

// Import ALL enums from the new base package
import com.example.zipaboutgui.domain.enums.*;

// Import domain classes from the new project
import com.example.zipaboutgui.domain.equipment.Equipment;
import com.example.zipaboutgui.domain.parts.Battery;
import com.example.zipaboutgui.domain.parts.Controller;
import com.example.zipaboutgui.domain.parts.Motor;
import com.example.zipaboutgui.domain.user.User;

// IMPORTANT: this must point to the NEW ElectricVehicle class
import com.example.zipaboutgui.domain.vehicle.ElectricVehicle;

/**
 * Represents an electric bicycle (e-bike) in the ZipAbout fleet.
 *
 * This class extends ElectricVehicle and adds bike-specific attributes
 * such as assist level, wheel size, suspension, and braking system.
 */
public class EBike extends ElectricVehicle {

    // --- EBike-specific attributes ---
    private int assistLevel;
    private double wheelSizeInch;
    private int tireWidthMm;
    private int gearCount;
    private BrakeType brakeType;
    private FrameType frameType;
    private SuspensionType suspensionType;
    private boolean hasPedals;
    private DisplayFeature[] displayFeatures;

    /**
     * Constructor for EBike.
     *
     * Calls the ElectricVehicle constructor to initialise shared electric
     * vehicle properties (battery, motor, controller, equipment),
     * then sets sensible default bike-specific values.
     */
    public EBike(String make, String model, Equipment[] equipments,
                 Battery battery, Motor motor, Controller controller) {

        // Initialise common electric vehicle fields
        super(make, model, equipments, battery, motor, controller);

        // Default commuter e-bike settings
        this.assistLevel = 3;
        this.wheelSizeInch = 28.0;
        this.tireWidthMm = 45;
        this.gearCount = 9;
        this.brakeType = BrakeType.MECHANICAL_DISC;
        this.frameType = FrameType.HYBRID;
        this.suspensionType = SuspensionType.FRONT;
        this.hasPedals = true;

        // Basic display features
        this.displayFeatures = new DisplayFeature[] {
                DisplayFeature.SPEED,
                DisplayFeature.BATTERY_PERCENT
        };

        // Default electric system configuration
        setMotorType(MotorType.HUB_REAR);
        setRideMode(RideMode.NORMAL);
        setPasType(PasType.CADENCE_BASED);
        setHasThrottle(true);
        setLightsIntegrated(true);
    }

    @Override
    public String getVehicleType() {
        return "Electric Bike";
    }

    // --- Getters and setters (unchanged) ---
    public int getAssistLevel() { return assistLevel; }
    public void setAssistLevel(int assistLevel) { this.assistLevel = assistLevel; }

    public double getWheelSizeInch() { return wheelSizeInch; }
    public void setWheelSizeInch(double wheelSizeInch) { this.wheelSizeInch = wheelSizeInch; }

    public int getTireWidthMm() { return tireWidthMm; }
    public void setTireWidthMm(int tireWidthMm) { this.tireWidthMm = tireWidthMm; }

    public int getGearCount() { return gearCount; }
    public void setGearCount(int gearCount) { this.gearCount = gearCount; }

    public BrakeType getBrakeType() { return brakeType; }
    public void setBrakeType(BrakeType brakeType) { this.brakeType = brakeType; }

    public FrameType getFrameType() { return frameType; }
    public void setFrameType(FrameType frameType) { this.frameType = frameType; }

    public SuspensionType getSuspensionType() { return suspensionType; }
    public void setSuspensionType(SuspensionType suspensionType) { this.suspensionType = suspensionType; }

    public boolean hasPedals() { return hasPedals; }
    public void setHasPedals(boolean hasPedals) { this.hasPedals = hasPedals; }

    public DisplayFeature[] getDisplayFeatures() { return displayFeatures; }
    public void setDisplayFeatures(DisplayFeature[] displayFeatures) { this.displayFeatures = displayFeatures; }

    /**
     * Polymorphic behaviour: how an e-bike starts moving.
     */
    @Override
    public void go() {
        System.out.println(getMake() + " " + getModel()
                + " starts moving with pedal assist (level " + assistLevel + ").");
    }

    /**
     * Polymorphic behaviour: how an e-bike brakes.
     */
    @Override
    public void brake() {
        System.out.println(getMake() + " " + getModel()
                + " is braking using " + brakeType + " brakes.");
    }

    /**
     * Prints full details of the e-bike, including electric system,
     * equipment, and current booking status.
     */
    @Override
    public void printDetails() {
        System.out.println("=== Electric Bike ===");
        System.out.println("Make: " + getMake());
        System.out.println("Model: " + getModel());

        System.out.println("Frame type: " + frameType);
        System.out.println("Suspension: " + suspensionType);
        System.out.println("Assist level: " + assistLevel);
        System.out.println("Wheel size: " + wheelSizeInch + " inch");
        System.out.println("Tyre width: " + tireWidthMm + " mm");
        System.out.println("Gear count: " + gearCount);
        System.out.println("Brake type: " + brakeType);
        System.out.println("Has pedals: " + (hasPedals ? "Yes" : "No"));

        // Electric system info
        System.out.println("Motor type: " + getMotorType());
        System.out.println("Ride mode: " + getRideMode());
        System.out.println("PAS type: " + getPasType());

        // Battery and motor info
        System.out.println("Battery: " + getBattery().getCapacityWh() + " Wh ("
                + getBattery().getCurrentChargePercent() + "%), chemistry: "
                + getBattery().getChemistry());
        System.out.println("Motor power: " + getMotor().getPowerWatts() + " W");
        System.out.println("Controller firmware: " + getController().getFirmwareVersion());

        // Equipment list
        if (!getEquipments().isEmpty()) {
            System.out.println("Equipment:");
            for (var e : getEquipments()) {
                System.out.println(" - " + e.getType() + " (" + e.getSize() + ")");
            }
        }

        // Booking status
        boolean available = isAvailable();
        User currentUser = getCurrentUser();
        System.out.println("Available: " + (available ? "Yes" : "No"));
        if (!available && currentUser != null) {
            System.out.println("Currently rented by: " + currentUser.getName());
        }

        System.out.println("----------------------------------------");
    }
}
