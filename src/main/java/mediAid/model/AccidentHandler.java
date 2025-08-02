package main.java.mediAid.model;

public class AccidentHandler {

    public static String getGuidance(String accidentType) {
        switch (accidentType.toLowerCase()) {
            case "burn":
                return "Cool the burn under running water for 10 minutes. Do not apply ice.";
            case "snake bite":
                return "Keep the victim calm and still. Seek medical help immediately.";
            case "bone dislocation":
                return "Immobilize the joint. Do not try to relocate. Visit a hospital.";
            default:
                return "No guidance available for this type of accident.";
        }
    }
}
