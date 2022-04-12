package glazer.daniel.pwr.app.billboard;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.rmi.RemoteException;

public class BillboardWindow extends JFrame {
    private JPanel panel1;
    private JButton deleteButton;
    private JButton setButton;
    private JTextField tableSize;
    private Billboard billboard;

    public BillboardWindow(Billboard billboard) {
        setContentPane(panel1);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.billboard = billboard;

        setButton.addActionListener(e -> {
            billboard.setBillboardID();
        });

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
                }catch (NumberFormatException ex){
                    System.out.println("Błędny format liczbowy");
                }
            }
        });

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
