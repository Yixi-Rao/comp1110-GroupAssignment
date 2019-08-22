package comp1110.ass2;

import static comp1110.ass2.Orientation.*;

public enum PiecesType {
    A,B,C,D,E,F,G,H,I,J;

    public Location[] createPiece(int x,int y, Orientation orientation){
        int[] pieceOffsets = pieceMapOffset[this.ordinal()][Integer.parseInt(orientation.toChar()+"")];
        System.out.println(Integer.parseInt(orientation.toChar()+""));//$1$
        Location[] locations = new Location[pieceOffsets.length/2];
        for (int i = 0;i < pieceOffsets.length / 2;i++){
            System.out.println(pieceOffsets[2*i]+","+pieceOffsets[2*i + 1]);
            locations[i] = new Location(x + pieceOffsets[2*i],y + pieceOffsets[2*i + 1]);
        }
        return locations;
    }


    private static int[][][] pieceMapOffset = new int[][][]{
            {{0,0,1,0,2,0,1,1}, {1,0,0,1,1,1,1,2}, {1,0,0,1,1,1,2,1}, {0,0,0,1,1,1,0,2}},
            {{1,0,2,0,3,0,0,1,1,1}, {0,0,0,1,1,1,1,2,1,3}, {2,0,3,0,0,1,1,1,2,1}, {0,0,0,1,0,2,1,2,1,3}},
            {{2,0,0,1,1,1,2,1,3,1}, {0,0,0,1,0,2,1,2,0,3}, {0,0,1,0,2,0,3,0,1,1}, {1,0,0,1,1,1,1,2,1,3}},
            {{0,0,1,0,2,0,2,1}, {1,0,1,1,0,2,1,2}, {0,0,0,1,1,1,2,1}, {0,0,1,0,0,1,0,2}},
            {{0,0,1,0,2,0,0,1,1,1}, {0,0,1,0,0,1,1,1,1,2}, {1,0,2,0,0,1,1,1,2,1}, {0,0,0,1,1,1,0,2,1,2}},
            {{0,0,1,0,2,0}, {0,0,0,1,0,2}},
            {{0,0,1,0,1,1,2,1}, {1,0,0,1,1,1,0,2}},
            {{0,0,1,0,2,0,0,1,0,2}, {0,0,1,0,2,0,2,1,2,2}, {2,0,2,1,0,2,1,2,2,2}, {0,0,0,1,0,2,1,2,2,2}},
            {{0,0,1,0,1,1}, {1,0,0,1,1,1}, {0,0,0,1,1,1}, {0,0,1,0,0,1}},
            {{0,0,1,0,2,0,3,0,0,1}, {0,0,1,0,1,1,1,2,1,3}, {0,3,0,1,1,1,2,1,3,1}, {0,0,0,1,0,2,0,3,1,3}}
    };

    public static void main(String[] args) {
        Location[] l = A.createPiece(0,0,SOUTH);
        for (Location i:l) {
            System.out.println(i.getX()+"|"+i.getY());
        }
    }
}
