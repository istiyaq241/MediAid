package mediAid.model;

import java.util.List;

public class TreatmentContext {

    private static String condition;
    private static String age;
    private static String gender;
    private static String location;
    private static List<String> minorSymptoms;
    private static String type;

    public static void setContext(String t, String c) {
        type = t;
        condition = c;
    }

    public static String getType() {
        return type;
    }

    public static void setType(String t) {
        type = t;
    }

    public static void setCondition(String c) {
        condition = c;
    }

    public static String getCondition() {
        return condition;
    }

    public static void setAge(String a) {
        age = a;
    }

    public static String getAge() {
        return age;
    }

    public static void setGender(String g) {
        gender = g;
    }

    public static String getGender() {
        return gender;
    }

    public static void setLocation(String l) {
        location = l;
    }

    public static String getLocation() {
        return location;
    }

    public static void setMinorSymptoms(List<String> s) {
        minorSymptoms = s;
    }

    public static List<String> getMinorSymptoms() {
        return minorSymptoms;
    }
}
