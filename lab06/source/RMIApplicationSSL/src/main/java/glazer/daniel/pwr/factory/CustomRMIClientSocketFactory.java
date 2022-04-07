package glazer.daniel.pwr.factory;

import java.io.IOException;
import java.net.Socket;
import java.rmi.server.RMIClientSocketFactory;

public class CustomRMIClientSocketFactory implements RMIClientSocketFactory {
    @Override
    public Socket createSocket(String host, int port) throws IOException {
        return null;
    }
}
