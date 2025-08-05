package model;

import java.util.List;

public class ConditionAdvisor {
    public static String getAdvice(List<String> symptoms, int age, String gender, String location) {
        String ageGroup = (age < 5) ? "Under 5" : (age > 60 ? "Elderly" : "Adult");

        if (symptoms.contains("fever") && symptoms.contains("cold") && location.equals("Urban")) {
            return "viral";
        }

        if (symptoms.contains("diarrhoea") || symptoms.contains("stomachache") || symptoms.contains("vomiting")) {
            if (ageGroup.equals("Under 5")) {
                return "cholera";
            } else {
                return "foodpoisoning";
            }
        }

        if (symptoms.contains("skin rash") && symptoms.contains("fever")) {
            return "dengue";
        }

        if (symptoms.contains("short breath") && ageGroup.equals("Under 5")) {
            return "respiratory";
        }

        if (symptoms.contains("swollen feet") && gender.equals("Female") && location.equals("Coastal")) {
            return "preeclampsia";
        }

        if (symptoms.contains("fever") && symptoms.contains("bodyache") && symptoms.contains("nausea")) {
            return "dengue";
        }

        return symptoms.get(0);
    }
}
