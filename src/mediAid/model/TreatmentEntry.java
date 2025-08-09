package mediAid.model;

import java.util.List;

public class TreatmentEntry {

    private String name;
    private List<String> ageGroups;
    private List<String> genders;
    private List<String> locations;
    private List<String> minorSymptoms;
    private List<String> treatment;
    private List<String> warnings;
    private List<String> finalAdvice;

    public String getName() {
        return name;
    }

    public List<String> getAgeGroups() {
        return ageGroups;
    }

    public List<String> getGenders() {
        return genders;
    }

    public List<String> getLocations() {
        return locations;
    }

    public List<String> getMinorSymptoms() {
        return minorSymptoms;
    }

    public List<String> getTreatment() {
        return treatment;
    }

    public List<String> getWarnings() {
        return warnings;
    }

    public List<String> getFinalAdvice() {
        return finalAdvice;
    }

    // Aliases
    public List<String> getAge() {
        return getAgeGroups();
    }

    public List<String> getGender() {
        return getGenders();
    }

    public List<String> getLocation() {
        return getLocations();
    }
}
