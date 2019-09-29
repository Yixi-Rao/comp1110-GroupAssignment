package comp1110.ass2;

public enum Colours {
    BLUE,
    WHITE,
    RED,
    GREEN;


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
