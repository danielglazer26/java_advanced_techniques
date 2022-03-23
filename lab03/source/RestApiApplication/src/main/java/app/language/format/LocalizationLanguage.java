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
    private Format[] formatAnswer, formatQuestion;

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

        formatAnswer = new Format[]{
                new ChoiceFormat(limitsAnswer, answerStrings),
                null,
                new ChoiceFormat(limits, fileStrings),
                NumberFormat.getInstance(mf.getLocale()),
                NumberFormat.getInstance(mf.getLocale())};

        formatQuestion = new Format[]{
                null,
                NumberFormat.getInstance(mf.getLocale())};
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
    public String createAnswer(int answerCorrection, String countryCode, int cityNumber, int population) {
        mf.applyPattern(resourceBundle.getString("answer"));
        mf.setFormats(formatAnswer);
        Object[] answerObject = {answerCorrection, countryCode, chooseTypeOfNumber(cityNumber), population,
                cityNumber};
        return mf.format(answerObject);
    }

    /**
     * Creates question for specified countryCode and population
     */
    public String createQuestion(String countryCode, int population) {
        mf.applyPattern(resourceBundle.getString("question"));
        mf.setFormats(formatQuestion);
        Object[] questionObject = {countryCode, population};
        return mf.format(questionObject);
    }

    /**
     * Gets template string from bundle
     *
     * @return template string
     */
    public String getTemplate(String template) {
        return resourceBundle.getString(template);
    }

    /**
     * Chooses type of number variation
     */
    private int chooseTypeOfNumber(int number) {

        if (number > 100) {
            String s = String.valueOf(number);
            String s2 = s.substring(s.length() - 2);
            number = Integer.parseInt(s2);
            if (number == 0 || number == 1)
                return 3;
        }

        if (number > 20) {
            String s = String.valueOf(number);
            number = s.charAt(s.length() - 1) - '0';
            if (number > 1 && number < 5)
                return 2;
        }

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
