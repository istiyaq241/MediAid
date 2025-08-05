package model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.util.Map;
import java.util.Scanner;

public class TreatmentLoader {
    private static Map<String, Treatment> treatmentMap;

    static {
        try {
            InputStream is = TreatmentLoader.class.getResourceAsStream("/assets/json/treatments.json");
            Scanner scanner = new Scanner(is).useDelimiter("\\A");
            String json = scanner.hasNext() ? scanner.next() : "";
            scanner.close();

            Gson gson = new Gson();
            treatmentMap = gson.fromJson(json, new TypeToken<Map<String, Treatment>>() {}.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Treatment getTreatment(String condition) {
        return treatmentMap.get(condition.toLowerCase());
    }
}
