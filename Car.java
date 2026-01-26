import java.awt.*;


//Abstract base class representing a generic car
//Cannot be instantiated directly, must instead be extended by specific car types
public abstract class Car {

    protected int nrDoors;
    protected double enginePower;
    protected double currentSpeed;
    protected Color color;
    protected String modelName;

    //Constructor which initializes a Car with the given parameters
    public Car(int nrDoors, Color color, double enginePower, String modelName) {

        this.nrDoors = nrDoors; // Number of doors on the car
        this.enginePower = enginePower; // Engine power of the car
        this.color = color; // Color of the car
        this.modelName = modelName; // The car model name
        stopEngine();
    }

    //Public getter/setter methods
    //Returns number of doors
    public int getNrDoors(){
        return nrDoors;
    }

    //Returns engine power
    public double getEnginePower(){
        return enginePower;
    }

    //Returns current speed
    public double getCurrentSpeed(){
        return currentSpeed;
    }

    //Returns the color of the car
    public Color getColor(){
        return color;
    }

    //Sets a new color to the car
    public void setColor(Color clr){
        this.color = clr;
    }

    //Starts the engine and sets a minimal initial speed
    public void startEngine(){
        currentSpeed = 0.1;
    }

    //Stops the engine by putting the current speed to 0
    public void stopEngine(){
        currentSpeed = 0;
    }

    //Abstract method implemented by the subclasses to determine their specific speedFactor
    protected abstract double speedFactor();

    //Increases the current speed based on the amount parameter and the specific cars speedFactor
    protected void incrementSpeed(double amount){
        currentSpeed = getCurrentSpeed() + speedFactor() * amount;
    }

    //Decreases the current speed based on the amount parameter and the specific cars speedFactor
    protected void decrementSpeed(double amount){
        currentSpeed = getCurrentSpeed() - speedFactor() * amount;
    }

    // TODO fix this method according to lab pm
    public void gas(double amount){
        incrementSpeed(amount);
    }

    // TODO fix this method according to lab pm
    public void brake(double amount){
        decrementSpeed(amount);
    }
}
