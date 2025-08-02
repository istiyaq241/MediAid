package main.java.mediAid.model;

public class DiseaseHandler {

    public static String getGuidance(String symptom, String gender, int age) {
        switch (symptom.toLowerCase()) {
            case "fever":
                return "Drink plenty of fluids. Use paracetamol for relief. Visit a doctor if fever persists.";
            case "stomachache":
                return "Avoid solid food for a few hours. Try ORS. Seek help if pain worsens.";
            case "cold":
                return "Keep warm and hydrated. Steam inhalation may help.";
            default:
                return "No temporary guidance available for this symptom.";
        }
    }
}
