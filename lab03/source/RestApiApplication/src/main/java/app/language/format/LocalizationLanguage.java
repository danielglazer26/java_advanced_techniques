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

    LocalizationLanguage(String[] pref) {
        refreshLocal(pref);
    }

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

    public void refreshLocal(String[] pref) {
        Locale locale = new Locale(pref[0], pref[1]);
        resourceBundle = ResourceBundle.getBundle("app.language.bundles.Bundle", locale);

        mf = new MessageFormat("");
        mf.setLocale(locale);
    }

    /* public void formatLanguage() {
        // pierwszy od limitu
        //ostatni od wartości
        Object[] answer = {null, "PL", null, "500000", null};
        for (int i = 0; i < 10; i++) {
            answer[0] = new Random().nextInt(2);
            int number = Math.abs(new Random().nextInt(11));
            answer[4] = String.valueOf(number);
            answer[2] = chooseTypeOfNumber(number);
            System.out.println(mf.format(answer));
        }
    }*/
    public String createAnswer(int answerCorrection, String countryCode, int cityNumber, String population){
        mf.applyPattern(resourceBundle.getString("answer"));
        mf.setFormats(formats);
        Object[] answerObject = {answerCorrection, countryCode, chooseTypeOfNumber(cityNumber), population,
                String.valueOf(cityNumber) };
        return mf.format(answerObject);
    }

    public String createQuestion(String countryCode, String population) {
        mf.applyPattern(resourceBundle.getString("question"));
        Object[] questionObject = {countryCode, population};
        return mf.format(questionObject);
    }

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
