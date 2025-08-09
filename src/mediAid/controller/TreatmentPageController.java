package mediAid.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import mediAid.Main;
import mediAid.TreatmentLoader;
import mediAid.model.TreatmentContext;
import mediAid.model.TreatmentEntry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TreatmentPageController {

    @FXML private Label conditionLabel;
    @FXML private TextArea treatmentTextArea;

    private TreatmentEntry matched;
    private String ageStr;
    private String location;

    @FXML
    public void initialize() {
        String condition = TreatmentContext.getCondition();
        if (conditionLabel != null) conditionLabel.setText(condition != null ? condition : "");

        ageStr = TreatmentContext.getAge();
        location = TreatmentContext.getLocation();

        matched = TreatmentLoader.getTreatment(condition);
        if (matched == null) {
            setText("❌ No matching treatment found for: " + condition);
        } else {
            setText("Select a stage from the right →");
        }
    }

    @FXML
    public void showPrimary() {
        if (matched != null && matched.getTreatment() != null && !matched.getTreatment().isEmpty()) {
            List<String> filtered = filterLines(matched.getTreatment());
            setText(String.join("\n", filtered));
        } else {
            setText("No primary treatment steps found.");
        }
    }

    @FXML
    public void showFinal() {
        if (matched != null && matched.getFinalAdvice() != null && !matched.getFinalAdvice().isEmpty()) {
            // Final advice is usually generic; still apply light location filter just in case
            List<String> filtered = filterLines(matched.getFinalAdvice());
            setText(String.join("\n", filtered));
        } else {
            setText("No final advice found.");
        }
    }

    @FXML
    public void showWarnings() {
        if (matched != null && matched.getWarnings() != null && !matched.getWarnings().isEmpty()) {
            // Warnings are usually generic; keep as-is
            List<String> filtered = filterLines(matched.getWarnings());
            setText(String.join("\n", filtered));
        } else {
            setText("No warning notes found.");
        }
    }

    @FXML
    public void goBack() {
        Main.changeScene(Main.getPreviousScene() != null ? Main.getPreviousScene() : "/view/HomePage.fxml");
    }

    private void setText(String text) {
        if (treatmentTextArea != null) treatmentTextArea.setText(text);
    }

    /* ----------------------------
       Display-time filtering logic
       ---------------------------- */

    private List<String> filterLines(List<String> lines) {
        if (lines == null || lines.isEmpty()) return lines;

        Integer age = parseIntSafe(ageStr);
        String loc = location == null ? "" : location.toLowerCase();

        List<String> out = new ArrayList<>();
        for (String raw : lines) {
            if (raw == null || raw.isBlank()) {
                continue;
            }
            String line = raw;

            boolean mentionsAge = containsAny(line, "0–", "0-", "13+", "yrs");
            boolean mentionsLoc = containsAny(
                    line.toLowerCase(),
                    "rural", "urban", "coastal", "forest", "shelter"
            );

            // Age gate
            if (mentionsAge && age != null) {
                if (!ageLineMatches(line, age)) {
                    continue;
                }
            }
            // Location gate
            if (mentionsLoc && loc.length() > 0) {
                if (!locationLineMatches(line, loc)) {
                    continue;
                }
            }

            out.add(line);
        }
        // If everything got filtered out (too strict), fall back to original so user isn't left blank
        if (out.isEmpty()) return lines;
        return out;
    }

    private boolean ageLineMatches(String line, int age) {
        String s = line.replace('–', '-').toLowerCase();

        // Matches like "0-12 yrs:"
        if (s.contains("0-12")) {
            return age >= 0 && age <= 12;
        }
        // Matches like "13+ yrs:"
        if (s.contains("13+")) {
            return age >= 13;
        }
        // Generic "children"/"adults" (best-effort)
        if (s.contains("child")) {
            return age <= 12;
        }
        if (s.contains("adult")) {
            return age >= 13;
        }
        // If it mentions yrs but no recognizable band, allow the line
        if (s.contains("yrs")) return true;

        // No explicit age tag => keep
        return true;
    }

    private boolean locationLineMatches(String line, String locLower) {
        String s = line.toLowerCase();

        // If the line mentions multiple regions ("forest/rural", "shelters or coastal"), match if any fits
        List<String> forestRural = Arrays.asList("forest", "rural");
        List<String> shelterCoastal = Arrays.asList("shelter", "coastal");

        if (s.contains("forest") || s.contains("rural")) {
            return forestRural.stream().anyMatch(locLower::contains);
        }
        if (s.contains("shelter") || s.contains("coastal")) {
            return shelterCoastal.stream().anyMatch(locLower::contains);
        }
        if (s.contains("urban")) {
            return locLower.contains("urban");
        }

        // Mentions a location word we don't parse specially => allow
        return true;
    }

    private boolean containsAny(String text, String... tokens) {
        if (text == null) return false;
        for (String t : tokens) if (text.contains(t)) return true;
        return false;
    }

    private Integer parseIntSafe(String s) {
        try { return s == null ? null : Integer.parseInt(s.trim()); }
        catch (Exception e) { return null; }
    }
}
