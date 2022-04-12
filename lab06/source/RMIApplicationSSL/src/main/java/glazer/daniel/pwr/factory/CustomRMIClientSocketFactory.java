package glazer.daniel.pwr.factory;

import javax.net.ssl.SSLSocketFactory;
import java.io.IOException;
import java.io.Serializable;
import java.net.Socket;
import java.rmi.server.RMIClientSocketFactory;

public class CustomRMIClientSocketFactory implements RMIClientSocketFactory, Serializable {

    private static final long serialVersionUID = 1L;

    @Override
    public Socket createSocket(String host, int port) throws IOException {
        SSLSocketFactory sslSocketFactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
        return sslSocketFactory.createSocket(host, port);
    }

    public int hashCode() {
        return getClass().hashCode();
    }

    public boolean equals(Object obj) {
        if (obj == this) return true;
         else return obj != null && getClass() == obj.getClass();
    }

}
