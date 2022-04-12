package glazer.daniel.pwr.app.manager;

import glazer.daniel.pwr.api.billboards.Order;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.rmi.RemoteException;

public class ManagerWindow extends JFrame {
    private DefaultTableModel tableOrderModel, tableBillboardModel;
    private JPanel panel1;
    private JButton addAdvertiseButton;
    private JButton deleteAdvertiseButton;
    private JTable billboardsTable;
    private JTable ordersTable;

    private final Manager manager;

    public ManagerWindow(Manager manager) {
        setContentPane(panel1);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        this.manager = manager;

        addAdvertiseButton.addActionListener(e -> {
            try {
                Integer a = (Integer) tableOrderModel.getValueAt(ordersTable.getSelectedRow(), 0);
                System.out.println(a);
                Order order = manager.getOrders().get(a);
                manager.getBillboards().get(1).addAdvertisement(order.advertText, order.displayPeriod, a);
            } catch (RemoteException ex) {
                ex.printStackTrace();
            }
        });
        deleteAdvertiseButton.addActionListener(e -> {
            try {
                manager.getBillboards().get(1).removeAdvertisement(1);
            } catch (RemoteException ex) {
                ex.printStackTrace();
            }
        });

        createOrderTable();
        createBillboardTable();
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
        tableOrderModel.addColumn("Client");
        ordersTable.setModel(tableOrderModel);

    }

    private void createBillboardTable() {
        tableBillboardModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableBillboardModel.addColumn("Billboard ID");
        tableBillboardModel.addColumn("IBillboard");
        billboardsTable.setModel(tableBillboardModel);

    }

    public void updateOrderTable() {
        tableOrderModel.setRowCount(0);
        manager.getOrders().forEach((integer, order) -> tableOrderModel.addRow(new Object[]{integer, order.advertText,
                order.displayPeriod.toSeconds(), order.client.hashCode()}));

    }

    public void updateBillboardTable() {
        tableBillboardModel.setRowCount(0);
        manager.getBillboards().forEach((integer, billboard) -> tableBillboardModel.addRow(new Object[]{integer,
                billboard.hashCode()}));

    }
}
