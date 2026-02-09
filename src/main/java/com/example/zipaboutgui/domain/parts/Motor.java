package com.example.zipaboutgui.domain.parts;

/**
 * Represents an electric motor used in electric vehicles.
 * <p>
 * Stores the motor's power rating in watts and provides simple
 * getters/setters for use by electric vehicle components.
 */
public class Motor {

    /** Motor power in watts. */
    private int powerW;

    /**
     * Creates a new motor with the given power rating.
     *
     * @param powerW the motor's wattage (e.g., 250, 300, 350 W)
     */
    public Motor(int powerW) {
        this.powerW = powerW;
    }

    /**
     * Returns the motor's power in watts.
     *
     * @return the wattage value
     */
    public int getPowerW() {
        return powerW;
    }

    /**
     * Updates the motor's power rating.
     *
     * @param powerW new wattage value
     */
    public void setPowerW(int powerW) {
        this.powerW = powerW;
    }

    /**
     * Returns the motor's power as a formatted string.
     * <p>
     * Useful for printing in vehicle details.
     *
     * @return the wattage value as a string
     */
    public String getPowerWatts() {
        return String.valueOf(powerW);
    }
}
