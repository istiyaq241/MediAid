package mediAid.controller;

public class Reminder {
    private String name, age, gender, disease;
    private String timeRemaining;

    public Reminder(String name, String age, String gender, String disease, long msLeft) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.disease = disease;
        this.timeRemaining = formatTime(msLeft);
    }

    private String formatTime(long ms) {
        long hours = ms / (1000 * 60 * 60);
        return hours + " hours left";
    }

    public String getName() { return name; }
    public String getAge() { return age; }
    public String getGender() { return gender; }
    public String getDisease() { return disease; }
    public String getTimeRemaining() { return timeRemaining; }
}
