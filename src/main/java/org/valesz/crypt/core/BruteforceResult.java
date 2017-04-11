package org.valesz.crypt.core;

import java.util.List;

/**
 * Result of one attempt of bruteforce method.
 *
 * Created by Zdenek Vales on 18.3.2017.
 */
public class BruteforceResult {

    public final EncryptionMethod encryptionMethod;

    public final String decryptedText;

    public final String key;

    public final List<String> foundWords;

    public BruteforceResult(EncryptionMethod encryptionMethod, String decryptedText, String key, List<String> foundWords) {
        this.encryptionMethod = encryptionMethod;
        this.decryptedText = decryptedText;
        this.key = key;
        this.foundWords = foundWords;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("method: ").append(encryptionMethod).append("\n");
        sb.append("key: ").append(key).append("\n");
        sb.append("found words: ");
        for(String word : foundWords) {
            sb.append(word).append(" ");
        }
        sb.append("\n");
        sb.append("decrypted text: ").append(decryptedText).append("\n\n\n\n");
        return sb.toString();
    }
}
