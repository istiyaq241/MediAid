package mediAid.model;

public class Volunteer {
    private String name, age, gender, blood, profession, location;

    public Volunteer(String name, String age, String gender, String blood, String profession, String location) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.blood = blood;
        this.profession = profession;
        this.location = location;
    }

    public String getName() { return name; }
    public String getAge() { return age; }
    public String getGender() { return gender; }
    public String getBlood() { return blood; }
    public String getProfession() { return profession; }
    public String getLocation() { return location; }
}
