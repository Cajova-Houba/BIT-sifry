package org.valesz.crypt.ui.tools.misc;

import org.valesz.crypt.controller.AppController;

import javax.swing.*;

/**
 * Created by valesz on 03.05.2017.
 */
public class MiscTab {
    private JPanel configPane;
    private JPanel outputPane;
    private JPanel selectCipherPane;
    private JRadioButton atbasRadio;
    private JRadioButton caesarRadioButton;
    private JRadioButton vigenereRadioButton;
    private JTextArea outputTextArea;
    private JButton encryptBtn;
    private JButton decryptBtn;
    private JTextField keyTextField;
    private JPanel mainPanel;

    private AppController controller;

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public void setController(AppController controller) {
        this.controller = controller;
        this.controller.setMiscTab(this);
    }
}
