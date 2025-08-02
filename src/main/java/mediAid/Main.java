package main.java.mediAid;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        Label welcome = new Label("Welcome to MediAid");

        VBox root = new VBox(10);
        root.getChildren().add(welcome);

        Scene scene = new Scene(root, 400, 200);
        primaryStage.setTitle("MediAid System");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

//syncing new devie
