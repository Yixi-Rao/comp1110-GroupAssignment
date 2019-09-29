package comp1110.ass2.gui;

//import com.sun.glass.ui.View;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

/**
 * A very simple viewer for piece placements in the IQ-Focus game.
 * <p>
 * NOTE: This class is separate from your main game class.  This
 * class does not play a game, it just illustrates various piece
 * placements.
 */
public class Viewer extends Application {

    /* board layout */
    private static final int SQUARE_SIZE = 60;
    private static final int VIEWER_WIDTH = 720;
    private static final int VIEWER_HEIGHT = 480;

    // External frame line of the game area
    private static final double EX_FRAME_X = SQUARE_SIZE * 1.5;
    private static final double EX_FRAME_Y = SQUARE_SIZE;
    private static final double EX_FRAME_LEN_X = SQUARE_SIZE * 9.0;
    private static final double EX_FRAME_LEN_Y = SQUARE_SIZE * 5.0;
    private static final int EX_FRAME_STROKE = 2; //
    private static final Color EX_FRAME_COLOR = Color.BLACK; // colour of the frame line
    private static final Color EX_FRAME_FILL = Color.TRANSPARENT; // colour inside the box

    // Internal frame line of the game area (red line)
    private static final double IN_FRAME_X = EX_FRAME_X + SQUARE_SIZE * 3.0;
    private static final double IN_FRAME_Y = EX_FRAME_Y + SQUARE_SIZE * 1.0;
    private static final double IN_FRAME_LEN_X = SQUARE_SIZE * 3.0;
    private static final double IN_FRAME_LEN_Y = SQUARE_SIZE * 3.0;
    private static final int IN_FRAME_STROKE = 2;
    private static final Color IN_FRAME_COLOR = Color.RED;
    private static final Color IN_FRAME_FILL = Color.TRANSPARENT;

    // Square lines inside the game area
    private static final int LINE_STROKE = 1;
    private static final Color LINE_COLOR = Color.GREY;

    // Resource location
    private static final String URI_BASE = "assets/";

    // Group setting
    private final Group root = new Group();
    private final Group graphs = new Group();
    private final Group layouts = new Group();
    private final Group controls = new Group();

    // Text field
    private TextField textField;

    // Map for piece size, form as - pieceType: [pieceWidth, pieceHeight]
    private final Map<String, double[]> pieceSizes = new HashMap<>() {{
        put("a", new double[] {SQUARE_SIZE * 3.0, SQUARE_SIZE * 2.0});
        put("b", new double[] {SQUARE_SIZE * 4.0, SQUARE_SIZE * 2.0});
        put("c", new double[] {SQUARE_SIZE * 4.0, SQUARE_SIZE * 2.0});
        put("d", new double[] {SQUARE_SIZE * 3.0, SQUARE_SIZE * 2.0});
        put("e", new double[] {SQUARE_SIZE * 3.0, SQUARE_SIZE * 2.0});
        put("f", new double[] {SQUARE_SIZE * 3.0, SQUARE_SIZE * 1.0});
        put("g", new double[] {SQUARE_SIZE * 3.0, SQUARE_SIZE * 2.0});
        put("h", new double[] {SQUARE_SIZE * 3.0, SQUARE_SIZE * 3.0});
        put("i", new double[] {SQUARE_SIZE * 2.0, SQUARE_SIZE * 2.0});
        put("j", new double[] {SQUARE_SIZE * 4.0, SQUARE_SIZE * 2.0});
    }};

    private Line drawLine(double startX, double startY, double endX, double endY) {
        Line line = new Line(startX, startY, endX, endY);
        line.setStroke(LINE_COLOR);
        line.setStrokeWidth(LINE_STROKE);
        return line;
    }

    private Rectangle drawRectangle(double startX, double startY, double sideLenX, double sideLenY,
                                    int strokeWidth, Color strokeColor, Color fillColor) {
        Rectangle rectangle = new Rectangle(startX, startY, sideLenX, sideLenY);
        rectangle.setFill(fillColor);
        rectangle.setStroke(strokeColor);
        rectangle.setStrokeWidth(strokeWidth);
        return rectangle;
    }

