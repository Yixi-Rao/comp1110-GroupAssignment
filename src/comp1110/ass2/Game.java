package comp1110.ass2;

public class Game {
    public Challenge challenge;
    /*public Colours[][] gameStates = {
            {null,null,null,null,null,null,null,null,null},
            {null,null,null,null,null,null,null,null,null},
            {null,null,null,null,null,null,null,null,null},
            {null,null,null,null,null,null,null,null,null},
            {null,null,null,null,null,null,null,null,null}
    };

    public Piece[][] pieces = new Piece[5][9];*/

    public Game(Challenge challenge) {this.challenge = challenge;}

    public void expectedGameState(String challenge){}

    /*public static boolean isPieceOnBoard(String piece){
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

        public  Boolean isPieceOverlap(String piece) {
            Piece piece2 = new Piece(piece);
            int x = piece2.getLocation().getX();
            int y = piece2.getLocation().getY();
            PiecesType type = piece2.getPiecesType();
            Orientation orientation = piece2.getOrientation();
            Location[] locations = type.createPiece(x,y,orientation);

            for (Location l:locations){
                if (pieces[l.getY()][l.getX()] != null)
                    return false;
            }
            return true;
        }

        public void addPiece(Piece piece){
        Location[] locations = piece.getPiecesType().createPiece(piece.getLocation().getX(),piece.getLocation().getY(),piece.getOrientation());
            for (Location l:locations){
                pieces[piece.getLocation().getY()][piece.getLocation().getX()] = piece;
            }
        }*/



}
