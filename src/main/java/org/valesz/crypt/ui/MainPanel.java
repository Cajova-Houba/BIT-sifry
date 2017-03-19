package org.valesz.crypt.ui;

import javax.swing.*;

/**
 * Created by Zdenek Vales on 17.3.2017.
 */
public class MainPanel {
    private JTabbedPane standardCiphersTab;
    private JPanel mainPanel;
    private JPanel byHandPanel;
    private JPanel standardCiphersPanel;
    private JPanel inputPanel;
    private JPanel configPanel;
    private JTextArea inputTextArea;
    private JLabel inputTexLabel;

    private void createUIComponents() {
        inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.PAGE_AXIS));
    }

    public static JFrame crateJFrame() {
        JFrame frame = new JFrame("BIT");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.getContentPane().add(new MainPanel().mainPanel);

        return frame;
    }
}
