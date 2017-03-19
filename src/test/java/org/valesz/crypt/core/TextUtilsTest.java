package org.valesz.crypt.core;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Zdenek Vales on 19.3.2017.
 */
public class TextUtilsTest {

    @Test
    public void testGetComplexViewText() {
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
}
