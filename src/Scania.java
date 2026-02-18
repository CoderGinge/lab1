import java.awt.*;

// Scania is a truck with a moveable truck bed that has to be handled separately
// It has certain rules or when the truck bed angle can be changed, and when the vehicle is allowed to drive
public class Scania extends Truck {

    // Current angle of the truck bed (0 = fully lowered)
    private double truckBedAngle = 0;

    //Constructor that calls the constructor of the base class
    public Scania() {
        super(2, Color.red, 90, "Scania");
    }

    // Returns the current truck bed angle
    public double getTruckBedAngle() {
        return truckBedAngle;
    }

    // Raises the truck bed, but only when the Scania is stood still
    public void raiseTruckBed(double amount) {
        if (getCurrentSpeed() == 0 && amount >= 0) {
            truckBedAngle = Math.min(truckBedAngle + amount, 70);
        }
    }

    public void lowerTruckBed(double amount) {
        if (getCurrentSpeed() == 0 && amount >= 0) {
            truckBedAngle = Math.max(truckBedAngle - amount, 0);
        }
    }

    // Checks Whether the Scania is allowed to drive
    // Driving is only allowed if the truck bed is fully lowered
    // Overrides the method in the base class
    @Override
    protected boolean canDrive() {
        return truckBedAngle == 0;
    }

    //Scania's specific implementation of speedFactor
    //Overrides the abstract method in the base class
    @Override
    protected double speedFactor() {
        return enginePower * 0.01;
    }

    @Override
    public void perform(Action action){
        switch(action){
            case LIFT_BED -> raiseTruckBed(10);
            case LOWER_BED -> lowerTruckBed(10);
            default -> { }
        }
    }

}



