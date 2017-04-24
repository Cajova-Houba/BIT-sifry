package org.valesz.crypt.main;

import org.valesz.crypt.core.BruteforceResult;
import org.valesz.crypt.core.Cryptor;
import org.valesz.crypt.core.OldDictionary;

import java.util.List;

/**
 * Created by Zdenek Vales on 20.3.2017.
 */
public class BruteForcer extends Thread {

    private String message;

    private List<String> keys;

    private List<String> expectedWords;

    private List<BruteforceResult> results;

    public BruteForcer(String message, List<String> keys, List<String> expectedWords) {
        this.message = message;
        this.keys = keys;
        this.expectedWords = expectedWords;
    }



    public void run() {
        results = Cryptor.bruteforce(message, new OldDictionary() {
            public List<String> getExpectedWords() {
                return expectedWords;
            }

            public List<String> getKeys() {
                return keys;
            }

            public double[] getLetterFrequency() {
                return new double[0];
            }
        });
    }

    public List<BruteforceResult> getResults() {
        return results;
    }
}
