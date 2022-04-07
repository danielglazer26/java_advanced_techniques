package glazer.daniel.pwr.factory;

import java.io.IOException;
import java.net.ServerSocket;
import java.rmi.server.RMIServerSocketFactory;

public class CustomRMIServerSocketFactory implements RMIServerSocketFactory {
    @Override
    public ServerSocket createServerSocket(int port) throws IOException {
        return null;
    }
}
