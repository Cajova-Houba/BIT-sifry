package org.valesz.crypt.core;

import org.valesz.crypt.core.atbas.AtbasInput;
import org.valesz.crypt.core.atbas.AtbasMethod;
import org.valesz.crypt.core.columnTrans.ColumnTransInput;
import org.valesz.crypt.core.columnTrans.ColumnTransMethod;
import org.valesz.crypt.core.dictionary.DictionaryService;
import org.valesz.crypt.core.dictionary.IDictionary;
import org.valesz.crypt.core.freqanal.FrequencyAnalyser;
import org.valesz.crypt.core.freqanal.FrequencyAnalysisMethod;
import org.valesz.crypt.core.freqanal.FrequencyAnalysisResult;
import org.valesz.crypt.core.vigenere.VigenereInput;
import org.valesz.crypt.core.vigenere.VigenereMethod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * This class contains all functions for encryption / decryption.
 *
 * Created by Zdenek Vales on 17.3.2017.
 */
public class Cryptor {

    private static final Logger logger = Logger.getLogger(Cryptor.class.getName());

    public static final int ALPHABET_LEN = 26;
    public static final int FIRST_LETTER = (int)'a';
    public static final int LAST_LETTER = (int)'z';

    /**
     * Performs a frequency analysis with Letters method.
     * @param message Message.
     * @return Frequency analysis result.
     */
    public static FrequencyAnalysisResult[] frequencyAnalysis(String message) {
        return frequencyAnalysis(message, FrequencyAnalysisMethod.Letters);
    }

    /**
     * Performs frequency analysis on text for various key lengths with every loaded dictionary.
     * For every ken length, average deviance is computed and added to array which will be returned.
     *
     * @param minKeyLength Min key length (inclusive).
     * @param maxKeyLength Max key length (inclusive).
     * @return Arrays of average deviations for every dictionary. Every array will be maxKeyLength-minKeyLength+1.
     */
    public static Map<IDictionary, double[]> analyzeForVariousKeyLength(String text, int minKeyLength, int maxKeyLength) {
        List<IDictionary> dictionaries = new ArrayList<>(DictionaryService.getInstance().getAll());
        Map<IDictionary, double[]> res = new HashMap<>();

        FrequencyAnalyser textAnalyser = new FrequencyAnalyser(text);

        for(IDictionary dictionary : dictionaries) {
            double[] avgDevs = new double[maxKeyLength-minKeyLength+1];
            for(int i = minKeyLength; i <= maxKeyLength; i++) {
                avgDevs[i-minKeyLength] = averageFrequencyAnalysisDeviance(textAnalyser, dictionary, i);
            }
            res.put(dictionary, avgDevs);
        }

        return res;
    }

    public static String guessVigenereKey(String encMessage, int expectedKeyLength, IDictionary dictionary) {
        StringBuilder keyBuilder = new StringBuilder();
        FrequencyAnalyser frequencyAnalyser = new FrequencyAnalyser(encMessage);

        // try to choose the right alphabet for every key char
        // prepare help frequencies
        Map<Character, List<FrequencyAnalysisResult>> alphabetShifts = new HashMap<>();
        alphabetShifts.put(new Character('a'), dictionary.getLettersFrequency());
        for(Character c = 'b'; c <= 'z'; c++) {
            Character previous = new Character((char)(c-1));
            alphabetShifts.put(new Character(c), FrequencyAnalysisResult.shiftResults(alphabetShifts.get(previous)));
        }

        // try to guess key chars
        for(int i = 0; i < expectedKeyLength; i++) {
            List<FrequencyAnalysisResult> faLetters = frequencyAnalyser.analyse(FrequencyAnalysisMethod.Letters, expectedKeyLength,i);
            char possibleKeyChar = 'a';
            double minDev = Double.MAX_VALUE;
            for(Character c : alphabetShifts.keySet()) {
                double dev = FrequencyAnalysisResult.calculateDeviance(alphabetShifts.get(c), faLetters);
                if(dev < minDev) {
                    minDev = dev;
                    possibleKeyChar = c;
                }
            }
            keyBuilder.append(possibleKeyChar);
        }

        return keyBuilder.toString();
    }

    /**
     * Computes average deviance of analyzed text from dictionary for a certain key length.
     * @param frequencyAnalyser Analyser containing the text.
     * @param dictionary Dictionary against which the deviance will be computed.
     * @param keyLength Key length.
     * @return Average deviance.
     */
    public static double averageFrequencyAnalysisDeviance(FrequencyAnalyser frequencyAnalyser, IDictionary dictionary, int keyLength) {
        double avgDeviance = 0d;

        for(int i = 0; i < keyLength; i++) {
            List<FrequencyAnalysisResult> frequencyAnalysisResults = frequencyAnalyser.analyse(FrequencyAnalysisMethod.Letters, keyLength, i);
            avgDeviance += dictionary.calculateDeviation(frequencyAnalysisResults);
        }

        avgDeviance /= keyLength;

        return avgDeviance;
    }

    /**
     * Performs a frequency analysis (standard latin alphabet) and returns array containing frequencies of letters.
     * @param message Message.
     * @param method Method to be used for frequencyAnalysis.
     * @return Arrays of frequencies. Never null, length is Cryptor.ALPHABET_LEN.
     */
    public static FrequencyAnalysisResult[] frequencyAnalysis(String message, FrequencyAnalysisMethod method) {
        FrequencyAnalysisResult[] res = FrequencyAnalysisResult.prepareForStandardAlphabet();

        String text = "";
        StringBuilder textBuilder = new StringBuilder();

        switch (method) {
            case Letters:
                text = message;
                break;
            case EvenChars:
                for(int i = 0; i < message.length(); i++) {
                    if((i+1) % 2 == 0) {
                        textBuilder.append(message.charAt(i));
                    }
                }
                text = textBuilder.toString();
                break;

            case OddChars:
                for (int i = 0; i < message.length(); i++) {
                    if((i+1) % 2 ==1) {
                        textBuilder.append(message.charAt(i));
                    }
                }
                text = textBuilder.toString();
                break;
        }

        for(int i = 0; i < text.length(); i++) {
            char c = Character.toLowerCase(text.charAt(i));

            if (c >= FIRST_LETTER && c <= LAST_LETTER) {
                res[c - FIRST_LETTER].incAbsoluteCount();
            }
        }

        for(FrequencyAnalysisResult r : res) {
            r.recountRelativeCount(text.length());
        }

        return res;
    }

    public static String atbas(String message) {
        return new AtbasMethod().encrypt(new AtbasInput(message)).getText();
    }

    public static String deAtbas(String message) {
        return atbas(message);
    }

    public static String vigenere(String message, String key) {
        return new VigenereMethod().encrypt(new VigenereInput(message, key)).getText();
    }

    public static String deVigenere(String message, String key) {
        return new VigenereMethod().decrypt(new VigenereInput(message, key)).getText();
    }

    public static String columnTrans(String message, String key) {
        return new ColumnTransMethod().encrypt(new ColumnTransInput(message, key)).getText();
    }

    public static String deColumnTrans(String message, String key) {
        return new ColumnTransMethod().decrypt(new ColumnTransInput(message, key)).getText();
    }

}
