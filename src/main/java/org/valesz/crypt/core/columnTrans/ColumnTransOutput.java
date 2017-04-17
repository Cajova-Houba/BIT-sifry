package org.valesz.crypt.core.columnTrans;


import org.valesz.crypt.core.EncryptionMethodOutput;

/**
 * Created by Zdenek Vales on 17.4.2017.
 */
public class ColumnTransOutput implements EncryptionMethodOutput {

    private final String openText;


    public ColumnTransOutput(String openText) {
        this.openText = openText;
    }

    public String getText() {
        return openText;
    }

}
