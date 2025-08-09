package mediAid.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import mediAid.Main;

import java.io.BufferedReader;
import java.io.FileReader;

public class ReminderPageController {
    @FXML private TableView<Reminder> reminderTable;
    @FXML private TableColumn<Reminder, String> nameCol, ageCol, genderCol, diseaseCol, timeCol;

    @FXML
    public void initialize() {
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        ageCol.setCellValueFactory(new PropertyValueFactory<>("age"));
        genderCol.setCellValueFactory(new PropertyValueFactory<>("gender"));
        diseaseCol.setCellValueFactory(new PropertyValueFactory<>("disease"));
        timeCol.setCellValueFactory(new PropertyValueFactory<>("timeRemaining"));

        ObservableList<Reminder> data = FXCollections.observableArrayList();

        try (BufferedReader reader = new BufferedReader(new FileReader("data/reminders.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 6) {
                    try {
                        long recorded = Long.parseLong(parts[5].trim());
                        long diff = System.currentTimeMillis() - recorded;
                        long remaining = 3 * 24 * 60 * 60 * 1000L - diff;
                        if (remaining > 0) {
                            data.add(new Reminder(
                                    parts[0].trim(), // name
                                    parts[1].trim(), // age
                                    parts[2].trim(), // gender
                                    parts[4].trim(), // disease
                                    remaining
                            ));
                        }
                    } catch (NumberFormatException e) {
                        // ignore bad lines
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        reminderTable.setItems(data);
    }

    @FXML
    public void goBack() {
        Main.changeScene("/view/HomePage.fxml");
    }
}
