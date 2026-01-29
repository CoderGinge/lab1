import org.junit.Test;
import static org.junit.Assert.*;

public class SaabTurboTest {

    @Test
    public void turboOnGivesMoreAcceleration() {
        Saab95 car = new Saab95();
        car.startEngine();
        //turboOn = False, currentSpeed = 0.1

        car.setTurboOff();
        double offBefore = car.getCurrentSpeed();
        car.gas(1.0);
        double offDelta = car.getCurrentSpeed() - offBefore;
        //testing acceleration without the turbo

        car.stopEngine();
        car.startEngine();
        //reset

        car.setTurboOn();
        double onBefore = car.getCurrentSpeed();
        car.gas(1.0);
        double onDelta = car.getCurrentSpeed() - onBefore;
        //testing acceleration with the turbo

        assertTrue(onDelta > offDelta);
        //this must be true, otherwise fail
    }
}
