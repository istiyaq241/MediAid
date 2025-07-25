// Line 1
package mediAid; // Line 2

// Line 3
import javafx.application.Application;       // Line 4
import javafx.scene.Scene;                   // Line 5
import javafx.scene.control.Label;           // Line 6
import javafx.scene.layout.VBox;             // Line 7
import javafx.stage.Stage;                   // Line 8

// Line 9
public class Main extends Application {      // Line 10

    @Override                                // Line 11
    public void start(Stage primaryStage) {  // Line 12
        // Temporary homepage UI              // Line 13
        Label welcome = new Label("Welcome to MediAid"); // Line 14

        VBox root = new VBox(10);            // Line 15
        root.getChildren().add(welcome);     // Line 16

        Scene scene = new Scene(root, 400, 200);         // Line 17
        primaryStage.setTitle("MediAid System");         // Line 18
        primaryStage.setScene(scene);                   // Line 19
        primaryStage.show();                             // Line 20
    }                                                    // Line 21

    public static void main(String[] args) {             // Line 22
        launch(args);                                    // Line 23
    }                                                    // Line 24
}                                                        // Line 25
