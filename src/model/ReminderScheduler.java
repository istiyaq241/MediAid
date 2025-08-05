package model;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;
import java.util.*;

public class ReminderScheduler {
    private static final long INTERVAL_MS = 43200000;
    private static final long MAX_DURATION_SEC = 259200;
    private static final String SHOWN_FILE = "shown_reminders.txt";
    private static final Set<String> shownSet = new HashSet<>();

    public static void start() {
        loadShownReminders();

        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                List<String> reminders = ReminderManager.getActiveReminders(MAX_DURATION_SEC);

                for (String r : reminders) {
                    String id = extractReminderKey(r);
                    if (!shownSet.contains(id)) {
                        shownSet.add(id);
                        saveShownReminder(id);
                        Platform.runLater(() -> showReminderPopup(r));
                    }
                }
            }
        }, 0, INTERVAL_MS);
    }

    private static void loadShownReminders() {
        try (BufferedReader reader = new BufferedReader(new FileReader(SHOWN_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                shownSet.add(line.trim());
            }
        } catch (IOException ignored) {}
    }

    private static void saveShownReminder(String id) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(SHOWN_FILE, true))) {
            writer.write(id);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String extractReminderKey(String message) {
        if (message.contains("Reminder:")) {
            return message.replace("Reminder: ", "").split("\\(")[0].trim();
        } else if (message.contains("⛔")) {
            return message.replace("⛔", "").split(":")[0].trim();
        }
        return message.trim();
    }

    private static void showReminderPopup(String message) {
        Stage popup = new Stage();
        popup.initModality(Modality.APPLICATION_MODAL);
        popup.setTitle("⏰ MediAid Reminder");

        Label msgLabel = new Label(message);
        msgLabel.setFont(new Font("Arial", 16));
        msgLabel.setWrapText(true);
        msgLabel.setTextFill(message.contains("⛔") ? Color.RED : Color.DARKBLUE);

        VBox box = new VBox(20, msgLabel);
        box.setAlignment(Pos.CENTER);
        box.setStyle("-fx-background-color: #ffffff; -fx-padding: 30; -fx-border-radius: 10; -fx-background-radius: 10; -fx-border-color: #0066cc;");

        Scene scene = new Scene(box, 400, 150);
        popup.setScene(scene);
        popup.show();
    }
}
