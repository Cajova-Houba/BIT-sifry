package org.valesz.crypt.core;

import org.valesz.crypt.ui.AlphabetTable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
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
     * Performs a frequency analysis with All method.
     * @param message Message.
     * @return Frequency analysis result.
     */
    public static FrequencyAnalysisResult[] frequencyAnalysis(String message) {
        return frequencyAnalysis(message, FrequencyAnalysisMethod.All);
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
            case All:
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
    public static List<BruteforceResult> bruteforce(String message, IDictionary dictionary) {
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
            res.add(new BruteforceResult(EncryptionMethod.Atbas, decText, "", new ArrayList<String>(foundWords)));
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
                res.add(new BruteforceResult(EncryptionMethod.Vigenere, decText, key, new ArrayList<String>(foundWords)));
            }

            decText = deColumnTrans(message, key);
            foundWords.clear();
            for(String expectedWord : expectedWords) {
                if (decText.contains(expectedWord)) {
                    foundWords.add(expectedWord);
                }
            }
            if (foundWords.size() > 0) {
                res.add(new BruteforceResult(EncryptionMethod.ColumnTrans, decText, key, new ArrayList<String>(foundWords)));
            }
        }


        return res;
    }

    public static String atbas(String message) {
        StringBuilder res = new StringBuilder();

        for (int i = 0; i < message.length(); i++) {
            char newChar = Character.toLowerCase(message.charAt(i));
            newChar = (char)((int)newChar - FIRST_LETTER+1);
            newChar = (char)(ALPHABET_LEN - (int)newChar);
            newChar = (char)((int)newChar + FIRST_LETTER);
            res.append(newChar);
        }

        return res.toString();
    }

    public static String deAtbas(String message) {
        return atbas(message);
    }

    public static String vigenere(String message, String key) {
        int keyLen = key.length();
        int msgLen = message.length();
        StringBuilder res = new StringBuilder();

//        logger.log(Level.FINE, "Vigenere, message: "+message+"; key: "+key);

        for(int i = 0; i < msgLen; i++) {
            char origChar = message.charAt(i);
            if(origChar < FIRST_LETTER || origChar > LAST_LETTER) {
                res.append(origChar);
                continue;
            }
            char keyChar = key.charAt(i % keyLen);

            int newCharI = (int)keyChar - FIRST_LETTER;
            newCharI += (int)origChar - FIRST_LETTER;
            newCharI = newCharI % ALPHABET_LEN;
            newCharI += FIRST_LETTER;

            char newChar = (char)newCharI;

//            logger.log(Level.FINE, String.format("Conversion: %c + %c => %c", origChar, keyChar, newChar));
            res.append(newChar);
        }

        return res.toString();
    }

    public static String deVigenere(String message, String key) {
        int keyLen = key.length();
        int msgLen = message.length();
        StringBuilder res = new StringBuilder();

//        logger.log(Level.FINE, "Vigenere, message: "+message+"; key: "+key);

        for(int i = 0; i < msgLen; i++) {
            char encChar = message.charAt(i);
            if(encChar < FIRST_LETTER || encChar > LAST_LETTER) {
                res.append(encChar);
                continue;
            }
            char keyChar = key.charAt(i % keyLen);

            int origCharI = (int)encChar - FIRST_LETTER;
            origCharI -= (int)keyChar - FIRST_LETTER;
            origCharI = origCharI % ALPHABET_LEN;
            while(origCharI < 0) {
                origCharI += ALPHABET_LEN;
            }
            origCharI += FIRST_LETTER;

            char origChar = (char)origCharI;

//            logger.log(Level.FINE, String.format("Conversion: %c + %c => %c", encChar, keyChar, origChar));
            res.append(origChar);
        }

        return res.toString();
    }

    public static String columnTrans(String message, String key) {
        int keyLen = key.length();
        int msgLen = message.length();
        int[] keyNumbers = new int[keyLen];

        // for repeating chars
        for(int i = 0; i< keyLen;i++) {
            keyNumbers[i] = -1;
        }

        // adjust message len
        int mod = msgLen % keyLen;
        if(mod != 0) {
            for(int i = 0; i < mod; i ++) {
                message += "a";
            }
        }
        msgLen = message.length();

        char[][] table = new char [msgLen / keyLen][keyLen];

        // assign numbers to key chars
        char[] tmpKey = key.toCharArray();
        Arrays.sort(tmpKey);
        String sortedKey = String.valueOf(tmpKey);
//        logger.log(Level.FINE, String.format("Key: %s, sorted key: %s", key, sortedKey));
        for(int i = 0; i < keyLen; i++) {
            for(int j = 0; j < keyLen; j++) {
                if( key.charAt(j) == sortedKey.charAt(i) && keyNumbers[j] == -1) {
                    keyNumbers[j] = i;
                    break;
                }
            }
        }

//        logger.log(Level.FINE, "Key numbers: ");
        for(int i = 0; i< keyLen; i++) {
//            logger.log(Level.FINE, String.format("%c => %d", key.charAt(i), keyNumbers[i]));
        }

        // put message to table
        int msgCur = 0;
        for(int i = 0; i < table.length; i++) {
            for (int j = 0; j < table[0].length; j++) {
                table[i][j] = message.charAt(msgCur);
                msgCur++;
            }
        }

        // print table
        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table[0].length; j++) {
                System.out.print(table[i][j]+" ");
            }
            System.out.print("\n");
        }

        // encrypt the message
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < keyLen; i++) {
            for (int j = 0; j < keyLen; j++) {
                if(keyNumbers[j] == i) {
                    for (int k = 0; k < table.length; k++) {
                        res.append(table[k][j]);
                    }
                    break;
                }
            }
        }


        return res.toString();
    }

    public static String deColumnTrans(String message, String key) {
        int keyLen = key.length();
        int msgLen = message.length();
        int[] keyNumbers = new int[keyLen];

        // for repeating chars
        for(int i = 0; i< keyLen;i++) {
            keyNumbers[i] = -1;
        }

        // adjust message len - shouldn't be needed
        int mod = msgLen % keyLen;
        if(mod != 0) {
            for(int i = 0; i < mod; i ++) {
                message += "a";
            }
        }
        msgLen = message.length();

        char[][] table = new char [msgLen / keyLen][keyLen];

        // assign numbers to key chars
        char[] tmpKey = key.toCharArray();
        Arrays.sort(tmpKey);
        String sortedKey = String.valueOf(tmpKey);
//        logger.log(Level.FINE, String.format("Key: %s, sorted key: %s", key, sortedKey));
        for(int i = 0; i < keyLen; i++) {
            for(int j = 0; j < keyLen; j++) {
                if( key.charAt(j) == sortedKey.charAt(i) && keyNumbers[j] == -1) {
                    keyNumbers[j] = i;
                    break;
                }
            }
        }

//        logger.log(Level.FINE, "Key numbers: ");
        for(int i = 0; i< keyLen; i++) {
//            logger.log(Level.FINE, String.format("%c => %d", key.charAt(i), keyNumbers[i]));
        }

        // put message to table
        int msgCur = 0;
        for(int i = 0; i < keyLen; i++) {
            int keyNumPos = 0;
            for (int j = 0; j < keyLen; j++) {
                if(keyNumbers[j] == i) {
                    keyNumPos = j;
                    break;
                }
            }

            for (int j = 0; j < table.length; j++) {
                table[j][keyNumPos] = message.charAt(msgCur);
                msgCur++;
            }
        }

        // print table
        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table[0].length; j++) {
//                System.out.print(table[i][j]+" ");
            }
//            System.out.print("\n");
        }

        // decrypt the message
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table[0].length; j++) {
                res.append(table[i][j]);
            }
        }


        return res.toString();
    }

    public static int feistel(int message, int blockLength, int iterations, int key) {

        int left = (message & 0xFFFF0000) >> 16;
        int right = message & 0x0000FFFF;



        return 0;
    }

}
