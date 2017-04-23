package org.valesz.crypt.controller;

import org.valesz.crypt.core.freqanal.FrequencyAnalyser;
import org.valesz.crypt.core.freqanal.FrequencyAnalysisMethod;
import org.valesz.crypt.core.freqanal.FrequencyAnalysisResult;
import org.valesz.crypt.ui.InputPanel;
import org.valesz.crypt.ui.MainWindow;
import org.valesz.crypt.ui.tools.FrequencyAnalysisTab;

import java.util.List;

/**
 * Controller for connection between UI and core.
 *
 * Created by Zdenek Vales on 23.4.2017.
 */
public class AppController {

    public static AppController getInstance() {
        return instance;
    }

    private static AppController instance = new AppController();

    // UI components
    private MainWindow mainWindow;
    private FrequencyAnalysisTab frequencyAnalysisTab;
    private InputPanel inputPanel;

    private AppController() {

    }

    public void setFrequencyAnalysisTab(FrequencyAnalysisTab frequencyAnalysisTab) {
        this.frequencyAnalysisTab = frequencyAnalysisTab;
    }

    public void setInputPanel(InputPanel inputPanel) {
        this.inputPanel = inputPanel;
    }

    public void setMainWindow(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
    }

    public void performFrequencyAnalysis() {
        String encryptedText = inputPanel.getEncryptedText();
        int period = frequencyAnalysisTab.getPeriod();
        int offset = frequencyAnalysisTab.getOffset();

        System.out.println(String.format("Period: %d, offset: %d.",period, offset));
        if(period >= encryptedText.length()) {
            mainWindow.displayStatusMessage("Period is bigger than encrypted message length.");
            return;
        }

        if(offset >= encryptedText.length()) {
            mainWindow.displayStatusMessage("Offset is bigger thatn encrypted message length.");
            return;
        }

        FrequencyAnalyser fa = new FrequencyAnalyser(encryptedText);
        List<FrequencyAnalysisResult> letters = fa.analyse(FrequencyAnalysisMethod.Letters, period,offset);
        List<FrequencyAnalysisResult> digrams = fa.analyse(FrequencyAnalysisMethod.Digrams, period,offset);
        List<FrequencyAnalysisResult> trigrams = fa.analyse(FrequencyAnalysisMethod.Trigrams, period,offset);

        frequencyAnalysisTab.setLanguage("CZ");
        frequencyAnalysisTab.setLetterFreqAnal(letters);
        frequencyAnalysisTab.setDigramFreqAnal(digrams);
        frequencyAnalysisTab.setTriegamFreqAnal(trigrams);
    }
}
