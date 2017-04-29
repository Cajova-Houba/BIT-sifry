package org.valesz.crypt.core.dictionary;

import org.valesz.crypt.core.freqanal.FrequencyAnalysisResult;

import java.util.ArrayList;
import java.util.Comparator;
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

    private final List<FrequencyAnalysisResult> sortedFrequency;

    public Dictionary(String languageCode, List<FrequencyAnalysisResult> letterFrequency) {
        this.languageCode = languageCode;
        this.letterFrequency = letterFrequency;
        this.sortedFrequency = new ArrayList<>(letterFrequency);
        this.sortedFrequency.sort((o1,o2) -> {
            if (o1.getRelativeCount() >= o2.getRelativeCount()) {
                return 1;
            } else {
                return -1;
            }
        });
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public List<FrequencyAnalysisResult> getLettersFrequency() {
        return letterFrequency;
    }

    public double calculateDeviation(List<FrequencyAnalysisResult> frequencyAnalysisResults) {
        if(frequencyAnalysisResults.size() != letterFrequency.size()) {
            return Double.NaN;
        }

        List<FrequencyAnalysisResult> tmp = new ArrayList<>(frequencyAnalysisResults);
        tmp.sort((o1,o2) -> {
            if (o1.getRelativeCount() >= o2.getRelativeCount()) {
                return 1;
            } else {
                return -1;
            }
        });

        double relativeDeviance = 0d;
        Iterator<FrequencyAnalysisResult> thisLetterFreqIt = sortedFrequency.iterator();
        Iterator<FrequencyAnalysisResult> otherLetterFreqIt = tmp.iterator();
        while(thisLetterFreqIt.hasNext()) {
            double thisRelCount = thisLetterFreqIt.next().getRelativeCount();
            double otherRelCount = otherLetterFreqIt.next().getRelativeCount();

            relativeDeviance += Math.pow((thisRelCount-otherRelCount),2);

        }

        relativeDeviance /= letterFrequency.size();

        return Math.sqrt(relativeDeviance);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Dictionary that = (Dictionary) o;

        return languageCode != null ? languageCode.equals(that.languageCode) : that.languageCode == null;
    }

    @Override
    public int hashCode() {
        return languageCode != null ? languageCode.hashCode() : 0;
    }
}
