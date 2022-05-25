import javax.swing.*;
import java.awt.event.*;
import java.util.Arrays;
import java.util.stream.DoubleStream;

public class MainWindow extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JTextField tableToSort;
    private JCheckBox sortOrder;
    public Double[] a;
    public Boolean order;


    public MainWindow() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        buttonOK.addActionListener((e)->{
            a = Arrays.stream(tableToSort.getText().split(" "))
                    .map(Double::parseDouble)
                    .toArray(Double[]::new);
            order = sortOrder.isSelected();
            dispose();
        });
    }


    public static MainWindow startWindow() {
        MainWindow dialog = new MainWindow();
        dialog.pack();
        dialog.setVisible(true);
        return dialog;
    }

    public static void main(String[] args) {
        NativeSort nativeSort = new NativeSort();
        System.out.println("Sort1: ");
        Arrays.stream(nativeSort.sort01(new Double[]{5.0, 2.0, 10.0, 15.0}, true)).forEach(System.out::println);

        System.out.println("Sort2: ");
        nativeSort.order = false;
        Arrays.stream(nativeSort.sort02(new Double[]{5.0, 2.0, 10.0, 15.0})).forEach(System.out::println);

        System.out.println("Sort3 i Sort4: ");
        nativeSort.sort03();
        Arrays.stream(nativeSort.b).forEach(System.out::println);

        System.exit(0);
    }
}
