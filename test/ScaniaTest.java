import org.junit.Test;
import static org.junit.Assert.*;

public class ScaniaTest {

    private static final double EPS = 1e-9;

    // Tests if the setter-methods for changing the truck bed angle works at all
    @Test
    public void raiseTruckBedWorks() {
        Scania scania = new Scania();
        double before = scania.getTruckBedAngle();
        scania.raiseTruckBed(10);
        assertEquals((before+10), scania.getTruckBedAngle(), EPS);
    }

    @Test
    public void lowerTruckBedWorks() {
        Scania scania = new Scania();
        scania.raiseTruckBed(20);
        double before = scania.getTruckBedAngle();
        scania.lowerTruckBed(10);
        assertEquals((before-10), scania.getTruckBedAngle(), EPS);
    }

    // Tests if the setter-methods handles the restrictions for the possible states of the truck bed
    @Test
    public void raiseTruckBedHasLimit70() {
        Scania scania = new Scania();
        scania.raiseTruckBed(80);
        assertEquals(70, scania.getTruckBedAngle(), EPS);
    }

    @Test
    public void lowerTruckBedHasLimit0() {
        Scania scania = new Scania();
        scania.lowerTruckBed(10);
        assertEquals(0, scania.getTruckBedAngle(), EPS);
    }

    // Tests that the Scania shouldn't be able to move if the truck bed isn't fully lowered
    @Test
    public void scaniaCanNotDriveIfTruckBedIsRaised() {
        Scania scania = new Scania();
        scania.raiseTruckBed(10);
        scania.gas(0.5);
        assertEquals(0, scania.getCurrentSpeed(), EPS);
    }

    // tests that the truck bed won't move if the Scania is moving
    @Test
    public void truckBedCanNotMoveIfScaniaIsMoving() {
        Scania scania = new Scania();
        scania.gas(0.5);
        scania.raiseTruckBed(10);
        assertEquals(0, scania.getTruckBedAngle(), EPS);
    }

    // Tests that the Scania can move if the truck bed is fully lowered
    @Test
    public void scaniaCanDriveIfTruckBedIsFullyLowered() {
        Scania scania = new Scania();
        scania.gas(0.5);
        assertEquals(0.45, scania.getCurrentSpeed(), EPS);
    }

}
