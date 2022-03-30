package app.status;


import javax.swing.*;

public class MyStatusListener extends JFrame implements StatusListener {
    private JPanel panel1;
    private JLabel taskIdLabel;
    private JLabel resultLabel;
    private JProgressBar progressTask;
    private final String className;


    public MyStatusListener(String className) {
        setContentPane(panel1);
        this.className = className;
        setVisible(true);
        setSize(400, 200);
    }

    public void setResultLabel(String result) {
        resultLabel.setText("Result: " + result);
    }

    @Override
    public void statusChanged(Status s) {
        taskIdLabel.setText(className + " task: " + s.getTaskId());
        progressTask.setValue(s.getProgress());
    }
}
