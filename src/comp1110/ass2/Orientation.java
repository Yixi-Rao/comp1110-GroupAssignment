package comp1110.ass2;

/*
authorship details:  written by the group
 */


/**
 * All situations of piece's orientation
 */
public enum Orientation {
    NORTH, EAST, SOUTH, WEST;


    /**
     * From piece's orientation to String,
     */
    public char toChar() {
        if (this == NORTH ){
            return '0';         // The last digit of placement string represents the orientation
        }else if (this == EAST){
            return '1';
        }else if (this == SOUTH){
            return '2';
        }else {
            return '3';
        }
    }
}
