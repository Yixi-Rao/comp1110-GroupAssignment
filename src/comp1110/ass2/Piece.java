package comp1110.ass2;

import javafx.geometry.Orientation;

import javax.print.attribute.standard.OrientationRequested;

public class Piece {
    private PiecesType piecesType;
    private Location location;
    private Orientation orientation;
    private Colours colours;

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
