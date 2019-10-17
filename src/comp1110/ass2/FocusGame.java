package comp1110.ass2;

import java.util.*;

/**
 * This class provides the text interface for the IQ Focus Game
 * <p>
 * The game is based directly on Smart Games' IQ-Focus game
 * (https://www.smartgames.eu/uk/one-player-games/iq-focus)
 */
public class FocusGame {
    /*
    authorship details:  written by the Group
     */

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
     * it will store the valid central location
     */
    public static Set<String> validCentralPieces = new HashSet<>();

    /*
    authorship details:  written by the Wenxuan Li
     */
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

    /*
    authorship details:  written by the Wenxuan Li
     */
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

    /*
    authorship details:  written by the Wenxuan Li and yixi rao
     */
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
        pieces = new Piece[5][9];
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

    /*
    authorship details:  written by the Wenxuan Li and yixi rao
     */
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

    /*
    authorship details:  written by the Wenxuan Li and yixi rao
     */
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
    /*
    authorship details:  written by the Wenxuan Li and yixi rao
     */
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
    /*
    authorship details:  written by the Wenxuan Li and yixi rao
     */
    /**
     * Add all the piece on to the board and update the game state.
     * @param placement all pieces that will be added
     */
    public static void addAllPiece(String placement){
        for (int i = 0;i < placement.length()/4;i++) {
            String subpiece = placement.substring(4 * i, 4 + (4 * i));
            addPiece(new Piece(subpiece));
        }
    }

    /*
    authorship details:  written by the yixi rao
     */
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

    /*
    authorship details:  written by the yixi rao
     */
    /**
     * Given a string describing a placement of pieces and a string describing
     * a challenge, return a set of all possible next viable piece placements
     * which cover a specific board location.
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
     * @param col      The location's column.
     * @param row      The location's row.
     * @return A set of viable piece placements, or null if there are none.
     */
    static Set<String> getViablePiecePlacements(String placement, String challenge, int col, int row) {
        setChallenge(challenge);    //first add the challenge to the game state so we have a criteria to test
        Set<String> result = new HashSet<>();   //answer set of viable piece
        for (int y:Yrange){                 //for all the x on the board try to find a piece
            if (Math.abs(y - row) > 3)
                continue;
            for (int x:Xrange){         //for all the y on the board try to find a piece
                if (x == 0 && y== 4)
                    continue;
                if (Math.abs(x - col) > 3)
                    continue;
                for (String type:refineTypes(placement)){    //for all the types on the board try to find a piece
                    for (String orientation:orientations){
                        if (isPlacementStringValid(placement + type + "" + x + "" + y + "" + orientation) && isOccupyGrid(col,row,new Piece(type + "" + x + "" + y + "" + orientation)) && isValidColour(new Piece(type + "" + x + "" + y + "" + orientation)) ) {
                            result.add(type + "" + x + "" + y + "" + orientation);
                        }
                    }
                }
            }
        }
        pieces = new Piece[5][9];
        gameStatesColour = new Colours[5][9];
        if (result.isEmpty())
            return null;
        return result;
    }// FIXME Task 6: determine the set of all viable piece placements given existing placements and a challenge

    /*
    authorship details:  written by the yixi rao
     */
    /**
     * function which will reduce the number of the usable types (some types is already used)
     * @param placement pieces that is already placed on the board
     * @return all types of the pieces which is not used
     */
    public static List<String> refineTypes(String placement){
        List<String> typeList = new ArrayList<>(Arrays.asList(types));
        List<String> refineList = new ArrayList<>(Arrays.asList(types));
        for (int i = 0;i < placement.length()/4;i++){
            if (typeList.contains(placement.charAt(i*4)+"")){
                refineList.remove(placement.charAt(i*4)+"");
            }
        }
        return refineList;
    }


    /*
    authorship details:  written by the yixi rao
     */
    /**
     * update the challenge colour to the board
     * @param challenge the colour challenge string
     */
    public static void setChallenge(String challenge){
        gameStatesColour = new Colours[5][9];
        for (int i = 0 ;i<challenge.length();i++){
            gameStatesColour[centralLocation[i].getY()][centralLocation[i].getX()] = Colours.toColour(challenge.charAt(i));
        }
    }

