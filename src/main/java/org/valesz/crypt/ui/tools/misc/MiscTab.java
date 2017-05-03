package org.valesz.crypt.ui.tools.misc;

import org.valesz.crypt.controller.AppController;
import org.valesz.crypt.core.EncryptionMethodType;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
    private JButton loadInputBtn;
    private JButton saveOutputBtn;

    private AppController controller;

    public MiscTab() {
        encryptBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.encrypt();
            }
        });
        decryptBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.decrypt();
            }
        });
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public void setController(AppController controller) {
        this.controller = controller;
        this.controller.setMiscTab(this);
    }

    public EncryptionMethodType getSelectedMethodType() {
        if(atbasRadio.isSelected()) {
            return EncryptionMethodType.Atbas;
        } else if(caesarRadioButton.isSelected()) {
            return EncryptionMethodType.Caesar;
        } else if(vigenereRadioButton.isSelected()) {
            return EncryptionMethodType.Vigenere;
        } else {
            return EncryptionMethodType.Atbas;
        }
    }

    public String getKey() {
        return keyTextField.getText();
    }

    public void setOutputText(String text) {
        outputTextArea.setText(text);
    }

}
