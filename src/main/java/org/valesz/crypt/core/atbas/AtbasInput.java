package org.valesz.crypt.core.atbas;

import org.valesz.crypt.core.EncryptionMethodInput;

/**
 * Created by Zdenek Vales on 17.4.2017.
 */
public class AtbasInput implements EncryptionMethodInput {

    private final String openText;

    public AtbasInput(String openText) {
        this.openText = openText;
    }

    public String getText() {
        return openText;
    }

    @Override
    public EncryptionMethodInput cloneInput(String newText) {
        return new AtbasInput(newText);
    }
}
