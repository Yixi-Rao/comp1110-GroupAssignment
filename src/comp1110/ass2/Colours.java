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

/*


    public Colours[] createColours(Orientation orientation){
        return colourMapOffset[this.ordinal()][Integer.parseInt(orientation.toChar()+"")];
    }



    public static Colours[][][] colourMapOffset = new Colours[][][]{
            {{GREEN,WHITE,RED,RED}, {GREEN,RED,WHITE,RED}, {RED,RED,WHITE,GREEN}, {RED,WHITE,RED,GREEN}},
            {{BLUE,GREEN,GREEN,WHITE,WHITE}, {WHITE,WHITE,BLUE,GREEN,GREEN}, {WHITE,WHITE,GREEN,GREEN,BLUE}, {GREEN,GREEN,BLUE,WHITE,WHITE}},
            {{GREEN,RED,RED,WHITE,BLUE}, {RED,RED,WHITE,GREEN,BLUE}, {BLUE,WHITE,RED,RED,GREEN}, {BLUE,GREEN,WHITE,RED,RED}},
            {{RED,RED,RED,BLUE}, {RED,RED,BLUE,RED}, {BLUE,RED,RED,RED}, {RED,BLUE,RED,RED}},
            {{BLUE,BLUE,BLUE,RED,RED}, {RED,BLUE,RED,BLUE,BLUE}, {RED,RED,BLUE,BLUE,BLUE}, {BLUE,BLUE,RED,BLUE,RED}},
            {{WHITE,WHITE,WHITE}, {WHITE,WHITE,WHITE},{WHITE,WHITE,WHITE}, {WHITE,WHITE,WHITE}},
            {{WHITE,BLUE,BLUE,WHITE}, {WHITE,BLUE,BLUE,WHITE},{WHITE,BLUE,BLUE,WHITE}, {WHITE,BLUE,BLUE,WHITE}},
            {{RED,GREEN,GREEN,WHITE,WHITE}, {WHITE,WHITE,RED,GREEN,GREEN}, {WHITE,WHITE,RED,GREEN,GREEN}, {GREEN,GREEN,RED,WHITE,WHITE}},
            {{BLUE,BLUE,WHITE}, {BLUE,WHITE,BLUE}, {WHITE,BLUE,BLUE}, {BLUE,WHITE,BLUE}},
            {{GREEN,GREEN,WHITE,RED,GREEN}, {GREEN,GREEN,GREEN,WHITE,RED}, {GREEN,RED,WHITE,GREEN,GREEN}, {RED,WHITE,GREEN,GREEN,GREEN}}
    };*/
    //public Colours[] createColours()






















}
