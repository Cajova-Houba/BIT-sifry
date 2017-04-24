package org.valesz.crypt.core;

import org.junit.Test;
import org.valesz.crypt.core.dictionary.DictionaryService;

import static org.junit.Assert.assertTrue;

/**
 * Created by valesz on 24.04.2017.
 */
public class DictionaryServiceTest {

    @Test
    public void testInitialization() {
        // nothing should happen
        DictionaryService dictionaryService = DictionaryService.getInstance();
        assertTrue("No dictionaries should be laoded!", dictionaryService.getAll().isEmpty());
    }
}
