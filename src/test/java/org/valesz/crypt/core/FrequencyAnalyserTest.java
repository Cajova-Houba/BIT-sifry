package org.valesz.crypt.core;

import org.junit.Test;
import org.valesz.crypt.core.freqanal.FrequencyAnalyser;
import org.valesz.crypt.core.freqanal.FrequencyAnalysisMethod;
import org.valesz.crypt.core.freqanal.FrequencyAnalysisResult;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Zdenek Vales on 23.4.2017.
 */
public class FrequencyAnalyserTest {

    @Test
    public void testShift() {
        List<FrequencyAnalysisResult> res = Arrays.asList(
                new FrequencyAnalysisResult("a", 1, 0.1),
                new FrequencyAnalysisResult("b", 2, 0.2),
                new FrequencyAnalysisResult("z", 3, 0.3)
        );

        List<FrequencyAnalysisResult> expected = Arrays.asList(
                new FrequencyAnalysisResult("a", 3, 0.3),
                new FrequencyAnalysisResult("b", 1, 0.1),
                new FrequencyAnalysisResult("z", 2, 0.2)
        );

        List<FrequencyAnalysisResult> shifted = FrequencyAnalysisResult.shiftResults(res);
        assertNotNull("Null returned!", shifted);
        Iterator<FrequencyAnalysisResult> exIt = expected.iterator();
        Iterator<FrequencyAnalysisResult> shiftedIt = shifted.iterator();
        while(exIt.hasNext()) {
            FrequencyAnalysisResult ex = exIt.next();
            FrequencyAnalysisResult sh = shiftedIt.next();
            assertEquals("Wrong character!", ex.getCharacter(), sh.getCharacter());
            assertEquals("Wrong absolute count!", ex.getAbsoluteCount(), sh.getAbsoluteCount());
            assertEquals("Wrong relative count!", ex.getRelativeCount(), sh.getRelativeCount(), 0.1);
        }
    }

    @Test
    public void testLetterFrequencyAnalysis() {
        String toAnalyse = "abc ab c\n a bc";
        int absCount = 3;
        double relCount = 3 / 9.0;
        FrequencyAnalyser analyser = new FrequencyAnalyser(toAnalyse);
        List<FrequencyAnalysisResult> res = analyser.analyse(FrequencyAnalysisMethod.Letters, 0,0);
        assertNotNull("Null returned!", res);
        assertEquals("Three result objects expected!", Cryptor.ALPHABET_LEN, res.size());
        for(FrequencyAnalysisResult r : res) {
            String l = r.getCharacter();
            if(l.equals("a") || l.equals("b") || l.equals("c")) {
                assertEquals("Wrong absolute count for letter "+l+"!", absCount, r.getAbsoluteCount());
                assertEquals("Wrong relative count for letter "+l+"!", relCount, r.getRelativeCount(), 0.01);
            } else {
                assertEquals("Wrong absolute count for letter "+l+"!", 0, r.getAbsoluteCount());
                assertEquals("Wrong relative count for letter "+l+"!", 0, r.getRelativeCount(), 0.01);
            }
        }
    }

    @Test
    public void testLetterPeriodFrequencyAnalysis() {
        String toAnalyse = "abc ab c\n a bcab";
        int absCount = 2;
        double relCount = 2 / 6.0;
        FrequencyAnalyser analyser = new FrequencyAnalyser(toAnalyse);
        List<FrequencyAnalysisResult> res = analyser.analyse(FrequencyAnalysisMethod.Letters, 2,0);
        assertNotNull("Null returned!", res);
        assertEquals("Three result objects expected!", Cryptor.ALPHABET_LEN, res.size());
        for(FrequencyAnalysisResult r : res) {
            String l = r.getCharacter();
            if(l.equals("a") || l.equals("b") || l.equals("c")) {
                assertEquals("Wrong absolute count for letter "+l+"!", absCount, r.getAbsoluteCount());
                assertEquals("Wrong relative count for letter "+l+"!", relCount, r.getRelativeCount(), 0.01);
            } else {
                assertEquals("Wrong absolute count for letter "+l+"!", 0, r.getAbsoluteCount());
                assertEquals("Wrong relative count for letter "+l+"!", 0, r.getRelativeCount(), 0.01);
            }
        }
    }

    @Test
    public void testLetterPeriodOffsetFrequencyAnalysis() {
        String toAnalyse = "abc ab c\n a bcabc";
        int absCount = 2;
        double relCount = 2 / 6.0;
        FrequencyAnalyser analyser = new FrequencyAnalyser(toAnalyse);
        List<FrequencyAnalysisResult> res = analyser.analyse(FrequencyAnalysisMethod.Letters, 2,1);
        assertNotNull("Null returned!", res);
        assertEquals("Three result objects expected!", Cryptor.ALPHABET_LEN, res.size());
        for(FrequencyAnalysisResult r : res) {
            String l = r.getCharacter();
            if(l.equals("a") || l.equals("b") || l.equals("c")) {
                assertEquals("Wrong absolute count for letter "+l+"!", absCount, r.getAbsoluteCount());
                assertEquals("Wrong relative count for letter "+l+"!", relCount, r.getRelativeCount(), 0.01);
            } else {
                assertEquals("Wrong absolute count for letter "+l+"!", 0, r.getAbsoluteCount());
                assertEquals("Wrong relative count for letter "+l+"!", 0, r.getRelativeCount(), 0.01);
            }
        }
    }

