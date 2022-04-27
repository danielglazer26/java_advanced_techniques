package glazer.daniel.pwr.app.billboard;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.rmi.RemoteException;
import java.time.Duration;

public class BillboardWindow extends JFrame {
    private JPanel contentPane;
    private JButton deleteButton;
    private JButton setButton;
    private JTextField tableSize;
    private JLabel advertisementLabel;
    private final Billboard billboard;

    public BillboardWindow(Billboard billboard) {
        setContentPane(contentPane);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.billboard = billboard;

        setButton.addActionListener(e -> billboard.setBillboardID());

        deleteButton.addActionListener(e -> {
            try {
                billboard.deleteBillboardID();
            } catch (RemoteException ex) {
                ex.printStackTrace();
            }
        });

        tableSize.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                try {
                    if (KeyEvent.VK_ENTER == e.getKeyCode())
                        billboard.setTableCapacity(Integer.parseInt(tableSize.getText()));
                } catch (NumberFormatException ex) {
                    System.out.println("Błędny format liczbowy");
                }
            }
        });

        pack();
    }

    public void setLabelAdvertisement(Duration time, String text) {
        Advertisement a = billboard.getQueue().poll();
        a.setUsedTime(Duration.ofSeconds(a.getUsedTime().toSeconds() + time.toSeconds()));
        if (a.getUsedTime().toSeconds() < a.getDisplayPeriod().toSeconds()) {
            advertisementLabel.setText(a.getAdvertisementText());
            billboard.addToQueue(a);
        } else
            advertisementLabel.setText(text);

        pack();
    }


    @Override
    public void dispose() {
        try {
            billboard.deleteBillboardID();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }
}
