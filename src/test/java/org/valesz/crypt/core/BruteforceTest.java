package org.valesz.crypt.core;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * This class will contain tests for the Cryptor.bruteforce() method.
 *
 * Created by Zdenek Vales on 18.3.2017.
 */
public class BruteforceTest {

    @Test
    public void testAtbas() {
        String openText = "abcdefghijklmnopqrstuvwxyz";
        String encText = Cryptor.atbas(openText);

        List<BruteforceResult> results = Cryptor.bruteforce(encText, new IDictionary() {
            public List<String> getExpectedWords() {
                return Arrays.asList("abc", "def", "ghi");
            }

            public double[] getLetterFrequency() {
                return new double[0];
            }

            public List<String> getKeys() {
                return Arrays.asList("asdf");
            }
        });

        assertNotNull("Null returned!", results);
        BruteforceResult result = null;
        for (BruteforceResult res : results) {
            if(res.encryptionMethod == EncryptionMethod.Atbas) {
                result = res;
            }
        }
        assertNotNull("Atbas result object expected!", result);
        assertEquals("Wrong decrypted text!", openText, result.decryptedText);
        assertEquals("Wrong number of found words!", 3, result.foundWords.size());
    }

    @Test
    public void testVigenere() {
        String openText = "zeptaslisebudespetminutvypadatjakoblbecnezeptaslisebudesblbcempocelyzivot";
        final String key = "klicek";
        String encText = Cryptor.vigenere(openText, key);

        List<BruteforceResult> results = Cryptor.bruteforce(encText, new IDictionary() {
            public List<String> getExpectedWords() {
                return Arrays.asList("zeptas", "budes", "vypadat", "blbcem", "zivot", "abcdef");
            }

            public double[] getLetterFrequency() {
                return new double[0];
            }

            public List<String> getKeys() {
                return Arrays.asList("k", "kl", "kli", "klic", "klice", key);
            }
        });

        assertNotNull("Null returned!", results);
        List<BruteforceResult> vigenereResults = new ArrayList<BruteforceResult>();
        for (BruteforceResult res : results) {
            if(res.encryptionMethod == EncryptionMethod.Vigenere) {
                vigenereResults.add(res);
            }
        }

        assertFalse("At least one vigenere result object expected!", vigenereResults.isEmpty());
        boolean ok = false;
        for(BruteforceResult vigenereResult : vigenereResults) {
            ok = openText.equalsIgnoreCase(vigenereResult.decryptedText) && key.equalsIgnoreCase(vigenereResult.key) && 5 == vigenereResult.foundWords.size();
            if(ok) {
                break;
            }
        }

        assertTrue("Correct result object expected!", ok);
    }
}
