package org.valesz.crypt.ui;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Zdenek Vales on 11.4.2017.
 */
public class InputPanel extends JPanel {

    private JTextArea encTextArea;

    public InputPanel() {
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setAlignmentX(LEFT_ALIGNMENT);
        initComponents();
    }

    public void initComponents() {
        JLabel label = new JLabel("Šifrovaný text:");
        label.setAlignmentX(LEFT_ALIGNMENT);
        add(label);

        encTextArea = new JTextArea();
        encTextArea.setRows(20);
        encTextArea.setAlignmentX(0f);
        JScrollPane sp = new JScrollPane(encTextArea);
        sp.setAlignmentX(LEFT_ALIGNMENT);
        add(sp);
    }
}
