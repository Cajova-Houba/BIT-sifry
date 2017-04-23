package org.valesz.crypt.ui;

import org.valesz.crypt.controller.AppController;
import org.valesz.crypt.ui.tools.ToolsPanel;

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
    private JTabbedPane toolsPanel;

    private AppController controller;

    public MainWindow(AppController controller){
        super(TITLE);
        this.controller = controller;
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));
        initComponents();
        pack();
    }

    private void initComponents() {
        inputPanel = new InputPanel(controller);
        toolsPanel = new ToolsPanel(controller);
        this.getContentPane().add(inputPanel);
        this.getContentPane().add(toolsPanel);
    }
}
