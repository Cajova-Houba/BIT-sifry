package org.valesz.crypt.core.freqanal;

/**
 * Possible methods of performing frequency analysis.
 *
 * Created by Zdenek Vales on 19.3.2017.
 */
public enum FrequencyAnalysisMethod {

    /**
     * Perform the letter frequency analysis on the whole message.
     */
    Letters,

    /**
     * Perform the letter frequency analysis only on even chars of the message.
     * @deprecated
     * Use the 'n' and 'offset' parameters of analyse method in FrequencyAnalyser.
     */
    @Deprecated
    EvenChars,

    /**
     * Perform the letter frequency analysis only on odd chars of the message.
     * @deprecated
     * Use the 'n' and 'offset' parameters of analyse method in FrequencyAnalyser.
     */
    @Deprecated
    OddChars,

    /**
     * Perform digram frequency analysis.
     */
    Digrams,

    /**
     * Perform trigram frequency analysis.
     */
    Trigrams,

}
