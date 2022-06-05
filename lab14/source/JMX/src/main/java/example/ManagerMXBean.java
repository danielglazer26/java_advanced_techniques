package example;


public interface ManagerMXBean {
    public Data getData();
    public void setData(Data d);

    public Data2 getData2();
    public void setData2(Data2 d);
    public void setNumber(int i);
    public int getNumber();

}
