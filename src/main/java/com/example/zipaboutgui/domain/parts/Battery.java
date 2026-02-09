package com.example.zipaboutgui.domain.parts;

//import main.java.roehampton.msayyid.zipabout.domain.enums.BatteryChemistry;
import com.example.zipaboutgui.domain.enums.*;

/**
 * Represents a rechargeable battery pack used by an electric vehicle.
 * <p>
 * Tracks capacity in watt-hours, current charge level (as a percentage),
 * whether the battery is removable, charge cycle count, and chemistry type.
 */
public class Battery {

    private double level;          // 0–100 (%)
    private double capacityWh;     // capacity in watt-hours
    private boolean removable;
    private int cycleCount;
    private BatteryChemistry chemistry;

    /**
     * Creates a new battery with default chemistry (LI_ION).
     *
     * @param capacityWh capacity in watt-hours
     * @param level      current charge level (percentage 0–100)
     * @param removable  true if the battery is removable
     */
    public Battery(double capacityWh, double level, boolean removable) {
        this(capacityWh, level, removable, BatteryChemistry.LI_ION);
    }

    /**
     * Creates a new battery with a specific chemistry.
     *
     * @param capacityWh capacity in watt-hours
     * @param level      current charge level (percentage 0–100)
     * @param removable  true if the battery is removable
     * @param chemistry  chemistry type of this battery pack
     */
    public Battery(double capacityWh, double level, boolean removable,
                   BatteryChemistry chemistry) {
        this.capacityWh = capacityWh;
        this.level = level;
        this.removable = removable;
        this.chemistry = chemistry;
    }

    public int getCycleCount() {
        return cycleCount;
    }

    public void setCycleCount(int cycleCount) {
        this.cycleCount = cycleCount;
    }

    public boolean isRemovable() {
        return removable;
    }

    public void setRemovable(boolean removable) {
        this.removable = removable;
    }

    public double getCapacityWh() {
        return capacityWh;
    }

    public void setCapacityWh(double capacityWh) {
        this.capacityWh = capacityWh;
    }

    public void setLevel(double level) {
        this.level = level;
    }

    /**
     * Returns the current charge level as a percentage (0–100).
     *
     * @return current charge level
     */
    public double getLevel() {
        return this.level;
    }

    public BatteryChemistry getChemistry() {
        return chemistry;
    }

    public void setChemistry(BatteryChemistry chemistry) {
        this.chemistry = chemistry;
    }

    /**
     * Returns the current charge as a whole-percent string, e.g. "75".
     *
     * @return formatted charge percentage string
     */
    public String getCurrentChargePercent() {
        return String.format("%.0f", level);
    }
}
