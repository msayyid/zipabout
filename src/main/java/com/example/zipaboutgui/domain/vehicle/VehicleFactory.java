package com.example.zipaboutgui.domain.vehicle;

//import main.java.roehampton.msayyid.zipabout.domain.enums.RideMode;
//import com.example.zipaboutgui.domain.enums.RideMode;
//import main.java.roehampton.msayyid.zipabout.domain.enums.ScooterType;
import com.example.zipaboutgui.domain.enums.*;
//import main.java.roehampton.msayyid.zipabout.domain.enums.VehicleKind;
//import main.java.roehampton.msayyid.zipabout.domain.equipment.Equipment;
import com.example.zipaboutgui.domain.equipment.*;
//import main.java.roehampton.msayyid.zipabout.domain.parts.Battery;
//import main.java.roehampton.msayyid.zipabout.domain.parts.Controller;
//import main.java.roehampton.msayyid.zipabout.domain.parts.Motor;
import com.example.zipaboutgui.domain.parts.*;
//import main.java.roehampton.msayyid.zipabout.domain.vehicle.electric.EBike;
//import main.java.roehampton.msayyid.zipabout.domain.vehicle.electric.EScooter;
//import main.java.roehampton.msayyid.zipabout.domain.vehicle.electric.ESkateboard;
//import main.java.roehampton.msayyid.zipabout.domain.vehicle.electric.Segway;
import com.example.zipaboutgui.domain.vehicle.*;
//import main.java.roehampton.msayyid.zipabout.domain.vehicle.non_electric.Bike;
//import main.java.roehampton.msayyid.zipabout.domain.vehicle.non_electric.KickScooter;
//import main.java.roehampton.msayyid.zipabout.domain.vehicle.non_electric.Skateboard;
import com.example.zipaboutgui.domain.vehicle.electric.EBike;
import com.example.zipaboutgui.domain.vehicle.electric.EScooter;
import com.example.zipaboutgui.domain.vehicle.electric.ESkateboard;
import com.example.zipaboutgui.domain.vehicle.electric.Segway;
import com.example.zipaboutgui.domain.vehicle.non_electric.*;
/**
 * Factory for creating concrete {@link Vehicle} instances.
 * <p>
 * This class implements a simple Factory Method pattern: client code supplies
 * a {@link VehicleKind} and the factory decides which concrete subclass
 * (e.g. {@link EBike}, {@link EScooter}, {@link Bike}) to instantiate.
 * This keeps object creation logic in one place and decouples callers from
 * specific constructors.
 */
public class VehicleFactory {

    /**
     * Creates a concrete {@link Vehicle} instance based on the given {@link VehicleKind}.
     * <p>
     * For electric vehicles, the supplied {@link Battery}, {@link Motor} and {@link Controller}
     * are passed to the constructor. For non-electric vehicles, these may be {@code null}.
     *
     * @param kind       the type of vehicle to create (e-bike, scooter, bike, etc.)
     * @param make       the manufacturer of the vehicle
     * @param model      the model name of the vehicle
     * @param equipments an array of equipment items associated with the vehicle (e.g. helmet)
     * @param battery    the battery to use (for electric vehicles), or {@code null} otherwise
     * @param motor      the motor to use (for electric vehicles), or {@code null} otherwise
     * @param controller the controller to use (for electric vehicles), or {@code null} otherwise
     * @return a new {@link Vehicle} of the requested kind
     * @throws IllegalArgumentException if the vehicle kind is unknown
     */
    public Vehicle createVehicle(
            VehicleKind kind,
            String make,
            String model,
            Equipment[] equipments,
            Battery battery,
            Motor motor,
            Controller controller
    ) {
        switch (kind) {
            case E_BIKE:
                return new EBike(make, model, equipments, battery, motor, controller);

            case E_SCOOTER:
                // Basic commuter scooter by default, using NORMAL ride mode
                return new EScooter(
                        make,
                        model,
                        equipments,
                        battery,
                        motor,
                        controller,
                        ScooterType.COMMUTER,
                        RideMode.NORMAL
                );

            case E_SKATEBOARD:
                return new ESkateboard(make, model, equipments, battery, motor, controller);

            case SEGWAY:
                return new Segway(make, model, equipments, battery, motor, controller);

            case BIKE:
                return new Bike(make, model, equipments);

            case KICK_SCOOTER:
                return new KickScooter(make, model, equipments);

            case SKATEBOARD:
                return new Skateboard(make, model, equipments);

            default:
                throw new IllegalArgumentException("Unknown vehicle type: " + kind);
        }
    }
}
