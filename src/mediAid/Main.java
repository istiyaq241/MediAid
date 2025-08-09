package mediAid;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    private static Stage primaryStage;
    private static String previousScene = "/view/HomePage.fxml"; // fallback default

    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Welcome.fxml"));
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.setTitle("MediAid");
        stage.setMinWidth(900);
        stage.setMinHeight(600);
        stage.sizeToScene();
        stage.show();
    }

    /** Simple scene switcher used across the app (snapshot 2 behavior). */
    public static void changeScene(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource(fxmlPath));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = getPrimaryStage();

            stage.setScene(scene);
            stage.setMinWidth(900);
            stage.setMinHeight(600);
            stage.sizeToScene();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void setPreviousScene(String path) {
        previousScene = path;
    }

    public static String getPreviousScene() {
        return previousScene;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
