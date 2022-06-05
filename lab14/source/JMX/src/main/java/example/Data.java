package example;

import javax.management.openmbean.CompositeData;

public class Data implements DataMBean {
	private String s = "";
	private Integer i = 0;

	@Override
	public String getStringParam() {
		return s;
	}

	@Override
	public void setStringParam(String s) {
		this.s = s;
	}

	@Override
	public Integer getIntegerParam() {
		return i;
	}

	@Override
	public void setIntegerParam(Integer i) {
		this.i = i;
	}

	@Override
	public void method() {
	}


    public static Data from(CompositeData cd) {
    	Data d = new Data();
    	d.setIntegerParam((Integer) cd.get("integerParam"));
    	d.setStringParam((String) cd.get("stringParam"));
		return d;

    }

}
