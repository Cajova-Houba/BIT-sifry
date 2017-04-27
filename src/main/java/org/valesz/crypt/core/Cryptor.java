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

    /**
     * Method will try to break the encrypted message. It will keep trying to decrypt the message. After every attempt,
     * words from dictionary will be searched for in decrypted string and if any match occurs, attempt will be added to result
     * array.
     * @param message Message to be decrypted.
     * @param dictionary Dictionary to be used.
     * @return List of found results. If no matches are found, empty list is returned. Never null.
     */
    public static List<BruteforceResult> bruteforce(String message, OldDictionary dictionary) {
        List<BruteforceResult> res = new ArrayList<BruteforceResult>();

        List<String> expectedWords = dictionary.getExpectedWords();
        List<String> keysToTry = dictionary.getKeys();
        List<String> foundWords = new ArrayList<String>();
        String decText = "";

        // try atbas first
        decText = deAtbas(message);
        for (String commonWord: expectedWords) {
            if(decText.contains(commonWord)) {
                foundWords.add(commonWord);
            }
        }
        if (foundWords.size() > 0) {
            // add possible result
            res.add(new BruteforceResult(EncryptionMethodType.Atbas, decText, "", new ArrayList<String>(foundWords)));
        }


        // try key methods
        for(String key : keysToTry) {
            decText = deVigenere(message, key);
            foundWords.clear();
            for(String expectedWord: expectedWords) {
                if (decText.contains(expectedWord)) {
                    foundWords.add(expectedWord);
                }
            }
            if (foundWords.size() > 0) {
                res.add(new BruteforceResult(EncryptionMethodType.Vigenere, decText, key, new ArrayList<String>(foundWords)));
            }

            decText = deColumnTrans(message, key);
            foundWords.clear();
            for(String expectedWord : expectedWords) {
                if (decText.contains(expectedWord)) {
                    foundWords.add(expectedWord);
                }
            }
            if (foundWords.size() > 0) {
                res.add(new BruteforceResult(EncryptionMethodType.ColumnTrans, decText, key, new ArrayList<String>(foundWords)));
            }
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
