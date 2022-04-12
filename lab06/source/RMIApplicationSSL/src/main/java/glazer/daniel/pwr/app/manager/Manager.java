package glazer.daniel.pwr.app.manager;

import glazer.daniel.pwr.api.billboards.IBillboard;
import glazer.daniel.pwr.api.billboards.IManager;
import glazer.daniel.pwr.api.billboards.Order;
import glazer.daniel.pwr.factory.CustomRMIClientSocketFactory;
import glazer.daniel.pwr.factory.CustomRMIServerSocketFactory;

import java.io.Serializable;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;

public class Manager extends UnicastRemoteObject implements IManager, Serializable {

    private static final long serialVersionUID = 1L;
    private static final int PORT = 2022;
    private Integer orderId = 1;
    private Integer billboardId = 1;

    private final HashMap<Integer, Order> orders = new HashMap<>();
    private final HashMap<Integer, IBillboard> billboards = new HashMap<>();

    private ManagerWindow managerWindow;

    protected Manager() throws RemoteException {
        super(PORT, new CustomRMIClientSocketFactory(), new CustomRMIServerSocketFactory());
        managerWindow = new ManagerWindow(this);
        managerWindow.setVisible(true);
    }

    private void createConnection() {
        if(System.getSecurityManager() == null){
            System.setSecurityManager(new SecurityManager());
        }
        try {
            Registry registry = LocateRegistry.createRegistry(PORT,  new CustomRMIClientSocketFactory(),
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
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean placeOrder(Order order) throws RemoteException {
        try{
            order.client.setOrderId(orderId);
            orders.put(orderId++, order);
            managerWindow.updateOrderTable();
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean withdrawOrder(int orderId) throws RemoteException {
        try {
            orders.remove(orderId);
            managerWindow.updateOrderTable();
            return true;
        }catch (Exception e){
            return false;
        }

    }


    public static void main(String[] args) {
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
