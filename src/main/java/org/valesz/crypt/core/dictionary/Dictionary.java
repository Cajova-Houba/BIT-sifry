package org.valesz.crypt.core.dictionary;

import org.valesz.crypt.core.freqanal.FrequencyAnalysisResult;

import java.util.Iterator;
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

    public double calculateDeviation(List<FrequencyAnalysisResult> frequencyAnalysisResults) {
        // todo
        if(frequencyAnalysisResults.size() != letterFrequency.size()) {
            return Double.NaN;
        }

        double relativeDeviance = 0d;
        Iterator<FrequencyAnalysisResult> thisLetterFreqIt = letterFrequency.iterator();
        Iterator<FrequencyAnalysisResult> otherLetterFreqIt = frequencyAnalysisResults.iterator();
        while(thisLetterFreqIt.hasNext()) {
            double thisRelCount = thisLetterFreqIt.next().getRelativeCount();
            double otherRelCount = otherLetterFreqIt.next().getRelativeCount();

            relativeDeviance += Math.pow((thisRelCount-otherRelCount),2);

        }

        relativeDeviance /= letterFrequency.size();

        return Math.sqrt(relativeDeviance);
    }
}
