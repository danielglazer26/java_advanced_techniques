package glazer.daniel.pwr.app.billboard;

import glazer.daniel.pwr.api.billboards.IBillboard;
import glazer.daniel.pwr.api.billboards.IManager;
import glazer.daniel.pwr.factory.CustomRMIClientSocketFactory;

import java.io.Serializable;
import java.net.InetAddress;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.time.Duration;
import java.util.HashMap;

public class Billboard extends UnicastRemoteObject implements IBillboard, Serializable {

    private static final long serialVersionUID = 1L;
    private static final int PORT = 2022;
    private IManager iManager;

    private int billboardID = 0;
    private int tableCapacity;
    private Duration displayInterval;
    private final BillboardWindow billboardWindow;


    private final HashMap<Integer, Advertisement> advertisements = new HashMap<>();

    protected Billboard() throws RemoteException {
        super();
        tableCapacity = 3;
        billboardWindow = new BillboardWindow(this);
        billboardWindow.setVisible(true);
    }

    public static void main(String[] args) {
        try {
            new Billboard().makeConnection();
        } catch (RemoteException ignored) {

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

    public void setBillboardID() {
        try {
            deleteBillboardID();
            billboardID = iManager.bindBillboard(this);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void deleteBillboardID() throws RemoteException {
        if (billboardID != 0)
            if (iManager.unbindBillboard(billboardID))
                billboardID = 0;

    }


    @Override
    public boolean addAdvertisement(String advertText, Duration displayPeriod, int orderId) throws RemoteException {
        if (tableCapacity - advertisements.size() > 0) {
            advertisements.put(orderId, new Advertisement(advertText, displayPeriod));
            return true;
        }
        return false;
    }

    @Override
    public boolean removeAdvertisement(int orderId) throws RemoteException {
        try {
            advertisements.remove(orderId);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public int[] getCapacity() throws RemoteException {
        return new int[]{tableCapacity, tableCapacity - advertisements.size()};
    }

    @Override
    public void setDisplayInterval(Duration displayInterval) throws RemoteException {
        this.displayInterval = displayInterval;
    }

    @Override
    public boolean start() throws RemoteException {
        return false;
    }

    @Override
    public boolean stop() throws RemoteException {
        return false;
    }

    public Duration getDisplayInterval() {
        return displayInterval;
    }

    public int getTableCapacity() {
        return tableCapacity;
    }

    public void setTableCapacity(int tableCapacity) {
        this.tableCapacity = tableCapacity;
    }
}
