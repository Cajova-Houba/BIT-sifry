package org.valesz.crypt.core;

import org.valesz.crypt.core.freqanal.FrequencyAnalysisMethod;

/**
 * This class will contain some cools utils for strings.
 *
 * Created by Zdenek Vales on 19.3.2017.
 */
public class TextUtils {

    /**
     * Returns a complex view for encrypted message with Letters method.
     * @param encryptedMessage Encrypted message.
     * @param alphabet Alphabet
     * @param charsPerLine Number of characters per one line.
     * @return Complex view.
     */
    public static String getComplexViewText(String encryptedMessage, String[] alphabet, int charsPerLine) {
        return getComplexViewText(encryptedMessage, alphabet, charsPerLine, FrequencyAnalysisMethod.Letters);
    }

    /**
     * Returns a multiline string which will display encrypted and translated message.
     *
     * Example:
     * alphabet: a->z, b->y, r->a, e->b rest is empty
     * charsPerLine: 10
     * encryptedMessage: Original message
     *
     * result:
     * Original m
     *  a    z
     *
     * essage
     * b  z b
     *
     * @param encryptedMessage Encrypted message.
     * @param alphabet Alphabet. Each item is expected a letter which will translate the original letter on its position.
     *                 Example: letter on the first position will be used to translate 'a' letter, letter on the second position
     *                 will be used to translate 'b' letter and so on...
     *                 If some character is not defined, empty string should be used.
     * @param charsPerLine How many characters is on 1 line.
     * @param replacingMethod Method stating which characters in message to be translated.
     * @return Multiline string.
     */
    public static String getComplexViewText(String encryptedMessage, String[] alphabet, int charsPerLine, FrequencyAnalysisMethod replacingMethod) {
        StringBuilder res = new StringBuilder();

        // split string into lines
        String[] lines = new String[(int)Math.ceil(encryptedMessage.length() / (double)charsPerLine)];
        for(int i = 0; i < lines.length; i++) {
            lines[i] = encryptedMessage.substring(i*charsPerLine,Math.min((i+1)*charsPerLine, encryptedMessage.length()));
        }

        // for each line of original message, create a line of translated message
        // charCnt will be counting just chars - so that special symbols don't break even/odd char order
        int charCnt = 0;
        for(String line : lines) {
            res.append(line).append("\n");

            // translate letters and append either new char o space
            for(int i = 0; i < line.length(); i++) {
                char originalLetter = Character.toLowerCase(line.charAt(i));
                if(originalLetter >= Cryptor.FIRST_LETTER && originalLetter <= Cryptor.LAST_LETTER) {
                    charCnt++;
                    if(alphabet[originalLetter - Cryptor.FIRST_LETTER].length() == 0) {
                        // no char is defined, append space
                        res.append(" ");
                    } else {
                        // character is defined, append it
                        switch (replacingMethod) {
                            case Letters:
                                res.append(alphabet[originalLetter - Cryptor.FIRST_LETTER]);
                                break;
                            case EvenChars:
                                if((charCnt) % 2 == 0) {
                                    res.append(alphabet[originalLetter - Cryptor.FIRST_LETTER]);
                                } else {
                                    res.append(" ");
                                }
                                break;
                            case OddChars:
                                if((charCnt) % 2 == 1) {
                                    res.append(alphabet[originalLetter - Cryptor.FIRST_LETTER]);
                                } else {
                                    res.append(" ");
                                }
                                break;
                        }
                    }
                } else {
                    // just append other chars
                    res.append(originalLetter);
                }

            }

            // add two new lines after translation
            res.append("\n\n");
        }

        return res.toString();
    }
}
