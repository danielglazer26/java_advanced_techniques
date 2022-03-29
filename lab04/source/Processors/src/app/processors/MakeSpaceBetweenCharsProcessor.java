package app.processors;

import app.status.StatusListener;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.atomic.AtomicInteger;

public class MakeSpaceBetweenCharsProcessor implements Processor, DefaultProcessorMethods {
    private String result;
    private int taskId;

    @Override
    public boolean submitTask(String task, StatusListener sl) {
        taskId++;
        AtomicInteger ai = new AtomicInteger(0);

        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        ScheduledFuture<?> scheduleFuture = counter(ai, taskId, executorService, sl);
        finalizeTask(task, ai, scheduleFuture, executorService);

        return true;
    }

    @Override
    public String getInfo() {
        return "This class make space between chars";
    }

    @Override
    public String getResult() {
        return result;
    }

    @Override
    public void makeTask(String task) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < task.length() - 1; i++) {
            stringBuilder.append(task.charAt(i)).append(" ");
        }
        stringBuilder.append(task.charAt(task.length() - 1));
        result = stringBuilder.toString();
    }
}
