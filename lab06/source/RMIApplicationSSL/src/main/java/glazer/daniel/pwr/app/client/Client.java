package glazer.daniel.pwr.app.client;

import glazer.daniel.pwr.api.billboards.IClient;
import glazer.daniel.pwr.api.billboards.IManager;
import glazer.daniel.pwr.api.billboards.Order;
import glazer.daniel.pwr.factory.CustomRMIClientSocketFactory;

import java.io.Serializable;
import java.net.InetAddress;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.time.Duration;
import java.util.HashMap;

public class Client extends UnicastRemoteObject implements IClient, Serializable {

    private static final long serialVersionUID = 1L;
    private static final int PORT = 2022;
    private IManager iManager;
    private final HashMap<Integer, Order> orders = new HashMap<>();
    private final ClientWindow clientWindow;
    private final Order order = new Order();


    protected Client() throws RemoteException {
        super();
        clientWindow = new ClientWindow(this);
        clientWindow.setVisible(true);
    }

    public static void main(String[] args) {
        try {
            new Client().makeConnection();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void makeConnection() {
        System.setProperty("javax.net.ssl.keyStore", "keys");
        System.setProperty("javax.net.ssl.keyStorePassword", "passphrase");
        System.setProperty("javax.net.ssl.trustStore", "keys");
        System.setProperty("javax.net.ssl.trustStorePassword", "passphrase");

        try {
            Registry registry = LocateRegistry.getRegistry(
                    InetAddress.getLocalHost().getHostName(),
                    PORT,
                    new CustomRMIClientSocketFactory());
            iManager = (IManager) registry.lookup("IManager");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void setOrderId(int orderId) throws RemoteException {
        orders.put(orderId, order);
    }
    public void deleteOrder(int orderID) throws RemoteException {
        if(iManager.withdrawOrder(orderID)) {
            orders.remove(orderID);
            clientWindow.updateOrderTable();
        }
    }

    public void createOrder(String text, String billboardTime) throws RemoteException {
        order.client = this;
        order.advertText = text;
        order.displayPeriod = Duration.ofSeconds(Integer.parseInt(billboardTime));

        runCallback();

        if(iManager.placeOrder(order))
            clientWindow.updateOrderTable();
    }

    private void runCallback() {
        new Thread(() -> {
            int size = orders.size();
            while (true) {
                try {
                    Thread.sleep(200);
                    if (orders.size() > size) {
                        clientWindow.buttonActivation(true);
                        break;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

    public HashMap<Integer, Order> getOrders() {
        return orders;
    }
}
