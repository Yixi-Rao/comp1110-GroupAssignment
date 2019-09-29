package comp1110.ass2;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class LocationTest {

    private static Location location;

    @BeforeClass
    public static void beforeAll() {
        location = new Location(1, 2);
    }

    @Test
    public void getX() {
        assertEquals(1, location.getX());
    }

    @Test
    public void getY() {
        assertEquals(2, location.getY());
    }

    @Test
    public void testEquals() {
        assertTrue(location.equals(new Location(1, 2)));
    }
}