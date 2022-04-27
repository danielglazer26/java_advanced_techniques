package glazer.daniel.pwr.factory;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocketFactory;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.rmi.server.RMIServerSocketFactory;
import java.security.*;
import java.security.cert.CertificateException;

public class CustomRMIServerSocketFactory implements RMIServerSocketFactory {

    private SSLServerSocketFactory sslServerSocketFactory = null;


    public CustomRMIServerSocketFactory() {
        try {
            char[] passphrase = "passphrase".toCharArray();

            KeyStore keyStore = KeyStore.getInstance("JKS");
            keyStore.load(new FileInputStream("keys"), passphrase);

            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("SunX509");
            keyManagerFactory.init(keyStore, passphrase);

            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(keyManagerFactory.getKeyManagers(), null, null);

            sslServerSocketFactory = sslContext.getServerSocketFactory();
        } catch (NoSuchAlgorithmException | CertificateException | KeyStoreException | IOException | UnrecoverableKeyException | KeyManagementException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ServerSocket createServerSocket(int port) throws IOException {
        return sslServerSocketFactory.createServerSocket(port);
    }
    public int hashCode() {
        return getClass().hashCode();
    }

    public boolean equals(Object obj) {
        if (obj == this) return true;
         else return obj != null && getClass() == obj.getClass();
    }

}
