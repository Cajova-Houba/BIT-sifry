package org.valesz.crypt.ui.tools.freqAnal;

import org.valesz.crypt.controller.AppController;
import org.valesz.crypt.core.freqanal.FrequencyAnalysisResult;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Zdenek Vales on 17.4.2017.
 */
public class FrequencyAnalysisTab {
    private JPanel mainPanel;
    private JPanel configPanel;
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
    private JPanel trigamsPanel;
    private JPanel letterPanel;
    private JPanel digramsPanel;

    private AppController controller;

    private File csvFile;

    public FrequencyAnalysisTab() {
        freqAnalBtn.addActionListener(e -> controller.performFrequencyAnalysis());
        saveToFileBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();

                int retVal = fileChooser.showOpenDialog(getMainPanel());

                if(retVal == JFileChooser.APPROVE_OPTION) {
                    csvFile = fileChooser.getSelectedFile();
                    controller.saveFrequencyAnalysis();
                } else {
                    csvFile = null;
                }
            }
        });
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
        this.controller.setFrequencyAnalysisTab(this);
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

    public FrequencyAnalysisResult[] getLetterData() {
        return ((FrequencyAnalysisTableModel)letterFreqTable.getModel()).getData();
    }

    public FrequencyAnalysisResult[] getDigramData() {
        return ((FrequencyAnalysisTableModel)digramFreqTable.getModel()).getData();
    }

    public FrequencyAnalysisResult[] getTrigramData() {
        return ((FrequencyAnalysisTableModel)trigramFreqTable.getModel()).getData();
    }

    public File getCsvFile() {
        return csvFile;
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

    public int getPeriod() {
        return (int)periodSpinner.getValue();
    }

    public int getOffset() {
        return (int)offsetSpinner.getValue();
    }
}
