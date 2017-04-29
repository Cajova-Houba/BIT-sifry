package org.valesz.crypt.ui;

/**
 * Created by Zdenek Vales on 29.4.2017.
 */
public class StatusMessages {

    public class Formated {
        public static final String DICTIONARY_LOADED = "Slovník %s načten.";

        public static final String FILE_FORMAT_ERROR = "Špatný formát souboru: %s";

        public static final String WRONG_KEY_LEN_RANGE = "Špatný rozsah délky klíče: [%d:%d]";
    }

    public static final String NO_INPUT_TEXT = "Není vstupní text.";

    public static final String ERROR_LOADING_DICTIONARY = "Chyba při načítání slovníku.";

    public static final String WRONG_KEY_LENGTH = "Špatná délka klíče.";

    public static final String WRONG_DICTIONARY = "Špatný slovník.";

    public static final String WRONG_PERIOD = "Perioda je větší než celková délka textu.";

    public static final String WRONG_OFFSET = "Offset je větší než délka textu.";

    public static final String NO_FILE_PATH = "Není zadána cesta k souboru.";

    public static final String FILE_NOT_FOUND = "Soubor nenalezen.";
}
