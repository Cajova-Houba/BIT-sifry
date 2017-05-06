package org.valesz.crypt.ui;

import org.valesz.crypt.controller.AppController;
import org.valesz.crypt.ui.input.InputPanel;
import org.valesz.crypt.ui.tools.ToolsPanel;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;

/**
 * Created by Zdenek Vales on 11.4.2017.
 */
public class MainWindow extends JFrame {

    public static final String TITLE = "BIT - Cryptor";
    public static final int WIDTH = 800;
    public static final int HEIGHT = 700;

    private InputPanel inputPanel;
    private JTabbedPane toolsPanel;
    private JLabel statusLabel;

    private AppController controller;

    public MainWindow(AppController controller){
        super(TITLE);
        this.controller = controller;
        controller.setMainWindow(this);
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        initComponents();
        pack();
    }

    private void initComponents() {
        inputPanel = new InputPanel();
        inputPanel.setController(controller);
        toolsPanel = new ToolsPanel(controller);
        this.getContentPane().add(inputPanel.getMainPanel(), BorderLayout.NORTH);
        this.getContentPane().add(toolsPanel, BorderLayout.CENTER);
        initStatusPanel();
    }

    private void initStatusPanel() {
        JPanel statusPanel = new JPanel();
        statusPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
        this.add(statusPanel, BorderLayout.SOUTH);
        statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.X_AXIS));
        statusPanel.setPreferredSize(new Dimension(WIDTH, 20));
        statusPanel.setAlignmentX(0f);
        statusLabel = new JLabel("Status: Running.");
        statusLabel.setHorizontalAlignment(SwingConstants.LEFT);
        statusLabel.setAlignmentX(0f);
        statusPanel.add(statusLabel);
    }

    public void displayStatusMessage(String message) {
        statusLabel.setText("Status: "+message);
    }
}
