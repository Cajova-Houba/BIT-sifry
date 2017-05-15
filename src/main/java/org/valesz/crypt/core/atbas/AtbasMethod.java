package org.valesz.crypt.core.atbas;

import org.valesz.crypt.core.EncryptionMethod;
import org.valesz.crypt.core.utils.TextUtils;

import static org.valesz.crypt.core.Cryptor.ALPHABET_LEN;
import static org.valesz.crypt.core.Cryptor.FIRST_LETTER;
import static org.valesz.crypt.core.Cryptor.LAST_LETTER;

/**
 * This class implements the atbas encryption.
 *
 * Created by Zdenek Vales on 17.4.2017.
 */
public class AtbasMethod implements EncryptionMethod<AtbasInput, AtbasOutput>{

    public AtbasOutput encrypt(AtbasInput cipherInput) {
        StringBuilder res = new StringBuilder();
        String message = TextUtils.stripText(cipherInput.getText());

        for (int i = 0; i < message.length(); i++) {
            char newChar = Character.toLowerCase(message.charAt(i));
            if(newChar < FIRST_LETTER || newChar > LAST_LETTER) {
                res.append(newChar);
                continue;
            }
            newChar = (char)((int)newChar - FIRST_LETTER+1);
            newChar = (char)(ALPHABET_LEN - (int)newChar);
            newChar = (char)((int)newChar + FIRST_LETTER);
            res.append(newChar);
        }

        return new AtbasOutput(res.toString());
    }

    public AtbasOutput decrypt(AtbasInput cipherInput) {
        return encrypt(cipherInput);
    }
}
