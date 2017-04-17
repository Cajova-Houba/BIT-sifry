package org.valesz.crypt.core.vigenere;


import org.valesz.crypt.core.EncryptionMethodOutput;

/**
 * Created by Zdenek Vales on 17.4.2017.
 */
public class VigenereOutput implements EncryptionMethodOutput {

    private final String openText;


    public VigenereOutput(String openText) {
        this.openText = openText;
    }

    public String getText() {
        return openText;
    }

}
