package org.valesz.crypt.core.atbas;

import org.valesz.crypt.core.EncryptionMethodOutput;

/**
 * Created by Zdenek Vales on 17.4.2017.
 */
public class AtbasOutput implements EncryptionMethodOutput {

    private final String openText;

    public AtbasOutput(String openText) {
        this.openText = openText;
    }

    public String getText() {
        return openText;
    }
}
