package org.valesz.crypt.controller;

import org.valesz.crypt.core.dictionary.DictionaryLoader;
import org.valesz.crypt.core.dictionary.DictionaryService;
import org.valesz.crypt.core.dictionary.IDictionary;
import org.valesz.crypt.core.dictionary.NotADictionaryFileException;
import org.valesz.crypt.core.freqanal.FrequencyAnalyser;
import org.valesz.crypt.core.freqanal.FrequencyAnalysisMethod;
import org.valesz.crypt.core.freqanal.FrequencyAnalysisResult;
import org.valesz.crypt.ui.InputPanel;
import org.valesz.crypt.ui.MainWindow;
import org.valesz.crypt.ui.tools.dictionary.DictionaryTab;
import org.valesz.crypt.ui.tools.freqAnal.FrequencyAnalysisTab;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

/**
 * Controller for connection between UI and core.
 *
 * Created by Zdenek Vales on 23.4.2017.
 */
public class AppController {

    public static final Logger logger = Logger.getLogger(AppController.class.getName());

    public static AppController getInstance() {
        return instance;
    }

    private static AppController instance = new AppController();

    // UI components
    private MainWindow mainWindow;
    private FrequencyAnalysisTab frequencyAnalysisTab;
    private InputPanel inputPanel;
    private DictionaryTab dictionaryTab;

    private AppController() {

    }

    public void setFrequencyAnalysisTab(FrequencyAnalysisTab frequencyAnalysisTab) {
        this.frequencyAnalysisTab = frequencyAnalysisTab;
    }

    public void setDictionaryTab(DictionaryTab dictionaryTab) {
        this.dictionaryTab = dictionaryTab;
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
            displayStatus("Period is bigger than encrypted message length.");
            return;
        }

        if(offset >= encryptedText.length()) {
            displayStatus("Offset is bigger thatn encrypted message length.");
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

    public void displayStatus(String status) {
        mainWindow.displayStatusMessage(status);
    }

    public void addDictionary(String filePath) {

        if(filePath.isEmpty()) {
            displayStatus("No file path.");
            return;
        }

        File dictFile = new File(filePath);
        if(!dictFile.exists()) {
            displayStatus("File does not exist.");
            return;
        }

        try {
            IDictionary dictionary = DictionaryLoader.loadDictionaryFromFile(filePath);
            DictionaryService.getInstance().addDictionary(dictionary);
        } catch (IOException e) {
            logger.severe("Exception ("+e.getClass()+") while loading dictionary: "+e.getMessage());
            displayStatus("Error occurred while loading the dictionary.");
            return;
        } catch (NotADictionaryFileException e) {
            logger.severe("Format error while loading dictionary: "+e.getMessage());
            displayStatus("File format error: "+e.getMessage());
            return;
        }

        dictionaryTab.updateDictionaryList();
    }


}
