package org.valesz.crypt.core;

/**
 * An interface for generic encryption method.
 *
 * Created by Zdenek Vales on 17.4.2017.
 */
public interface EncryptionMethod<T extends EncryptionMethodInput, S extends EncryptionMethodOutput> {

    /**
     * Encrypts the input and returns object containing the encrypted text.
     *
     * @param cipherInput Input object.
     * @return Object containing the encrypted input.
     */
    S encrypt(T cipherInput);

    /**
     * Performs operation inverse to encryption over input data and returns object which contains decrypted text.
     *
     * @param cipherInput Input object.
     * @return Object containing the decrypted text.
     */
    S decrypt(T cipherInput);
}
