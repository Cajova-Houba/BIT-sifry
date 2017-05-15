package org.valesz.crypt.core.dictionary;

import org.valesz.crypt.core.freqanal.FrequencyAnalysisResult;
import org.valesz.crypt.core.utils.FileUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * A simple class used for loading dictionaries from files.
 *
 * Created by valesz on 24.04.2017.
 */
// todo: test
public class DictionaryLoader {

    public static final int MIN_LINES = 2;
    public static final String DELIMITER = ",";
    public static final String DICTIONARY_FILE_EXTENSION = "dict";
    public static final int EXTENSION_LEN = DICTIONARY_FILE_EXTENSION.length();


    /**
     * Loads a dictionary from csv file. The first line of file should contain only the language code.
     * Every line after the first one should contain two, comma separated values: letter,relative count convertible to Double.
     *
     * @param file Source file. If the file has less than MIN_LINES, exception is thrown.
     * @return Loaded dictionary.
     */
    public static IDictionary loadDictionaryFromFile(File file) throws IOException, NotADictionaryFileException {
        List<String> lines = FileUtils.readLinesFromFile(file);
        if(lines.size() < MIN_LINES) {
            throw new NotADictionaryFileException("The file doesn't contain enough data to load a dictionary.");
        }

        List<FrequencyAnalysisResult> letterFrequencies = new ArrayList<FrequencyAnalysisResult>();
        String languageCode = "";

        Iterator<String> lineIt = lines.iterator();
        languageCode = lineIt.next();

        int line = 1;
        while (lineIt.hasNext()) {
            String[] items = lineIt.next().split(DELIMITER);
            if(items.length != 2) {
                throw new NotADictionaryFileException("Two items expected on line: "+line+"!");
            }

            String letter = items[0];
            double relativeCount = 0;
            try {
                relativeCount = Double.parseDouble(items[1]);
            } catch (NumberFormatException e) {
                throw new NotADictionaryFileException("Wrong frequency format on line: "+line+"!");
            }

            letterFrequencies.add(new FrequencyAnalysisResult(letter, 0, relativeCount));

            line++;
        }

        return new Dictionary(languageCode, letterFrequencies);
    }

    /**
     * Loads a dictionary from csv file. The first line of file should contain only the language code.
     * Every line after the first one should contain two, comma separated values: letter,relative count convertible to Double.
     *
     * @param fileName Name of the source file. If the file has less than MIN_LINES, exception is thrown.
     * @return Loaded dictionary.
     */
    public static IDictionary loadDictionaryFromFile(String fileName) throws FileNotFoundException, IOException, NotADictionaryFileException {
        return DictionaryLoader.loadDictionaryFromFile(new File(fileName));
    }
}
