package org.valesz.crypt.ui.tools;

import org.valesz.crypt.controller.AppController;
import org.valesz.crypt.ui.tools.dictionary.DictionaryTab;
import org.valesz.crypt.ui.tools.freqAnal.FrequencyAnalysisTab;
import org.valesz.crypt.ui.tools.misc.MiscTab;
import org.valesz.crypt.ui.tools.vigenere.VigenereTab;

import javax.swing.*;

/**
 * Created by Zdenek Vales on 17.4.2017.
 */
public class ToolsPanel extends JTabbedPane {

    private FrequencyAnalysisTab faTab;
    private DictionaryTab dictionaryTab;
    private VigenereTab vigenereTab;
    private MiscTab miscTab;

    private AppController controller;

    public ToolsPanel(AppController controller) {
        this.controller = controller;
        initComponents();
    }

    private void initComponents() {
        faTab = new FrequencyAnalysisTab();
        faTab.setController(controller);
        vigenereTab = new VigenereTab();
        vigenereTab.setController(controller);
        dictionaryTab = new DictionaryTab();
        dictionaryTab.setController(controller);
        miscTab = new MiscTab();
        miscTab.setController(controller);
        addTab("Frekvenční analýza", faTab.getMainPanel());
        addTab("Vigenere", vigenereTab.getMainPanel());
        addTab("Slovníky", dictionaryTab.getMainPanel());
        addTab("Ostatní", miscTab.getMainPanel());
    }
}