    void drawGameArea() {
        // Draw external frame line
        Rectangle externalFrame = drawRectangle(
                EX_FRAME_X, EX_FRAME_Y, EX_FRAME_LEN_X, EX_FRAME_LEN_Y,
                EX_FRAME_STROKE, EX_FRAME_COLOR, EX_FRAME_FILL);
        layouts.getChildren().add(externalFrame);

        // Draw internal frame line
        Rectangle internalFrame = drawRectangle(
                IN_FRAME_X, IN_FRAME_Y, IN_FRAME_LEN_X, IN_FRAME_LEN_Y,
                IN_FRAME_STROKE, IN_FRAME_COLOR, IN_FRAME_FILL);
        layouts.getChildren().add(internalFrame);

        // Draw horizontal checker lines inside game area
        double endX = EX_FRAME_X + EX_FRAME_LEN_X; // end line x
        for (int index = 1; index < 5; index++) {
            double y = EX_FRAME_Y + SQUARE_SIZE * index;
            Line line = drawLine(EX_FRAME_X, y, endX, y);
            layouts.getChildren().addAll(line);
        }

        // Draw vertical checker lines inside game area
        double endY = EX_FRAME_Y + EX_FRAME_LEN_Y;
        for (int index = 1; index < 10; index++) {
            double x = EX_FRAME_X + SQUARE_SIZE * index;
            Line line = drawLine(x, EX_FRAME_Y, x, endY);
            layouts.getChildren().addAll(line);
        }
    }

    /**
     * Draw a placement in the window, removing any previously drawn one
     *
     * @param placement A valid placement string
     */
    void makePlacement(String placement) {
        graphs.getChildren().clear();
        if (!placement.isBlank()) {
            String[] pieces = placement.split("(?<=\\G.{4})");
            for (String piece: pieces) {
                System.out.println(piece);
                String pieceName = piece.substring(0, 1);
                int pieceX = Integer.parseInt(piece.substring(1, 2));
                int pieceY = Integer.parseInt(piece.substring(2, 3));
                int pieceOri = Integer.parseInt(piece.substring(3));

                double pieceWidth = pieceSizes.get(pieceName)[0]; // the width of the piece
                double pieceHeight = pieceSizes.get(pieceName)[1]; // the Height of the piece

                double pieceStartX = EX_FRAME_X + SQUARE_SIZE * pieceX; // 90 + 60 * x
                double pieceStartY = EX_FRAME_Y + SQUARE_SIZE * pieceY; // 60 + 60 * y
                if (pieceOri % 2 == 1) { // Spherical Coordinate Transform (when pieceOri == 1 or 3)
                    pieceStartX -= (pieceWidth - pieceHeight) / 2.0;
                    System.out.println(pieceStartX);
                    pieceStartY += (pieceWidth - pieceHeight) / 2.0;
                    System.out.println(pieceStartY);
                }

                String piecePath = Viewer.class.getResource(URI_BASE + pieceName + "-0.png").toString();
                ImageView pieceView = new ImageView(new Image(piecePath));

                pieceView.setFitWidth(pieceWidth);
                pieceView.setFitHeight(pieceHeight);
                pieceView.setRotate(90.0 * pieceOri);

                pieceView.setX(pieceStartX);
                pieceView.setY(pieceStartY);

                graphs.getChildren().add(pieceView);
            }
        }
    }

    /**
     * Create a basic text field for input and a refresh button.
     */
    private void makeControls() {
        Label label1 = new Label("Placement:");
        textField = new TextField();
        textField.setPrefWidth(300); // length of the textbox
        Button button = new Button("Refresh");
        button.setOnAction(event -> {
            makePlacement(textField.getText());
            textField.clear();
        });
        HBox hb = new HBox();
        hb.getChildren().addAll(label1, textField, button);
        hb.setSpacing(10);
        hb.setLayoutX(130);
        hb.setLayoutY(VIEWER_HEIGHT - 50);
        controls.getChildren().add(hb);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("FocusGame Viewer");
        Scene scene = new Scene(root, VIEWER_WIDTH, VIEWER_HEIGHT);

        root.getChildren().add(layouts);
        root.getChildren().add(controls);

        drawGameArea();
        makeControls();

        root.getChildren().add(graphs);

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
