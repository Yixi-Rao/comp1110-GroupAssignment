package comp1110.ass2;

public class Game {
    private Challenge challenge;
    private Colours[][] gameStates;

    private Piece[][] pieces = new Piece[5][9];

    public Game(Challenge challenge) {this.challenge = challenge;}

    public void expectedGameState(String challenge){}

}
