import java.awt.*;
import java.util.ArrayDeque;
import java.util.Deque; //FILO behavior, push and pop

public class CarTransport extends Truck {

    private final int maxCapacity;
    private final Deque<Car> loadedCars;

    // rampDown, true means the ramp is down
    private boolean rampDown;

    // A reasonable distance to be able to load a car.
    private static final double MAX_LOAD_DISTANCE = 2.0;
    // A size limit. We assume cars with too high engine power are too large/heavy.
    private static final double MAX_ALLOWED_ENGINE_POWER = 150.0;

    public CarTransport() {
        // Chosen reasonable default values
        super(2, Color.blue, 200, "CarTransport");
        this.maxCapacity = 8;
        this.loadedCars = new ArrayDeque<>();
        this.rampDown = false;
    }

    public CarTransport(int maxCapacity) {
        super(2, Color.blue, 200, "CarTransport");
        this.maxCapacity = Math.max(1, maxCapacity); //we can choose the capacity, min 1
        this.loadedCars = new ArrayDeque<>();
        this.rampDown = false;
    }

    public boolean isRampDown() {
        return rampDown;
    } //easy way to check if the ramp is up or down

    public void lowerRamp() {
        if (getCurrentSpeed() == 0) {
            rampDown = true;
        }
    } //ramp down if standing still


    public void raiseRamp() {
        rampDown = false;
    } //raise the ramp



    public boolean loadCar(Car car) {
        if (car == null) return false;
        if (!rampDown) return false;
        if (getCurrentSpeed() != 0) return false;
        if (loadedCars.size() >= maxCapacity) return false;
        if (loadedCars.contains(car)) return false;
        if (!isCarEligibleSize(car)) return false;
        if (!isCarCloseEnough(car)) return false;
        //None of these can be true in order to load a car

        loadedCars.push(car); // FILO: last in, first out
        syncLoadedCarsPosition();
        return true; //car can be loaded
    }


    public Car unloadCar() {
        if (!rampDown) return null;
        if (getCurrentSpeed() != 0) return null;
        if (loadedCars.isEmpty()) return null;

        Car car = loadedCars.pop(); //last car on goes out
        placeCarNearTransport(car);
        return car; //unloading a car if none of the points above are correct
    }

    public int getLoadedCount() {
        return loadedCars.size();
    } //how many cars do we have on

    @Override
    public void move() {
        super.move(); //overriding move
        syncLoadedCarsPosition(); //The cars on the trailer will move like the truck
    }

    private void syncLoadedCarsPosition() {
        for (Car c : loadedCars) {
            c.x = this.x;
            c.y = this.y;
        } //same position
    }

    private boolean isCarCloseEnough(Car car) {
        double dx = car.x - this.x;
        double dy = car.y - this.y;
        return Math.hypot(dx, dy) <= MAX_LOAD_DISTANCE;
    } //is the car close enough

    private boolean isCarEligibleSize(Car car) {
        return car.getEnginePower() <= MAX_ALLOWED_ENGINE_POWER;
    } //is the car within our size limit

    private void placeCarNearTransport(Car car) {
        //when we remove the car from the transport, its location should just behind the car, in every direction.
        double offset = 1.0;
        switch (direction) { //this part doesn't give 100% coverage, apparently its an implicit default branch according to JaCoCo, we choose to ignore this because it is not an issue
            case NORTH -> {
                car.x = this.x;
                car.y = this.y - offset;
            }
            case SOUTH -> {
                car.x = this.x;
                car.y = this.y + offset;
            }
            case EAST -> {
                car.x = this.x - offset;
                car.y = this.y;
            }
            case WEST -> {
                car.x = this.x + offset;
                car.y = this.y;
            }
        }
    }

    @Override
    protected boolean canDrive() {
        // The transport cannot drive while the ramp is down
        return !rampDown;
    }

    @Override
    protected double speedFactor() {
        return enginePower * 0.01;
    }
}
