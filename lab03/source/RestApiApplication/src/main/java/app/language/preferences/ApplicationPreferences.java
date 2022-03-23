package app.language.preferences;

import java.util.Locale;
import java.util.prefs.Preferences;

public class ApplicationPreferences {

    private final Preferences pref;
    private String[] languageSettings;

    public ApplicationPreferences() {
        pref = Preferences.userNodeForPackage(this.getClass());
        readPreferences();
    }

    /**
     * Read preferences
     * If no preferences exist, a registry will be created
     * Otherwise, they will be loaded from the registry
     */
    private void readPreferences() {
        Locale locale = Locale.getDefault();
        String[] value = {pref.get("language", "null"), pref.get("country", "null")};
        if (value[0].equals("null"))
            pref.put("language", locale.getLanguage());
        if (value[1].equals("null"))
            pref.put("country", locale.getCountry());

        languageSettings = value;
    }


    /**
     * Set local language preferences
     * @param language language local code
     * @param country country local code
     */
    public void setPreferences(String language, String country) {
        pref.put("language", language);
        pref.put("country", country);
        languageSettings[0] = language;
        languageSettings[1] = country;
    }

    public String[] getLanguageSettings() {
        return languageSettings;
    }
}
