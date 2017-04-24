package org.valesz.crypt.core;

import org.junit.Test;
import org.valesz.crypt.core.dictionary.*;
import org.valesz.crypt.core.freqanal.FrequencyAnalysisResult;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
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
        assertTrue("No dictionaries should be loaded!", dictionaryService.getAll().isEmpty());
        dictionaryService.addDictionary(new Dictionary(langCode, new ArrayList<>()));
        assertEquals("No dictionaries should be loaded!",1, dictionaryService.getAll().size());
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
}
