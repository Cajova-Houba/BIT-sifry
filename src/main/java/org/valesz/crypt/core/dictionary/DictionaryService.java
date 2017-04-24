package org.valesz.crypt.core.dictionary;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * A singleton class which holds all loaded dictionaries.
 * It's possible to have only one dictionary for each language code.
 *
 * Created by valesz on 24.04.2017.
 */
// todo: test
public class DictionaryService {

    private static final Logger logger = Logger.getLogger(DictionaryService.class.getName());

    public static DictionaryService getInstance() {
        return instance;
    }

    private static DictionaryService instance = new DictionaryService();

    /**
     * Language code is used as a key.
     */
    private Map<String, IDictionary> loadedDictionaries;

    private DictionaryService() {
        loadedDictionaries = new HashMap<String, IDictionary>();

        // basic initialization
        try {
            String baseDir = getClass().getProtectionDomain().getCodeSource().getLocation().toString();
            reloadDictionaries(baseDir);
        } catch (Exception e) {
            logger.severe("Initialization failed with exception: "+e.getClass()+". Message: "+e.getMessage());
        }
    }

    /**
     * Returns all loaded dictionaries.
     * @return Collection containing the dictionaries.
     */
    public Collection<IDictionary> getAll() {
        return loadedDictionaries.values();
    }

    /**
     * Returns the dictionary for a language.
     * @param languageCode Language code.
     * @return Dictionary or null if the language for the code doesn't exist.
     */
    public IDictionary getDictionary(String languageCode) {
        return loadedDictionaries.get(languageCode);
    }

    /**
     * Reloads dictionaries.
     *
     * @param baseDirectory Base directory with dictionaries. All files with DictionaryLoader.DICTIONARY_FILE_EXTENSION will be used.
     */
    public void reloadDictionaries(String baseDirectory) throws IOException, NotADictionaryFileException {
        File baseDir = new File(baseDirectory);

        if(!baseDir.exists() || !baseDir.isDirectory()) {
            // not a dir
            return;
        }

        File[] file = baseDir.listFiles();
        for(File f : file) {
            String fName = f.getName();
            if(fName.length() <= DictionaryLoader.EXTENSION_LEN) {
                // too short name
                continue;
            }

            // check extension
            if(!fName.substring(fName.length() - DictionaryLoader.EXTENSION_LEN, fName.length()).equals(DictionaryLoader.DICTIONARY_FILE_EXTENSION)) {
                continue;
            }

            addDictionary(DictionaryLoader.loadDictionaryFromFile(fName));
        }
    }

    /**
     * Adds a dictionary. If another dictionary for same language code is already present, it will be overwritten.
     * @param dictionary Dictionary to be added.
     */
    public void addDictionary(IDictionary dictionary) {
        loadedDictionaries.put(dictionary.getLanguageCode(), dictionary);
    }


}
