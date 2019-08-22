package comp1110.ass2;

import static comp1110.ass2.Orientation.*;

import javax.print.attribute.standard.OrientationRequested;
import java.net.NoRouteToHostException;

public class Piece {

    private PiecesType piecesType;  // Which tile type is it (a ... j)
    private Location location;      // The piece's current location on game
    private Orientation orientation;// The piece's current orientation
    private Colours colours;        // The piece's colours

    public Piece(String piece){
        this.piecesType = PiecesType.valueOf(Character.toString(piece.charAt(0)-32));
        this.orientation = placementToOrientation(piece);
        this.location = pieceToLocation(piece);

    }
    public Location getLocation(){return location;}
    public Orientation getOrientation(){return orientation;}
    public PiecesType getPiecesType(){return piecesType;}
    public Colours getColous(){return colours;}

    public static Orientation placementToOrientation(String piece) {
        if (piece.charAt(3) == '0')
            return NORTH;
        else if (piece.charAt(3) == '1')
            return EAST;
        else if (piece.charAt(3) == '2')
            return SOUTH;
        else
            return WEST;
    }
    public static Location pieceToLocation(String piece) {
        int x = 0;
        switch (piece.charAt(1)){
            case '0':
                x = 0;break;
            case '1':
                x = 1;break;
            case '2':
                x = 2;break;
            case '3':
                x = 3;break;
            case '4':
                x = 4;break;
            case '5':
                x = 5;break;
            case '6':
                x = 6;break;
            case '7':
                x = 7;break;
            case '8':
                x = 8;break;
        }
        int y=0;
        switch (piece.charAt(2)){
            case '0':
                y = 0;break;
            case '1':
                y = 1;break;
            case '2':
                y = 2;break;
            case '3':
                y = 3;break;
            case '4':
                y = 4;break;
        }
        return new Location(x,y);
    }
}
