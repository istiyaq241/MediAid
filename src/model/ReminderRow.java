package model;

public class ReminderRow {
    private String name;
    private String disease;
    private String time;

    public ReminderRow(String name, String disease, String time) {
        this.name = name;
        this.disease = disease;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public String getDisease() {
        return disease;
    }

    public String getTime() {
        return time;
    }
}
