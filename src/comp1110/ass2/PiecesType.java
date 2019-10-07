package comp1110.ass2;

/*
authorship details:  written by the group and offset written by Yixi Rao
 */

import static comp1110.ass2.Colours.*;
import static comp1110.ass2.Orientation.*;

/**
 * All the piece type from A to J
 */
public enum PiecesType {
    A,B,C,D,E,F,G,H,I,J;

    /**
     * given a location in terms of x,y and an orientation of a piece,it will return the coordinates of all
     * the piece according to board.
     * @param x x of piece location
     * @param y y of piece location
     * @param orientation orientation of piece
     * @return all the piece locations refer to board coordinate
     */
    public Location[] createPiece(int x,int y, Orientation orientation){
        int[] pieceOffsets = pieceMapOffset[this.ordinal()][Integer.parseInt(orientation.toChar()+"")];     //offsets pf a specified piece
        Location[] locations = new Location[pieceOffsets.length/2]; //all the piece locations refer to board coordinate
        for (int i = 0;i < pieceOffsets.length / 2;i++){
            locations[i] = new Location(x + pieceOffsets[2*i],y + pieceOffsets[2*i + 1]);
        }
        return locations;
    }

    /**
     * given an orientation of a piece,it will return the colours of all
     * the piece according to board.
     * @param orientation orientation of piece
     * @return all the colour refer to board
     */
    public Colours[] createColours(Orientation orientation){
        return colourMapOffset[this.ordinal()][Integer.parseInt(orientation.toChar()+"")];
    }

    /**
     * offsets of coordinate of all the types, with the order of "a,b,c.....i" and "0,1,2,3"
     */
    public static int[][][] pieceMapOffset = new int[][][]{
            {{0,0,1,0,2,0,1,1},     {1,0,0,1,1,1,1,2},     {1,0,0,1,1,1,2,1},     {0,0,0,1,1,1,0,2}},
            {{1,0,2,0,3,0,0,1,1,1}, {0,0,0,1,1,1,1,2,1,3}, {2,0,3,0,0,1,1,1,2,1}, {0,0,0,1,0,2,1,2,1,3}},
            {{2,0,0,1,1,1,2,1,3,1}, {0,0,0,1,0,2,1,2,0,3}, {0,0,1,0,2,0,3,0,1,1}, {1,0,0,1,1,1,1,2,1,3}},
            {{0,0,1,0,2,0,2,1},     {1,0,1,1,0,2,1,2},     {0,0,0,1,1,1,2,1},     {0,0,1,0,0,1,0,2}},
            {{0,0,1,0,2,0,0,1,1,1}, {0,0,1,0,0,1,1,1,1,2}, {1,0,2,0,0,1,1,1,2,1}, {0,0,0,1,1,1,0,2,1,2}},
            {{0,0,1,0,2,0},         {0,0,0,1,0,2},         {0,0,1,0,2,0},         {0,0,0,1,0,2}},
            {{0,0,1,0,1,1,2,1},     {1,0,0,1,1,1,0,2},     {0,0,1,0,1,1,2,1},     {1,0,0,1,1,1,0,2}},
            {{0,0,1,0,2,0,0,1,0,2}, {0,0,1,0,2,0,2,1,2,2}, {2,0,2,1,0,2,1,2,2,2}, {0,0,0,1,0,2,1,2,2,2}},
            {{0,0,1,0,1,1},         {1,0,0,1,1,1},         {0,0,0,1,1,1},         {0,0,1,0,0,1}},
            {{0,0,1,0,2,0,3,0,0,1}, {0,0,1,0,1,1,1,2,1,3}, {3,0,0,1,1,1,2,1,3,1}, {0,0,0,1,0,2,0,3,1,3}}
    };

    /**
     * colours of piece of all the types, with the order of "a,b,c.....i" and "0,1,2,3"
     */
    public static Colours[][][] colourMapOffset = new Colours[][][]{
            {{GREEN,WHITE,RED,RED},           {GREEN,RED,WHITE,RED},          {RED,RED,WHITE,GREEN},          {RED,WHITE,RED,GREEN}},
            {{BLUE,GREEN,GREEN,WHITE,WHITE}, {WHITE,WHITE,BLUE,GREEN,GREEN}, {WHITE,WHITE,GREEN,GREEN,BLUE}, {GREEN,GREEN,BLUE,WHITE,WHITE}},
            {{GREEN,RED,RED,WHITE,BLUE},     {RED,RED,WHITE,GREEN,BLUE},      {BLUE,WHITE,RED,RED,GREEN},     {BLUE,GREEN,WHITE,RED,RED}},
            {{RED,RED,RED,BLUE},             {RED,RED,BLUE,RED},               {BLUE,RED,RED,RED},              {RED,BLUE,RED,RED}},
            {{BLUE,BLUE,BLUE,RED,RED},       {RED,BLUE,RED,BLUE,BLUE},         {RED,RED,BLUE,BLUE,BLUE},       {BLUE,BLUE,RED,BLUE,RED}},
            {{WHITE,WHITE,WHITE},            {WHITE,WHITE,WHITE},              {WHITE,WHITE,WHITE},             {WHITE,WHITE,WHITE}},
            {{WHITE,BLUE,BLUE,WHITE},        {WHITE,BLUE,BLUE,WHITE},          {WHITE,BLUE,BLUE,WHITE},         {WHITE,BLUE,BLUE,WHITE}},
            {{RED,GREEN,GREEN,WHITE,WHITE},  {WHITE,WHITE,RED,GREEN,GREEN},   {WHITE,WHITE,GREEN,GREEN,RED},   {GREEN,GREEN,RED,WHITE,WHITE}},
            {{BLUE,BLUE,WHITE},               {BLUE,WHITE,BLUE},                {WHITE,BLUE,BLUE},               {BLUE,WHITE,BLUE}},
            {{GREEN,GREEN,WHITE,RED,GREEN},  {GREEN,GREEN,GREEN,WHITE,RED},    {GREEN,RED,WHITE,GREEN,GREEN},  {RED,WHITE,GREEN,GREEN,GREEN}}
    };

    public static void main(String[] args) {
        Location[] l = B.createPiece(0,2,EAST);
        for (Location i:l) {
          System.out.println(i.getX()+"|"+i.getY());
        }
    }
}
