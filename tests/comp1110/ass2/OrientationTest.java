package comp1110.ass2;


import org.junit.Test;


import static junit.framework.TestCase.assertEquals;


public class OrientationTest {


    @Test
    public void orientationTest(){
        assertEquals('0', Orientation.NORTH.toChar());
        assertEquals('1', Orientation.EAST.toChar());
        assertEquals('2', Orientation.SOUTH.toChar());
        assertEquals('3', Orientation.WEST.toChar());

    }



}
