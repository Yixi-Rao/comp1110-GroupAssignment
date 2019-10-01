package comp1110.ass2;
/*
authorship details:  written by the wenxuan Li
 */
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;

/**
 * to test the piece off set table wherther it can map to the right place and give the correct coordinates
 */
public class PiecesOffsetTest {
    @Test
    public void offsetOf_x00y(){
        for (int t = 0;t < FocusGame.types.length;t++){
            for (int o = 0; o < FocusGame.orientations.length;o++){
                Piece piece = new Piece(FocusGame.types[t]+"00"+o);
                Location[] lout = piece.getPiecesType().createPiece(0,0,piece.getOrientation());
                int[] lexpected = PiecesType.pieceMapOffset[t][o];
                for (int L = 0;L < lout.length;L++){
                    assertTrue(lout[L].equals(new Location(lexpected[2*L],lexpected[2*L+1])));
                }
            }
        }
    }
}
