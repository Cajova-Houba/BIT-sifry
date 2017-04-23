package org.valesz.crypt.ui;

import org.valesz.crypt.controller.AppController;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Zdenek Vales on 11.4.2017.
 */
public class InputPanel extends JPanel {

    private JTextArea encTextArea;

    private AppController controller;

    public InputPanel(AppController controller) {
        this.controller = controller;
        this.controller.setInputPanel(this);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        initComponents();
    }

    public void initComponents() {
        JLabel label = new JLabel("Šifrovaný text:", SwingConstants.LEFT);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        add(label);

        encTextArea = new JTextArea();
        encTextArea.setRows(2);
        JScrollPane sp = new JScrollPane(encTextArea);
//        sp.setAlignmentX(JScrollPane.LEFT_ALIGNMENT);
        add(sp);
    }

    public String getEncryptedText() {
        return encTextArea.getText();
    }
}
