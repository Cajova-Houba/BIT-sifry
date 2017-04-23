package org.valesz.crypt.ui.tools;

import org.valesz.crypt.controller.AppController;

import javax.swing.*;

/**
 * Created by Zdenek Vales on 17.4.2017.
 */
public class ToolsPanel extends JTabbedPane {

    private FrequencyAnalysisTab faTab;
    private JPanel freqAnalaTab;
    private JPanel vigenereTab;

    private AppController controller;

    public ToolsPanel(AppController controller) {
        this.controller = controller;
        initComponents();
    }

    private void initComponents() {
        faTab = new FrequencyAnalysisTab();
        faTab.setController(controller);
        freqAnalaTab = faTab.getMainPanel();
        vigenereTab = new VigenereTab().getMainPanel();
        addTab("Frekvenční analýza", freqAnalaTab);
        addTab("Vigenere", vigenereTab);
    }
}
