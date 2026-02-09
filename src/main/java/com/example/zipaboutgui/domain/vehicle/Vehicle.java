package com.example.zipaboutgui.domain.vehicle;

//import main.java.roehampton.msayyid.zipabout.domain.equipment.Equipment;
//import main.java.roehampton.msayyid.zipabout.domain.user.User;
import com.example.zipaboutgui.domain.user.User;
import com.example.zipaboutgui.domain.equipment.Equipment;

import java.util.List;
import java.util.UUID;

/**
 * Base abstract class for all ZipAbout vehicles.
 * <p>
 * A {@code Vehicle} has an internal unique ID, human-readable asset code,
 * basic descriptive properties (make, model, etc.), an optional set of
 * {@link Equipment} items, and simple booking state (available or booked
 * by a {@link User}).
 *
 * Concrete subclasses (e.g. electric bikes, scooters, non-electric bikes)
 * extend this class and implement movement behaviours and more detailed
 * attributes as needed.
 */
public abstract class Vehicle {

    // Core identity
    private final String id = UUID.randomUUID().toString();  // internal unique id
    private String assetCode;                                // human-visible code like EB-001

    // Basic descriptive attributes
    private String make;
    private String model;
    private String color;
    private int year;
    private double weightKg;
    private int maxLoad;

    // Associated equipment (helmet, gloves, etc.)
    private Equipment[] equipments;

    // Booking state
    private boolean available = true;
    private User currentUser;

    /**
     * Creates a vehicle with the given make, model and associated equipment.
     *
     * @param make       manufacturer name
     * @param model      model name
     * @param equipments array of equipment items associated with this vehicle
     */
    public Vehicle(String make, String model, Equipment[] equipments) {
        this.make = make;
        this.model = model;
        this.equipments = equipments;
    }

    /**
     * Protected no-argument constructor provided for subclasses or frameworks
     * that may need to construct a vehicle and set properties later.
     */
    protected Vehicle() {
        // Intentionally empty
    }

    /**
     * Assigns a human-visible asset code to this vehicle (e.g. "EB-001").
     *
     * @param assetCode the asset code to assign
     */
    public void assignAssetCode(String assetCode) {
        this.assetCode = assetCode;
    }

    // --- Getters ---

    public String getId() {
        return id;
    }

    public String getAssetCode() {
        return assetCode;
    }

    public String getMake() {
        return make;
    }

    public String getColor() {
        return color;
    }

    public int getYear() {
        return year;
    }

    public String getModel() {
        return model;
    }

    public int getMaxLoad() {
        return maxLoad;
    }

    public double getWeightKg() {
        return weightKg;
    }

    /**
     * Returns the equipment associated with this vehicle as an unmodifiable list.
     *
     * @return list of equipment items (may be empty)
     */
    public List<Equipment> getEquipments() {
        return equipments == null ? List.of() : List.of(equipments);
    }

    // --- Behaviour ---

    /**
     * Prints a simple textual representation of this vehicle.
     * Subclasses may override to include more details.
     */
    public void printDetails() {
        System.out.println(getVehicleType() + ": " + make + " " + model);
    }

    /**
     * Adds an equipment item to this vehicle.
     *
     * @param e the equipment item to add
     */
    public void addEquipment(Equipment e) {
        if (e == null) {
            return;
        }
        if (equipments == null) {
            equipments = new Equipment[]{e};
            return;
        }
        // Grow the array by one and copy
        Equipment[] newArray = new Equipment[equipments.length + 1];
        System.arraycopy(equipments, 0, newArray, 0, equipments.length);
        newArray[equipments.length] = e;
        equipments = newArray;
    }

    /**
     * Starts moving this vehicle. Concrete subclasses define the exact behaviour.
     */
    public abstract void go();

    /**
     * Brakes this vehicle. Concrete subclasses define the exact behaviour.
     */
    public abstract void brake();

    /**
     * Marks this vehicle as booked by the given user.
     *
     * @param user the user who is booking the vehicle
     */
    public void markAsBooked(User user) {
        this.available = false;
        this.currentUser = user;
    }

    /**
     * Marks this vehicle as released and available for booking.
     */
    public void markAsReleased() {
        this.available = true;
        this.currentUser = null;
    }

    /**
     * Returns the user who currently has this vehicle booked, or {@code null}
     * if the vehicle is available.
     *
     * @return current user or null
     */
    public User getCurrentUser() {
        return currentUser;
    }

    /**
     * Returns whether this vehicle is currently available for booking.
     *
     * @return true if the vehicle is not booked, false otherwise
     */
    public boolean isAvailable() {
        return available;
    }

    /**
     * Returns a human-friendly type name for this vehicle
     * (e.g. "Electric Bike", "Bike", "Kick Scooter").
     *
     * @return the vehicle type as a string
     */
    public abstract String getVehicleType();
}
