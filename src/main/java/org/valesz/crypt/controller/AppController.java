package org.valesz.crypt.controller;

import org.valesz.crypt.core.Cryptor;
import org.valesz.crypt.core.EncryptionMethodType;
import org.valesz.crypt.core.dictionary.DictionaryLoader;
import org.valesz.crypt.core.dictionary.DictionaryService;
import org.valesz.crypt.core.dictionary.IDictionary;
import org.valesz.crypt.core.dictionary.NotADictionaryFileException;
import org.valesz.crypt.core.freqanal.FrequencyAnalyser;
import org.valesz.crypt.core.freqanal.FrequencyAnalysisMethod;
import org.valesz.crypt.core.freqanal.FrequencyAnalysisResult;
import org.valesz.crypt.core.utils.FileUtils;
import org.valesz.crypt.core.utils.TextUtils;
import org.valesz.crypt.ui.MainWindow;
import org.valesz.crypt.ui.StatusMessages;
import org.valesz.crypt.ui.input.InputPanel;
import org.valesz.crypt.ui.tools.columnTrans.ColumnTransTab;
import org.valesz.crypt.ui.tools.dictionary.DictionaryTab;
import org.valesz.crypt.ui.tools.freqAnal.FrequencyAnalysisTab;
import org.valesz.crypt.ui.tools.misc.MiscTab;
import org.valesz.crypt.ui.tools.vigenere.VigenereTab;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
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
    private VigenereTab vigenereTab;
    private MiscTab miscTab;
    private ColumnTransTab columnTransTab;

    private AppController() {

    }

    public void saveOutput() {
        File outputFile = miscTab.getOutputFile();
        String outText = miscTab.getOutputText();

        if(outputFile == null) {
            displayStatus(StatusMessages.NO_OUTPUT_FILE);
            return;
        }

        try {
            FileUtils.writeToFile(outputFile, outText);
        } catch (IOException e) {
            displayStatus(String.format(StatusMessages.Formated.ERROR_WRITING_TO_FILE, outputFile.getName()));
            return;
        }

        displayStatus(String.format(StatusMessages.Formated.SAVED_TO_FILE, outputFile.getName()));
    }

    public void loadInput() {
        File inputFile = miscTab.getInputFile();
        if(inputFile == null) {
            displayStatus(StatusMessages.NO_INPUT_FILE);
            return;
        }

        if(!inputFile.exists()) {
            displayStatus(String.format(StatusMessages.Formated.FILE_NOT_FOUND, inputFile.getName()));
            return;
        }

        if(!inputFile.canRead()) {
            displayStatus(String.format(StatusMessages.Formated.CANT_READ_FILE, inputFile.getName()));
            return;
        }

        String inputText = "";
        try {
            inputText = FileUtils.readFromFile(inputFile);
        } catch (IOException e) {
            logger.severe("Error while reading file "+inputFile.getName()+". "+e.getMessage());
            displayStatus(String.format(StatusMessages.Formated.ERROR_READING_FILE, inputFile.getName()));
            return;
        }

        inputPanel.setInputText(inputText);
        displayDefaultStatus();
    }

    public void decrypt() {
        EncryptionMethodType type = miscTab.getSelectedMethodType();
        String key = miscTab.getKey();
        String inputText = inputPanel.getInputText();
        String outputText = "";

        if(inputText.isEmpty()) {
            displayStatus(StatusMessages.NO_INPUT_TEXT);
            return;
        }

        switch (type) {
            case Atbas:
                outputText = Cryptor.deAtbas(inputText);
                break;
            case Caesar:
                key = TextUtils.stripText(key);
                if(key.isEmpty()) {
                    displayStatus(StatusMessages.NO_KEY);
                    return;
                }
                outputText = Cryptor.deVigenere(inputText, key.substring(0,1));
                break;
            case Vigenere:
                key = TextUtils.stripText(key);
                if(key.isEmpty()) {
                    displayStatus(StatusMessages.NO_KEY);
                    return;
                }
                outputText = Cryptor.deVigenere(inputText, key);
                break;
            case ColumnTrans:
                key = TextUtils.stripText(key);
                if(key.isEmpty()) {
                    displayStatus(StatusMessages.NO_KEY);
                    return;
                }
                outputText = Cryptor.deColumnTrans(inputText, key);
                break;
        }

        miscTab.setOutputText(outputText);
        displayDefaultStatus();
    }

    public void encrypt() {
        EncryptionMethodType type = miscTab.getSelectedMethodType();
        String key = miscTab.getKey();
        String inputText = inputPanel.getInputText();
        String outputText = "";

        if(inputText.isEmpty()) {
            displayStatus(StatusMessages.NO_INPUT_TEXT);
            return;
        }

        switch (type) {
            case Atbas:
                outputText = Cryptor.atbas(inputText);
                break;
            case Caesar:
                key = TextUtils.stripText(key);
                if(key.isEmpty()) {
                    displayStatus(StatusMessages.NO_KEY);
                    return;
                }
                outputText = Cryptor.vigenere(inputText, key.substring(0,1));
                break;
            case Vigenere:
                key = TextUtils.stripText(key);
                if(key.isEmpty()) {
                    displayStatus(StatusMessages.NO_KEY);
                    return;
                }
                outputText = Cryptor.vigenere(inputText, key);
                break;
            case ColumnTrans:
                key = TextUtils.stripText(key);
                if(key.isEmpty()) {
                    displayStatus(StatusMessages.NO_KEY);
                    return;
                }
                outputText = Cryptor.columnTrans(inputText, key);
                break;
        }

        miscTab.setOutputText(outputText);
        displayDefaultStatus();
    }

    public void setMiscTab(MiscTab miscTab) {
        this.miscTab = miscTab;
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

    public void setVigenereTab(VigenereTab vigenereTab) {
        this.vigenereTab = vigenereTab;
    }

    public void guessVigenereKey() {
        String encryptedText = inputPanel.getInputText();
        int keyLen = vigenereTab.getKeyLength();
        IDictionary dictionary = vigenereTab.getSelectedDictionary();

        if(encryptedText.isEmpty()) {
            displayStatus(StatusMessages.NO_INPUT_TEXT);
            return;
        }

        if(keyLen < 0) {
            displayStatus(StatusMessages.WRONG_KEY_LENGTH);
            return;
        }
        if(dictionary == null) {
            displayStatus(StatusMessages.WRONG_DICTIONARY);
            return;
        }

        String key = Cryptor.guessVigenereKey(encryptedText, keyLen, dictionary);
        String decryptedText = Cryptor.deVigenere(encryptedText, key);

        vigenereTab.displayKey(key);
        vigenereTab.displayDecryptedText(decryptedText);
        displayDefaultStatus();
    }

    public void performVigenereAnalysis() {
        String encryptedText = inputPanel.getInputText();
        int keyLenMin = vigenereTab.getMinKeyLen();
        int keyLenMax = vigenereTab.getMaxKeyLen();

        if(encryptedText.isEmpty()) {
            displayStatus(StatusMessages.NO_INPUT_TEXT);
            return;
        }

        if(keyLenMin >= keyLenMax) {
            displayStatus(String.format(StatusMessages.Formated.WRONG_KEY_LEN_RANGE,keyLenMin, keyLenMax));
            return;
        }

        Map<IDictionary, double[]> res = Cryptor.analyzeForVariousKeyLength(encryptedText,keyLenMin,keyLenMax);

        if(res.isEmpty()) {
            return;
        }
        int[] xVals = new int[keyLenMax-keyLenMin+1];
        for(int i = keyLenMin; i < keyLenMax+1; i++) {
            xVals[i - keyLenMin] = i;
        }

        vigenereTab.setHistograms(res, xVals);
        displayDefaultStatus();
    }

    public void performFrequencyAnalysis() {
        String encryptedText = inputPanel.getInputText();
        int period = frequencyAnalysisTab.getPeriod();
        int offset = frequencyAnalysisTab.getOffset();

        if(encryptedText.isEmpty()) {
            displayStatus(StatusMessages.NO_INPUT_TEXT);
            return;
        }

        if(period >= encryptedText.length()) {
            displayStatus(StatusMessages.WRONG_PERIOD);
            return;
        }

        if(offset >= encryptedText.length()) {
            displayStatus(StatusMessages.WRONG_OFFSET);
            return;
        }

        FrequencyAnalyser fa = new FrequencyAnalyser(encryptedText);
        List<FrequencyAnalysisResult> letters = fa.analyse(FrequencyAnalysisMethod.Letters, period,offset);
        List<FrequencyAnalysisResult> digrams = fa.analyse(FrequencyAnalysisMethod.Digrams, period,offset);
        List<FrequencyAnalysisResult> trigrams = fa.analyse(FrequencyAnalysisMethod.Trigrams, period,offset);

        IDictionary dictionary = DictionaryService.getInstance().getLowestDevianceDictionary(letters);

        if(dictionary == null) {
            frequencyAnalysisTab.setLanguage("-");
        } else {
            frequencyAnalysisTab.setLanguage(dictionary.getLanguageCode());
        }
        frequencyAnalysisTab.setLetterFreqAnal(letters);
        frequencyAnalysisTab.setDigramFreqAnal(digrams);
        frequencyAnalysisTab.setTriegamFreqAnal(trigrams);
        displayDefaultStatus();
    }

    public void displayStatus(String status) {
        mainWindow.displayStatusMessage(status);
    }

    public void addDictionary(String filePath) {

        if(filePath.isEmpty()) {
            displayStatus(StatusMessages.NO_FILE_PATH);
            return;
        }

        File dictFile = new File(filePath);
        if(!dictFile.exists()) {
            displayStatus(StatusMessages.FILE_NOT_FOUND);
            return;
        }

        try {
            IDictionary dictionary = DictionaryLoader.loadDictionaryFromFile(filePath);
            DictionaryService.getInstance().addDictionary(dictionary);
            displayStatus(String.format(StatusMessages.Formated.DICTIONARY_LOADED, dictionary.getLanguageCode()));
        } catch (IOException e) {
            logger.severe("Exception ("+e.getClass()+") while loading dictionary: "+e.getMessage());
            displayStatus(StatusMessages.ERROR_LOADING_DICTIONARY);
            return;
        } catch (NotADictionaryFileException e) {
            logger.severe("Format error while loading dictionary: "+e.getMessage());
            displayStatus(String.format(StatusMessages.Formated.FILE_FORMAT_ERROR,e.getMessage()));
            return;
        }

        dictionaryTab.updateDictionaryList();
        displayDefaultStatus();
    }

    public void guessColumnTransKey() {
        // load inputs
        String encText = inputPanel.getInputText();
        int threadCount = columnTransTab.getThreadCount();
        int minKeyLen = columnTransTab.getMinKeyLength();
        int maxKeyLen = columnTransTab.getMaxKeyLength();
        List<String> expectedWords = Arrays.asList(
                "zeptasli",
                "budes",
                "pet",
                "minut",
                "vypadat",
                "jako",
                "blbec",
                "nezeptas"
        );

        // compute stuff
        String key = "";
        int maxMatch = 0;
        for (int i = minKeyLen; i <= maxKeyLen; i++) {
            Cryptor.ColumnTransGuessKeyResult res = Cryptor.guessColumntransKey(encText, i, expectedWords);
            if(res.matches > maxMatch) {
                key = res.key;
                maxMatch = res.matches;
            }
        }

        // display results
        columnTransTab.displayFoundKey(key);
        columnTransTab.setDecText(Cryptor.deColumnTrans(encText, key));
        displayDefaultStatus();
    }

    private void displayDefaultStatus() {
        displayStatus(StatusMessages.DEFAULT);
    }

    public void setColumnTransTab(ColumnTransTab columnTransTab) {
        this.columnTransTab = columnTransTab;
    }
}
