package org.valesz.crypt.ui.input;

import org.valesz.crypt.controller.AppController;

import javax.swing.*;

/**
 * Created by valesz on 03.05.2017.
 */
public class InputPanel {
    private JPanel mainPanel;
    private JTextArea inputTextArea;

    private AppController controller;

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public void setController(AppController controller) {
        this.controller = controller;
        this.controller.setInputPanel(this);
    }

    public void setInputText(String inputText) {
        inputTextArea.setText(inputText);
    }

    public String getInputText() {
        return inputTextArea.getText();
    }
}
