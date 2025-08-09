package mediAid.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import mediAid.Main;
import mediAid.model.TreatmentContext;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class AccidentHelpController {

    // Side menu + service lookup
    @FXML private ScrollPane sideScroll;     // <--- new: we toggle this (not just VBox) so no gutter remains
    @FXML private VBox sideMenu;
    @FXML private ComboBox<String> divisionBox;
    @FXML private ComboBox<String> upazilaBox;
    @FXML private ComboBox<String> serviceBox;
    @FXML private Label serviceResult;

    // Image tiles (IDs are used for click handler)
    @FXML private ImageView choking, bleeding, seizure, cpr, snakebite, burn;

    private final Map<String, Set<String>> divisionMap = new HashMap<>();

    @FXML
    public void initialize() {
        // hide the *ScrollPane* so nothing is left visible when closed
        if (sideScroll != null) {
            sideScroll.setVisible(false);
            sideScroll.setManaged(false);
        }
        // your previous behavior: VBox exists and is laid out when scroll is shown
        if (sideMenu != null) {
            sideMenu.setVisible(true);
            sideMenu.setManaged(true);
        }

        loadDivisionsFromServicesTxt();

        if (divisionBox != null) {
            divisionBox.setOnAction(e -> updateUpazilas());
        }
        if (serviceBox != null) {
            serviceBox.getItems().setAll("Ambulance", "Medical", "Fire Service");
        }
    }

    /* ---------------------- Service directory ---------------------- */

    private void loadDivisionsFromServicesTxt() {
        try (BufferedReader reader = new BufferedReader(new FileReader("data/emergency_services_all_divisions.txt"))) {
            String line;
            String currentDivision = null;

            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.startsWith("District:")) {
                    currentDivision = line.substring(9).trim();
                    divisionMap.putIfAbsent(currentDivision, new TreeSet<>());
                } else if (line.startsWith("Upazila:") && currentDivision != null) {
                    String upazila = line.substring(8).trim();
                    divisionMap.get(currentDivision).add(upazila);
                }
            }

            if (divisionBox != null) {
                divisionBox.getItems().clear();
                divisionBox.getItems().addAll(divisionMap.keySet());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateUpazilas() {
        if (divisionBox == null || upazilaBox == null) return;

        String selected = divisionBox.getValue();
        upazilaBox.getItems().clear();

        if (selected != null && divisionMap.containsKey(selected)) {
            upazilaBox.getItems().addAll(divisionMap.get(selected));
        }
    }



    @FXML
    public void toggleMenu() {
        if (sideScroll != null) {
            boolean show = !sideScroll.isVisible();
            sideScroll.setVisible(show);
            sideScroll.setManaged(show);
        }
    }



    @FXML
    public void findServiceNumber() {
        String district = divisionBox.getValue();
        String upazila = upazilaBox.getValue();
        String service = serviceBox.getValue();

        if (district == null || upazila == null || service == null) {
            if (serviceResult != null) serviceResult.setText("Please select division, upazila, and service type.");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader("data/emergency_services_all_divisions.txt"))) {
            String line;
            boolean matchDistrict = false;
            boolean matchUpazila = false;

            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.startsWith("District:")) {
                    matchDistrict = line.substring(9).trim().equalsIgnoreCase(district);
                    matchUpazila = false;
                } else if (matchDistrict && line.startsWith("Upazila:")) {
                    matchUpazila = line.substring(8).trim().equalsIgnoreCase(upazila);
                } else if (matchDistrict && matchUpazila && line.startsWith(service + ":")) {
                    String contact = line.substring(service.length() + 1).trim();
                    if (serviceResult != null) serviceResult.setText(contact);
                    return;
                }
            }

            if (serviceResult != null) serviceResult.setText("No contact found for the selected area and service.");
        } catch (IOException e) {
            if (serviceResult != null) serviceResult.setText("Error reading emergency contact data.");
            e.printStackTrace();
        }
    }


    /* ---------------------- Navigation / actions ---------------------- */

    @FXML
    public void goToInput(MouseEvent event) {
        ImageView clicked = (ImageView) event.getSource();
        String condition = clicked.getId(); // choking, cpr, seizure, burn, bleeding, snakebite

        boolean needsInput = condition.equals("burn")
                || condition.equals("bleeding")
                || condition.equals("snakebite");

        // Always set base context FIRST (some implementations clear other fields)
        TreatmentContext.setContext("accident", condition);

        if (needsInput) {
            Main.setPreviousScene("/view/AccidentHelp.fxml");
            Main.changeScene("/view/AccidentInput.fxml");
        } else {
            // Ensure TreatmentPage has safe values even on a cold start
            if (TreatmentContext.getAge() == null || TreatmentContext.getAge().isBlank()) {
                TreatmentContext.setAge("0");
            }
            if (TreatmentContext.getGender() == null || TreatmentContext.getGender().isBlank()) {
                TreatmentContext.setGender("Unknown");
            }
            if (TreatmentContext.getLocation() == null || TreatmentContext.getLocation().isBlank()) {
                TreatmentContext.setLocation("Unknown");
            }
            TreatmentContext.setMinorSymptoms(null);

            Main.setPreviousScene("/view/AccidentHelp.fxml");
            Main.changeScene("/view/TreatmentPage.fxml");
        }
    }



    @FXML public void goBack()           { Main.changeScene("/view/HomePage.fxml"); }
    @FXML public void goToReminder()     { Main.setPreviousScene("/view/AccidentHelp.fxml"); Main.changeScene("/view/ReminderPage.fxml"); }
    @FXML public void goToAddSituation() { Main.changeScene("/view/AddSituationForm.fxml"); }
    @FXML public void goToVolunteer()    { Main.changeScene("/view/VolunteerForm.fxml"); }
    @FXML public void goToSeekHelp()     { Main.changeScene("/view/SeekHelpPage.fxml"); }
    @FXML public void goToAbout()        { Main.changeScene("/view/About.fxml"); }
    @FXML public void goToCredits()      { Main.changeScene("/view/Credits.fxml"); }
    @FXML public void goToHome()         { Main.changeScene("/view/HomePage.fxml"); }
}
