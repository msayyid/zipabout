package com.example.zipaboutgui.service;

import java.time.format.DateTimeFormatter;

/**
 * Observer that sends a simple console notification when a rental is completed.
 * <p>
 * Implements the {@link RentalObserver} interface and is registered with
 * {@link RentalService} so that users receive confirmation messages when their
 * rentals finish.
 */
public class NotificationObserver implements RentalObserver {

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @Override
    public void onRentalCompleted(Rental rental) {
        String endTimeText = rental.getEndTime() != null
                ? rental.getEndTime().format(FORMATTER)
                : "now";

        System.out.println(
                "\n[NOTIFICATION] Dear " + rental.getUser().getName()
                        + ", your rental " + rental.getId()
                        + " of " + rental.getVehicle().getVehicleType() + ": "
                        + rental.getVehicle().getModel()
                        + " has been completed at " + endTimeText + "."
        );
    }
}
