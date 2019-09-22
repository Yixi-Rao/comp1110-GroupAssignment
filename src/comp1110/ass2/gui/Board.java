package comp1110.ass2.gui;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class Board extends Application {

    private static final int SQUARE_SIZE = 50; // the width of the cell

    private static final int CHESS_MARGIN = 16;//the margin of the board
    private static final int CHESS_WIDTH = 483;
    private static final int CHESS_HEIGHT= 283;
    private static final int CHESS_X =450 ;
    private static final int CHESS_Y = 209;
    private static final int MARGIN_X =0;//
    private static final int MARGIN_Y =0;//
    private static final int BOARD_WIDTH = 933;
    private static final int BOARD_HEIGHT = 700;
    private static final int PLAY_AREA_Y = 225;
    private static final int PLAY_AREA_X = 466;

    private static final long ROTATION_THRESHOLD = 50;

    public static final char NOT_PLACED = 255;


    /* where to find media assets */
    private static final String URI_BASE = "assets/";
    private static final String BASEBOARD_URI = Board.class.getResource(URI_BASE + "baseboard.png").toString();

    char[] pieceState = new char[6];

    /* Graphical representations of tiles */
    class GPiece extends ImageView {
        int pieceID;

        /**
         * Construct a particular playing tile
         *
         * @param piece The letter representing the tile to be created.
         */
        GPiece(char piece) {
            if (piece > 'f' || piece < 'a') {
                throw new IllegalArgumentException("Bad piece: \"" + piece + "\"");
            }
            this.pieceID = piece - 'a';
            //setFitHeight(2 * SQUARE_SIZE);
            //setFitWidth(SQUARE_SIZE);       ------------------未定义：根据每个piece的宽度和长度来设置
        }

    }

    class DraggablePiece extends GPiece {
        int homeX, homeY;                                     // the position in the window where the tile should be when not on the board
        double mouseX, mouseY;                               // the last known mouse positions (used when dragging)
        Image[] images = new Image[4];
        int orientation;                                     // 0=North... 3=West
        long lastRotationTime = System.currentTimeMillis(); // only allow rotation every ROTATION_THRESHOLD (ms) This caters for mice which send multiple scroll events per tick.


        /**
         * Construct a draggable piece
         *
         * @param piece The piece identifier ('a' - 'i')
         */
        DraggablePiece(char piece) {
            super(piece);
            for (int i = 0; i < 4; i++) {
                char idx = (char) (i + '0');
                images[i] = new Image(Board.class.getResource(URI_BASE + piece + "-" + idx + ".png").toString());
                setImage(images[0]);
                orientation = 0;
                pieceState[piece - 'a'] = NOT_PLACED;
                homeX = MARGIN_X + ((piece - 'a') % 2) * SQUARE_SIZE * 4;
                setLayoutX(homeX);
                homeY = MARGIN_Y + ((piece - 'a') / 2) * 5 * SQUARE_SIZE * 3;
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
                    double movementX = event.getSceneX() - mouseX;//位移
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
        }

        /**
         * Snap the tile to the nearest grid position (if it is over the grid)
         */
        private void snapToGrid() {

            if (onBoard() && (!alreadyOccupied())) {
                if ((getLayoutX() >= (PLAY_AREA_X - (SQUARE_SIZE / 2))) && (getLayoutX() < (PLAY_AREA_X + (SQUARE_SIZE / 2)))) {
                    setLayoutX(PLAY_AREA_X);
                } else if ((getLayoutX() >= PLAY_AREA_X + (SQUARE_SIZE / 2)) && (getLayoutX() < PLAY_AREA_X + 1.5 * SQUARE_SIZE)) {
                    setLayoutX(PLAY_AREA_X + SQUARE_SIZE);
                } else if ((getLayoutX() >= PLAY_AREA_X + 1.5 * SQUARE_SIZE) && (getLayoutX() < PLAY_AREA_X + 2.5 * SQUARE_SIZE)) {
                    setLayoutX(PLAY_AREA_X + 2 * SQUARE_SIZE);
                } else if ((getLayoutX() >= PLAY_AREA_X + 2.5 * SQUARE_SIZE) && (getLayoutX() < PLAY_AREA_X + 3.5 * SQUARE_SIZE)) {
                    setLayoutX(PLAY_AREA_X + 3 * SQUARE_SIZE);
                }

                if ((getLayoutY() >= (PLAY_AREA_Y - (SQUARE_SIZE / 2))) && (getLayoutY() < (PLAY_AREA_Y + (SQUARE_SIZE / 2)))) {
                    setLayoutY(PLAY_AREA_Y);
                } else if ((getLayoutY() >= PLAY_AREA_Y + (SQUARE_SIZE / 2)) && (getLayoutY() < PLAY_AREA_Y + 1.5 * SQUARE_SIZE)) {
                    setLayoutY(PLAY_AREA_Y + SQUARE_SIZE);
                } else if ((getLayoutY() >= PLAY_AREA_Y + 1.5 * SQUARE_SIZE) && (getLayoutY() < PLAY_AREA_Y + 2.5 * SQUARE_SIZE)) {
                    setLayoutY(PLAY_AREA_Y + 2 * SQUARE_SIZE);
                }
                setPosition();//分别判断要设定的xy位置
            } else {
                snapToHome();
            }
            checkCompletion();
        }

        /**
         * Rotate the tile by 90 degrees and update any relevant state
         */
        private void rotate() {
            orientation = (orientation + 1) % 4; // 这里orientation进行递归
            setImage(images[(orientation)]);
            //setFitWidth((1 + (orientation % 2)) * SQUARE_SIZE);
            //setFitHeight((2 - (orientation % 2)) * SQUARE_SIZE); -----------未定义fit
            //根据不同的方向进行调整
            toFront();
            setPosition();
        }

        /**
         * Determine the grid-position of the origin of the tile
         * or 'NOT_PLACED' if it is off the grid, taking into account its rotation.
         */
        private void setPosition() {
            int x = (int) (getLayoutX() - PLAY_AREA_X) / SQUARE_SIZE;//棋盘坐标x（0-4）
            int y = (int) (getLayoutY() - PLAY_AREA_Y) / SQUARE_SIZE;//棋盘坐标y（0-3）
            if (x < 0)//代表在home位置
                pieceState[pieceID] = NOT_PLACED;
            else {
                char val = (char) ((y * 4 + x) * 4 + orientation);
                pieceState[pieceID] = val;
            }
        }
    }























    // FIXME Task 7: Implement a basic playable Focus Game in JavaFX that only allows pieces to be placed in valid places

    // FIXME Task 8: Implement challenges (you may use challenges and assets provided for you in comp1110.ass2.gui.assets: sq-b.png, sq-g.png, sq-r.png & sq-w.png)

    // FIXME Task 10: Implement hints

    // FIXME Task 11: Generate interesting challenges (each challenge may have just one solution)

    @Override
    public void start(Stage primaryStage) {

    }
}
