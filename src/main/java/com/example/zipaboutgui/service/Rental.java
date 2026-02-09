package com.example.zipaboutgui.service;

//import main.java.roehampton.msayyid.zipabout.domain.enums.RentalStatus;
//import main.java.roehampton.msayyid.zipabout.domain.user.User;
//import main.java.roehampton.msayyid.zipabout.domain.vehicle.Vehicle;
import com.example.zipaboutgui.domain.enums.*;
import com.example.zipaboutgui.domain.user.*;
import com.example.zipaboutgui.domain.vehicle.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a single rental of a vehicle by a user.
 * <p>
 * A {@code Rental} links one {@link User} to one {@link Vehicle} for a period of time.
 * It tracks start/end timestamps and a {@link RentalStatus} (ACTIVE, COMPLETED, CANCELLED),
 * and is responsible for updating the vehicle's booking state.
 */
public class Rental {

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private final String id;
    private final User user;
    private final Vehicle vehicle;

    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private RentalStatus status;

    /**
     * Creates a new rental and marks the vehicle as booked by the user.
     * The rental starts immediately at the current time and is initially ACTIVE.
     *
     * @param id      unique identifier for this rental
     * @param user    the user renting the vehicle
     * @param vehicle the vehicle being rented
     */
    public Rental(String id, User user, Vehicle vehicle) {
        this.id = id;
        this.user = user;
        this.vehicle = vehicle;
        this.startTime = LocalDateTime.now();
        this.status = RentalStatus.ACTIVE;

        // Mark the vehicle as no longer available
        vehicle.markAsBooked(user);
    }

    public String getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public RentalStatus getStatus() {
        return status;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    /**
     * Returns {@code true} if this rental is currently active.
     *
     * @return true if status is ACTIVE, false otherwise
     */
    public boolean isActive() {
        return status == RentalStatus.ACTIVE;
    }

    /**
     * Marks this rental as completed and releases the vehicle.
     * <p>
     * Sets the status to COMPLETED, records the end time, and calls
     * {@link Vehicle#markAsReleased()}.
     * If the rental is not ACTIVE, this method does nothing.
     */
    public void complete() {
        if (status != RentalStatus.ACTIVE) {
            return; // already completed or cancelled
        }
        this.status = RentalStatus.COMPLETED;
        this.endTime = LocalDateTime.now();
        vehicle.markAsReleased();
    }

    /**
     * Cancels this rental and releases the vehicle.
     * <p>
     * Sets the status to CANCELLED, records the end time, and calls
     * {@link Vehicle#markAsReleased()}.
     * If the rental is not ACTIVE, this method does nothing.
     */
    public void cancel() {
        if (status != RentalStatus.ACTIVE) {
            return;
        }
        this.status = RentalStatus.CANCELLED;
        this.endTime = LocalDateTime.now();
        vehicle.markAsReleased();
    }

    /**
     * Returns the duration of this rental in minutes, if start and end times are set.
     *
     * @return the duration in minutes, or -1 if the rental is not yet finished
     */
    public long getDurationMinutes() {
        if (startTime == null || endTime == null) {
            return -1;
        }
        return Duration.between(startTime, endTime).toMinutes();
    }

    private String formatTime(LocalDateTime time) {
        return time == null ? "n/a" : time.format(FORMATTER);
    }

    /**
     * Prints a summary of this rental, including user, vehicle, status and times.
     */
    public void printDetails() {
        long mins = getDurationMinutes();
        System.out.println("Rental ID: " + id);
        System.out.println("User: " + user.getName());
        System.out.println("Vehicle: " + vehicle.getVehicleType() + " - " + vehicle.getModel());
        System.out.println("Status: " + status);
        System.out.println("Start: " + formatTime(startTime));
        System.out.println("End: " + formatTime(endTime));
        if (mins >= 0) {
            System.out.println("Duration: " + mins + " min");
        }
        System.out.println("----------------------------------------");
    }
}
