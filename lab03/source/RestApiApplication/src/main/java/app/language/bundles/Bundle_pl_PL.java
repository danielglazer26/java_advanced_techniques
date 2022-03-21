package app.language.bundles;

import java.util.ListResourceBundle;

public class Bundle_pl_PL extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        return new Object[][]{
                {"question", "Podaj liczbę miast istniejących w {0}, których populacja jest większa niż {1}"},
                {"answerCorrect", "Tak, masz rację"},
                {"answerWrong", "Nie, nie masz racji"},
                {"answer", "{0}. W państwie {1} {2} populacja jest większa niż {3}"},
                {"zero", "nie istnieje żadne miasto, którego"},
                {"one", "istnieje jedno miasto, którego"},
                {"two-for", "istnieją {4} miasta, których"},
                {"multi", "istnieje {4} miast, których"}
        };
    }
}
