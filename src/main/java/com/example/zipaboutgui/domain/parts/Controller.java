package com.example.zipaboutgui.domain.parts;

/**
 * Represents the electronic controller of an electric vehicle.
 * <p>
 * The controller manages how the motor and battery behave (e.g. power delivery).
 * For this project, we simply track a firmware version string.
 */
public class Controller {

    /**
     * Firmware version identifier (for example "v1.0", "v2.1").
     */
    private String firmWareVersion;

    /**
     * Creates a controller with the given firmware version.
     *
     * @param firmWareVersion firmware version identifier
     */
    public Controller(String firmWareVersion) {
        this.firmWareVersion = firmWareVersion;
    }

    /**
     * Returns the firmware version.
     *
     * @return the firmware version string
     */
    public String getFirmWareVersion() {
        return firmWareVersion;
    }

    /**
     * Updates the firmware version string.
     *
     * @param firmWareVersion new firmware version
     */
    public void setFirmWareVersion(String firmWareVersion) {
        this.firmWareVersion = firmWareVersion;
    }

    /**
     * Preferred getter name for use in other classes.
     * <p>
     * This delegates to {@link #getFirmWareVersion()} to keep compatibility
     * with the existing field name.
     *
     * @return the firmware version string
     */
    public String getFirmwareVersion() {
        return firmWareVersion;
    }
}
