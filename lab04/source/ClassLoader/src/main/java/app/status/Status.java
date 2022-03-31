package app.status;

public class Status {

    /**
     * Task identifier
     */
    private final int taskId;
    /**
     * Processing progress in percent
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