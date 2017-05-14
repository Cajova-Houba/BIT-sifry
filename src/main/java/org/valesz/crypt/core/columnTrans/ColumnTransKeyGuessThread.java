package org.valesz.crypt.core.columnTrans;

import org.valesz.crypt.core.Cryptor;

import javax.swing.*;
import java.util.List;

/**
 * A thread which will try to find the best key for given key length.
 *
 * @author Zdenek Vales
 */
public class ColumnTransKeyGuessThread extends Thread {

    /**
     * Encrypted message to be broken.
     */
    private final String message;

    /**
     * Key length to be used.
     */
    private final int keyLength;

    /**
     * Words to be searched for in decrypted text.
     */
    private final List<String> expectedWords;

    private ColumnTransGuessKeyResult res;

    public ColumnTransKeyGuessThread(String message, int keyLength, List<String> expectedWords) {
        this.message = message;
        this.keyLength = keyLength;
        this.expectedWords = expectedWords;
    }

    public ColumnTransGuessKeyResult getRes() {
        return res;
    }

    @Override
    public void run() {
        res = Cryptor.guessColumntransKey(message,keyLength, expectedWords);
    }
}
