package comp1110.ass2;

import java.util.Set;
/**
 * This class provides the text interface for the IQ Focus Game
 * <p>
 * The game is based directly on Smart Games' IQ-Focus game
 * (https://www.smartgames.eu/uk/one-player-games/iq-focus)
 */
public class FocusGame {

    public Colours[][] gameStates = {
            {null,null,null,null,null,null,null,null,null},
            {null,null,null,null,null,null,null,null,null},
            {null,null,null,null,null,null,null,null,null},
            {null,null,null,null,null,null,null,null,null},
            {null,null,null,null,null,null,null,null,null}
    };

    public static Piece[][] pieces = new Piece[5][9];


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
    public static boolean isPieceOnBoard(String piece){
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

    public static boolean isPieceOverlap(String piece) {
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

    public static void addPiece(Piece piece){
        Location[] locations = piece.getPiecesType().createPiece(piece.getLocation().getX(),piece.getLocation().getY(),piece.getOrientation());
        for (Location l:locations){
            pieces[l.getY()][l.getX()] = piece;

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
        // FIXME Task 6: determine the set of all viable piece placements given existing placements and a challenge
        return null;
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
    }
}
