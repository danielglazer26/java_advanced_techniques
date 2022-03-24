package app.processors;

import app.status.StatusListener;

public class SquareRootProcessor implements Processor{

    @Override
    public boolean submitTask(String task, StatusListener sl) {
        return false;
    }

    @Override
    public String getInfo() {
        return null;
    }

    @Override
    public String getResult() {
        return null;
    }
}
