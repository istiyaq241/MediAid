package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.ReminderManager;
import model.ReminderRow;

import java.util.List;

public class ReminderController {

    @FXML private TableView<ReminderRow> reminderTable;
    @FXML private TableColumn<ReminderRow, String> nameCol;
    @FXML private TableColumn<ReminderRow, String> diseaseCol;
    @FXML private TableColumn<ReminderRow, String> timeCol;

    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
        loadTable();
    }

    @FXML
    public void initialize() {
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        diseaseCol.setCellValueFactory(new PropertyValueFactory<>("disease"));
        timeCol.setCellValueFactory(new PropertyValueFactory<>("time"));
    }

    @FXML
    public void refreshReminders() {
        loadTable();
    }

    private void loadTable() {
        List<String> reminders = ReminderManager.getActiveReminders(259200);
        ObservableList<ReminderRow> rows = FXCollections.observableArrayList();

        for (String s : reminders) {
            if (s.contains("Reminder:")) {
                String[] parts = s.replace("Reminder:", "").split("-");
                if (parts.length == 2 && parts[1].contains("(")) {
                    String name = parts[0].trim();
                    String[] sub = parts[1].split("\\(");
                    String condition = sub[0].trim();
                    String time = sub[1].replace(")", "").trim();
                    rows.add(new ReminderRow(name, condition, time));
                }
            } else {
                rows.add(new ReminderRow("⛔", s, ""));
            }
        }

        reminderTable.setItems(rows);
    }

    @FXML
    public void goBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/HomePage.fxml"));
            Scene scene = new Scene(loader.load());
            HomePage controller = loader.getController();
            controller.setStage(stage);
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
