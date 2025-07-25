package mediAid.model;

public abstract class EmergencyCase {

    protected String name;
    protected String description;

    public EmergencyCase(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public abstract String getTreatment();
}
