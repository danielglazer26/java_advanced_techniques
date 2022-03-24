package app.status;

public class Status {

    /**
     * identyfikator zadania
     */
    private final int taskId;
    /**
     * postęp przetwarzania w procentach
     */
    private final int progress;

    public int getProgress() {
        return progress;
    }

    public int getTaskId() {
        return taskId;
    }

    public Status(int taskId, int progress) {
        this.taskId = taskId;
        this.progress = progress;
    }
}
