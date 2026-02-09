package com.example.zipaboutgui.domain.user;

//import main.java.roehampton.msayyid.zipabout.service.Rental;
import com.example.zipaboutgui.domain.enums.Role;
import com.example.zipaboutgui.service.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a ZipAbout user who can book and rent vehicles.
 * <p>
 * A {@code User} tracks:
 * <ul>
 *     <li>a unique ID and name,</li>
 *     <li>their loyalty points and completed rentals,</li>
 *     <li>whether they are a VIP,</li>
 *     <li>their current and past {@link Rental} objects.</li>
 * </ul>
 */
public class User {

    private String password;
    private Role role;

    private final String id;
    private final String name;
    // username, login identifier, unique (new)
    private final String username;

    private int loyaltyPoints;
    private int totalCompletedRentals;
    private boolean vip;

    private final List<Rental> rentals;





    /**
     * Creates a new user with a given ID and name.
     *
     * @param id   unique identifier for this user
     * @param name display name for this user
     */
    public User(String id, String name) {
        this.id = id;
        this.name = name;
        this.username = name; // temp
        this.rentals = new ArrayList<>();
    }


    // new constructor
    public User(String id, String username, String name, String password, Role role) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.password = password;
        this.role = role;
        this.rentals = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public String getUsername() { return username;}

    public String getName() {
        return name;
    }

    public int getLoyaltyPoints() {
        return loyaltyPoints;
    }

    public int getTotalCompletedRentals() {
        return totalCompletedRentals;
    }

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }

    public boolean isAdmin() {
        return role == Role.ADMIN;
    }


    public boolean isVip() {
        return vip;
    }

    /**
     * Returns a mutable list of this user's rentals (active and completed).
     *
     * @return list of rentals associated with this user
     */
    public List<Rental> getRentals() {
        return rentals;
    }

    /**
     * Adds the given number of points to this user's loyalty balance.
     *
     * @param points number of points to add (may be positive only)
     */
    public void addLoyaltyPoints(int points) {
        loyaltyPoints += points;
    }

    /**
     * Adds a rental to this user's list of rentals.
     *
     * @param rental the rental to add
     */
    public void addRental(Rental rental) {
        rentals.add(rental);
    }

    /**
     * Removes a rental from this user's list of rentals.
     *
     * @param rental the rental to remove
     */
    public void removeRental(Rental rental) {
        rentals.remove(rental);
    }

    /**
     * Increments the total number of completed rentals for this user.
     * <p>
     * This is normally called from the rental service when a rental is completed.
     */
    public void incrementCompletedRentals() {
        totalCompletedRentals++;
    }

    /**
     * Checks whether this user qualifies for VIP status and updates the flag.
     * <p>
     * In this implementation, users become VIP when they reach at least 5 loyalty points.
     */
    public void checkAndUpdateVipStatus() {
        if (!vip && loyaltyPoints >= 5) {
            vip = true;
            System.out.println(name + " has become a VIP user!");
        }
    }

    /**
     * Attempts to redeem a free ride using loyalty points.
     * <p>
     * A free ride costs 5 loyalty points. If the user has enough points,
     * 5 points are deducted and a message is printed.
     */
    public void redeemFreeRide() {
        if (loyaltyPoints >= 5) {
            loyaltyPoints -= 5;
            System.out.println(name + " redeemed a free ride. Remaining points: " + loyaltyPoints);
        } else {
            System.out.println(name + " does not have enough points to redeem a free ride.");
        }
    }

    /**
     * Prints a summary of this user's details, loyalty and current active rentals.
     */
    public void printDetails() {
        System.out.println("User ID: " + id);
        System.out.println("Username: " + username);
        System.out.println("Name: " + name);
        System.out.println("Total completed rentals: " + totalCompletedRentals);
        System.out.println("Loyalty Points: " + loyaltyPoints);
        System.out.println("VIP: " + (vip ? "Yes" : "No"));

        int activeCount = 0;
        for (Rental r : rentals) {
            if (r.isActive()) {
                activeCount++;
            }
        }
        System.out.println("Current active rentals: " + activeCount);
        System.out.println("----------------------------------------");
    }

    /**
     * Prints all bookings associated with this user (active and past).
     */
    public void printMyBookings() {
        System.out.println("Bookings for " + name + ":");
        if (rentals.isEmpty()) {
            System.out.println("- No bookings found.");
            return;
        }

        for (Rental r : rentals) {
            System.out.println(
                    "- " + r.getVehicle().getModel()
                            + " | Status: " + r.getStatus()
            );
        }
    }

    @Override
    public String toString() {
        return getId() + " - " + getName();
    }
}
