package com.example.zipaboutgui.service;

/**
 * Observer interface for reacting to completed rentals.
 * <p>
 * Classes that implement this interface can be registered with
 * {@link RentalService} and will be notified whenever a rental
 * is completed.
 */
public interface RentalObserver {

    /**
     * Called by {@link RentalService} when a rental has been completed.
     *
     * @param rental the rental that has just been completed
     */
    void onRentalCompleted(Rental rental);
}
