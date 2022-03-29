package app;

import app.processors.MakeSpaceBetweenCharsProcessor;
import app.status.MyStatusListener;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        MakeSpaceBetweenCharsProcessor toLowerCaseProcessor = new MakeSpaceBetweenCharsProcessor();
        toLowerCaseProcessor.submitTask("Tekst", new MyStatusListener());


        ExecutorService executor = Executors.newSingleThreadExecutor();

        executor.submit(() -> {
            String result = null;
            while (true) {
                try {
                    Thread.sleep(800);

                    // String result = (String) getResultMethod.invoke(o,new Object[] {});
                    result =  toLowerCaseProcessor.getResult();
                } catch (InterruptedException | IllegalArgumentException e) {
                    e.printStackTrace();
                }
                if (result != null) {
                    System.out.println("Result: " + result);
                    executor.shutdown();
                    break;
                }
            }
        });
    }
}
