package app.status;

public class MyStatusListener implements StatusListener {
    @Override
    public void statusChanged(Status s) {
        System.out.println("Progress:" + s.getProgress() + " TaskId:" + s.getTaskId());
    }
}