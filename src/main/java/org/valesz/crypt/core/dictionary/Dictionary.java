package org.valesz.crypt.core.dictionary;

import org.valesz.crypt.core.freqanal.FrequencyAnalysisResult;

import java.util.List;

/**
 * Basic dictionary implementation.
 *
 * Created by valesz on 24.04.2017.
 */
// todo: test
public class Dictionary implements IDictionary{

    private final String languageCode;

    private final List<FrequencyAnalysisResult> letterFrequency;

    public Dictionary(String languageCode, List<FrequencyAnalysisResult> letterFrequency) {
        this.languageCode = languageCode;
        this.letterFrequency = letterFrequency;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public List<FrequencyAnalysisResult> getLettersFrequency() {
        return letterFrequency;
    }

    public double calculateDeviance(List<FrequencyAnalysisResult> frequencyAnalysisResults) {
        // todo
        return 0;
    }
}