    @Test
    public void testDigramFrequencyAnalysis() {
        String toAnalyse = "abc ab c\n a bca";
        int absCount = 3;
        double relCount = 3 / 9.0;
        FrequencyAnalyser analyser = new FrequencyAnalyser(toAnalyse);
        List<FrequencyAnalysisResult> res = analyser.analyse(FrequencyAnalysisMethod.Digrams, 0,0);
        assertNotNull("Null returned!", res);
        assertEquals("Three result objects expected!", 3, res.size());
        for(FrequencyAnalysisResult r : res) {
            String l = r.getCharacter();
            assertTrue("Wrong letter: "+l+"!", l.equals("ab") || l.equals("bc") || l.equals("ca"));
            assertEquals("Wrong absolute count!", absCount, r.getAbsoluteCount());
            assertEquals("Wrong relative count!", relCount, r.getRelativeCount(), 0.01);
        }
    }

    @Test
    public void testDigramPeriodFrequencyAnalysis() {
        String toAnalyse = "abc ab c\n a b";
        int absCount = 2;
        double relCount = 2 / 6.0;
        FrequencyAnalyser analyser = new FrequencyAnalyser(toAnalyse);
        List<FrequencyAnalysisResult> res = analyser.analyse(FrequencyAnalysisMethod.Digrams, 2,0);
        assertNotNull("Null returned!", res);
        assertEquals("Three result objects expected!", 3, res.size());
        for(FrequencyAnalysisResult r : res) {
            String l = r.getCharacter();
            if(l.equals("ac") || l.equals("ba") || l.equals("cb")) {
                assertEquals("Wrong absolute count for letter "+l+"!", absCount, r.getAbsoluteCount());
                assertEquals("Wrong relative count for letter "+l+"!", relCount, r.getRelativeCount(), 0.01);
            } else {
                assertEquals("Wrong absolute count for letter "+l+"!", 0, r.getAbsoluteCount());
                assertEquals("Wrong relative count for letter "+l+"!", 0, r.getRelativeCount(), 0.01);
            }
        }
    }

    @Test
    public void testDigramPeriodOffsetFrequencyAnalysis() {
        String toAnalyse = "abc ab c\n a bc";
        int absCount = 2;
        double relCount = 2 / 6.0;
        FrequencyAnalyser analyser = new FrequencyAnalyser(toAnalyse);
        List<FrequencyAnalysisResult> res = analyser.analyse(FrequencyAnalysisMethod.Digrams, 2,1);
        assertNotNull("Null returned!", res);
        assertEquals("Three result objects expected!", 3, res.size());
        for(FrequencyAnalysisResult r : res) {
            String l = r.getCharacter();
            if(l.equals("ac") || l.equals("ba") || l.equals("cb")) {
                assertEquals("Wrong absolute count for letter "+l+"!", absCount, r.getAbsoluteCount());
                assertEquals("Wrong relative count for letter "+l+"!", relCount, r.getRelativeCount(), 0.01);
            } else {
                assertEquals("Wrong absolute count for letter "+l+"!", 0, r.getAbsoluteCount());
                assertEquals("Wrong relative count for letter "+l+"!", 0, r.getRelativeCount(), 0.01);
            }
        }
    }

    @Test
    public void testTrigramFrequencyAnalysis() {
        String toAnalyse = "abc ab c\n a bcab";
        int absCount = 3;
        double relCount = 3 / 9.0;
        FrequencyAnalyser analyser = new FrequencyAnalyser(toAnalyse);
        List<FrequencyAnalysisResult> res = analyser.analyse(FrequencyAnalysisMethod.Trigrams, 0,0);
        assertNotNull("Null returned!", res);
        assertEquals("Three result objects expected!", 3, res.size());
        for(FrequencyAnalysisResult r : res) {
            String l = r.getCharacter();
            assertTrue("Wrong letter: "+l+"!", l.equals("abc") || l.equals("bca") || l.equals("cab"));
            assertEquals("Wrong absolute count!", absCount, r.getAbsoluteCount());
            assertEquals("Wrong relative count!", relCount, r.getRelativeCount(), 0.01);
        }
    }

    @Test
    public void testTrigramPeriodFrequencyAnalysis() {
        String toAnalyse = "abc ab c\n a bca";
        int absCount = 2;
        double relCount = 2 / 6.0;
        FrequencyAnalyser analyser = new FrequencyAnalyser(toAnalyse);
        List<FrequencyAnalysisResult> res = analyser.analyse(FrequencyAnalysisMethod.Trigrams, 2,0);
        assertNotNull("Null returned!", res);
        assertEquals("Three result objects expected!", 3, res.size());
        for(FrequencyAnalysisResult r : res) {
            String l = r.getCharacter();
            assertTrue("Wrong letter: "+l+"!", l.equals("acb") || l.equals("bac") || l.equals("cba"));
            assertEquals("Wrong absolute count!", absCount, r.getAbsoluteCount());
            assertEquals("Wrong relative count!", relCount, r.getRelativeCount(), 0.01);
        }
    }

    @Test
    public void testTrigramPeriodOffsetFrequencyAnalysis() {
        String toAnalyse = "abc ab c\n a bcab";
        int absCount = 2;
        double relCount = 2 / 6.0;
        FrequencyAnalyser analyser = new FrequencyAnalyser(toAnalyse);
        List<FrequencyAnalysisResult> res = analyser.analyse(FrequencyAnalysisMethod.Trigrams, 2,1);
        assertNotNull("Null returned!", res);
        assertEquals("Three result objects expected!", 3, res.size());
        for(FrequencyAnalysisResult r : res) {
            String l = r.getCharacter();
            assertTrue("Wrong letter: "+l+"!", l.equals("acb") || l.equals("bac") || l.equals("cba"));
            assertEquals("Wrong absolute count!", absCount, r.getAbsoluteCount());
            assertEquals("Wrong relative count!", relCount, r.getRelativeCount(), 0.01);
        }
    }
}
