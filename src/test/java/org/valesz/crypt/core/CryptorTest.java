package org.valesz.crypt.core;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Test.
 *
 * Created by Zdenek Vales on 17.3.2017.
 */
public class CryptorTest {

    @Test
    public void testVigenere() {
        String openText = "zeptaslisebudespetminutvypadatjakoblbecnezeptaslisebudesblbcempocelyzivot";
        String encText = "aevkbsrztehleeygftszouzmzpgubtprlohcceiefzkguaycjsksvdkjclhtfmvfderpaibfu";
        String key = "bagr";

        String text = Cryptor.vigenere(openText, key);
        assertEquals("Wrong text after encryption!", encText, text);

        text = Cryptor.deVigenere(text, key);
        assertEquals("Wrong text after decryption!", openText, text);
    }

    @Test
    public void testColumnTrans() {
        String openText = "zeptaslisebudespetminutvypadatjakoblbecnezeptaslisebudesblbcempocelyzivot";
        String openText2 = "zeptaslisebudespetminutvypadatjakoblbecnezeptaslisebudesblbcempocelyzi";
        String encText = "euuazbmitdellyasybtecpdtkeupsmacibztevopdospplaseeitnscileabsblzbnjeee";
        String key = "zakodovano";

        String text = Cryptor.columnTrans(openText, key);
        assertEquals("Wrong text after encryption!", encText, text);

        text = Cryptor.deColumnTrans(text, key);
        assertEquals("Wrong text after decryption!", openText2, text);
    }

    @Test
    public void testAtbas() {
        String openText = "abcdefghijklmnopqrstuvwxyz";
        String encText = "zyxwvutsrqponmlkjihgfedcba";

        String text = Cryptor.atbas(openText);
        assertEquals("Wrong text after encryption!", encText, text);

        text = Cryptor.deAtbas(text);
        assertEquals("Wrong text after decryption!", openText, text);
    }

    @Test
    public void testFrequencyAnalysis1() {
        String text = "abcdefghijklmnopqrstuvwxyz";
        double expectedRelativeCount = 1.0 / text.length();
        FrequencyAnalysisResult[] res = Cryptor.frequencyAnalysis(text);
        assertNotNull("Null returned!", res);
        for (int i = 0; i < res.length; i++) {
            FrequencyAnalysisResult r = res[i];
            assertNotNull("Null at position "+i+"!", r);
            assertEquals("Wrong letter!", text.charAt(i), r.getCharacter().charValue());
            assertEquals("Wrong absolute count.", 1, r.getAbsoluteCount());
            assertEquals("Wrong relative count", expectedRelativeCount, r.getRelativeCount(), 0.01);
        }
    }

    @Test
    public void testFrequencyAnalysis2() {
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        String text = "a";
        double expectedRelativeCount = 1.0 ;
        FrequencyAnalysisResult[] res = Cryptor.frequencyAnalysis(text);
        for (int i = 0; i < res.length; i++) {
            FrequencyAnalysisResult r = res[i];
            assertNotNull("Null at position "+i+"!", r);
            assertEquals("Wrong letter!", alphabet.charAt(i), r.getCharacter().charValue());
            if(r.getCharacter().charValue() == 'a') {
                assertEquals("Wrong absolute count!", 1, r.getAbsoluteCount());
                assertEquals("Wrong relative count!", expectedRelativeCount, r.getRelativeCount(), 0.01);

            } else {
                assertEquals("Wrong absolute count.", 0, r.getAbsoluteCount());
                assertEquals("Wrong relative count", 0, r.getRelativeCount(), 0.01);
            }
        }
    }
}
