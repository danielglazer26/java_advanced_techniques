package app.language.format;

import java.text.ChoiceFormat;
import java.text.Format;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public class LocalizationLanguage {

    private ResourceBundle resourceBundle;
    private MessageFormat mf;
    private Format[] formats;

    public LocalizationLanguage(String[] pref) {
        refreshLocal(pref);
    }


    /**
     * Creates choice formats for answers and number variation
     */
    private void createChoiceFormat() {
        double[] limitsAnswer = {0, 1};
        String[] answerStrings = {
                resourceBundle.getString("answerWrong"),
                resourceBundle.getString("answerCorrect")};

        double[] limits = {0, 1, 2, 3};
        String[] fileStrings = {
                resourceBundle.getString("zero"),
                resourceBundle.getString("one"),
                resourceBundle.getString("two-for"),
                resourceBundle.getString("multi")};

        formats = new Format[]{
                new ChoiceFormat(limitsAnswer, answerStrings),
                null,
                new ChoiceFormat(limits, fileStrings),
                null,
                NumberFormat.getInstance()};

    }

    /**
     * Refreshes defined localization
     */
    public void refreshLocal(String[] pref) {
        Locale locale = new Locale(pref[0], pref[1]);
        resourceBundle = ResourceBundle.getBundle("app.language.bundles.Bundle", locale);
        mf = new MessageFormat("");
        mf.setLocale(locale);
        createChoiceFormat();
    }

    /**
     * Creates answer for specified data
     */
    public String createAnswer(int answerCorrection, String countryCode, int cityNumber, String population){
        mf.applyPattern(resourceBundle.getString("answer"));
        mf.setFormats(formats);
        Object[] answerObject = {answerCorrection, countryCode, chooseTypeOfNumber(cityNumber), population,
                String.valueOf(cityNumber) };
        return mf.format(answerObject);
    }

    /**
     * Creates question for specified countryCode and population
     */
    public String createQuestion(String countryCode, String population) {
        mf.applyPattern(resourceBundle.getString("question"));
        Object[] questionObject = {countryCode, population};
        return mf.format(questionObject);
    }

    /**
     * Gets template string from bundle
     * @return template string
     */
    public String getTemplate(String template){
        return resourceBundle.getString(template);
    }

    /**
     * Chooses type of number variation
     */
    private int chooseTypeOfNumber(int number) {
        if (number == 0)
            return 0;
        else if (number == 1)
            return 1;
        else if (number > 1 && number < 5)
            return 2;
        else
            return 3;
    }
}
