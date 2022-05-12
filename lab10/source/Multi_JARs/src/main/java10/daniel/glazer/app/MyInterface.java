package daniel.glazer.app;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public interface MyInterface {

    default String textSorter(String text) {
        var words = text.split(" ");
        var list = List.of(words);

        var sortedList = list.stream().sorted().collect(Collectors.toList());

        var array = sortedList.toArray(new String[0]);
        return makeNumerations(array);
    }

    private String makeNumerations(String[] array) {
        var stringBuilder = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            stringBuilder.append(i + 1).append(". ").append(array[i]).append(" ");
        }
        return stringBuilder.toString();
    }

}

