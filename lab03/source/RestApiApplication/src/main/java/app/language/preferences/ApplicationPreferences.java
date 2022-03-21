package app.language.preferences;

import java.util.Locale;
import java.util.prefs.Preferences;

public class ApplicationPreferences {

    private final Preferences pref;
    private String[] languageSettings;

    ApplicationPreferences() {
        pref = Preferences.userNodeForPackage(this.getClass());
    }

    public void readPreferences() {
        Locale locale = Locale.getDefault();
        String[] value = {pref.get("language", "null"), pref.get("country", "null")};
        if (value[0].equals("null"))
            pref.put("language", locale.getLanguage());
        if (value[1].equals("null"))
            pref.put("country", locale.getCountry());

        languageSettings = value;
    }

    public void setPreferences(String language, String country) {
        pref.put("language", language);
        pref.put("country", country);
    }

    public String[] getLanguageSettings() {
        return languageSettings;
    }
}
