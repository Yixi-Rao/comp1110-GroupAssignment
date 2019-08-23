package comp1110.ass2;

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

    public static void main(String[] args) {
        //Location[] l = J.createPiece(3,3,SOUTH);
        //for (Location i:l) {
        //   System.out.println(i.getX()+"|"+i.getY());
        //}
    }
}
