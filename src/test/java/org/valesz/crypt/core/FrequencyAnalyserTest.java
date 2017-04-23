package org.valesz.crypt.core;

import org.junit.Test;
import org.valesz.crypt.core.freqanal.FrequencyAnalyser;
import org.valesz.crypt.core.freqanal.FrequencyAnalysisMethod;
import org.valesz.crypt.core.freqanal.FrequencyAnalysisResult;

import java.util.List;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Zdenek Vales on 23.4.2017.
 */
public class FrequencyAnalyserTest {

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
}
