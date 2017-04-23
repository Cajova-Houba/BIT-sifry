package org.valesz.crypt.ui.tools;

import org.valesz.crypt.controller.AppController;
import org.valesz.crypt.core.freqanal.FrequencyAnalysisResult;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;

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
    private JSpinner offsetSpinner;
    private JSpinner periodSpinner;

    private AppController controller;

    public FrequencyAnalysisTab() {
        freqAnalBtn.addActionListener(e -> controller.performFrequencyAnalysis());
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    private void createUIComponents() {
        letterFreqTable = new FrequencyAnalysisTable();
        digramFreqTable = new FrequencyAnalysisTable(Arrays.asList(new FrequencyAnalysisResult("aa",0,0)));
        trigramFreqTable = new FrequencyAnalysisTable(Arrays.asList(new FrequencyAnalysisResult("aaa",0,0)));

        periodSpinner = new JSpinner(new SpinnerNumberModel(1,1,99,1));
        offsetSpinner = new JSpinner(new SpinnerNumberModel(1,1,99,1));
    }

    public void setController(AppController controller) {
        this.controller = controller;
        controller.setFrequencyAnalysisTab(this);
    }

    public void setLetterFreqAnal(List<FrequencyAnalysisResult> data) {
        ((FrequencyAnalysisTable)letterFreqTable).fillTable(data);
        setLettersCount(Integer.toString(data.size()));
    }

    public void setDigramFreqAnal(List<FrequencyAnalysisResult> data) {
        ((FrequencyAnalysisTable)digramFreqTable).fillTable(data);
        setDigramCount(Integer.toString(data.size()));
    }

    public void setTriegamFreqAnal(List<FrequencyAnalysisResult> data) {
        ((FrequencyAnalysisTable)trigramFreqTable).fillTable(data);
        setTrigramCount(Integer.toString(data.size()));
    }

    public void setLanguage(String language) {
        languageText.setText(language);
    }

    public void setLettersCount(String cnt) {
        lettersText.setText(cnt);
    }

    public void setDigramCount(String cnt) {
        digramsText.setText(cnt);
    }

    public void setTrigramCount(String cnt) {
        trigramsText.setText(cnt);
    }
}
