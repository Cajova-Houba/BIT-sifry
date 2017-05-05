package org.valesz.crypt.core.vigenere;


import org.valesz.crypt.core.EncryptionMethodInput;

/**
 * Created by Zdenek Vales on 17.4.2017.
 */
public class VigenereInput implements EncryptionMethodInput {

    private final String openText;

    private final String key;

    public VigenereInput(String openText, String key) {
        this.openText = openText;
        this.key = key;
    }

    public String getText() {
        return openText;
    }

    public String getKey() {
        return key;
    }

    @Override
    public EncryptionMethodInput cloneInput(String newText) {
        return new VigenereInput(newText, key);
    }
}
