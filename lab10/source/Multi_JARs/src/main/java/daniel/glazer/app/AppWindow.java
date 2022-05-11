package daniel.glazer.app;

import javax.swing.*;
import java.awt.*;

import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

public class AppWindow  {

    private JFrame frame;

    public AppWindow() {
        initialize();


    }
    public void setVisible(boolean bln){
        frame.setVisible(bln);
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 450, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{0, 0, 0};
        gridBagLayout.rowHeights = new int[]{0, 0};
        gridBagLayout.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
        gridBagLayout.rowWeights = new double[]{1.0, Double.MIN_VALUE};
        frame.getContentPane().setLayout(gridBagLayout);

        JPanel panel_1 = new JPanel();
        GridBagConstraints gbc_panel_1 = new GridBagConstraints();
        gbc_panel_1.anchor = GridBagConstraints.NORTH;
        gbc_panel_1.insets = new Insets(0, 0, 0, 5);
        gbc_panel_1.fill = GridBagConstraints.HORIZONTAL;
        gbc_panel_1.gridx = 0;
        gbc_panel_1.gridy = 0;
        frame.getContentPane().add(panel_1, gbc_panel_1);
        GridBagLayout gbl_panel_1 = new GridBagLayout();
        gbl_panel_1.columnWidths = new int[] {0};
        gbl_panel_1.rowHeights = new int[] {0, 0};
        gbl_panel_1.columnWeights = new double[]{0.0};
        gbl_panel_1.rowWeights = new double[]{0.0, Double.MIN_VALUE};
        panel_1.setLayout(gbl_panel_1);

        JLabel windowField = new JLabel("");
        GridBagConstraints gbc_windowField = new GridBagConstraints();
        gbc_windowField.anchor = GridBagConstraints.NORTH;
        gbc_windowField.insets = new Insets(0, 0, 0, 5);
        gbc_windowField.gridx = 0;
        gbc_windowField.gridy = 0;
        panel_1.add(windowField, gbc_windowField);

        JPanel panel = new JPanel();
        GridBagConstraints gbc_panel = new GridBagConstraints();
        gbc_panel.gridwidth = 2;
        gbc_panel.fill = GridBagConstraints.BOTH;
        gbc_panel.gridx = 1;
        gbc_panel.gridy = 0;
        frame.getContentPane().add(panel, gbc_panel);
        GridBagLayout gbl_panel = new GridBagLayout();
        gbl_panel.columnWidths = new int[] {0, 0};
        gbl_panel.rowHeights = new int[]{0, 0};
        gbl_panel.columnWeights = new double[]{0.0, 0.0};
        gbl_panel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
        panel.setLayout(gbl_panel);

        JLabel lblNewLabel_1 = new JLabel("Twoja wersja Javy:");
        GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
        gbc_lblNewLabel_1.insets = new Insets(0, 0, 0, 5);
        gbc_lblNewLabel_1.gridx = 0;
        gbc_lblNewLabel_1.gridy = 0;
        panel.add(lblNewLabel_1, gbc_lblNewLabel_1);

        JLabel javaField = new JLabel("");
        GridBagConstraints gbc_javaField = new GridBagConstraints();
        gbc_javaField.fill = GridBagConstraints.HORIZONTAL;
        gbc_javaField.gridx = 1;
        gbc_javaField.gridy = 0;
        panel.add(javaField, gbc_javaField);

        frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        windowField.setText("To jest okno dla Javy 8");
        javaField.setText(System.getProperty("java.version"));
        frame.pack();
    }


}
