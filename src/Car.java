import java.awt.*;


//Abstract base class representing a generic car
//Cannot be instantiated directly, must instead be extended by specific car types
public abstract class Car implements Movable {

    protected int nrDoors;
    protected double enginePower;
    protected double currentSpeed;
    protected Color color;
    protected String modelName;

    protected double x;
    protected double y;

    protected enum Directions{
        NORTH, WEST, EAST, SOUTH    //enum sätter ett begränsat/förbestämt antal gilltiga alternativ
    }
    protected Directions direction; //en variabel direction av typen Directions, mst vara en av de fyra giltiga
                                    // protected för inkapsling, vill ej kunna ändra
                                    //subklasser kan använda inte användaren direkt

    //Constructor which initializes a Car with the given parameters
    public Car(int nrDoors, Color color, double enginePower, String modelName) {

        this.nrDoors = nrDoors; // Number of doors on the car
        this.enginePower = enginePower; // Engine power of the car
        this.color = color; // Color of the car
        this.modelName = modelName; // The car model name
        this.direction=Directions.NORTH; // ursprungligen är bilen vänd mot nord, säkerställer en korrekt startriktning
        this.currentSpeed=0;            // sätter detta istället för stopEngine() för 
                                        //att inte riskera overriding i en subklass
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
        if (!canDrive()){return;} // kontrollerar att fordonet får starta motorn, och där med öka hatighet
        currentSpeed = 0.1;
    }

    //Stops the engine by putting the current speed to 0
    public void stopEngine(){
        currentSpeed = 0;
    }

    //Returns true by default. Subclasses can override this method to restrict when the vehicle is allowed to drive.
    protected boolean canDrive() { return true; }

    //Abstract method implemented by the subclasses to determine their specific speedFactor
    protected abstract double speedFactor();

    //Increases the current speed based on the amount parameter and the specific cars speedFactor
    protected void incrementSpeed(double amount){
        currentSpeed = Math.min(getCurrentSpeed() + speedFactor() * amount, enginePower); // hastigheten överstiger inte enginepower
    }

    //Decreases the current speed based on the amount parameter and the specific cars speedFactor
    protected void decrementSpeed(double amount){
        currentSpeed = Math.max(getCurrentSpeed() - speedFactor() * amount,0); // lägsta hastighet är 0 
    }

     public void gas(double amount){
        if (amount >= 0 && amount <= 1){ // kontrollerat att amount>=0 OCH <=1
            if (!canDrive()){return;} // kontrollerar att fordonet får köra
            incrementSpeed(amount);
        }
    }

    public void brake(double amount){
        if (amount >= 0 && amount <= 1){
            decrementSpeed(amount);
        }
    }

    // implementera metoderna från interfacet movable
    //interfacet säger att objektet kan röra på sig och svänga, inte hur, lätt 
    // att skriva över till varje typ av objekt ex bil,båt, cykel osv utan att behöva ändra kod
    @Override
    public void move(){
        switch (direction){
            case NORTH -> y+= currentSpeed;
            case WEST  -> x-= currentSpeed;
            case EAST  -> x+= currentSpeed;
            case SOUTH -> y-= currentSpeed;
        }
    }

    @Override
    public void turnLeft(){
        switch (direction){
            case NORTH -> direction= Directions.WEST;
            case WEST  -> direction= Directions.SOUTH;
            case EAST  -> direction= Directions.NORTH;
            case SOUTH -> direction= Directions.EAST;
        }
    }

    @Override
    public void turnRight(){
        switch (direction){
            case NORTH -> direction= Directions.EAST;
            case WEST  -> direction= Directions.NORTH;
            case EAST  -> direction= Directions.SOUTH;
            case SOUTH -> direction= Directions.WEST;
        }
    }
}

   

    