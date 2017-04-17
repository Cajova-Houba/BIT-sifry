package org.valesz.crypt.ui.tools;

import javax.swing.*;

/**
 * Created by Zdenek Vales on 17.4.2017.
 */
public class ToolsPanel extends JTabbedPane {

    private JPanel freqAnalaTab;
    private JPanel vigenereTab;

    public ToolsPanel() {
        initComponents();
    }

    private void initComponents() {
        freqAnalaTab = new FrequencyAnalysisTab().getMainPanel();
        vigenereTab = new VigenereTab().getMainPanel();
        addTab("Frekvenční analýza", freqAnalaTab);
        addTab("Vigenere", vigenereTab);
    }
}
