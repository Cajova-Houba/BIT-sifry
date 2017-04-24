package org.valesz.crypt.core.dictionary;

/**
 * Thrown when trying to load a dictionary from file which doesn't contain one,
 * or the data inside are not in correct format.
 *
 * Created by valesz on 24.04.2017.
 */
public class NotADictionaryFileException extends Exception {

    public NotADictionaryFileException(String message) {
        super(message);
    }
}
