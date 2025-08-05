package model;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;

public class ReminderTicker {

    public static void start(VBox reminderPanel) {
        Queue<String> queue = new LinkedList<>(ReminderManager.getActiveReminders(259200));

        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                if (!queue.isEmpty()) {
                    String next = queue.poll();
                    Platform.runLater(() -> {
                        reminderPanel.getChildren().clear();
                        Label label = new Label(next);
                        label.setWrapText(true);
                        label.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
                        reminderPanel.getChildren().add(label);
                    });
                }
            }
        }, 0, 6000);
    }
}

