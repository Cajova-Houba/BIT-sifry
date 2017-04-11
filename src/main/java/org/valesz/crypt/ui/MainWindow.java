package org.valesz.crypt.ui;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Zdenek Vales on 11.4.2017.
 */
public class MainWindow extends JFrame {

    public static final String TITLE = "BIT - Cryptor";
    public static final int WIDTH = 600;
    public static final int HEIGHT = 600;

    private JPanel inputPanel;
    private JPanel toolsPanel;

    public MainWindow(){
        super(TITLE);
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        initComponents();
        pack();
    }

    private void initComponents() {
        inputPanel = new InputPanel();
        add(inputPanel);
    }
}
