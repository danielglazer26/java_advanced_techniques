package glazer.daniel.pwr.app.manager;

import glazer.daniel.pwr.api.billboards.IBillboard;
import glazer.daniel.pwr.api.billboards.IManager;
import glazer.daniel.pwr.api.billboards.Order;
import glazer.daniel.pwr.factory.CustomRMIClientSocketFactory;
import glazer.daniel.pwr.factory.CustomRMIServerSocketFactory;

import javax.swing.*;
import java.io.Serializable;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;

public class Manager extends UnicastRemoteObject implements IManager, Serializable {

    private static final long serialVersionUID = 1L;
    private static final int PORT = 2022;
    private Integer orderId = 1;
    private Integer billboardId = 1;

    private final HashMap<Integer, Order> orders = new HashMap<>();
    private final HashMap<Integer, IBillboard> billboards = new HashMap<>();

    private final ManagerWindow managerWindow;

    protected Manager() throws RemoteException {
        super(PORT, new CustomRMIClientSocketFactory(), new CustomRMIServerSocketFactory());
        managerWindow = new ManagerWindow(this);
        managerWindow.setVisible(true);
    }

    private void createConnection() {
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        try {
            Registry registry = LocateRegistry.createRegistry(PORT, new CustomRMIClientSocketFactory(),
                    new CustomRMIServerSocketFactory());
            registry.bind("IManager", this);
        } catch (RemoteException | AlreadyBoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int bindBillboard(IBillboard billboard) throws RemoteException {
        billboards.put(billboardId, billboard);
        managerWindow.updateBillboardTable();
        return billboardId++;
    }

    @Override
    public boolean unbindBillboard(int billboardId) throws RemoteException {
        try {
            billboards.remove(billboardId);
            managerWindow.updateBillboardTable();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean placeOrder(Order order) throws RemoteException {
        try {
            if (!sendOrder(order))
                throw new Exception("Brak wolnych miejsc");
            order.client.setOrderId(orderId);
            orders.put(orderId, order);
            orderId++;
            managerWindow.updateOrderTable();
            managerWindow.updateBillboardTable();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean sendOrder(Order order) {
        AtomicBoolean freeSpace = new AtomicBoolean(false);
        billboards.forEach((integer, billboard) -> {
            try {
                if (billboard.getCapacity()[1] > 0) {
                    billboard.addAdvertisement(order.advertText, order.displayPeriod, orderId);
                    freeSpace.set(true);
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });

        return freeSpace.get();
    }

    @Override
    public boolean withdrawOrder(int orderId) throws RemoteException {
        AtomicBoolean removeOrder = new AtomicBoolean(false);
        try {
            orders.remove(orderId);
            billboards.forEach((integer, billboard) -> {
                try {
                    if (billboard.removeAdvertisement(orderId))
                        removeOrder.set(true);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            });
            managerWindow.updateOrderTable();
            managerWindow.updateBillboardTable();
            return removeOrder.get();
        } catch (Exception e) {
            return false;
        }

    }


    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("com.formdev.flatlaf.FlatDarculaLaf");
            JFrame.setDefaultLookAndFeelDecorated(true);
        } catch (UnsupportedLookAndFeelException
                | ClassNotFoundException
                | InstantiationException
                | IllegalAccessException e) {
            e.printStackTrace();
        }
        try {
            new Manager().createConnection();
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }

    public HashMap<Integer, Order> getOrders() {
        return orders;
    }

    public HashMap<Integer, IBillboard> getBillboards() {
        return billboards;
    }
}
