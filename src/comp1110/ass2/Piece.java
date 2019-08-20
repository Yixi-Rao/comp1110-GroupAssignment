package comp1110.ass2;

import javafx.geometry.Orientation;

import javax.print.attribute.standard.OrientationRequested;

public class Piece {
    private PiecesType piecesType;  // Which tile type is it (a ... j)
    private Location location;      // The piece's current location on game
    private Orientation orientation;// The piece's current orientation
    private Colours colours;        // The piece's colours

    public Piece(String piece){

    }
    public Location getLocation(){return location;}
    public Orientation getOrientation(){return orientation;}
    public PiecesType getPiecesType(){return piecesType;}
    public Colours getColous(){return colours;}

    public static Orientation placementToOrientation(String piece) {
        return null;
    }
}
