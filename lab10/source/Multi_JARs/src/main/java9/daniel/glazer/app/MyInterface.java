package daniel.glazer.app;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public interface MyInterface {

    default String textSorter(String text) {
        String[] words = text.split(" ");
        List<String> list = List.of(words);

        List<String> sortedList = list.stream().sorted().collect(Collectors.toList());

        String[] array = sortedList.toArray(new String[0]);
        return makeNumerations(array);
    }

    private String makeNumerations(String[] array) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            stringBuilder.append(i + 1).append(". ").append(array[i]).append(" ");
        }
        return stringBuilder.toString();
    }

}

