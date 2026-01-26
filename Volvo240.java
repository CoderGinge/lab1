import java.awt.*;

//Volvo240 is a special type of Car with a fixed trimFactor, which has to be handled separately
public class Volvo240 extends Car{

    // Fixed trimFactor
    // It is protected so that it can be used by subclasses, and final since it should never change.
    protected final static double trimFactor = 1.25;

    //Constructor that calls the constructor of the base class
    public Volvo240(){
        super(4, Color.black, 100, "Volvo240");
    }

    //Volvo240's specific implementation of speedFactor, which takes the trimFactor into account
    //Overrides the abstract method in the base class
    @Override
    protected double speedFactor(){
        return enginePower * 0.01 * trimFactor;
    }
}
