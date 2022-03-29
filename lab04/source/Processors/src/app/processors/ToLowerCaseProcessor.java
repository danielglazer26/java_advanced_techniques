package app.processors;

import app.status.StatusListener;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.atomic.AtomicInteger;

public class ToLowerCaseProcessor implements Processor, DefaultProcessorMethods {
    private String result;
    private static int taskId;

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
        return "This class make all letters lower case";
    }

    @Override
    public String getResult() {
        return result;
    }

    @Override
    public void makeTask(String task) {
        result = task.toLowerCase();
    }
}
