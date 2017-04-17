package org.valesz.crypt.core;

/**
 * Input for encryp/decrypt methods. Object should contain all necessities for the particular encryption method.
 *
 * Created by Zdenek Vales on 17.4.2017.
 */
public interface EncryptionMethodInput {

    /**
     * Returns the text of this input object.
     *
     * @return A string.
     */
    String getText();

}
