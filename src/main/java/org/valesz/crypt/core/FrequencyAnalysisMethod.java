package org.valesz.crypt.core;

/**
 * Possible methods of performing frequency analysis.
 *
 * Created by Zdenek Vales on 19.3.2017.
 */
public enum FrequencyAnalysisMethod {

    /**
     * Perform frequency analysis on the whole message.
     */
    All,

    /**
     * Perform frequency analysis only on even chars of the message.
     */
    EvenChars,

    /**
     * Perform frequency analysis only on odd chars of the message.
     */
    OddChars

}
