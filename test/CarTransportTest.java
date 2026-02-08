import org.junit.Test;
import static org.junit.Assert.*;

public class CarTransportTest {

  //we try a big car with more than 150 engine power
  private static class BigCar extends Car {
    public BigCar() { super(4, java.awt.Color.gray, 200, "BigCar"); }
    @Override protected double speedFactor() { return enginePower * 0.01; }
  }

  // trying a small car, less than 150 engine power that we can test with in other scenarios
  private static class SmallCar extends Car {
    public SmallCar() { super(2, java.awt.Color.green, 100, "SmallCar"); }
    @Override protected double speedFactor() { return enginePower * 0.01; }
  }

  @Test
  public void lowerRamp_onlyWhenStill_branchCoverage() {
    CarTransport t = new CarTransport();


    t.currentSpeed = 1.0;
    t.lowerRamp();
    assertFalse(t.isRampDown());


    t.currentSpeed = 0.0;
    t.lowerRamp();
    assertTrue(t.isRampDown());
  } //testing to lower ramp when still and not still

  @Test
  public void loadCar_allFalseBranches_andTrueBranch() {
    CarTransport t = new CarTransport(1);
    SmallCar c1 = new SmallCar();

    //car = null?
    assertFalse(t.loadCar(null));

    //ramp up, loading ok?
    assertFalse(t.loadCar(c1));

    // ramp down but speed != 0
    t.lowerRamp();
    t.currentSpeed = 1.0;
    assertFalse(t.loadCar(c1));
    t.currentSpeed = 0.0;

    //too far away to load car
    c1.x = t.x + 100;
    c1.y = t.y + 100;
    assertFalse(t.loadCar(c1));

    //close enough => success
    c1.x = t.x;
    c1.y = t.y;
    assertTrue(t.loadCar(c1));
    assertEquals(1, t.getLoadedCount()); //count = 1

    //testing if we can load the same car twice
    assertFalse(t.loadCar(c1));

    //the transport is full
    SmallCar c2 = new SmallCar();
    c2.x = t.x;
    c2.y = t.y;
    assertFalse(t.loadCar(c2));

    //the car is too big (BigCar)
    CarTransport t2 = new CarTransport(2);
    t2.lowerRamp();
    BigCar big = new BigCar();
    big.x = t2.x;
    big.y = t2.y;
    assertFalse(t2.loadCar(big));
  }

  @Test
  public void unloadCar_allNullBranches_andSuccess() {
    CarTransport t = new CarTransport();
    SmallCar c = new SmallCar();
    c.x = t.x;
    c.y = t.y;

    //ramp up, should not be able to unload
    assertNull(t.unloadCar());

    //ramp down but empty transport
    t.lowerRamp();
    assertNull(t.unloadCar());

    // ramp down but speed != 0
    assertTrue(t.loadCar(c));
    t.currentSpeed = 1.0;
    assertNull(t.unloadCar());
    t.currentSpeed = 0.0;
  }

  @Test
  public void placeCarNearTransport_allDirections() {
    CarTransport t = new CarTransport();
    t.lowerRamp();
    //does the car end up in the right place after we drop it off. All directions
    // NORTH (default)
    SmallCar n = new SmallCar(); n.x = t.x; n.y = t.y;
    assertTrue(t.loadCar(n));
    Car unloadedN = t.unloadCar();
    assertNotNull(unloadedN);
    assertEquals(t.x, unloadedN.x, 0.0001);
    assertEquals(t.y - 1.0, unloadedN.y, 0.0001);

    // EAST
    t.turnRight(); // NORTH -> EAST
    SmallCar e = new SmallCar(); e.x = t.x; e.y = t.y;
    assertTrue(t.loadCar(e));
    Car unloadedE = t.unloadCar();
    assertEquals(t.x - 1.0, unloadedE.x, 0.0001);
    assertEquals(t.y, unloadedE.y, 0.0001);

    // SOUTH
    t.turnRight(); // EAST -> SOUTH
    SmallCar s = new SmallCar(); s.x = t.x; s.y = t.y;
    assertTrue(t.loadCar(s));
    Car unloadedS = t.unloadCar();
    assertEquals(t.x, unloadedS.x, 0.0001);
    assertEquals(t.y + 1.0, unloadedS.y, 0.0001);

    // WEST
    t.turnRight(); // SOUTH -> WEST
    SmallCar w = new SmallCar(); w.x = t.x; w.y = t.y;
    assertTrue(t.loadCar(w));
    Car unloadedW = t.unloadCar();
    assertEquals(t.x + 1.0, unloadedW.x, 0.0001);
    assertEquals(t.y, unloadedW.y, 0.0001);
  }

  @Test
  public void move_syncLoadedCarsPosition() {
    CarTransport t = new CarTransport();
    SmallCar c = new SmallCar();
    c.x = t.x;
    c.y = t.y;

    t.lowerRamp();
    assertTrue(t.loadCar(c));

    t.raiseRamp();
    t.startEngine();
    t.gas(1.0);
    t.move();

    //loaded car should always have the same position as the transport, is there any step that prevent this in some way?
    assertEquals(t.x, c.x, 0.0001);
    assertEquals(t.y, c.y, 0.0001);
  }

  @Test
  public void canDrive_falseWhenRampDown_trueWhenRampUp() {
    CarTransport t = new CarTransport();

    t.lowerRamp();
    //startEngine should be blocked by canDrive() because the ramp is down
    t.startEngine();
    assertEquals(0.0, t.getCurrentSpeed(), 0.0001);

    t.raiseRamp();
    t.startEngine();
    assertTrue(t.getCurrentSpeed() > 0.0);
  }

  private static final double EPS = 1e-9;
  //"float number"


  @Test
  public void cannotLoadSameCarTwice_coversContainsBranch() {
    CarTransport t = new CarTransport();
    Volvo240 v = new Volvo240();

    // nära nog
    v.x = t.x;
    v.y = t.y;

    t.lowerRamp();
    assertTrue(t.loadCar(v));
  } //car cant be loaded twice but with full coverage




}