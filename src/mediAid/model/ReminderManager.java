package mediAid.model;

import java.io.*;
import java.time.*;
import java.util.*;

public class ReminderManager {
    private static final String FILE_PATH = "reminders.txt";
    private static final long MAX_DURATION_HOURS = 72;

    public static void addReminder(String name, String condition) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            long timestamp = Instant.now().getEpochSecond();
            writer.write(name + "," + condition + "," + timestamp);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<String> getActiveReminders() {
        List<String> reminders = new ArrayList<>();
        long now = Instant.now().getEpochSecond();

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String name = parts[0];
                    String condition = parts[1];
                    long startTime = Long.parseLong(parts[2]);

                    long hoursPassed = (now - startTime) / 3600;
                    if (hoursPassed < MAX_DURATION_HOURS) {
                        reminders.add("Reminder: " + name + " - " + condition + " (" + (72 - hoursPassed) + "h left)");
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