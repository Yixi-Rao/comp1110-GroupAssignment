package comp1110.ass2;

import java.util.HashSet;
import java.util.Set;
/**
 * This class provides the text interface for the IQ Focus Game
 * <p>
 * The game is based directly on Smart Games' IQ-Focus game
 * (https://www.smartgames.eu/uk/one-player-games/iq-focus)
 */
public class FocusGame {
    /**
     * a board with 43 grid and each cell representing a colour of part the piece
     */
    public static Colours[][] gameStatesColour = new Colours[5][9];

    /**
     * a board with 43 grid and each cell representing a location of part the piece
     */
    public static Piece[][] pieces = new Piece[5][9];

    /**
     * the nine location of centre of the board
     */
    public static final Location[] centralLocation = {new Location(3,1),new Location(4,1),new Location(5,1),
                                                        new Location(3,2),new Location(4,2),new Location(5,2),
                                                        new Location(3,3),new Location(4,3),new Location(5,3)};
    /**
     * the range or domain of types
     */
    public static final String[] types = {"a","b","c","d","e","f","g","h","i","j"};

    /**
     * the range or domain of coordinates
     */
    public static final String[] orientations = {"0","1","2","3"};

    /**
     * the range or domain of x coordinates
     */
    private static final int[] Xrange = {0,1,2,3,4,5,6,7,8};

    /**
     * the range or domain of y coordinates
     */
    private static final int[] Yrange = {0,1,2,3,4};


    /**
     * Determine whether a piece placement is well-formed according to the
     * following criteria:
     * - it consists of exactly four characters
     * - the first character is in the range a .. j (shape)
     * - the second character is in the range 0 .. 8 (column)
     * - the third character is in the range 0 .. 4 (row)
     * - the fourth character is in the range 0 .. 3 (orientation)
     *
     * @param piecePlacement A string describing a piece placement
     * @return True if the piece placement is well-formed
     */
    static boolean isPiecePlacementWellFormed(String piecePlacement) {
        if (piecePlacement.length() != 4)
            return false;
        if ( !((int)'a' <= piecePlacement.charAt(0) && piecePlacement.charAt(0) <= (int)'j'))
            return false;
        if (!((int)'0' <= piecePlacement.charAt(1) && piecePlacement.charAt(1) <= (int)'8'))
            return false;
        if (!((int)'0' <= piecePlacement.charAt(2) && piecePlacement.charAt(2) <= (int)'4'))
            return false;
        if (!((int)'0' <= piecePlacement.charAt(3) && piecePlacement.charAt(3) <= (int)'3'))
            return false;

        // FIXME Task 2: determine whether a piece placement is well-formed
        return true;
    }

    /**
     * Determine whether a placement string is well-formed:
     * - it consists of exactly N four-character piece placements (where N = 1 .. 10);
     * - each piece placement is well-formed
     * - no shape appears more than once in the placement
     *
     * @param placement A string describing a placement of one or more pieces
     * @return True if the placement is well-formed
     */
    public static boolean isPlacementStringWellFormed(String placement) {
        if (placement.length() % 4 != 0 || (4 > placement.length() || placement.length() > 40)){
            return false;}

        String type = "";
        for (int i = 0;i < placement.length()/4;i++) {
            String piece = placement.substring(4 * i, 4 + (4 * i));
            if (!isPiecePlacementWellFormed(piece))
                return false;
            type = type + piece.charAt(0);
        }
        for (int i = 0;i < type.length();i++) {
            String first = ""+type.charAt(i);
            type = type.replace(type.charAt(i)+"","@");
            if (type.contains(""+first))
                return false;
        }
        // FIXME Task 3: determine whether a placement is well-formed
        return true;


    }

    /**
     * Determine whether a placement string is valid.
     *
     * To be valid, the placement string must be:
     * - well-formed, and
     * - each piece placement must be a valid placement according to the
     *   rules of the game:
     *   - pieces must be entirely on the board
     *   - pieces must not overlap each other
     *
     * @param placement A placement string
     * @return True if the placement sequence is valid
     */
    public static boolean isPlacementStringValid(String placement) {
        if (!isPlacementStringWellFormed(placement)){
            return false;}
        for (int i = 0;i < placement.length()/4;i++) {
            String subpiece = placement.substring(4 * i, 4 + (4 * i));
            if (!isPieceOnBoard(subpiece)){
                pieces = new Piece[5][9];
                return false;}
            if (isPieceOverlap(subpiece)){
                pieces = new Piece[5][9];
                return false;}
            addPiece(new Piece(subpiece));
        }
        pieces = new Piece[5][9];
        // FIXME Task 5: determine whether a placement string is valid
        return true;
    }

