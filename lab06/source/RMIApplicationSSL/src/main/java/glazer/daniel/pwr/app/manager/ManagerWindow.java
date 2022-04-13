package glazer.daniel.pwr.app.manager;

import glazer.daniel.pwr.api.billboards.Order;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.rmi.RemoteException;
import java.time.Duration;
import java.time.format.DateTimeParseException;

public class ManagerWindow extends JFrame {
    private DefaultTableModel tableOrderModel, tableBillboardModel;
    private JPanel panel1;
    private JTable billboardsTable;
    private JTable ordersTable;
    private JButton startButton;
    private JButton stopButton;
    private JTextField displayIntervalField;
    private JDialog dialog;

    private final Manager manager;

    public ManagerWindow(Manager manager) {
        setContentPane(panel1);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        this.manager = manager;


        startButton.addActionListener(e -> {
            try {
                if (manager.getBillboards().get(getBillboardIdFromTable()).start())
                    createJDialog("Billboard started");
                else
                    throw new Exception();
            } catch (RemoteException ex) {
                ex.printStackTrace();
            } catch (Exception exception) {
                createJDialog("Something go wrong");
            }
        });
        stopButton.addActionListener(e -> {
            try {
                if (manager.getBillboards().get(getBillboardIdFromTable()).stop())
                    createJDialog("Billboard stopped");
                else
                    throw new Exception();
            } catch (RemoteException ex) {
                ex.printStackTrace();
            } catch (Exception exception) {
                createJDialog("Something go wrong");
            }
        });
        createOrderTable();
        createBillboardTable();
        pack();

        displayIntervalField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (KeyEvent.VK_ENTER == e.getKeyCode()) {
                    try {
                        manager.getBillboards()
                                .get(getBillboardIdFromTable())
                                .setDisplayInterval(Duration
                                        .ofSeconds(Integer.parseInt(displayIntervalField.getText())));
                    } catch (RemoteException ex) {
                        ex.printStackTrace();
                    } catch (DateTimeParseException exception) {
                        System.out.println("Zły format czasu");
                    }
                }
            }
        });
    }

    private void createJDialog(String text) {
        dialog = new JDialog(this, "Information");
        JLabel label = new JLabel(text);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        dialog.add(label);
        dialog.setBounds(300, 300, 200, 100);
        dialog.setVisible(true);
    }

    private Integer getBillboardIdFromTable() {
        return (Integer) tableBillboardModel.getValueAt(billboardsTable.getSelectedRow(), 0);
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
        tableBillboardModel.addColumn("Table buffer");
        tableBillboardModel.addColumn("Free space");
        billboardsTable.setModel(tableBillboardModel);

    }

    public void updateOrderTable() {
        tableOrderModel.setRowCount(0);
        manager.getOrders().forEach((integer, order) -> tableOrderModel.addRow(new Object[]{integer, order.advertText,
                order.displayPeriod.toSeconds(), order.client.hashCode()}));

    }

    public void updateBillboardTable() {
        tableBillboardModel.setRowCount(0);
        manager.getBillboards().forEach((integer, billboard) -> {
            try {
                int[] table = billboard.getCapacity();
                tableBillboardModel.addRow(new Object[]{integer,
                        billboard.hashCode(), table[0], table[1]});
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });

    }
}
