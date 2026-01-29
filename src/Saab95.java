import java.awt.*;

//Saab95 is a special type of Car that has a turbo, which has to be handled separately
public class Saab95 extends Car{

    // Keeps track of whether turbo is on or off.
    // It is protected so that it can be used by subclasses, but can't be accessed directly by the user
    protected boolean turboOn;

    //Constructor that calls the constructor of the base class
    public Saab95(){
        super(2, Color.red, 125, "Saab95");
	    turboOn = false;
    }

    //Public methods to turn the turbo on or off
    public void setTurboOn(){
	    turboOn = true;
    }

    public void setTurboOff(){
	    turboOn = false;
    }

    //Saab95's specific implementation of speedFactor, which takes the turbo into account
    //Overrides the abstract method in the base class
    @Override
    protected double speedFactor(){
        double turbo = 1;
        if(turboOn) turbo = 1.3;
        return enginePower * 0.01 * turbo;
    }
}
