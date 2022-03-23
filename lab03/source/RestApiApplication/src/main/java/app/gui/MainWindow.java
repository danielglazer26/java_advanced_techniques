package app.gui;

import app.language.format.LocalizationLanguage;
import app.language.preferences.ApplicationPreferences;
import app.server.connection.geoClient.GeoClientConnection;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

class MainWindow extends JFrame {

    private JButton buttonLanguage;
    private JPanel contentPane;
    private JLabel questionLabel;
    private JLabel answerLabel;
    private JButton nextQuestionButton;
    private JButton answer1;
    private JButton answer2;
    private JButton answer3;
    private JButton answer4;
    private final ApplicationPreferences pref;
    private final LocalizationLanguage lang;
    private final GeoClientConnection geo;
    private Timer timer;
    private int population;
    private final ArrayList<JButton> answerButtonsList;
    private int answerCorrection;
    private final ImageIcon iconPoland;
    private final ImageIcon iconUK;

    public MainWindow() {
        setContentPane(contentPane);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(800, 200);

        pref = new ApplicationPreferences();
        lang = new LocalizationLanguage(pref.getLanguageSettings());
        geo = new GeoClientConnection();

        answerButtonsList = new ArrayList<>(List.of(answer1, answer2, answer3, answer4));

        iconPoland = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource(
                "icons/icons8-poland-48.png")));
        iconUK = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource(
                "icons/icons8-great-britain-48.png")));

        nextQuestionButton.setText(lang.getTemplate("next"));

        creatButtonLanguage();
        createAnswerButtons();
        setNextQuestionButtonListener();
        createTimer();
        generateQuestion();
    }


    /**
     * Changes application language
     */
    private void creatButtonLanguage() {
        buttonLanguage.addActionListener(e -> {
            if (pref.getLanguageSettings()[0].equals("pl")) {
                pref.setPreferences("en", "EN");
                buttonLanguage.setIcon(iconPoland);
            } else {
                pref.setPreferences("pl", "PL");
                buttonLanguage.setIcon(iconUK);
            }
            lang.refreshLocal(pref.getLanguageSettings());

            nextQuestionButton.setText(lang.getTemplate("next"));
            setQuestionLabel();

            if (!answerLabel.getText().equals(""))
                setAnswerLabel();

        });
    }

    /**
     * Adds action listeners to answer buttons
     */
    private void createAnswerButtons() {
        answerButtonsList.forEach((button) -> button.addActionListener(e -> setAnswerCorrectness(button.getText())));
    }

    /**
     * Starts next question
     */
    private void setNextQuestionButtonListener() {
        nextQuestionButton.addActionListener(e -> {
            generateQuestion();
            answerLabel.setText("");
        });
    }

    /**
     * Creates a timer that activates the button from the next question
     */
    private void createTimer() {
        timer = new Timer(1000, e -> {
            nextQuestionButton.setEnabled(true);
            timer.stop();
        });
    }

    /**
     * Generates question from randomized population and country
     */
    private void generateQuestion() {
        population = generateRandomData(300000, 10000);
        geo.generateData(generateRandomData(199, 0), population);
        setQuestionLabel();
        generateAnswers();
        nextQuestionButton.setEnabled(false);
        timer.start();
    }

    /**
     * Sets question label
     */
    private void setQuestionLabel() {
        questionLabel.setText(lang.createQuestion(geo.getCountryCode(), population));
    }

    /**
     * Sets answer label
     */
    private void setAnswerLabel() {
        answerLabel.setText(lang.createAnswer(answerCorrection, geo.getCountryCode(), geo.getCityNumber(),
                population));
    }

    /**
     * Generates answers which are displayed on buttons
     */
    private void generateAnswers() {
        int answer = geo.getCityNumber();
        int offset = (int) (answer * 0.5 + 1);
        int correctAnswerButton = generateRandomData(4, 0);

        ArrayList<Integer> arrayAnswer = new ArrayList<>();


        while (arrayAnswer.size() != 4) {
            Integer number = generateRandomData(offset * 5, offset);
            if (!arrayAnswer.contains(number) && number != answer)
                arrayAnswer.add(number);
        }

        for (int i = 0; i < 4; i++) {
            if (i != correctAnswerButton)
                answerButtonsList.get(i).setText(String.valueOf(arrayAnswer.get(i)));
            else
                answerButtonsList.get(i).setText(String.valueOf(answer));
        }

    }

    /**
     * Checks answer correctness
     */
    private void setAnswerCorrectness(String cityNumber) {

        if (cityNumber.equals(String.valueOf(geo.getCityNumber()))) {
            answerCorrection = 1;
            answerLabel.setForeground(Color.GREEN);
        } else {
            answerCorrection = 0;
            answerLabel.setForeground(Color.RED);
        }
        setAnswerLabel();

    }

    /**
     * Generates random number
     */
    private int generateRandomData(int range, int offset) {
        Random random = new Random();
        return random.nextInt(range) + offset;
    }
}
