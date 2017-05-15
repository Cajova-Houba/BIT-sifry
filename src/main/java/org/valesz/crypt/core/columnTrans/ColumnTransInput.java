package org.valesz.crypt.core.columnTrans;


import org.valesz.crypt.core.EncryptionMethodInput;

/**
 * Created by Zdenek Vales on 17.4.2017.
 */
public class ColumnTransInput implements EncryptionMethodInput {

    private final String openText;

    private final String key;

    public ColumnTransInput(String openText, String key) {
        this.openText = openText;
        this.key = key;
    }

    public String getText() {
        return openText;
    }

    /**
     * This is expected to be already stripped.
     * @return
     */
    public String getKey() {
        return key;
    }

    @Override
    public EncryptionMethodInput cloneInput(String newText) {
        return new ColumnTransInput(newText, key);
    }
}
