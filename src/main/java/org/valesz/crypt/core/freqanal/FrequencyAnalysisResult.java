package org.valesz.crypt.core.freqanal;

import org.valesz.crypt.core.Cryptor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * A simple class which represents result of frequency analysis for a piece of string.
 * That means list of those object will be returned after frequency analysis.
 *
 * Created by Zdenek Vales on 19.3.2017.
 */
public class FrequencyAnalysisResult {

    /**
     * Calculate the deviance of other list from reference list.
     * Both list are expected to have desc order (relative count) and also same length.
     *
     * @param reference
     * @param other
     * @return
     */
    public static double calculateDeviance(List<FrequencyAnalysisResult> reference, List<FrequencyAnalysisResult> other) {
        double dev = 0.0;
        Iterator<FrequencyAnalysisResult> refIt = reference.iterator();
        Iterator<FrequencyAnalysisResult> otherIt = other.iterator();

        while(refIt.hasNext() && otherIt.hasNext()) {
            dev += Math.pow(refIt.next().getRelativeCount() - otherIt.next().getRelativeCount(),2);
        }

        dev /= reference.size();

        return Math.sqrt(dev);
    }

    /**
     * Takes the input and shifts every relative and absolute count by 1 (the last one will be shifted to first one).
     *
     * @return Shifted frequency analysis results.
     */
    public static List<FrequencyAnalysisResult> shiftResults(List<FrequencyAnalysisResult> frequencyAnalysisResults) {
        List<FrequencyAnalysisResult> shifted = new ArrayList<>(frequencyAnalysisResults.size());
        for(int i = 0; i < frequencyAnalysisResults.size(); i++) {
            FrequencyAnalysisResult r = frequencyAnalysisResults.get((frequencyAnalysisResults.size()-1+i) % frequencyAnalysisResults.size());
            FrequencyAnalysisResult prev = frequencyAnalysisResults.get(i);

            shifted.add(new FrequencyAnalysisResult(prev.character, r.getAbsoluteCount(), r.getRelativeCount()));
        }

        return shifted;
    }

    /**
     * Returns an array of 26, non-null FrequencyAnalysisResult objects. Each item will have a letter (a..z) assigned and
     * relative/absolute count set to 0.
     * @return
     */
    public static FrequencyAnalysisResult[] prepareForStandardAlphabet() {
        FrequencyAnalysisResult[] res = new FrequencyAnalysisResult[Cryptor.ALPHABET_LEN];
        char c = 'a';
        for(int i = 0; i < res.length; i++) {
            res[i] = new FrequencyAnalysisResult(String.valueOf(c),0,0);
            c ++;
        }

        return res;
    }

    private String character;

    private int absoluteCount;

    private double relativeCount;

    public FrequencyAnalysisResult(String character, int absoluteCount, double relativeCount) {
        this.character = character;
        this.absoluteCount = absoluteCount;
        this.relativeCount = relativeCount;
    }

    public String getCharacter() {
        return character;
    }

    public int getAbsoluteCount() {
        return absoluteCount;
    }

    public double getRelativeCount() {
        return relativeCount;
    }

    public void setRelativeCount(double relativeCount) {
        this.relativeCount = relativeCount;
    }

    public void incAbsoluteCount() {
        this.absoluteCount++;
    }

    /**
     * Sets relative count to absoluteCount/length.
     * @param length Length.
     */
    public void recountRelativeCount(int length) {
        this.relativeCount = this.absoluteCount / (double)length;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FrequencyAnalysisResult that = (FrequencyAnalysisResult) o;

        if (absoluteCount != that.absoluteCount) return false;
        if (Double.compare(that.relativeCount, relativeCount) != 0) return false;
        return character.equals(that.character);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = character.hashCode();
        result = 31 * result + absoluteCount;
        temp = Double.doubleToLongBits(relativeCount);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
