package mediAid.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import mediAid.model.Volunteer;

import java.io.BufferedReader;
import java.io.FileReader;

public class SeekHelpController {
    @FXML private TableView<Volunteer> volunteerTable;
    @FXML private TableColumn<Volunteer, String> nameCol, ageCol, genderCol, bloodCol, professionCol, locationCol;

    @FXML
    public void initialize() {
        ObservableList<Volunteer> data = FXCollections.observableArrayList();

        try (BufferedReader reader = new BufferedReader(new FileReader("data/volunteers.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" \\| ");
                if (parts.length >= 6) {
                    data.add(new Volunteer(
                            parts[0].trim(), // name
                            parts[1].trim(), // age
                            parts[2].trim(), // gender
                            parts[3].trim(), // blood
                            parts[4].trim(), // profession
                            parts[5].trim()  // location
                    ));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        ageCol.setCellValueFactory(new PropertyValueFactory<>("age"));
        genderCol.setCellValueFactory(new PropertyValueFactory<>("gender"));
        bloodCol.setCellValueFactory(new PropertyValueFactory<>("blood"));
        professionCol.setCellValueFactory(new PropertyValueFactory<>("profession"));
        locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));

        volunteerTable.setItems(data);
    }

    @FXML
    public void goBack() {
        mediAid.Main.changeScene("/view/HomePage.fxml");
    }
}
