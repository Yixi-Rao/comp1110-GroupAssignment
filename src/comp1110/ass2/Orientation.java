package comp1110.ass2;

public enum Orientation {
    NORTH, EAST, SOUTH, WEST;

    public char toChar() {
        // FIXME Task 2
        if (this == NORTH ){
            return '0';
        }else if (this == EAST){
            return '1';
        }else if (this == SOUTH){
            return '2';
        }else {
            return '3';
        }

    }
}
