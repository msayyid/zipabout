package com.example.zipaboutgui.service;

import com.example.zipaboutgui.domain.enums.*;
import com.example.zipaboutgui.domain.equipment.Equipment;
import com.example.zipaboutgui.domain.parts.Battery;
import com.example.zipaboutgui.domain.parts.Controller;
import com.example.zipaboutgui.domain.parts.Motor;
import com.example.zipaboutgui.domain.user.User;
import com.example.zipaboutgui.domain.vehicle.*;
import com.example.zipaboutgui.domain.vehicle.electric.EBike;
import com.example.zipaboutgui.domain.vehicle.electric.EScooter;
import com.example.zipaboutgui.domain.vehicle.non_electric.Bike;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Central booking and management service for the ZipAbout system.
 *
 * <p>This class acts as:
 * <ul>
 *   <li>The SINGLE source of truth for users, vehicles, and rentals</li>
 *   <li>A Singleton shared across all GUI controllers</li>
 *   <li>The Subject in the Observer pattern (maintenance, notifications)</li>
 * </ul>
 *
 * <p>All business rules are enforced here.
 * Controllers NEVER modify domain state directly.</p>
 */
public class RentalService {

    /**
     * Formatter used for CLI/debug output.
     */
    private static final DateTimeFormatter RENTAL_TIME_FORMAT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    /* -------------------------------
       Singleton instance
     -------------------------------- */
    private static RentalService rentalService;

    /* -------------------------------
       Domain collections
     -------------------------------- */
    private final List<User> users;
    private final List<Vehicle> vehicles;
    private final List<Rental> rentals;

    /* -------------------------------
       Observer pattern
     -------------------------------- */
    private final List<RentalObserver> observers;

    /**
     * Private constructor to enforce Singleton usage.
     */
    private RentalService() {
        this.users = new ArrayList<>();
        this.vehicles = new ArrayList<>();
        this.rentals = new ArrayList<>();
        this.observers = new ArrayList<>();
    }

    /**
     * Returns the single shared instance of RentalService.
     *
     * @return singleton instance
     */
    public static RentalService getInstance() {
        if (rentalService == null) {
            rentalService = new RentalService();
        }
        return rentalService;
    }

    /* =========================================================
       Observer registration
       ========================================================= */

    /**
     * Registers an observer interested in rental completion events.
     *
     * @param observer observer to register
     */
    public void addObserver(RentalObserver observer) {
        observers.add(observer);
    }

    /**
     * Notifies all observers when a rental is completed.
     *
     * @param rental completed rental
     */
    private void notifyRentalCompleted(Rental rental) {
        for (RentalObserver obs : observers) {
            obs.onRentalCompleted(rental);
        }
    }

    /* =========================================================
       User & vehicle registration
       ========================================================= */

    /**
     * Registers a new user.
     *
     * @param user user to register
     */
    public void registerUser(User user) {
        users.add(user);
        System.out.println("User registered: " + user.getName());
    }

    /**
     * Registers a new vehicle.
     *
     * @param vehicle vehicle to register
     */
    public void registerVehicle(Vehicle vehicle) {
        vehicles.add(vehicle);
        System.out.println(
                vehicle.getVehicleType() + " registered: " + vehicle.getModel()
        );
    }

    /* =========================================================
       Booking logic
       ========================================================= */

    /**
     * Books a vehicle for a user.
     *
     * Rules:
     * - User can have only ONE active rental
     * - Vehicle must be available
     *
     * @param user user booking
     * @param vehicle vehicle to book
     * @return created Rental or null if booking fails
     */
    public Rental bookVehicle(User user, Vehicle vehicle) {

        if (userHasActiveRental(user)) {
            System.out.println("User already has an active rental.");
            return null;
        }

        if (!vehicle.isAvailable()) {
            System.out.println(
                    "Vehicle already booked by: " +
                            vehicle.getCurrentUser().getName()
            );
            return null;
        }

        String rentalId = "R-" + (rentals.size() + 1);
        Rental rental = new Rental(rentalId, user, vehicle);

        rentals.add(rental);
        user.addRental(rental);

        System.out.println(
                user.getName() + " booked " + vehicle.getModel()
        );

        return rental;
    }

