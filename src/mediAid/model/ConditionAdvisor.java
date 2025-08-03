package mediAid.model;

import java.util.List;

public class ConditionAdvisor {
    public static String getAdvice(List<String> symptoms, int age, String gender, String location) {
        String ageGroup = (age < 5) ? "Under 5" : (age > 60 ? "Elderly" : "Adult");

        if (symptoms.contains("Fever") && symptoms.contains("Cold") && location.equals("Urban")) {
            return "Likely: Viral fever. Advice: Rest, hydration, paracetamol. Avoid aspirin.";
        }

        if (symptoms.contains("Diarrhoea") || symptoms.contains("Stomachache") || symptoms.contains("Vomiting")) {
            if (ageGroup.equals("Under 5")) {
                return "Likely: Cholera. Advice: ORS, zinc, hygiene, boiled water.";
            } else {
                return "Likely: Food poisoning or cholera. Advice: Rehydration, rest, safe water.";
            }
        }

        if (symptoms.contains("Skin rash") && symptoms.contains("Fever")) {
            return "Possible: Dengue or allergic reaction. Advice: Monitor temp, avoid mosquito bites.";
        }

        if (symptoms.contains("Short breath") && ageGroup.equals("Under 5")) {
            return "Possible: Respiratory infection. Advice: Warm fluids, monitor, refer if chest tight.";
        }

        if (symptoms.contains("Swollen feet") && gender.equals("Female") && location.equals("Coastal")) {
            return "Possible: Pre-eclampsia. Advice: Rest, low salt, monitor BP, refer if needed.";
        }

        if (symptoms.contains("Fever") && symptoms.contains("Body ache") && symptoms.contains("Nausea")) {
            return "Likely: Dengue or malaria. Advice: Paracetamol, fluids, monitor bleeding.";
        }

        return "General advice: Rest, hydration, monitor symptoms. Seek medical help if conditions worsen.";
    }
}
