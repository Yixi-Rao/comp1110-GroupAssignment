package comp1110.ass2.gui;

import comp1110.ass2.Challenge;
import comp1110.ass2.FocusGame;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class Board extends Application {

    private static final int SQUARE_SIZE = 47;      // the width of the cell

    private static final int CHESS_WIDTH = 482;     // the width of the game board
    private static final int CHESS_HEIGHT= 310;     // the height of the game board
    private static final int CHESS_X =450 ;     // the x of the board coordinate
    private static final int CHESS_Y = 209;     // the y of the board coordinate
    private static final int MARGIN_X =0;       //the x of the MARGIN
    private static final int MARGIN_Y =0;       //the y of the MARGIN
    private static final int BOARD_WIDTH = 933;         // the width of stage
    private static final int BOARD_HEIGHT = 700;        // the height of stage
    private static final int PLAY_AREA_Y = 269;          // the x of the playing area
    private static final int PLAY_AREA_X = 484;          // the y of the playing area

    private static final int CHALLENGE_X = 626;      // the x of the challenge board
    private static final int CHALLENGE_Y= 40;         // the y of the challenge board
    private static final int CHALLENGE_SQUARE = 43;     //the square size in challenge board

    private static final long ROTATION_THRESHOLD = 50;      //Allow rotation every 50 ms

    /* marker of the unplaced pieces */
    public static final String NOT_PLACED = "";

    /* the fit height and width of each different piece */
    private final Map<String, double[]> pieceSizes = new HashMap<>() {{
        put("a", new double[] {43 * 3, 43 * 2});
        put("b", new double[] {43 * 4.0, 43 * 2.0});
        put("c", new double[] {43 * 4.0, 43 * 2.0});
        put("d", new double[] {43 * 3.0, 43 * 2.0});
        put("e", new double[] {43 * 3.0, 43 * 2.0});
        put("f", new double[] {43 * 3.0, 43 * 1.0});
        put("g", new double[] {43 * 3.0, 43 * 2.0});
        put("h", new double[] {43 * 3.0, 43 * 3.0});
        put("i", new double[] {43 * 2.0, 43 * 2.0});
        put("j", new double[] {43 * 4.0, 43 * 2.0});
    }};

    /* node groups */
    private final Group root = new Group();
    private final Group gpieces = new Group();
    private final Group solution = new Group();
    private final Group Chess = new Group();
    private final Group controls = new Group();
    private final Group exposed = new Group();
    private final Group challengeGroup = new Group();


    /* where to find media assets */
    private static final String URI_BASE = "assets/";
    private static final String BASEBOARD_URI = Board.class.getResource(URI_BASE + "board.png").toString();

    /* it will store the message of each piece which is already placed to the board */
    private String[] pieceState = new String[10];

    /* the difficulty slider */
    private final Slider difficulty = new Slider();//困难的滑块

    /* Graphical representations of pieces */
    class GPiece extends ImageView {
        int pieceID;

        /**
         * Construct a particular playing piece
         *
         * @param piece The letter representing the piece to be created.
         */
        GPiece(char piece) {
            if (piece > 'j' || piece < 'a') {
                throw new IllegalArgumentException("Bad piece: \"" + piece + "\"");
            }
            this.pieceID = piece - 'a';
            setFitHeight(pieceSizes.get(piece+"")[1]);
            setFitWidth(pieceSizes.get(piece+"")[0]);
        }

        /**
         * A constructor used to build the challenge.
         *
         * @param challenge The challenge to be displayed (one of 120 objectives)
         * @param x    The x position of the challenge
         * @param y    The y position of the challenge
         */
        GPiece(int challenge,int index, int x, int y) {
//            if (!(challenge <= 80 && challenge >= 1)) {
//                throw new IllegalArgumentException("Bad challenge: \"" + challenge + "\"");
//            }
            String indexChallenge = Challenge.SOLUTIONS[challenge].objective;


                setImage(new Image(Board.class.getResource(URI_BASE + decideColour(indexChallenge.charAt(index)) + ".png").toString()));
                setFitHeight(CHALLENGE_SQUARE);
                setFitWidth(CHALLENGE_SQUARE);

                setLayoutX(x + (index % 3) * CHALLENGE_SQUARE );
                setLayoutY(y + ((index/3)%3) * CHALLENGE_SQUARE);
        }

        /**
         * decode the challenge colour to class instance colour
         * @param c the char represent the colour
         * @return the colour jpg name we want
         */
        public String decideColour(char c){
            if (c == 'R')
                return "sq-r";
            else if (c == 'B')
                return "sq-b";
            else if (c == 'G')
                return "sq-g";
            else
                return "sq-w";
        }



    }
    /**
     * This class extends piece with the capacity for it to be dragged and dropped,
     * and snap-to-grid.
     */
    class DraggablePiece extends GPiece {
        int homeX, homeY;                                     // the position in the window where the piece should be when not on the board
        double mouseX, mouseY;                               // the last known mouse positions (used when dragging)
        Image[] images = new Image[4];
        int orientation;                                     // 0=North... 3=West
        long lastRotationTime = System.currentTimeMillis();// only allow rotation every ROTATION_THRESHOLD (ms) This caters for mice which send multiple scroll events per tick.
        char id;

        /**
         * Construct a draggable piece
         *
         * @param piece The piece identifier ('a' - 'i')
         */
        DraggablePiece(char piece) {
            super(piece);
            this.id = piece;
            for (int i = 0; i < 4; i++) {   // create the four direction image of one piece
                char idx = (char) (i + '0');
                images[i] = new Image(Board.class.getResource(URI_BASE + piece + "-" + idx + ".png").toString());
            }
            //set up home location
            setImage(images[0]);
            orientation = 0;
            pieceState[piece - 'a'] = NOT_PLACED;
            homeX = MARGIN_X + ((piece - 'a') % 2) * SQUARE_SIZE * 4;
            setLayoutX(homeX);
            homeY = MARGIN_Y + (((piece - 'a') / 2) % 5) * SQUARE_SIZE * 3;
            setLayoutY(homeY);

            /* event handlers */
            setOnScroll(event -> {            // scroll to change orientation
                    if (System.currentTimeMillis() - lastRotationTime > ROTATION_THRESHOLD){
                        lastRotationTime = System.currentTimeMillis();
                        rotate();
                        event.consume();
                    }
                });
            setOnMousePressed(event -> {      // mouse press indicates begin of drag
                    mouseX = event.getSceneX();//Returns horizontal position of the event
                    mouseY = event.getSceneY();
                });
            setOnMouseDragged(event -> {      // mouse is being dragged
                    toFront();
                    double movementX = event.getSceneX() - mouseX;//movement of mouse
                    double movementY = event.getSceneY() - mouseY;
                    setLayoutX(getLayoutX() + movementX);
                    setLayoutY(getLayoutY() + movementY);
                    mouseX = event.getSceneX();
                    mouseY = event.getSceneY();
                    event.consume();
                });
            setOnMouseReleased(event -> {     // drag is complete
                    snapToGrid();
                });

        }

        /**
         * Snap the piece to the nearest grid position (if it is over the grid)
         */
        private void snapToGrid() {

            if (onBoard()) {
                if ((getLayoutX() >= (PLAY_AREA_X - (SQUARE_SIZE / 2))) && (getLayoutX() < (PLAY_AREA_X + (SQUARE_SIZE / 2)))) {
                    setLayoutX(PLAY_AREA_X);
                } else if ((getLayoutX() >= PLAY_AREA_X + (SQUARE_SIZE / 2)) && (getLayoutX() < PLAY_AREA_X + 1.5 * SQUARE_SIZE)) {
                    setLayoutX(PLAY_AREA_X + SQUARE_SIZE);
                } else if ((getLayoutX() >= PLAY_AREA_X + 1.5 * SQUARE_SIZE) && (getLayoutX() < PLAY_AREA_X + 2.5 * SQUARE_SIZE)) {
                    setLayoutX(PLAY_AREA_X + 2 * SQUARE_SIZE);
                } else if ((getLayoutX() >= PLAY_AREA_X + 2.5 * SQUARE_SIZE) && (getLayoutX() < PLAY_AREA_X + 3.5 * SQUARE_SIZE)) {
                    setLayoutX(PLAY_AREA_X + 3 * SQUARE_SIZE);
                }else if ((getLayoutX() >= PLAY_AREA_X + 3.5 * SQUARE_SIZE) && (getLayoutX() < PLAY_AREA_X + 4.5 * SQUARE_SIZE)) {
                    setLayoutX(PLAY_AREA_X + 4 * SQUARE_SIZE);
                }else if ((getLayoutX() >= PLAY_AREA_X + 4.5 * SQUARE_SIZE) && (getLayoutX() < PLAY_AREA_X + 5.5 * SQUARE_SIZE)) {
                    setLayoutX(PLAY_AREA_X + 5 * SQUARE_SIZE);
                }else if ((getLayoutX() >= PLAY_AREA_X + 5.5 * SQUARE_SIZE) && (getLayoutX() < PLAY_AREA_X + 6.5 * SQUARE_SIZE)) {
                    setLayoutX(PLAY_AREA_X + 6 * SQUARE_SIZE);
                }else if ((getLayoutX() >= PLAY_AREA_X + 6.5 * SQUARE_SIZE) && (getLayoutX() < PLAY_AREA_X + 7.5 * SQUARE_SIZE)) {
                    setLayoutX(PLAY_AREA_X + 7 * SQUARE_SIZE);
                }else if ((getLayoutX() >= PLAY_AREA_X + 7.5 * SQUARE_SIZE) && (getLayoutX() < PLAY_AREA_X + 8.5 * SQUARE_SIZE)) {
                    setLayoutX(PLAY_AREA_X + 8 * SQUARE_SIZE);
                }

                if ((getLayoutY() >= (PLAY_AREA_Y - (SQUARE_SIZE / 2))) && (getLayoutY() < (PLAY_AREA_Y + (SQUARE_SIZE / 2)))) {
                    setLayoutY(PLAY_AREA_Y);
                } else if ((getLayoutY() >= PLAY_AREA_Y + (SQUARE_SIZE / 2)) && (getLayoutY() < PLAY_AREA_Y + 1.5 * SQUARE_SIZE)) {
                    setLayoutY(PLAY_AREA_Y + SQUARE_SIZE);
                } else if ((getLayoutY() >= PLAY_AREA_Y + 1.5 * SQUARE_SIZE) && (getLayoutY() < PLAY_AREA_Y + 2.5 * SQUARE_SIZE)) {
                    setLayoutY(PLAY_AREA_Y + 2 * SQUARE_SIZE);
                }else if ((getLayoutY() >= PLAY_AREA_Y + 2.5 * SQUARE_SIZE) && (getLayoutY() < PLAY_AREA_Y + 3.5 * SQUARE_SIZE)) {
                    setLayoutY(PLAY_AREA_Y + 3 * SQUARE_SIZE);
                }else if ((getLayoutY() >= PLAY_AREA_Y + 3.5 * SQUARE_SIZE) && (getLayoutY() < PLAY_AREA_Y + 4.5 * SQUARE_SIZE)) {
                    setLayoutY(PLAY_AREA_Y + 4 * SQUARE_SIZE);
                }
                setPosition();
                if (!validPiece()){ // if is overlap or not on the board snap it to home
                    snapToHome();
                }
                    //decide x and y position
            } else {
                snapToHome();
            }
        }
        /**
         * @return true if the piece is on the board
         */
        private boolean onBoard() {
            return getLayoutX() > (PLAY_AREA_X - (SQUARE_SIZE / 2)) && (getLayoutX() < (PLAY_AREA_X + 8.5 * SQUARE_SIZE))
                    && getLayoutY() > (PLAY_AREA_Y - (SQUARE_SIZE / 2)) && (getLayoutY() < (PLAY_AREA_Y + 4.5 * SQUARE_SIZE));
        }//use function getLayoutX to get piece's location to decide whether it is available in the board


        /**
         * a function to check whether the current destination cell
         * is already occupied by another piece
         *
         * @return true if the destination cell for the current piece
         * is already occupied, and false otherwise
         */
        private boolean validPiece() {
            String placement = "";
            for (String p:pieceState){  //set up all the placement as a placement string
                placement = placement + p;
            }
            if (placement.equals(""))
                return true;
            return FocusGame.isPlacementStringValid(placement);     //use already placement string and add it to new piece
        }

        /**
         * Rotate the piece by 90 degrees and update any relevant state
         */
        private void rotate() {
            orientation = (orientation + 1) % 4; // recursion of orientation starts here
            setImage(images[(orientation)]);
            rotateSetFit(orientation);
            toFront();
            setPosition();  //modify the location and orientation
            if (!validPiece()){
                snapToHome();
            }
        }

        private void rotateSetFit(int orientation) {
            if (orientation == 1 || orientation == 3){  //if the rotation is 90 degree or 270 degree inverse the fit height and width
                setFitHeight(pieceSizes.get(this.id+"")[0]);
                setFitWidth(pieceSizes.get(this.id+"")[1]);
            } else {
                setFitHeight(pieceSizes.get(this.id+"")[1]);
                setFitWidth(pieceSizes.get(this.id+"")[0]);
            }
        }


        /**
         * Snap the piece to its home position (if it is not on the grid)
         */
        private void snapToHome() {
            setLayoutX(homeX);
            setLayoutY(homeY);
            setFitHeight(pieceSizes.get(this.id+"")[1]);
            setFitWidth(pieceSizes.get(this.id+"")[0]);
            setImage(images[0]);
            orientation = 0;
            pieceState[pieceID] = NOT_PLACED;
        }

        /**
         * Determine the grid-position of the origin of the piece
         * or 'NOT_PLACED' if it is off the grid, taking into account its rotation.
         */
        private void setPosition() {
            int x = (int) (getLayoutX() - PLAY_AREA_X) / SQUARE_SIZE;//coordinate x（0-8）
            int y = (int) (getLayoutY() - PLAY_AREA_Y) / SQUARE_SIZE;//coordinate y（0-4）
            if (x < 0)// home position
                pieceState[pieceID] = NOT_PLACED;
            else {
                pieceState[pieceID] = ((char)(pieceID+'a')+"") + x + "" + y + "" + orientation;     //set up the piece to piece state
            }
        }
    }

    /**
     * Set up the group that represents the places that make the board
     */
    private void makeChess() {
        Chess.getChildren().clear();

        ImageView baseChessboard = new ImageView();
        baseChessboard.setImage(new Image(BASEBOARD_URI));
        baseChessboard.setFitWidth(CHESS_WIDTH);
        baseChessboard.setFitHeight(CHESS_HEIGHT);
        baseChessboard.setLayoutX(CHESS_X);
        baseChessboard.setLayoutY(CHESS_Y);
        Chess.getChildren().add(baseChessboard);

        Chess.toBack();
    }

    /**
     * Set up each of the six pieces
     */
    private void makePieces() {
        gpieces.getChildren().clear();
        for (char m = 'a'; m <= 'j'; m++) {
            gpieces.getChildren().add(new DraggablePiece(m));
        }
    }

    /**
     * Add the objective to the board
     */
    private void addObjectiveToBoard(int dif) {
        challengeGroup.getChildren().clear();
        for (int i = 0;i < 9;i++){
            challengeGroup.getChildren().add(new GPiece( dif,i, CHALLENGE_X, CHALLENGE_Y));
        }

    }

    /**
     * Put all of the pieces back in their home position
     */
    private void resetPieces() {
        gpieces.toFront();
        for (Node n : gpieces.getChildren()) {
            ((DraggablePiece) n).snapToHome();
        }
    }

    /**
     * Create the controls that allow the game to be restarted and the difficulty
     * level set.
     */
    private void makeControls() {
        Button button = new Button("Restart");
        button.setLayoutX(CHESS_X +7 * SQUARE_SIZE);
        button.setLayoutY(BOARD_HEIGHT - 55);
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                newGame();
            }
        });
        controls.getChildren().add(button);

        difficulty.setMin(1);
        difficulty.setMax(5);
        difficulty.setValue(0);
        difficulty.setShowTickLabels(true);//刻度线的标签（数字什么的）
        difficulty.setShowTickMarks(true);//显示刻度线
        difficulty.setMajorTickUnit(1);//主要刻度线（显示标签的刻度线）的单位间隔
        difficulty.setMinorTickCount(0);//每两个主刻度线之间的间隔
        difficulty.setSnapToTicks(true);//是否滑动后的值与刻度线一致

        difficulty.setLayoutX(609);//CHESS_X +7 * SQUARE_SIZE - 170
        difficulty.setLayoutY(BOARD_HEIGHT - 50);
        controls.getChildren().add(difficulty);

        final Label difficultyCaption = new Label("Difficulty:");
        difficultyCaption.setTextFill(Color.GREY);
        difficultyCaption.setLayoutX(539);
        difficultyCaption.setLayoutY(BOARD_HEIGHT - 50);
        controls.getChildren().add(difficultyCaption);
    }


    /**
     * Start a new game, resetting everything as necessary
     */
    private void newGame() {
        int dif = Challenge.diffiToNum((int) (difficulty.getValue() - 1));
        makePieces();
        addObjectiveToBoard(dif);
        resetPieces();
    }

    // FIXME Task 7: Implement a basic playable Focus Game in JavaFX that only allows pieces to be placed in valid places

    // FIXME Task 8: Implement challenges (you may use challenges and assets provided for you in comp1110.ass2.gui.assets: sq-b.png, sq-g.png, sq-r.png & sq-w.png)

    // FIXME Task 10: Implement hints

    // FIXME Task 11: Generate interesting challenges (each challenge may have just one solution)

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("IQ-focus");
        Scene scene = new Scene(root, BOARD_WIDTH, BOARD_HEIGHT);

        root.getChildren().add(gpieces);
        root.getChildren().add(Chess);
        root.getChildren().add(controls);
        root.getChildren().add(challengeGroup);

        makeChess();//place a board
        makeControls();

        newGame();

        primaryStage.setScene(scene);
        primaryStage.show();

    }
}
