import org.junit.Test;
import static org.junit.Assert.*;

public class CarTurnCoverageTest {

    @Test
    public void turnLeft_coversAllCases() {
        Car car = new Volvo240();

        car.direction = Car.Directions.NORTH;
        car.turnLeft();
        assertEquals(Car.Directions.WEST, car.direction);

        car.direction = Car.Directions.WEST;
        car.turnLeft();
        assertEquals(Car.Directions.SOUTH, car.direction);

        car.direction = Car.Directions.SOUTH;
        car.turnLeft();
        assertEquals(Car.Directions.EAST, car.direction);

        car.direction = Car.Directions.EAST;
        car.turnLeft();
        assertEquals(Car.Directions.NORTH, car.direction);
    }

    @Test
    public void turnRight_coversAllCases() {
        Car car = new Volvo240();

        car.direction = Car.Directions.NORTH;
        car.turnRight();
        assertEquals(Car.Directions.EAST, car.direction);

        car.direction = Car.Directions.EAST;
        car.turnRight();
        assertEquals(Car.Directions.SOUTH, car.direction);

        car.direction = Car.Directions.SOUTH;
        car.turnRight();
        assertEquals(Car.Directions.WEST, car.direction);

        car.direction = Car.Directions.WEST;
        car.turnRight();
        assertEquals(Car.Directions.NORTH, car.direction);
    }
    //full coverage, all directions
}
