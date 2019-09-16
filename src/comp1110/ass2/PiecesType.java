package comp1110.ass2;

import static comp1110.ass2.Colours.*;
import static comp1110.ass2.Orientation.*;

public enum PiecesType {
    A,B,C,D,E,F,G,H,I,J;

    public Location[] createPiece(int x,int y, Orientation orientation){
        int[] pieceOffsets = pieceMapOffset[this.ordinal()][Integer.parseInt(orientation.toChar()+"")];
        Location[] locations = new Location[pieceOffsets.length/2];
        for (int i = 0;i < pieceOffsets.length / 2;i++){
            locations[i] = new Location(x + pieceOffsets[2*i],y + pieceOffsets[2*i + 1]);
        }
        return locations;
    }

    public Colours[] createColours(Orientation orientation){
        return colourMapOffset[this.ordinal()][Integer.parseInt(orientation.toChar()+"")];
    }

    private static int[][][] pieceMapOffset = new int[][][]{
            {{0,0,1,0,2,0,1,1}, {1,0,0,1,1,1,1,2}, {1,0,0,1,1,1,2,1}, {0,0,0,1,1,1,0,2}},
            {{1,0,2,0,3,0,0,1,1,1}, {0,0,0,1,1,1,1,2,1,3}, {2,0,3,0,0,1,1,1,2,1}, {0,0,0,1,0,2,1,2,1,3}},
            {{2,0,0,1,1,1,2,1,3,1}, {0,0,0,1,0,2,1,2,0,3}, {0,0,1,0,2,0,3,0,1,1}, {1,0,0,1,1,1,1,2,1,3}},
            {{0,0,1,0,2,0,2,1}, {1,0,1,1,0,2,1,2}, {0,0,0,1,1,1,2,1}, {0,0,1,0,0,1,0,2}},
            {{0,0,1,0,2,0,0,1,1,1}, {0,0,1,0,0,1,1,1,1,2}, {1,0,2,0,0,1,1,1,2,1}, {0,0,0,1,1,1,0,2,1,2}},
            {{0,0,1,0,2,0}, {0,0,0,1,0,2},{0,0,1,0,2,0}, {0,0,0,1,0,2}},
            {{0,0,1,0,1,1,2,1}, {1,0,0,1,1,1,0,2},{0,0,1,0,1,1,2,1}, {1,0,0,1,1,1,0,2}},
            {{0,0,1,0,2,0,0,1,0,2}, {0,0,1,0,2,0,2,1,2,2}, {2,0,2,1,0,2,1,2,2,2}, {0,0,0,1,0,2,1,2,2,2}},
            {{0,0,1,0,1,1}, {1,0,0,1,1,1}, {0,0,0,1,1,1}, {0,0,1,0,0,1}},
            {{0,0,1,0,2,0,3,0,0,1}, {0,0,1,0,1,1,1,2,1,3}, {3,0,0,1,1,1,2,1,3,1}, {0,0,0,1,0,2,0,3,1,3}}
    };


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
    };

    public static void main(String[] args) {
        Location[] l = B.createPiece(0,2,EAST);
        for (Location i:l) {
          System.out.println(i.getX()+"|"+i.getY());
        }
    }
}
