package org.valesz.crypt.core;

/**
 * A simple class which represents result of frequency analysis for exactly one letter.
 * That means list of those object will be returned after frequency analysis.
 *
 * Created by Zdenek Vales on 19.3.2017.
 */
public class FrequencyAnalysisResult {

    /**
     * Returns an array of 26, non-null FrequencyAnalysisResult objects. Each item will have a letter (a..z) assigned and
     * relative/absolute count set to 0.
     * @return
     */
    public static FrequencyAnalysisResult[] prepareForStandardAlphabet() {
        FrequencyAnalysisResult[] res = new FrequencyAnalysisResult[Cryptor.ALPHABET_LEN];
        char c = 'a';
        for(int i = 0; i < res.length; i++) {
            res[i] = new FrequencyAnalysisResult(c,0,0);
            c ++;
        }

        return res;
    }

    private Character character;

    private int absoluteCount;

    private double relativeCount;

    public FrequencyAnalysisResult(Character character, int absoluteCount, double relativeCount) {
        this.character = character;
        this.absoluteCount = absoluteCount;
        this.relativeCount = relativeCount;
    }

    public Character getCharacter() {
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
}
