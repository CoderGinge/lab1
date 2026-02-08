import org.junit.Test;
import static org.junit.Assert.*;

public class MovementSimpleTest {
    private static final double EPS = 1e-9;

    @Test
    public void moveNorthIncreasesY() {
        Car car = new Volvo240();
        car.startEngine();

        double xBefore = car.x;
        double yBefore = car.y;

        car.move();

        assertEquals(xBefore, car.x, EPS);
        assertTrue(car.y > yBefore);
    }
    //testing to see if the car moves
    //the car starts with direction NORTH so this is an easy check

    @Test
    public void turnLeftAndRightChangesDirection() {
        Car car = new Saab95();

        assertEquals(Car.Directions.NORTH, car.direction);
        car.turnLeft();
        assertEquals(Car.Directions.WEST, car.direction);
        car.turnRight();
        assertEquals(Car.Directions.NORTH, car.direction);
    }
    //testing to see if it changes direction, starts in NORTH

    @Test
    public void moveCoversAllDirections() {
        Car car = new Volvo240();
        car.startEngine();

        // NORTH
        double y0 = car.y;
        car.move();
        assertTrue(car.y > y0);

        // EAST
        car.turnRight();
        double x1 = car.x;
        car.move();
        assertTrue(car.x > x1);

        // SOUTH
        car.turnRight();
        double y2 = car.y;
        car.move();
        assertTrue(car.y < y2);

        // WEST
        car.turnRight();
        double x3 = car.x;
        car.move();
        assertTrue(car.x < x3);
    }
    //testing to see if x and y changes in both directions

}
