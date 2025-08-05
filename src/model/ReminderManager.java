package model;

import java.io.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class ReminderManager {
    private static final String FILE_PATH = "reminders.txt";

    public static void addReminder(String name, String condition) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            long timestamp = Instant.now().getEpochSecond();
            writer.write(name + "," + condition + "," + timestamp);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<String> getActiveReminders(long maxDurationSec) {
        List<String> reminders = new ArrayList<>();
        long now = Instant.now().getEpochSecond();

        File file = new File(FILE_PATH);
        if (!file.exists()) return reminders;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String name = parts[0];
                    String condition = parts[1];
                    long startTime = Long.parseLong(parts[2]);

                    long secondsPassed = now - startTime;
                    if (secondsPassed < maxDurationSec) {
                        reminders.add("Reminder: " + name + " - " + condition + " (" + (maxDurationSec - secondsPassed) + "s left)");
                    } else {
                        reminders.add("⛔ " + name + ": Treatment expired. Please seek professional help (e.g., call 999).");
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return reminders;
    }
}
