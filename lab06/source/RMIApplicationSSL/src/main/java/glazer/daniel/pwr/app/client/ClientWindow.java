package glazer.daniel.pwr.app.client;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.rmi.RemoteException;

public class ClientWindow extends JFrame {
    private DefaultTableModel tableOrderModel;
    private JPanel contentPane;
    private JTextField billboardText;
    private JTextField duration;
    private JButton createNewOrderButton;
    private JButton deleteOrderButton;
    private JTable orderClientTable;

    private final Client client;

    public ClientWindow(Client client) throws HeadlessException {
        setContentPane(contentPane);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        this.client= client;

        createNewOrderButton.addActionListener(e -> {
            try {
                client.createOrder(billboardText.getText(), duration.getText());
                buttonActivation(false);
            } catch (RemoteException ex) {
                ex.printStackTrace();
            }
        });

        deleteOrderButton.addActionListener(e -> {
            try {
                client.deleteOrder((Integer)tableOrderModel.getValueAt(orderClientTable.getSelectedRow(), 0));
            } catch (RemoteException ex) {
                ex.printStackTrace();
            }
        });

        createOrderTable();
        pack();
    }

    private void createOrderTable() {
        tableOrderModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableOrderModel.addColumn("Order ID");
        tableOrderModel.addColumn("Text");
        tableOrderModel.addColumn("Duration");
        orderClientTable.setModel(tableOrderModel);

    }

    public void updateOrderTable() {
        tableOrderModel.setRowCount(0);
        client.getOrders().forEach((integer, order) -> tableOrderModel.addRow(new Object[]{integer, order.advertText,
                order.displayPeriod.toSeconds()}));

    }


    public void buttonActivation(boolean enable){
        createNewOrderButton.setEnabled(enable);
    }
}
