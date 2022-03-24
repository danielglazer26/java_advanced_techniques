package app.processors;

import app.status.StatusListener;

public class ToLowerCaseProcessor implements Processor {
    @Override
    public boolean submitTask(String task, StatusListener sl) {
        return false;
    }

    @Override
    public String getInfo() {
        return "This class make all letters lower case";
    }

    @Override
    public String getResult() {
        return null;
    }
}