    /**
     * Releases a vehicle.
     *
     * Rules:
     * - Only the booking user can release
     * - Loyalty & VIP logic applies
     * - Observers are notified
     *
     * @param user user releasing
     * @param vehicle vehicle being released
     */
    public void releaseVehicle(User user, Vehicle vehicle) {

        for (Rental r : rentals) {

            // Correct user + correct vehicle + active rental
            if (r.isActive()
                    && r.getUser().equals(user)
                    && r.getVehicle().equals(vehicle)) {

                r.complete();

                // Observer notification
                notifyRentalCompleted(r);

                // Loyalty system
                user.incrementCompletedRentals();

                if (user.getTotalCompletedRentals() > 3) {
                    user.addLoyaltyPoints(1);
                }

                user.checkAndUpdateVipStatus();

                System.out.println(
                        "Vehicle released: " + vehicle.getModel()
                );
                return;
            }

            // Prevent releasing someone else's rental
            if (r.isActive()
                    && r.getVehicle().equals(vehicle)
                    && !r.getUser().equals(user)) {

                System.out.println(
                        "Cannot release vehicle booked by another user."
                );
                return;
            }
        }

        System.out.println("No active rental found for this vehicle.");
    }

    /* =========================================================
       Rental queries (GUI support)
       ========================================================= */

    /**
     * Returns all active rentals.
     */
    public List<Rental> getActiveRentals() {
        return rentals.stream()
                .filter(Rental::isActive)
                .toList();
    }

    /**
     * Checks if a user has an active rental.
     */
    public boolean userHasActiveRental(User user) {
        return rentals.stream()
                .anyMatch(r -> r.isActive() && r.getUser().equals(user));
    }

    /**
     * Returns active rental for a vehicle.
     */
    public Rental getActiveRentalForVehicle(Vehicle vehicle) {
        for (Rental r : rentals) {
            if (r.isActive() && r.getVehicle().equals(vehicle)) {
                return r;
            }
        }
        return null;
    }

    /**
     * Returns all rentals for a user.
     */
    public List<Rental> getRentalsForUser(User user) {
        return rentals.stream()
                .filter(r -> r.getUser().equals(user))
                .toList();
    }

    /**
     * Returns active rentals for a user.
     */
    public List<Rental> getActiveRentalsForUser(User user) {
        List<Rental> result = new ArrayList<>();
        for (Rental r : rentals) {
            if (r.isActive() && r.getUser().equals(user)) {
                result.add(r);
            }
        }
        return result;
    }

    /**
     * Returns completed rentals for a user.
     */
    public List<Rental> getPastRentalsForUser(User user) {
        return rentals.stream()
                .filter(r -> !r.isActive())
                .filter(r -> r.getUser().equals(user))
                .toList();
    }

    /**
     * Returns completed rentals for a vehicle.
     */
    public List<Rental> getPastRentalsForVehicle(Vehicle vehicle) {
        return rentals.stream()
                .filter(r -> !r.isActive())
                .filter(r -> r.getVehicle().equals(vehicle))
                .toList();
    }

    /* =========================================================
       Admin operations
       ========================================================= */

    /**
     * Removes a user from the system.
     *
     * Rules:
     * - Cannot remove admins
     * - Cannot remove users with active rentals
     */
    public boolean removeUser(User user) {

        if (user == null) return false;

        if (user.getRole() == Role.ADMIN) return false;

        boolean hasActiveRental = rentals.stream()
                .anyMatch(r -> r.isActive() && r.getUser().equals(user));

        if (hasActiveRental) return false;

        users.remove(user);
        return true;
    }

    /* =========================================================
       Data access & seeding
       ========================================================= */

    public List<User> getUsers() {
        return users;
    }

    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    /**
     * Seeds default vehicles (GUI only).
     * Prevents double-seeding.
     */
    public void seedVehiclesIfEmpty() {
        if (!vehicles.isEmpty()) return;

        vehicles.add(new EBike(
                "Trek", "FX+ 2",
                new Equipment[]{},
                new Battery(400, 100, true, BatteryChemistry.LI_ION),
                new Motor(250),
                new Controller("v1.0")
        ));

        vehicles.add(new EScooter(
                "Xiaomi", "Pro 2",
                new Equipment[]{},
                new Battery(474, 85, false),
                new Motor(300),
                new Controller("v2.0"),
                ScooterType.OFF_ROAD,
                RideMode.NORMAL
        ));

        vehicles.add(new Bike(
                "Giant", "Escape 3",
                new Equipment[]{}
        ));
    }
}
