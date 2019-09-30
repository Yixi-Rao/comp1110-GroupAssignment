package comp1110.ass2;

import org.junit.Test;

import static junit.framework.TestCase.assertTrue;

public class PieceTest {


    public void test(String placement,Orientation ori){
        Orientation out = Piece.placementToOrientation(placement);
        assertTrue("Input was'" + placement + "', expected " + ori, ori == out);
    }


    public void test2(String placement,Location loc){
        Location out = Piece.pieceToLocation(placement);
        assertTrue("Input was'" + placement + "', expected " + loc + "but got" + out, loc.equals(out));

    }


    /**
     * Test the function placementToOrientation
     * The last digit of placement String represents the orientation of pieces
     */
    @Test
    public void testPlacementToOrientation(){
        test("a000",Orientation.NORTH);
        test("a001",Orientation.EAST);
        test("a002",Orientation.SOUTH);
        test("a003",Orientation.WEST);
    }


    /**
     * Test the function pieceToLoacation
     * The second digit of placement String represents the X position of pieces
     * The third digit of placement String represents the Y position of pieces
     */
    @Test
    public void testPieceToLocation() {
        for (int i = 0; i <= 8; i++){
            for (int j = 0; j <= 4; j++){
                Location cache = new Location(i,j);
                test2("a" + i + j + "0", cache);
            }
        }

    }
}
