package org.valesz.crypt.core.utils;

import org.valesz.crypt.core.Cryptor;
import org.valesz.crypt.core.freqanal.FrequencyAnalysisMethod;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * This class will contain some cools utils for strings.
 *
 * Created by Zdenek Vales on 19.3.2017.
 */
public class TextUtils {

    /**
     * Table for converting czech diacritic chards to standard alphabet.
     */
    public static final char[][] diacriticConversion = new char[][] {
            new char[] {'Á','a'},
            new char[] {'á','a'},
            new char[] {'Č','c'},
            new char[] {'č','c'},
            new char[] {'Ď','d'},
            new char[] {'ď','d'},
            new char[] {'É','e'},
            new char[] {'é','e'},
            new char[] {'Ě','e'},
            new char[] {'ě','e'},
            new char[] {'Í','i'},
            new char[] {'í','i'},
            new char[] {'Ň','n'},
            new char[] {'ň','n'},
            new char[] {'Ó','o'},
            new char[] {'ó','o'},
            new char[] {'Š','s'},
            new char[] {'š','s'},
            new char[] {'Ř','r'},
            new char[] {'ř','r'},
            new char[] {'Ť','t'},
            new char[] {'ť','t'},
            new char[] {'Ú','u'},
            new char[] {'ú','u'},
            new char[] {'Ů','u'},
            new char[] {'ů','u'},
            new char[] {'Ý','y'},
            new char[] {'ý','y'},
            new char[] {'Ž','z'},
            new char[] {'ž','z'}
    };

    /**
     * Count how many words from expectedWords list is contained in the message.
     * @param message
     * @param expectedWords
     * @return
     */
    public static int countMatches(String message, List<String> expectedWords) {
        int cntr = 0;
        for(String expectedWord : expectedWords) {
            if(message.contains(expectedWord))  {
                cntr++;
            }
        }

        return cntr;
    }

    /**
     * Recursively increments the string of given length so that aaa becomes aab, aab becomes aac, aaz becomes aba....
     * @param string Array of chars to be incremented.
     * @param len Length of the string.
     */
    public static void incString(char[] string, int len) {
        string[len -1] = (char)(string[len-1]+1);
        if(string[len-1] == ('z'+1)) {
            string[len-1] = 'a';
            if(len-1 == 0) {
                return;
            }
            incString(string, len-1);
        }
    }

    /**
     * Returns all possible permutations of english lowercase alphabet of given length.
     * @param len Length of the strings.
     * @return List containing possible permutations. It's size will be 26! / (26-len)!.
     */
    // todo: permutations algorithm
    public static List<String> allPossiblePermutations(int len) {
        Set<Character> alphabet = new HashSet<>();
        for(char c = 'a'; c <= 'z'; c++) {
            alphabet.add(c);
        }


        return new ArrayList<>();
    }

    /**
     * Strips all non-letter characters from text and converts upper case characters to lowercase
     * @param text
     * @return
     */
    public static String stripText(String text) {
        StringBuilder sb = new StringBuilder();

        for(char c : text.toCharArray()) {
            if(c >= 'a' && c <= 'z') {
                sb.append(c);
            } else if (c >= 'A' && c <='Z') {
                sb.append(Character.toLowerCase(c));
            } else {
                for (int i = 0; i < diacriticConversion.length; i++) {
                    if(diacriticConversion[i][0] == c) {
                        sb.append(diacriticConversion[i][1]);
                        break;
                    }
                }
            }
        }

        return sb.toString();
    }

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
