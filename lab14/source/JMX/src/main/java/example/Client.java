package example;

import javax.management.JMX;

import javax.management.MBeanServer;
import javax.management.MBeanServerConnection;

import javax.management.ObjectName;

import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
/**
 * Klasa pozwalaj�ca si�gn�� zdalnie do ziarna instrumentacji Manager
 *
 * @author tkubik
 *
 */
public class Client {

	public static void main(String args[]) throws Exception {
		int jmxPort = 8008;
		// utworzenie po��czenia
		// port powinien zgadza� si� z portem ustawionym w opcjach,
		// z jakimi uruchomiono Manager
		JMXServiceURL target = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://localhost:" + jmxPort + "/jmxrmi");
		JMXConnector connector = JMXConnectorFactory.connect(target);

		MBeanServerConnection mbs = connector.getMBeanServerConnection();

		// informacja o dostawcy wirtualnej maszyny
		ObjectName oname = new ObjectName(ManagementFactory.RUNTIME_MXBEAN_NAME);
		String vendor = (String) mbs.getAttribute(oname, "VmVendor");
        System.out.println(vendor);

        // mo�na odczytywa� oraz zapisywa� dane w zdalnej aplikacji
		ManagerMXBean proxy = JMX.newMXBeanProxy(mbs, new ObjectName("pl.edu.pwr.tkubik:name=" + "Manager"),
				ManagerMXBean.class);
		Data2 data2 = proxy.getData2();
		proxy.setData2(new Data2(2,"2"));
		System.out.println(data2);

	}
}