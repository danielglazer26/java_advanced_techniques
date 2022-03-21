package app.language.bundles;

import java.util.ListResourceBundle;

public class Bundle_en extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        return new Object[][]{
                {"question", "Give the number of cities existing in {0}, with a population greater than {1}"},
                {"answerCorrect", "Yes, you're right"},
                {"answerWrong", "No, you're not right"},
                {"answer", "{0}. In the state of {1} there {2} whose population is greater than {3}"},
                {"zero", "is no city"},
                {"one", "is one city"},
                {"two-for", "are {4} cities"},
                {"multi", "are {4} cities"}
        };
    }
}
