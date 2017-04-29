package org.valesz.crypt.core.dictionary;

import org.valesz.crypt.core.freqanal.FrequencyAnalysisResult;

import java.util.List;

/**
 * Dictionary interface. Each dictionary holds information about a certain language.
 *
 * Created by valesz on 24.04.2017.
 */

// todo: words
public interface IDictionary {

    /**
     * Returns a language code of this dictionary.
     *
     * @return String containing language code.
     */
    String getLanguageCode();

    /**
     * Returns a list containing relative frequencies of each letter in that language.
     * For simplicity, only ASCII, lowercase letters should be used.
     *
     * @return List containing the info about letter frequency in the language. Absolute counts are zero.
     */
    List<FrequencyAnalysisResult> getLettersFrequency();

    /**
     * Calculates deviation between the letter frequency of this dictionary with another set of letter frequency.
     *
     * @param frequencyAnalysisResults List containing the letter frequencies. Note that length of this list should be same as
     *                                 the one returned by getLettersFrequency().
     * @return Relative deviance. If the provided list has wrong size, Double.NaN is returned.
     */
    double calculateDeviation(List<FrequencyAnalysisResult> frequencyAnalysisResults);
}
