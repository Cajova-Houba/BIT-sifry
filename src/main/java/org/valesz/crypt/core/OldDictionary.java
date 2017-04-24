package org.valesz.crypt.core;

import java.util.List;

/**
 * Dictionary provides app with lists of common words, letters and their frequency.
 *
 * Created by Zdenek Vales on 18.3.2017.
 */
public interface OldDictionary {

    /**
     * Returns a list of words expected in decrypted text.
     * @return
     */
    List<String> getExpectedWords();

    /**
     * Returns a list of keys to try.
     * @return
     */
    List<String> getKeys();

    /**
     * Returns an array containing frequency of letters. Order is alphabetical (first item = frequency of first letter).
     * @return
     */
    double[] getLetterFrequency();
}
