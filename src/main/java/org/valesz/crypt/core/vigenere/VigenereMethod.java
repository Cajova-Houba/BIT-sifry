package org.valesz.crypt.core.vigenere;

import org.valesz.crypt.core.EncryptionMethod;

import static org.valesz.crypt.core.Cryptor.ALPHABET_LEN;
import static org.valesz.crypt.core.Cryptor.FIRST_LETTER;
import static org.valesz.crypt.core.Cryptor.LAST_LETTER;

/**
 * This class implements the vigenere encryption.
 *
 * Created by Zdenek Vales on 17.4.2017.
 */
public class VigenereMethod implements EncryptionMethod<VigenereInput, VigenereOutput> {

    public VigenereOutput encrypt(VigenereInput cipherInput) {
        String key = cipherInput.getKey();
        String message = cipherInput.getText();
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
        return new VigenereOutput(res.toString());
    }

    public VigenereOutput decrypt(VigenereInput cipherInput) {
        String key = cipherInput.getKey();
        String message = cipherInput.getText();
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
        return new VigenereOutput(res.toString());
    }
}
