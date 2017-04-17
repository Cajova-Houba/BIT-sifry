package org.valesz.crypt.ui.tools;

import javax.swing.*;

/**
 * Created by Zdenek Vales on 17.4.2017.
 */
public class VigenereTab {
    private JPanel mainPanel;
    private JTextField keyFilePath;
    private JSpinner keyLenFrom;
    private JSpinner keyLenTo;
    private JPanel keyPanel;
    private JButton breakButton;
    private JTextArea textArea1;

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
