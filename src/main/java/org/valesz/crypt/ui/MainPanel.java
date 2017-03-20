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

    private JTabbedPane tabPanel;
    private JPanel mainPanel;
    private JPanel byHandPanel;
    private JPanel vigenerePanel;
    private JPanel handInputPanel;
    private JPanel configPanel;
    private JTextArea intputTextArea;
    private JLabel inputTextAreaLabel;
    private JTextArea complexViewTextArea;
    private JButton complexViewBtn;
    private JTable alphabetTable;
    private JLabel alphabetLabel;
    private JScrollPane alphabetTableScrollPane;
    private JPanel toolsPanel;
    private JLabel frequencyAnalysisLabel;
    private JTable frequencyAnalysisTable;
    private JScrollPane faScrollPane;
    private JButton freqAnalBtn;
    private JPanel freqAnalBtnPanel;
    private JComboBox freqAnalMethodChoice;
    private JComboBox replaceMethodChoice;
    private JScrollPane inputTextAreaScrollPane;
    private JScrollPane complexViewTextAreaScrollPane;
    private JPanel inputPanel;
    private JPanel vKeyPanel;
    private JLabel minKeyLengthLabel;
    private JSpinner minKeyLength;
    private JLabel maxKeyLengthLabel;
    private JSpinner maxKeyLength;
    private JPanel outputFilePane;
    private JLabel outputFileNameLabel;
    private JTextField outputFileDisplay;
    private JButton outputFileBtn;
    private JButton vigenereBfBtn;

    public MainPanel() {
        complexViewBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    ByHandController.get().refreshComplexView();
                } catch (ControllerNotInitializedException ex) {
                    logger.log(Level.SEVERE, ex.getMessage());
                }
            }
        });
        freqAnalBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    ByHandController.get().doFrequencyAnalysis();
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
        ByHandController.initialize(mp.intputTextArea,
                mp.complexViewTextArea,
                (AlphabetTable)mp.alphabetTable,
                (FrequencyAnalysisTable)mp.frequencyAnalysisTable,
                (FaMethodComboBox)mp.freqAnalMethodChoice,
                (FaMethodComboBox)mp.replaceMethodChoice);

        return frame;
    }

    private void createUIComponents() {
        initAlphabetTable();
        initFrequencyAnalysisTable();
        initFreqAnalMethodChoice();
        initReplaceMethodChoice();
        initSpinners();
    }

    private void initAlphabetTable() {
        alphabetTable = new AlphabetTable();
    }

    private void initFrequencyAnalysisTable() {
        frequencyAnalysisTable = new FrequencyAnalysisTable();
    }

    private void initFreqAnalMethodChoice() {
        freqAnalMethodChoice = new FaMethodComboBox();
    }

    private void initReplaceMethodChoice() {
        replaceMethodChoice = new FaMethodComboBox();
    }

    private void initSpinners() {
        SpinnerNumberModel minModel = new SpinnerNumberModel(1,1,3,1);
        minKeyLength = new JSpinner(minModel);

        SpinnerNumberModel maxModel = new SpinnerNumberModel(3,3,6,1);
        maxKeyLength = new JSpinner(maxModel);
    }


}
