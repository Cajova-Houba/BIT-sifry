package org.valesz.crypt.core;

/**
 * Input for encryp/decrypt methods. Object should contain all necessities for the particular encryption method.
 *
 * Created by Zdenek Vales on 17.4.2017.
 */
public interface EncryptionMethodInput {

    /**
     * Returns the text of this input object. This is expected to be already stripped.
     *
     * @return A string.
     */
    String getText();

    /**
     * Clones this input object with all of its properties and new text.
     * @param newText New text.
     * @return Cloned object.
     */
    EncryptionMethodInput cloneInput(String newText);

}