    /*
    authorship details:  written by the yixi rao
     */
    /**
     * Determine whether a colour is not against the challenge colour.
     * @param piece the piece will be test
     * @return true if it is valid false otherwise
     */
    public static Boolean isValidColour(Piece piece){
        Location[] locations = piece.getPiecesType().createPiece(piece.getLocation().getX(),piece.getLocation().getY(),piece.getOrientation());
        Colours[] colours = piece.getColous();
        for (int i = 0;i < locations.length;i++){
            if (gameStatesColour[locations[i].getY()][locations[i].getX()] != null && gameStatesColour[locations[i].getY()][locations[i].getX()] != colours[i]){
                return false;}
        }
        return true;
    }


    /*
    authorship details:  written by the yixi rao
     */
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


    /*
    authorship details:  written by the yixi rao
     */
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
        validCentralPieces.clear();
        findCentralPieces("",challenge,3,1); // which will first fills the central nine cell or location and return the placement
        String answer = "";

        for (String placement:validCentralPieces){
            if ((getValidPlacementString(placement,challenge,0,0)).length() == 40){
                answer =  getValidPlacementString(placement,challenge,0,0); //find the answer and return
                break;
            }
        }
        String[] sortedPieces = new String[10];
        // answer is found and we need to reorder the answer by sorting than (a...j)
        for (int i = 0;i < answer.length()/4;i++) {
            String subpiece = answer.substring(4 * i, 4 + (4 * i));
            if (subpiece.charAt(3) - '0' > 1 && (subpiece.charAt(0) == 'f'|| subpiece.charAt(0) == 'g'))
               subpiece = subpiece.substring(0,3) +  ((subpiece.charAt(3) - '0')-2);
            int index = subpiece.charAt(0) - 'a';
            sortedPieces[index] = subpiece;
        }
        String realAnswer = "";
        for (String p:sortedPieces){
            realAnswer = realAnswer + p;
        }
        // FIXME Task 9: determine the solution to the game, given a particular challenge
        return realAnswer;
    }


    /*
    authorship details:  written by the yixi rao
     */
    /**
     * given a challenge and some placement find the valid placement string that satisfy the challenge and it is all valid
     * @param placement placement that is already on the board
     * @param challenge challenge we are going to achieve
     * @param x location x which it must be occupied
     * @param y location y which it must be occupied
     * @return the partial correct or total correct placement
     */
    public static String getValidPlacementString(String placement,String challenge,int x,int y){
        String result = placement;
        addAllPiece(placement);

        if (result.length() == 40){   //if the placement contains 10 pieces and it will be return as final correct answer
            pieces = new Piece[5][9];
            return result;
        }
        while (pieces[y][x] != null){   //if the loication is already occupied by some pieces than it will be skipped
            Location nextP1 = nextPoint(x,y);
            x = nextP1.getX();y = nextP1.getY();
        }
        Set<String> viablePieces = getViablePiecePlacements(placement,challenge,x,y);  // find the set of valid pieces of a particular location
        if (viablePieces == null){   // it is null means it is a dead cell and this a optimisation
            pieces = new Piece[5][9];
            return "";
        }

        for (String piece:viablePieces){
            result = getValidPlacementString(placement + piece,challenge,x,y);
            if (result.length() == 40){  //jump out of the loop if we find the solution
                pieces = new Piece[5][9];
                return result;}
            pieces = new Piece[5][9];
        }
        return result;
    }

    /*
    authorship details:  written by the yixi rao
     */
    /**
     * a recursive method which will find all the combination of all the valid  
     * @param placement placement that is already on the board
     * @param challenge challenge we are going to achieve
     * @param x location x which it must be occupied
     * @param y location y which it must be occupied
     * @return the partial correct or total correct placement which just occupy the central nine cell or location
     */
    public static String findCentralPieces(String placement,String challenge,int x,int y){
        String result = placement;
        addAllPiece(placement);
        if (isCentreAllOccupied()){  //if the placement occupies all the nine central cells and it will stop recurring
            validCentralPieces.add(result);
            return result;
        }

        while (pieces[y][x] != null){  //if the location is already occupied by some pieces than it will be skipped
            Location nextP1 = nextCentralPoint(x,y);
            x = nextP1.getX();y = nextP1.getY();
        }
        if (x == 6 && y == 3){ //meet the final location of the central location
            pieces = new Piece[5][9];
            return "";
        }
        Set<String> viablePieces = getViablePiecePlacements(placement,challenge,x,y);
        if (viablePieces == null){ // it is null means it is a dead cell and this a optimisation
            pieces = new Piece[5][9];
            return "";
        }

        for (String piece:viablePieces){// next recursion
            result = findCentralPieces(placement + piece,challenge,x,y);
            pieces = new Piece[5][9];
        }
        return result;
    }


    /*
    authorship details:  written by the yixi rao
     */
    /**
     * this will determine whether the all central location is occupied or not
     * @return  true if all the nine location is occupied, false otherwise
     */
    public static boolean isCentreAllOccupied(){
        for (Location l:centralLocation){
            if (pieces[l.getY()][l.getX()] == null)
                return false;
        }
        return true;
    }

    /*
    authorship details:  written by the yixi rao
     */
    /**
     * find the next point of the central location, from left to right and up to down
     * @param x current X location
     * @param y current Y location
     * @return next Location
     */
    public static Location nextCentralPoint(int x,int y){
        if (x == 5 && y == 3){
            return new Location(6,3);
        }
        int newX,newY = y;
        if (x == 5){
            newX = 3;
            if (y != 3)
                newY = y + 1;
        }else
            newX = x + 1;
        return new Location(newX,newY);
    }

    /*
    authorship details:  written by the yixi rao
     */
    /**
     *  find the next point with respect to current location from left to right and up to down
     * @param x current X location
     * @param y current Y location
     * @return next Location
     */
    public static Location nextPoint(int x,int y){
        int newX,newY = y;
        if (x == 8){
            newX = 0;
            if (y != 4)
                newY = y + 1;
        }else
            newX = x + 1;
        if (newX == 0 && newY == 4){
            newX = 1;newY = 4;
        }
        return new Location(newX,newY);
    }


    public static void main(String[] args) {
        pieces = new Piece[5][9];
        validCentralPieces.clear();
        System.out.println(findCentralPieces("","BGGWGGGWB",3,1));
        System.out.println(validCentralPieces);
        //System.out.println(getSolution("BGGWGGGWB"));
        //System.out.println(getValidPlacementString("g201f411b513h323","BWGGWGGWB",0,0));
        //System.out.println(getSolution("BRBBRBBWB"));


        //task 6
        //System.out.println(isPlacementStringValid("g201f411b513h323a000") && isOccupyGrid(0,0,new Piece("a000")) && isValidColour(new Piece("a000")));
        //System.out.println(getViablePiecePlacements("b210","BGGWGGGWB",4,2));
        //System.out.println(isPlacementStringValid("g201f411b513h323a000"));
        //System.out.println(isOccupyGrid(0,0,new Piece("a000")));
        //System.out.println(isValidColour(new Piece("a000")));
        //[i201j410f122a520c130g432, i201j410f122a520c130g430, i201j410f120a520c130g430, i201j410f120a520c130g432, i202j410h103a520c130g430, i303j410h103a520c130g430, i210h201j500b512c130g432, i303j410h103a520c130g432, d100j410i223a520c130g430, i210h201j500b512c130g430, d100j410i223a520c130g432, i202j410h103a520c130g432, i210h201j500b512a122g430, i210h201j500a520c130g432, i210h201b502a520c130g430, i210h201j500b512a122g432, b210h402c130g430, i210h201j500a520c130g430, b210h402c130g432, i201j410h103a520c130g432, b102j410i223a520c130g432, i210h201b502a520c130g432, b102j410i223a520c130g430, i210j410a520c130g430, i210j410a520c130g432, i201j410h103a520c130g430, e102j410b022a520c130g430, i303j410b022a520c130g432, e102j410b022a520c130g432, i303j410b022a520c130g430, b102j410f122a520c130g432, b102j410f122a520c130g430, b102j410f120a520c130g432, i202j410b022a520c130g430, i202j410b022a520c130g432, b102j410f120a520c130g430, d100j410f120a520c130g430, i210h201j510c130g430, i210h201j510a122g430, d100j410f122a520c130g430, i210h201j510c130g432, d100j410f122a520c130g432, i210h201j510a122g432, d100j410f120a520c130g432, e102j410f120a520c130g432, i201j410b022a520c130g432, i202j410f122a520c130g430, i201j410b022a520c130g430, e102j410f122a520c130g432, i303j410f120a520c130g432, i202j410f120a520c130g430, e102j410f122a520c130g430, i202j410f120a520c130g432, i303j410f120a520c130g430, e102j410i223a520c130g430, e102j410f120a520c130g430, e102j410i223a520c130g432, i202j410f122a520c130g432, d100j410b022a520c130g430, d100j410b022a520c130g432, i303j410f122a520c130g432, i303j410f122a520c130g430]
    }
}
