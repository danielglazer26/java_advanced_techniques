package app.processors.interfaces;

import app.status.Status;
import app.status.StatusListener;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public interface DefaultProcessorMethods {
    default ScheduledFuture<?> counter(AtomicInteger ai, int taskId,
                                       ScheduledExecutorService executorService, StatusListener sl) {

        return executorService.scheduleAtFixedRate(() -> {
                    ai.incrementAndGet();
                    sl.statusChanged(new Status(taskId, ai.get()));
                },
                1, 10, TimeUnit.MILLISECONDS);
    }

    default void finalizeTask(String task, AtomicInteger ai, ScheduledFuture<?> scheduleFuture, ScheduledExecutorService executorService) {
        ScheduledExecutorService scheduledExecutorServiceFinalize = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorServiceFinalize.scheduleAtFixedRate(() -> {
            if (ai.get() >= 100) {
                makeTask(task);
                System.out.println("finished");
                scheduleFuture.cancel(true);
                executorService.shutdown();
                scheduledExecutorServiceFinalize.shutdown();
            }
        }, 1, 10, TimeUnit.MILLISECONDS);
    }

    void makeTask(String task);
}