    /**
     * Determine whether a piece string is on board.
     * judging by the coordinates
     * @param piece the piece will be test
     * @return true if it is on the board, false otherwise
     */
    public static boolean isPieceOnBoard(String piece){
        //all the information all piece(Location,type,orientation)
        Piece piece1 = new Piece(piece);
        int x = piece1.getLocation().getX();
        int y = piece1.getLocation().getY();
        PiecesType type = piece1.getPiecesType();
        Orientation orientation = piece1.getOrientation();
        Location[] locations = type.createPiece(x,y,orientation);

        for (Location l:locations){
            if (l.getX() < 0 || l.getX() > 8)
                return false;
            if (l.getY() < 0 || l.getY() > 4)
                return false;
            if ((l.getX() == 0 && l.getY() == 4) ||(l.getX() == 8 && l.getY() == 4))
                return false;
        }
        return true;
    }

    /**
     * Determine whether a piece string is overlap with other pieces which is alreay on the board.
     * test by comparing the piece coordinates with coordinates on the board
     * @param piece the piece will be test
     * @return true if it is overlap, false otherwise
     */
    public static boolean isPieceOverlap(String piece) {
        //all the information all piece(Location,type,orientation)
        Piece piece2 = new Piece(piece);
        int x = piece2.getLocation().getX();
        int y = piece2.getLocation().getY();
        PiecesType type = piece2.getPiecesType();
        Orientation orientation = piece2.getOrientation();
        Location[] locations = type.createPiece(x,y,orientation);

        for (Location l:locations){
            if (pieces[l.getY()][l.getX()] != null)
                return true;
        }
        return false;
    }

    /**
     * Add a piece on to the board and update the game state.
     * @param piece the piece will be added
     */
    public static void addPiece(Piece piece){
        Location[] locations = piece.getPiecesType().createPiece(piece.getLocation().getX(),piece.getLocation().getY(),piece.getOrientation());
        for (Location l:locations){
            pieces[l.getY()][l.getX()] = piece;

        }
    }

    /**
     * Add a colour on to the board and update the game state.
     * @param piece the piece will be added
     */
    public static void addColour(Piece piece){
        Location[] locations = piece.getPiecesType().createPiece(piece.getLocation().getX(),piece.getLocation().getY(),piece.getOrientation());
        Colours[] colours = piece.getColous();
        for (int i = 0;i < locations.length;i++){
            gameStatesColour[locations[i].getY()][locations[i].getX()] = colours[i];
        }
    }



    /**
     * Given a string describing a placement of pieces and a string describing
     * a challenge, return a set of all possible next viable piece placements
     * which cover a specific board cell.
     *
     * For a piece placement to be viable
     * - it must be valid
     * - it must be consistent with the challenge
     *
     * @param placement A viable placement string
     * @param challenge The game's challenge is represented as a 9-character string
     *                  which represents the color of the 3*3 central board area
     *                  squares indexed as follows:
     *                  [0] [1] [2]
     *                  [3] [4] [5]
     *                  [6] [7] [8]
     *                  each character may be any of
     *                  - 'R' = RED square
     *                  - 'B' = Blue square
     *                  - 'G' = Green square
     *                  - 'W' = White square
     * @param col      The cell's column.
     * @param row      The cell's row.
     * @return A set of viable piece placements, or null if there are none.
     */
    static Set<String> getViablePiecePlacements(String placement, String challenge, int col, int row) {
        setChallenge(challenge);    //first add the challenge to the game state so we have a criteria to test
        Set<String> result = new HashSet<>();   //answer set of viable piece
        for (int x:Xrange){                 //for all the x on the board try to find a piece
            for (int y:Yrange){             //for all the y on the board try to find a piece
                for (String type:types){    //for all the types on the board try to find a piece
                    for (String orientation:orientations){
                        if (isPlacementStringValid(placement + type + "" + x + "" + y + "" + orientation) && isValidColour(new Piece(type + "" + x + "" + y + "" + orientation)) && isOccupyGrid(col,row,new Piece(type + "" + x + "" + y + "" + orientation))) {
                            result.add(type + "" + x + "" + y + "" + orientation);
                        }
                    }
                }
            }
        }
        gameStatesColour = new Colours[5][9];
        if (result.isEmpty())
            return null;
        return result;
    }// FIXME Task 6: determine the set of all viable piece placements given existing placements and a challenge

