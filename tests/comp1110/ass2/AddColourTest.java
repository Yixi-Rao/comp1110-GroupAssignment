package comp1110.ass2;

import org.junit.Test;

import static junit.framework.TestCase.assertTrue;

public class AddColourTest {


    @Test
    public void positionTest() {
        FocusGame.addColour(new Piece("a000"));
        Location[] locations = PiecesType.A.createPiece(0,0,Orientation.NORTH);
        for (Location l:locations){
            assertTrue("Input was '" + "a000" + "', expected " +"not null"+ " but got " + FocusGame.gameStatesColour[l.getY()][l.getX()],FocusGame.gameStatesColour[l.getY()][l.getX()] != null);
        }
        assertTrue(FocusGame.gameStatesColour[1][0] == null && FocusGame.gameStatesColour[1][2] == null);
        FocusGame.gameStatesColour = new Colours[5][9];
       //assertTrue(FocusGame.addColour(););
    }

    @Test
    public void colourTest() {
        FocusGame.addColour(new Piece("a000"));
        Location[] locations = PiecesType.A.createPiece(0,0,Orientation.NORTH);
        Colours[] colors_a000 = {Colours.GREEN,Colours.WHITE,Colours.RED,Colours.RED};
        for (int i = 0;i < locations.length;i++){
            assertTrue(FocusGame.gameStatesColour[locations[i].getY()][locations[i].getX()] == colors_a000[i]);
        }
        FocusGame.gameStatesColour = new Colours[5][9];

        FocusGame.addColour(new Piece("e321"));
        Location[] locations2 = PiecesType.E.createPiece(3,2,Orientation.EAST);
        Colours[] colors_e321 = {Colours.RED,Colours.BLUE,Colours.RED,Colours.BLUE,Colours.BLUE};
        for (int i = 0;i < locations2.length;i++){
            assertTrue(FocusGame.gameStatesColour[locations2[i].getY()][locations2[i].getX()] == colors_e321[i]);
        }
        FocusGame.gameStatesColour = new Colours[5][9];
    }


}
