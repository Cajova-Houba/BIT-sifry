package org.valesz.crypt.core;

import org.junit.Test;
import org.valesz.crypt.core.dictionary.*;
import org.valesz.crypt.core.freqanal.FrequencyAnalyser;
import org.valesz.crypt.core.freqanal.FrequencyAnalysisMethod;
import org.valesz.crypt.core.freqanal.FrequencyAnalysisResult;
import org.valesz.crypt.core.utils.FileUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by valesz on 24.04.2017.
 */
public class DictionaryTest {

    @Test
    public void testInitialization() {
        // nothing should happen
        DictionaryService dictionaryService = DictionaryService.getInstance();
    }

    @Test
    public void testManuallyAddDictionary() {
        String langCode = "asd";
        DictionaryService dictionaryService = DictionaryService.getInstance();
        int origCount = dictionaryService.getAll().size();
        dictionaryService.addDictionary(new Dictionary(langCode, new ArrayList<>()));
        assertEquals("One more dictionary should be loaded!",origCount+1, dictionaryService.getAll().size());
        assertNotNull("Dictionary not obtained!", dictionaryService.getDictionary(langCode));
    }

    @Test(expected = FileNotFoundException.class)
    public void testLoadDictionaryFromNonExistentFile() throws IOException, NotADictionaryFileException {
        DictionaryLoader.loadDictionaryFromFile("asdasd");
    }

    @Test(expected = NotADictionaryFileException.class)
    public void testLoadDictionaryFromBadExistentFile() throws IOException, NotADictionaryFileException {
        String dictName = "/badDictionary.txt";
        DictionaryLoader.loadDictionaryFromFile(getClass().getResource(dictName).getPath());
    }

    @Test
    public void testLoadDictionaryFromFile() throws IOException, NotADictionaryFileException {
        String dictName = "/testDictionary.txt";
        String langCode = "CZ";
        FrequencyAnalysisResult first = new FrequencyAnalysisResult("a",0,0.5);
        FrequencyAnalysisResult second = new FrequencyAnalysisResult("b",0,0.6);

        IDictionary dictionary = DictionaryLoader.loadDictionaryFromFile(getClass().getResource(dictName).getPath());
        assertEquals("Wrong language code!", langCode, dictionary.getLanguageCode());
        List<FrequencyAnalysisResult> letterFrequencies = dictionary.getLettersFrequency();
        assertEquals("Wrong letter frequencies count!", 2, letterFrequencies.size());
        assertEquals("Wrong 1 letter frequency!", first, letterFrequencies.get(0));
        assertEquals("Wrong 2 letter frequency!", second, letterFrequencies.get(1));
    }

    @Test
    public void testCalculateDeviation() throws IOException, NotADictionaryFileException {
        String dictName = "/testDictionary.txt";
        IDictionary dictionary = DictionaryLoader.loadDictionaryFromFile(getResourcePath(dictName));
        List<FrequencyAnalysisResult> other = Arrays.asList(
                new FrequencyAnalysisResult("a",0,0.1),
                new FrequencyAnalysisResult("a",0,0.2)
        );

        double expected = Math.sqrt((Math.pow(0.5 - 0.1,2) + Math.pow(0.6 - 0.2,2))/2);
        assertEquals("Wrong deviation!", expected, dictionary.calculateDeviation(other), 0.01);
    }

    @Test
    public void testCalculateBadDeviation() throws IOException, NotADictionaryFileException {
        String dictName = "/testDictionary.txt";
        IDictionary dictionary = DictionaryLoader.loadDictionaryFromFile(getResourcePath(dictName));
        double dev = dictionary.calculateDeviation(Arrays.asList());
        assertTrue("NaN should be returned!", Double.isNaN(dev));
    }

    @Test
    public void testGetLowestDevianceDictionary() throws IOException, NotADictionaryFileException {
        // load cz and en dictionary
        DictionaryService dictionaryService = DictionaryService.getInstance();
        dictionaryService.addDictionary(DictionaryLoader.loadDictionaryFromFile(getResourcePath("/cz.dict")));
        dictionaryService.addDictionary(DictionaryLoader.loadDictionaryFromFile(getResourcePath("/en.dict")));
        assertEquals("Two dictionaries expected!", 2, dictionaryService.getAll().size());

        // load czech and english text
        String czText = FileUtils.readFromFile(getResourcePath("/cz-text.txt"));
        FrequencyAnalyser czechnalyser = new FrequencyAnalyser(czText);
        String enText = FileUtils.readFromFile(getResourcePath("/en-text.txt"));
        FrequencyAnalyser enalyser = new FrequencyAnalyser(enText);

        // frequency analysis
        List<FrequencyAnalysisResult> czFrequencyAnalysisResults = czechnalyser.analyse(FrequencyAnalysisMethod.Letters, 0,0);
        List<FrequencyAnalysisResult> enFrequencyAnalysisResults = enalyser.analyse(FrequencyAnalysisMethod.Letters,0,0);

        // results
        IDictionary dictionary = dictionaryService.getLowestDevianceDictionary(czFrequencyAnalysisResults);
        assertNotNull("Null returned!", dictionary);
        assertEquals("Wrong language code!","CZ",dictionary.getLanguageCode());

        dictionary = dictionaryService.getLowestDevianceDictionary(enFrequencyAnalysisResults);
        assertNotNull("Null returned!", dictionary);
        assertEquals("Wrong language code!","EN",dictionary.getLanguageCode());
    }

    private String getResourcePath(String resourceName) {
        return getClass().getResource(resourceName).getPath();
    }
}
