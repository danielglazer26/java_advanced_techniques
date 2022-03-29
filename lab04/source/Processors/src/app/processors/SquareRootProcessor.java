package app.processors;

import app.status.StatusListener;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.atomic.AtomicInteger;

public class SquareRootProcessor implements Processor, DefaultProcessorMethods {

    private static int taskId;
    private String result;

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
    public ScheduledFuture<?> counter(AtomicInteger ai, int taskId, ScheduledExecutorService executorService, StatusListener sl) {
        return DefaultProcessorMethods.super.counter(ai, taskId, executorService, sl);
    }

    @Override
    public void finalizeTask(String task, AtomicInteger ai, ScheduledFuture<?> scheduleFuture, ScheduledExecutorService executorService) {
        DefaultProcessorMethods.super.finalizeTask(task, ai, scheduleFuture, executorService);
    }

    @Override
    public String getInfo() {
        return "This class make square from number";
    }

    @Override
    public String getResult() {
        return result;
    }

    @Override
    public void makeTask(String task) {
        result = String.valueOf(Math.sqrt(Double.parseDouble(task)));
    }
}
