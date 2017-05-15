package org.valesz.crypt.core;

import org.junit.Test;
import org.valesz.crypt.core.freqanal.FrequencyAnalysisMethod;
import org.valesz.crypt.core.utils.TextUtils;

import static org.junit.Assert.assertEquals;

/**
 * Created by Zdenek Vales on 19.3.2017.
 */
public class TextUtilsTest {

    @Test
    public void testStripText() {
        String origText = "příliš žluťoučký kůň úpěl ďábelské ódy;PŘÍLIŠ ŽLUŤOUČKÝ KŮŇ ÚPĚL ĎÁBELSKÉ ÓDY";
        String expectedText = "priliszlutouckykunupeldabelskeodypriliszlutouckykunupeldabelskeody";

        assertEquals("Wrong stripped text!", expectedText, TextUtils.stripText(origText));
    }

    @Test
    public void testGetComplexViewText1() {
        String encryptedMessage = "abcdef,a";
        String[] alphabet = new String[Cryptor.ALPHABET_LEN];
        for(int i = 0; i < alphabet.length; i++) {
            alphabet[i] = "";
        }
        alphabet[0] = "g";
        alphabet[2] = "e";
        int charsPerLine = 3;
        String expectedResult = "abc\ng e\n\ndef\n   \n\n,a\n,g\n\n";

        String complexViewText = TextUtils.getComplexViewText(encryptedMessage, alphabet, charsPerLine);

        assertEquals("Wrong complex view text!", expectedResult,  complexViewText);
    }

    @Test
    public void testGetComplexViewText2() {
        String encryptedMessage = "abcdef,a";
        String[] alphabet = new String[Cryptor.ALPHABET_LEN];
        for(int i = 0; i < alphabet.length; i++) {
            alphabet[i] = "";
        }
        alphabet[0] = "g";
        alphabet[1] = "k";
        alphabet[2] = "e";
        alphabet[5] = "o";
        int charsPerLine = 3;
        String expectedResultEven = "abc\n k \n\ndef\n  o\n\n,a\n, \n\n";
        String expectedResultOdd = "abc\ng e\n\ndef\n   \n\n,a\n,g\n\n";

        String complexViewText = TextUtils.getComplexViewText(encryptedMessage, alphabet, charsPerLine, FrequencyAnalysisMethod.EvenChars);
        assertEquals("Wrong even complex view text!", expectedResultEven,  complexViewText);

        complexViewText = TextUtils.getComplexViewText(encryptedMessage, alphabet, charsPerLine, FrequencyAnalysisMethod.OddChars);
        assertEquals("Wrong odd complex view text!", expectedResultOdd,  complexViewText);
    }
}
