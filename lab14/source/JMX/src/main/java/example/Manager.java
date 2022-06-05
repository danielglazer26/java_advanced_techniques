package example;

import java.lang.management.ManagementFactory;

import javax.management.ObjectName;
/**
 * Przyk�ad klasy s�u��cej jako ziarno MXBean
 * Klasa Manager posiada w�a�ciwo�ci:
 *  Data (MBean),
 *  Data2 (MXBean),
 *  number
 * Dwie pierwsze s� w�a�cio�ciami z�o�onymi
 * Mo�na je zobaczy� (ale nie edytowa�) w jconsole
 * Zmian� w�a�ciwo�ci mo�na wykona� zdalnie za pomoc�
 * klasy Client
 * @author tkubik
 *
 */
public class Manager implements ManagerMXBean {
	private int number = 0;
	private Data d = new Data();
	private Data2 d2 = new Data2(1, "1");

	@Override
	public Data getData() {
		return d;
	}

	@Override
	public void setData(Data d) {
		this.d = d;
	}

	@Override
	public Data2 getData2() {
		return d2;
	}

	@Override
	public void setData2(Data2 d) {
		this.d2 = d;
	}

	@Override
	public void setNumber(int i) {
		this.number = i;

	}

	@Override
	public int getNumber() {
		return this.number;
	}

	/*
	 * Nale�y uruchomi� z opcjami: -Dcom.sun.management.jmxremote
	 * -Dcom.sun.management.jmxremote.port=8008
	 * -Dcom.sun.management.jmxremote.authenticate=false
	 * -Dcom.sun.management.jmxremote.ssl=false
	 */
	public static void main(String[] args) {
		Manager impl = new Manager();
		try {
			ManagementFactory.getPlatformMBeanServer().registerMBean(impl,
					new ObjectName("pl.edu.pwr.tkubik:name=" + "Manager"));
			Thread.currentThread().join();
		} catch (Exception ex) {
			ex.printStackTrace(System.err);
		}
	}
}
