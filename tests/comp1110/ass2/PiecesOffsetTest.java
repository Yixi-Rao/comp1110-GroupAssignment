package comp1110.ass2;

import org.junit.Test;

import static junit.framework.TestCase.assertTrue;

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
