package comp1110.ass2.gui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
/**
 * A very simple viewer for piece placements in the IQ-Focus game.
 * <p>
 * NOTE: This class is separate from your main game class.  This
 * class does not play a game, it just illustrates various piece
 * placements.
 */
public class Viewer extends Application {
    Image a0 = new Image("comp1110/ass2/gui/assets/a0.png");
    Image a1 = new Image("comp1110/ass2/gui/assets/a1.png");
    Image a2 = new Image("comp1110/ass2/gui/assets/a2.png");
    Image a3 = new Image("comp1110/ass2/gui/assets/a3.png");
    Image b0 = new Image("comp1110/ass2/gui/assets/b0.png");
    Image b1 = new Image("comp1110/ass2/gui/assets/b1.png");
    Image b2 = new Image("comp1110/ass2/gui/assets/b2.png");
    Image b3 = new Image("comp1110/ass2/gui/assets/b3.png");
    Image c0 = new Image("comp1110/ass2/gui/assets/c0.png");
    Image c1 = new Image("comp1110/ass2/gui/assets/c1.png");
    Image c2 = new Image("comp1110/ass2/gui/assets/c2.png");
    Image c3 = new Image("comp1110/ass2/gui/assets/c3.png");
    Image d0 = new Image("comp1110/ass2/gui/assets/d0.png");
    Image d1 = new Image("comp1110/ass2/gui/assets/d1.png");
    Image d2 = new Image("comp1110/ass2/gui/assets/d2.png");
    Image d3 = new Image("comp1110/ass2/gui/assets/d3.png");
    Image e0 = new Image("comp1110/ass2/gui/assets/e0.png");
    Image e1 = new Image("comp1110/ass2/gui/assets/e1.png");
    Image e2 = new Image("comp1110/ass2/gui/assets/e2.png");
    Image e3 = new Image("comp1110/ass2/gui/assets/e3.png");
    Image f0 = new Image("comp1110/ass2/gui/assets/f0.png");
    Image f1 = new Image("comp1110/ass2/gui/assets/f1.png");
    Image f2 = new Image("comp1110/ass2/gui/assets/f2.png");
    Image f3 = new Image("comp1110/ass2/gui/assets/f3.png");
    Image g0 = new Image("comp1110/ass2/gui/assets/g0.png");
    Image g1 = new Image("comp1110/ass2/gui/assets/g1.png");
    Image g2 = new Image("comp1110/ass2/gui/assets/g2.png");
    Image g3 = new Image("comp1110/ass2/gui/assets/g3.png");
    Image h0 = new Image("comp1110/ass2/gui/assets/h0.png");
    Image h1 = new Image("comp1110/ass2/gui/assets/h1.png");
    Image h2 = new Image("comp1110/ass2/gui/assets/h2.png");
    Image h3 = new Image("comp1110/ass2/gui/assets/h3.png");
    Image i0 = new Image("comp1110/ass2/gui/assets/i0.png");
    Image i1 = new Image("comp1110/ass2/gui/assets/i1.png");
    Image i2 = new Image("comp1110/ass2/gui/assets/i2.png");
    Image i3 = new Image("comp1110/ass2/gui/assets/i3.png");
    Image j0 = new Image("comp1110/ass2/gui/assets/j0.png");
    Image j1 = new Image("comp1110/ass2/gui/assets/j1.png");
    Image j2 = new Image("comp1110/ass2/gui/assets/j2.png");
    Image j3 = new Image("comp1110/ass2/gui/assets/j3.png");

    /* board layout */
    private static final int SQUARE_SIZE = 60;
    private static final int VIEWER_WIDTH = 720;
    private static final int VIEWER_HEIGHT = 480;

    private static final String URI_BASE = "assets/";

    private final Group root = new Group();
    private final Group controls = new Group();
    private TextField textField;

    /**
     * Draw a placement in the window, removing any previously drawn one
     *
     * @param placement A valid placement string
     */
    void makePlacement(String placement) {
        // FIXME Task 4: implement the simple placement viewer
    }

    /**
     * Create a basic text field for input and a refresh button.
     */
    private void makeControls() {
        Label label1 = new Label("Placement:");
        textField = new TextField();
        textField.setPrefWidth(300);
        Button button = new Button("Refresh");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                makePlacement(textField.getText());
                textField.clear();
            }
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

        root.getChildren().add(controls);

        makeControls();

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
