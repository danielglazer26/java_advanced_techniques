package example;

import java.beans.ConstructorProperties;
import java.util.ArrayList;
import java.util.List;

import javax.management.openmbean.CompositeData;
import javax.management.openmbean.CompositeDataSupport;
import javax.management.openmbean.CompositeDataView;
import javax.management.openmbean.CompositeType;
import javax.management.openmbean.OpenDataException;
import javax.management.openmbean.OpenType;
import javax.management.openmbean.SimpleType;

public class Data2 implements CompositeDataView {

	private String s = "";
	private Integer i = 0;

	public String getStringParam() {
		return s;
	}

	public void setStringParam(String s) {
		this.s = s;
	}

	public int getIntegerParam() {
		return i;
	}

	public void setIntegerParam(int i) {
		this.i = i;
	}

	public void method() {
	}

	public static Data2 from(CompositeData cd) {
		return new Data2((Integer) cd.get("integerParam"), (String) cd.get("stringParam"));

	}
    // Nale�y zastosowa� adnotowany konstruktor albo
	// zaimplementowa� CompositeDataView
	// @ConstructorProperties({ "integerParam", "stringParam" })
	public Data2(int i, String s) {
		this.i = i;
		this.s = s;
	}

	// Implementacja metody z interfejsu CompositeDataView
	@Override
	public CompositeData toCompositeData(CompositeType ct) {
		try {
			CompositeData cd = new CompositeDataSupport(ct, new String[] { "integerParam", "stringParam" },
					new Object[] { i, s });
			assert ct.isValue(cd);
			return cd;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/*  Inna implementacja (zamiast tworzy� CompositeData na bazie oczekiwanego ct - CompositeType
	 *  stworzono support na bazie w�asnej definicji CompositeType)
	@Override
	public CompositeData toCompositeData(CompositeType ct) {
		CompositeDataSupport support = null;
		try {
			support = new CompositeDataSupport(
					new CompositeType("Data2 data type", "dta type for data2 information",
							new String[] { "integerParam", "stringParam" }, new String[] { "intp desc", "strp desc" },
							new OpenType[] { SimpleType.INTEGER, SimpleType.STRING }),
					new String[] { "integerParam", "stringParam" },
					new Object[] { getIntegerParam(), getStringParam() });
		} catch (OpenDataException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}
		return support;
	}
	 */
}