    /**
     * update the challenge colour to the board
     * @param challenge the colour challenge string
     */
    public static void setChallenge(String challenge){
        for (int i = 0 ;i<challenge.length();i++){
            gameStatesColour[centralLocation[i].getY()][centralLocation[i].getX()] = Colours.toColour(challenge.charAt(i));
        }
    }

    /**
     * Determine whether a colour is not against the challenge colour.
     * @param piece the piece will be test
     * @return true if it is valid false otherwise
     */
    public static Boolean isValidColour(Piece piece){
        Location[] locations = piece.getPiecesType().createPiece(piece.getLocation().getX(),piece.getLocation().getY(),piece.getOrientation());
        Colours[] colours = piece.getColous();
        for (int i = 0;i < locations.length;i++){
            //System.out.println(locations.length+","+i);
            if (gameStatesColour[locations[i].getY()][locations[i].getX()] != null && gameStatesColour[locations[i].getY()][locations[i].getX()] != colours[i]){
                //System.out.println(locations[i].getY()+","+locations[i].getX()+","+colours[i]);
                return false;}
        }
        return true;
    }

    /**
     *Determine whether a piece is occupied the location we want
     * @param col x of the location we want to occupied
     * @param row y of the location we want to occupied
     * @param piece the piece will be test
     * @return true if it is occupied the piece we want false otherwise
     */
    public  static Boolean isOccupyGrid(int col,int row,Piece piece){
        Location[] locations = piece.getPiecesType().createPiece(piece.getLocation().getX(),piece.getLocation().getY(),piece.getOrientation()); //piece location
        Location want = new Location(col,row);  //initialise the wanted location
        for (Location l:locations){
            if (l.equals(want))
                return true;
        }
        return false;
    }

    /**
     * Return the canonical encoding of the solution to a particular challenge.
     *
     * A given challenge can only solved with a single placement of pieces.
     *
     * Since some piece placements can be described two ways (due to symmetry),
     * you need to use a canonical encoding of the placement, which means you
     * must:
     * - Order the placement sequence by piece IDs
     * - If a piece exhibits rotational symmetry, only return the lowest
     *   orientation value (0 or 1)
     *
     * @param challenge A challenge string.
     * @return A placement string describing a canonical encoding of the solution to
     * the challenge.
     */
    public static String getSolution(String challenge) {
        // FIXME Task 9: determine the solution to the game, given a particular challenge
        return null;
    }

    public static void main(String[] args) {
        int a = 'b' - 'a';
        System.out.println(a);

        System.out.println((char)(a+'a')+"");
        //Set<String> strings = new HashSet<>(getViablePiecePlacements("j001","RRRRRWRWW",0,2));
        //System.out.println(strings);
         //System.out.println(isPlacementStringValid("j001f021"));;
       // System.out.println(isValidColour(new Piece("b022")));
        /*
        for (String s:strings){

            System.out.println(s);

        }*/

    }
}
//[e003, c002, e001, a000, c001, e002, g003, g002, e000, g001, g000, i000, i001, i002, i003, a001, a002, c003, a003, d002, d003, b001, d000, f003, b000, d001, f002, f001, f000, j000, j001, h000, h001, j003, h002, h003, b003, b002]
//[a000, a003, b001, b003, c001, c002, d000, d002, d003, e000, e001, e003, f000, f001, f002, f003, g000, g002, h000, h001, h003, i000, i002, i003, j000, j001, j003]

// a000, a003,-------a000, a001, a002, a003
// b001, b003,-------b000, b001, b002, b003,
// c001, c002,-------c001, c002, c003,
// d000, d002, d003,------- d000, d001, d002, d003,
// e000, e001, e003, -------e000, e001, e002, e003
// f000, f001, f002, f003,-------f000, f001, f002, f003
// g000, g002,-------g000, g001, g002, g003,
// h000, h001, h003,-------h000, h001, h002, h003
// i000, i002, i003-------i000, i001, i002, i003
// j000, j001, j003-------j000, j001, j003
