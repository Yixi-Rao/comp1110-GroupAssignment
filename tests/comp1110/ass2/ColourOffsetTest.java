package comp1110.ass2;

/*
authorship details:  written by the Yixi rao
 */

import org.junit.Test;

import static junit.framework.TestCase.assertTrue;

public class ColourOffsetTest {

    @Test
    public void colourOfAllPieces(){
        for (int t = 0;t < FocusGame.types.length;t++){
            for (int o = 0; o < FocusGame.orientations.length;o++){
                Piece piece = new Piece(FocusGame.types[t]+"00"+o);
                Colours[] coloursSample= piece.getColous();
                for (int c = 0;c<coloursSample.length;c++){
                    assertTrue(coloursSample[c] == PiecesType.colourMapOffset[t][o][c]);
                }
            }
        }
    }
}
