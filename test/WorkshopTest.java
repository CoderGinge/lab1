import org.junit.Test;
import static org.junit.Assert.*;

public class WorkshopTest {

  @Test
  public void addCar_coversNotFullAndFullBranches() {
    Workshop<Volvo240> w = new Workshop<>(1);
    Volvo240 v1 = new Volvo240();
    Volvo240 v2 = new Volvo240();

    //adding one car
    w.addCar(v1);

    //only one capacity, so this does not go in to the workshop
    w.addCar(v2);


    assertNotNull(w.takeCarOut(v1)); //this will return something, v1 is in the workshop
    assertNull(w.takeCarOut(v2)); //nothing will happen cause v2 is not in the workshop
  }

  @Test
  public void takeCarOut_coversContainsAndNotContainsBranches() {
    Workshop<Saab95> w = new Workshop<>(2);
    Saab95 s1 = new Saab95();

    //we never put a car in the workshop, then we can not take anything out.
    assertNull(w.takeCarOut(s1));


    w.addCar(s1);
    assertSame(s1, w.takeCarOut(s1)); //s1 and w.takeCarOut(s1) should be the same value
  }
}
