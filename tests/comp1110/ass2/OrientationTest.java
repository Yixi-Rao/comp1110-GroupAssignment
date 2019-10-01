package comp1110.ass2;

/*
authorship details:  written by the Fang peng
 */


import org.junit.Test;


import static junit.framework.TestCase.assertEquals;


public class OrientationTest {

    /**
     * Test the class Orientation
     * The last digit of placement String represents the orientation of pieces
     * '0' -- NORTH
     * '1' -- EAST
     * '2' -- SOUTH
     * '3' -- WEST
     */
    @Test
    public void orientationTest(){
        assertEquals('0', Orientation.NORTH.toChar());
        assertEquals('1', Orientation.EAST.toChar());
        assertEquals('2', Orientation.SOUTH.toChar());
        assertEquals('3', Orientation.WEST.toChar());

    } // test orientation of all pieces



}
