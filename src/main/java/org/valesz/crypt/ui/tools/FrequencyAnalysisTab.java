package org.valesz.crypt.ui.tools;

import javax.swing.*;

/**
 * Created by Zdenek Vales on 17.4.2017.
 */
public class FrequencyAnalysisTab {
    private JPanel mainPanel;
    private JPanel configPanel;
    private JPanel lettersPanel;
    private JPanel digramsPanel;
    private JPanel trigramsPanel;
    private JLabel languageText;
    private JLabel lettersText;
    private JLabel digramsText;
    private JLabel trigramsText;
    private JTable letterFreqTable;
    private JTable digramFreqTable;
    private JTable trigramFreqTable;
    private JButton freqAnalBtn;
    private JButton saveToFileBtn;

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
