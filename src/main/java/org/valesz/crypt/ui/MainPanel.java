package org.valesz.crypt.ui;

import org.valesz.crypt.controller.ByHandController;
import org.valesz.crypt.controller.ControllerNotInitializedException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Main panel, creates JFrame which holds app's GUI.
 *
 * Created by Zdenek Vales on 17.3.2017.
 */
public class MainPanel {
    private static final Logger logger = Logger.getLogger(MainPanel.class.getName());

    private JTabbedPane standardCiphersTab;
    private JPanel mainPanel;
    private JPanel byHandPanel;
    private JPanel standardCiphersPanel;
    private JPanel inputPanel;
    private JPanel configPanel;
    private JTextArea intputTextArea;
    private JLabel inputTextAreaLabel;
    private JLabel complexViewTextAreaLabel;
    private JTextArea complexViewTextArea;
    private JButton refreshBtn;
    private JTable alphabetTable;
    private JLabel alphabetLabel;
    private JScrollPane alphabetTableScrollPane;

    public MainPanel() {
        refreshBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    ByHandController.get().refreshComplexView();
                } catch (ControllerNotInitializedException ex) {
                    logger.log(Level.SEVERE, ex.getMessage());
                }
            }
        });
    }

    public static JFrame crateJFrame() {
        JFrame frame = new JFrame("BIT");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        MainPanel mp = new MainPanel();

        frame.getContentPane().add(mp.mainPanel);
        frame.pack();

        // initialize controllers
        ByHandController.initialize(mp.intputTextArea, mp.complexViewTextArea, (AlphabetTable)mp.alphabetTable);

        return frame;
    }

    private void createUIComponents() {
        initAlphabetTable();
    }

    private void initAlphabetTable() {
        alphabetTable = new AlphabetTable();
    }
}
