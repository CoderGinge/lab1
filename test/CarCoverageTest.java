import org.junit.Test;
import static org.junit.Assert.*;
import java.awt.Color;

public class CarCoverageTest {

    @Test
    public void gettersAndSetColor_areCovered() {
        Volvo240 v = new Volvo240();

        assertEquals(4, v.getNrDoors());
        assertEquals(100.0, v.getEnginePower(), 1e-9);

        Color old = v.getColor();
        v.setColor(Color.BLUE);
        assertNotEquals(old, v.getColor());
        assertEquals(Color.BLUE, v.getColor());
    }
    //testing our getters and setColor in order to reach 100% coverage
}


