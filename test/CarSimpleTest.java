import org.junit.Test;
import static org.junit.Assert.*;

public class CarSimpleTest {
    private static final double EPS = 1e-9;

    @Test
    public void startAndStopEngine() {
        Car car = new Volvo240();
        car.startEngine();
        assertEquals(0.1, car.getCurrentSpeed(), EPS);

        car.stopEngine();
        assertEquals(0.0, car.getCurrentSpeed(), EPS);
    }
    //testing start and stop, the car reaches a minimum speed > 0

    @Test
    public void gasIncreasesSpeed() {
        Car car = new Saab95();
        car.startEngine();
        double before = car.getCurrentSpeed();
        car.gas(0.7);
        assertTrue(car.getCurrentSpeed() > before);
    }
    //testing to see if the gas function actually increases the speed.

    @Test
    public void brakeDecreasesSpeed() {
        Car car = new Volvo240();
        car.startEngine();
        car.gas(1.0);
        double before = car.getCurrentSpeed();
        car.brake(0.5);
        assertTrue(car.getCurrentSpeed() < before);
    }
    //checking the breaking function

    @Test
    public void outOfRangeGasAndBrakeDoesNothing() {
        Car car = new Volvo240();
        car.startEngine();
        double before = car.getCurrentSpeed();

        car.gas(-1);
        car.gas(2);
        assertEquals(before, car.getCurrentSpeed(), EPS);

        car.brake(-1);
        car.brake(2);
        assertEquals(before, car.getCurrentSpeed(), EPS);
    }
    //testing to see if amount outside our index works or not. (It should not)
}

//JUnit is used to automatically confirm behavior.
//It is good in order to keep building the test without ruining anything.
//Also to see if everything is correct, the coverage system shows effectively where the problem is
//JUnit works as documentation and tests on the side