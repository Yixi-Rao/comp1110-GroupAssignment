package comp1110.ass2;
/**
 * All situations of piece's colours
 */
public enum Colours {
    BLUE,
    WHITE,
    RED,
    GREEN;

    /**
     * From piece's colours to String,
     */
    public static Colours toColour(char c){
        if (c == 'R' )
            return RED;
        else if (c == 'B')
            return BLUE;
        else if (c == 'G')
            return GREEN;
        else
            return WHITE;
    }






}
