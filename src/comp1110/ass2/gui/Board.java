package comp1110.ass2.gui;

import comp1110.ass2.FocusGame;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class Board extends Application {

    private static final int SQUARE_SIZE = 47; // the width of the cell

    private static final int CHESS_MARGIN = 30;//the margin of the board
    private static final int CHESS_WIDTH = 482;
    private static final int CHESS_HEIGHT= 310;
    private static final int CHESS_X =450 ;
    private static final int CHESS_Y = 209;
    private static final int MARGIN_X =0;//
    private static final int MARGIN_Y =0;//
    private static final int BOARD_WIDTH = 933;
    private static final int BOARD_HEIGHT = 700;
    private static final int PLAY_AREA_Y = 269;
    private static final int PLAY_AREA_X = 484;

    private static final long ROTATION_THRESHOLD = 50;

    public static final String NOT_PLACED = "";

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
    private final Group objective = new Group();


    /* where to find media assets */
    private static final String URI_BASE = "assets/";
    private static final String BASEBOARD_URI = Board.class.getResource(URI_BASE + "board.png").toString();

    private String[] pieceState = new String[10];

    /* Graphical representations of tiles */
    class GPiece extends ImageView {
        int pieceID;

        /**
         * Construct a particular playing tile
         *
         * @param piece The letter representing the tile to be created.
         */
        GPiece(char piece) {
            if (piece > 'j' || piece < 'a') {
                throw new IllegalArgumentException("Bad piece: \"" + piece + "\"");
            }
            this.pieceID = piece - 'a';
            setFitHeight(pieceSizes.get(piece+"")[1]);
            setFitWidth(pieceSizes.get(piece+"")[0]);
        }

    }

    class DraggablePiece extends GPiece {
        int homeX, homeY;                                     // the position in the window where the tile should be when not on the board
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
            for (int i = 0; i < 4; i++) {
                char idx = (char) (i + '0');
                images[i] = new Image(Board.class.getResource(URI_BASE + piece + "-" + idx + ".png").toString());}
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
                    double movementX = event.getSceneX() - mouseX;//move
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
                if (!validPiece()){
                    System.out.println(validPiece());
                    snapToHome();}
                    //decide x and y position
            } else {
                snapToHome();
            }
        }
        /**
         * @return true if the tile is on the board
         */
        private boolean onBoard() {
            return getLayoutX() > (PLAY_AREA_X - (SQUARE_SIZE / 2)) && (getLayoutX() < (PLAY_AREA_X + 8.5 * SQUARE_SIZE))
                    && getLayoutY() > (PLAY_AREA_Y - (SQUARE_SIZE / 2)) && (getLayoutY() < (PLAY_AREA_Y + 4.5 * SQUARE_SIZE));
        }//use function getLayoutX to get piece's location to decide whether it is available in the board


        /**
         * a function to check whether the current destination cell
         * is already occupied by another tile
         *
         * @return true if the destination cell for the current tile
         * is already occupied, and false otherwise
         */
        private boolean validPiece() {
            String placement = "";
            for (String p:pieceState){
                placement = placement + p;
            }
            System.out.println(placement);
            if (placement.equals(""))
                return true;
            return FocusGame.isPlacementStringValid(placement);
        }

        /**
         * Rotate the tile by 90 degrees and update any relevant state
         */
        private void rotate() {
            orientation = (orientation + 1) % 4; // recursion of orientation starts here
            setImage(images[(orientation)]);
            rotateSetFit(orientation);
            toFront();
            setPosition();
            if (!validPiece()){
                System.out.println(validPiece());
                snapToHome();}
        }

        private void rotateSetFit(int orientation) {
            if (orientation == 1 || orientation == 3){
                setFitHeight(pieceSizes.get(this.id+"")[0]);
                setFitWidth(pieceSizes.get(this.id+"")[1]);
            } else {
                setFitHeight(pieceSizes.get(this.id+"")[1]);
                setFitWidth(pieceSizes.get(this.id+"")[0]);
            }
        }


        /**
         * Snap the tile to its home position (if it is not on the grid)
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
         * Determine the grid-position of the origin of the tile
         * or 'NOT_PLACED' if it is off the grid, taking into account its rotation.
         */
        private void setPosition() {
            int x = (int) (getLayoutX() - PLAY_AREA_X) / SQUARE_SIZE;//coordinate x（0-4）
            int y = (int) (getLayoutY() - PLAY_AREA_Y) / SQUARE_SIZE;//coordinate y（0-3）
            if (x < 0)// home position
                pieceState[pieceID] = NOT_PLACED;
            else {
                pieceState[pieceID] = ((char)(pieceID+'a')+"") + x + "" + y + "" + orientation;
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
     * Set up each of the six tiles
     */
    private void makePieces() {
        gpieces.getChildren().clear();
        for (char m = 'a'; m <= 'j'; m++) {
            gpieces.getChildren().add(new DraggablePiece(m));
        }
    }

    /**
     * Put all of the tiles back in their home position
     */
    private void resetPieces() {
        gpieces.toFront();
        for (Node n : gpieces.getChildren()) {
            ((DraggablePiece) n).snapToHome();
        }
    }

    /**
     * Start a new game, resetting everything as necessary
     */
    private void newGame() {
        makePieces();
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

        makeChess();//place a board

        newGame();

        primaryStage.setScene(scene);
        primaryStage.show();

    }
}
