package mediAid;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import mediAid.model.TreatmentContext;
import mediAid.model.TreatmentEntry;

import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class TreatmentLoader {

    private static final List<TreatmentEntry> diseaseList  = new ArrayList<>();
    private static final List<TreatmentEntry> accidentList = new ArrayList<>();

    static {
        try (FileReader diseaseReader = new FileReader("data/treatment/disease_treatment.json")) {
            Type listType = new TypeToken<List<TreatmentEntry>>() {}.getType();
            List<TreatmentEntry> loaded = new Gson().fromJson(diseaseReader, listType);
            if (loaded != null) diseaseList.addAll(loaded);
        } catch (Exception e) {
            System.err.println("[TreatmentLoader] Failed loading disease_treatment.json: " + e.getMessage());
        }
        try (FileReader accidentReader = new FileReader("data/treatment/accident_treatment.json")) {
            Type listType = new TypeToken<List<TreatmentEntry>>() {}.getType();
            List<TreatmentEntry> loaded = new Gson().fromJson(accidentReader, listType);
            if (loaded != null) accidentList.addAll(loaded);
        } catch (Exception e) {
            System.err.println("[TreatmentLoader] Failed loading accident_treatment.json: " + e.getMessage());
        }
    }

    public static TreatmentEntry getTreatment(String conditionName) {
        if (conditionName == null) return null;

        String type     = trimOrNull(TreatmentContext.getType());
        String ageStr   = trimOrNull(TreatmentContext.getAge());
        String gender   = trimOrNull(TreatmentContext.getGender());
        String location = trimOrNull(TreatmentContext.getLocation());
        List<String> minor = TreatmentContext.getMinorSymptoms();

        List<TreatmentEntry> source =
                "accident".equalsIgnoreCase(type) ? accidentList : diseaseList;

        String probeName = normalizeName(conditionName);

        Integer age = parseIntSafe(ageStr);
        TreatmentEntry best = null;
        int bestScore = Integer.MIN_VALUE;
        int bestSpecificity = -1;

        for (TreatmentEntry e : source) {
            if (e == null || e.getName() == null) continue;
            if (!normalizeName(e.getName()).equals(probeName)) continue;

            if (!ageMatches(e.getAgeGroups(), ageStr)) continue;
            if (!matches(e.getGenders(),   gender))   continue;
            if (!matches(e.getLocations(), location)) continue;
            if (!minorAllows(e.getMinorSymptoms(), minor)) continue;

            int score = 0;
            int spec  = 0;

            if (e.getAgeGroups() != null && !e.getAgeGroups().isEmpty()) {
                spec += 2;
                if (age != null && groupListHasAge(e.getAgeGroups(), age)) score += 3;
            }
            if (e.getLocations() != null && !e.getLocations().isEmpty()) {
                spec += 2;
                if (containsIgnoreCase(e.getLocations(), location)) score += 2;
            }
            if (e.getGenders() != null && !e.getGenders().isEmpty()) {
                spec += 1;
                if (containsIgnoreCase(e.getGenders(), gender)) score += 1;
            }
            if (e.getMinorSymptoms() != null && !e.getMinorSymptoms().isEmpty()
                    && minor != null && !minor.isEmpty()) {
                int overlap = 0;
                for (String m : minor) if (containsIgnoreCase(e.getMinorSymptoms(), m)) overlap++;
                score += overlap;
                spec  += 1;
            }

            if (score > bestScore || (score == bestScore && spec > bestSpecificity)) {
                best = e;
                bestScore = score;
                bestSpecificity = spec;
            }
        }
        return best;
    }

    public static List<String> getDiseasesByMinorSymptom(String symptom) {
        Set<String> names = new LinkedHashSet<>();
        String probe = trimOrNull(symptom);
        if (probe == null) return new ArrayList<>(names);

        for (TreatmentEntry e : diseaseList) {
            if (e == null || e.getName() == null) continue;
            List<String> ms = e.getMinorSymptoms();
            if (ms == null || ms.isEmpty()) continue;
            if (containsIgnoreCase(ms, probe)) names.add(e.getName());
        }
        return new ArrayList<>(names);
    }

    /* ---------------- helpers ---------------- */

    private static String trimOrNull(String s) {
        if (s == null) return null;
        String t = s.trim();
        return t.isEmpty() ? null : t;
    }

    private static String normalizeName(String s) {
        if (s == null) return "";
        String t = s.trim()
                .replace('–', '-')
                .replaceAll("\\s*‑\\s*", "-")
                .replaceAll("\\s*-\\s*", "-")
                .replaceAll("\\s+", " ")
                .toLowerCase();
        return t;
    }

    private static boolean matches(List<String> allowed, String value) {
        if (allowed == null || allowed.isEmpty()) return true;
        if (value == null || value.isEmpty())     return false;
        return containsIgnoreCase(allowed, value);
    }

    private static boolean containsIgnoreCase(List<String> list, String probe) {
        if (list == null || probe == null) return false;
        String p = probe.trim().toLowerCase();
        for (String s : list) {
            if (s != null && s.trim().toLowerCase().equals(p)) return true;
        }
        return false;
    }

    private static boolean minorAllows(List<String> allowedMinor, List<String> userMinor) {
        if (allowedMinor == null || allowedMinor.isEmpty()) return true;
        if (userMinor == null || userMinor.isEmpty())       return true;
        for (String u : userMinor) if (containsIgnoreCase(allowedMinor, u)) return true;
        return false;
    }

    private static boolean ageMatches(List<String> groups, String ageStr) {
        if (groups == null || groups.isEmpty()) return true;
        if (ageStr == null || ageStr.isEmpty())  return false;
        Integer age = parseIntSafe(ageStr);
        if (age == null) return containsIgnoreCase(groups, ageStr);
        return groupListHasAge(groups, age);
    }

    private static boolean groupListHasAge(List<String> groups, int age) {
        for (String g : groups) {
            if (g == null || g.isEmpty()) continue;
            String t = g.replace('–', '-').replace(" ", "");
            if (t.endsWith("+")) {
                Integer start = parseIntSafe(t.substring(0, t.length() - 1));
                if (start != null && age >= start) return true;
            } else if (t.contains("-")) {
                String[] p = t.split("-");
                if (p.length == 2) {
                    Integer a = parseIntSafe(p[0]);
                    Integer b = parseIntSafe(p[1]);
                    if (a != null && b != null && age >= a && age <= b) return true;
                }
            } else {
                Integer exact = parseIntSafe(t);
                if (exact != null && age == exact) return true;
            }
        }
        return false;
    }

    private static Integer parseIntSafe(String s) {
        try { return Integer.parseInt(s.trim()); }
        catch (Exception ignore) { return null; }
    }
}
