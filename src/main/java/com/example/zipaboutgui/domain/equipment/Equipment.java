package com.example.zipaboutgui.domain.equipment;

//import main.java.roehampton.msayyid.zipabout.domain.enums.EquipmentType;
import com.example.zipaboutgui.domain.enums.*;

/**
 * Represents a piece of equipment associated with a vehicle,
 * such as a helmet, gloves, or kneepads.
 */
public class Equipment {

    private final EquipmentType type;
    private final String material;
    private final String size;

    /**
     * Creates a new equipment item.
     *
     * @param type     type of equipment (helmet, glove, etc.)
     * @param material main material (e.g. "ABS plastic", "Leather")
     * @param size     size label (e.g. "M", "L", "Universal")
     */
    public Equipment(EquipmentType type, String material, String size) {
        this.type = type;
        this.material = material;
        this.size = size;
    }

    public EquipmentType getType() {
        return type;
    }

    public String getMaterial() {
        return material;
    }

    public String getSize() {
        return size;
    }
}
