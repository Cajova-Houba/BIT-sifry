package org.valesz.crypt.core.freqanal;

import org.valesz.crypt.core.Cryptor;

import java.util.*;

/**
 * This object will perform a frequency analysis upon given text.
 *
 * Created by valesz on 19.04.2017.
 */
// todo: test
public class FrequencyAnalyser {

    /**
     * Text to perform analysis on.
     */
    private final String text;

    private final String lettersOnlyText;

    private final int textLen;

    private final int lettersOnlyTextLen;


    public FrequencyAnalyser(String text) {
        this.text = text;
        textLen = text.length();
        lettersOnlyText = text.replaceAll("[^a-zA-Z]", "");
        lettersOnlyTextLen = lettersOnlyText.length();
    }

    /**
     * Perform the analysis with given method.
     *
     * @param method Particular method to be used for frequency analysis.
     * @param n Distance between analyzed letters. If digrams/trigrams are analyzed, this is the distance between letters in them. Use 1 if you don't know what to do.
     * @param offset Start analysis from this position (non-alphabet characters ignored). Use 0 if you don't know what to do.
     * @return List of result object. Each object will contain unique string (which occurrence was analysed) and its abslute and relative count.
     */
    public List<FrequencyAnalysisResult> analyse(FrequencyAnalysisMethod method, int n, int offset) {

        if(n < 1) {
            n = 1;
        }

        if( offset < 0 || offset >= textLen) {
            offset = 0;
        }

        switch (method) {
            case Trigrams:
                return analyzeTrigrams(n, offset);
            case Digrams:
                return analyzeDigrams(n, offset);
            case Letters:
            default:
                return analyzeLetters(n, offset);
        }
    }

    /**
     * Perform frequency analysis on digrams.
     *
     * @param n Analyze only n-th letters/digrams/trigrams...
     * @param offset Start analysis from this position (non-alphabet characters ignored).
     * @return Result of digram frequency analysis. Results objects are orderd by digrams (aa, ab, ac...)
     */
    private List<FrequencyAnalysisResult> analyzeTrigrams(int n, int offset) {
        // use tree map so results are sorted by key (=trigram)
        Map<String, FrequencyAnalysisResult> trigramAnalysis = new TreeMap<String, FrequencyAnalysisResult>();
        int realTrigramCount = 0;

        // create digram from n-th letters
        int i = offset;
        while (i+2*n < lettersOnlyTextLen) {
            String trigram = Character.toLowerCase(lettersOnlyText.charAt(i))+""+Character.toLowerCase(lettersOnlyText.charAt(i+n))+""+Character.toLowerCase(lettersOnlyText.charAt(i+2*n));
            realTrigramCount++;

            // add digram to map
            if(trigramAnalysis.containsKey(trigram)) {
                trigramAnalysis.get(trigram).incAbsoluteCount();
            } else {
                trigramAnalysis.put(trigram, new FrequencyAnalysisResult(trigram, 1,0.0));
            }
            i++;
        }

        // relative counts
        for(String dig : trigramAnalysis.keySet()) {
            trigramAnalysis.get(dig).recountRelativeCount(realTrigramCount);
        }

        return new ArrayList<FrequencyAnalysisResult>(trigramAnalysis.values());
    }

    /**
     * Perform frequency analysis on digrams.
     *
     * @param n Analyze only n-th letters/digrams/trigrams...
     * @param offset Start analysis from this position (non-alphabet characters ignored).
     * @return Result of digram frequency analysis. Results objects are orderd by digrams (aa, ab, ac...)
     */
    private List<FrequencyAnalysisResult> analyzeDigrams(int n, int offset) {
        // use tree map so results are sorted by key (=digram)
        Map<String, FrequencyAnalysisResult> digramAnalysis = new TreeMap<String, FrequencyAnalysisResult>();
        int realDigramCount = 0;


        // create digram from n-th letters
        int i = offset;
        while (i+n < lettersOnlyTextLen) {
            String digram = Character.toLowerCase(lettersOnlyText.charAt(i))+""+Character.toLowerCase(lettersOnlyText.charAt(i+n));
            realDigramCount++;

            // add digram to map
            if(digramAnalysis.containsKey(digram)) {
                digramAnalysis.get(digram).incAbsoluteCount();
            } else {
                digramAnalysis.put(digram, new FrequencyAnalysisResult(digram, 1,0.0));
            }
            i++;
        }

        // relative counts
        for(String dig : digramAnalysis.keySet()) {
            digramAnalysis.get(dig).recountRelativeCount(realDigramCount);
        }

        return new ArrayList<FrequencyAnalysisResult>(digramAnalysis.values());
    }

    /**
     * Perform frequency analysis on letters.
     * @param n Analyze only n-th letters/digrams/trigrams...
     * @param offset Start analysis from this position (non-alphabet characters ignored).
     * @return Result of letter frequency analysis.
     */
    private List<FrequencyAnalysisResult> analyzeLetters(int n, int offset) {
        FrequencyAnalysisResult[] frequencyAnalysisResults = FrequencyAnalysisResult.prepareForStandardAlphabet();
        int realLength = 0;
        for(int i = offset; i < textLen; i += n) {
            char c = text.charAt(i);
            if(!Character.isLetter(c) || c > 128) {
                continue;
            }

            c = Character.toLowerCase(c);
            frequencyAnalysisResults[c- Cryptor.FIRST_LETTER].incAbsoluteCount();
            realLength++;
        }

        List<FrequencyAnalysisResult> res = new ArrayList<FrequencyAnalysisResult>(frequencyAnalysisResults.length);
        for(FrequencyAnalysisResult far : frequencyAnalysisResults) {
            far.recountRelativeCount(realLength);
            res.add(far);
        }

        return res;
    }


}
