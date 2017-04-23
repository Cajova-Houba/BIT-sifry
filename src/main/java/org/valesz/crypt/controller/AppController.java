package org.valesz.crypt.controller;

import org.valesz.crypt.core.freqanal.FrequencyAnalyser;
import org.valesz.crypt.core.freqanal.FrequencyAnalysisMethod;
import org.valesz.crypt.core.freqanal.FrequencyAnalysisResult;
import org.valesz.crypt.ui.InputPanel;
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

    public void performFrequencyAnalysis() {
        String encryptedText = inputPanel.getEncryptedText();
        FrequencyAnalyser fa = new FrequencyAnalyser(encryptedText);
        List<FrequencyAnalysisResult> letters = fa.analyse(FrequencyAnalysisMethod.Letters, 0,0);
        List<FrequencyAnalysisResult> digrams = fa.analyse(FrequencyAnalysisMethod.Digrams, 0,0);
        List<FrequencyAnalysisResult> trigrams = fa.analyse(FrequencyAnalysisMethod.Trigrams, 0,0);

        frequencyAnalysisTab.setLanguage("CZ");
        frequencyAnalysisTab.setLetterFreqAnal(letters);
        frequencyAnalysisTab.setDigramFreqAnal(digrams);
        frequencyAnalysisTab.setTriegamFreqAnal(trigrams);
    }
}
