package org.valesz.crypt.main;

import org.valesz.crypt.controller.AppController;
import org.valesz.crypt.controller.CLIController;
import org.valesz.crypt.core.EncryptionMethod;
import org.valesz.crypt.core.EncryptionMethodInput;
import org.valesz.crypt.core.atbas.AtbasInput;
import org.valesz.crypt.core.atbas.AtbasMethod;
import org.valesz.crypt.core.columnTrans.ColumnTransInput;
import org.valesz.crypt.core.columnTrans.ColumnTransMethod;
import org.valesz.crypt.core.dictionary.DictionaryLoader;
import org.valesz.crypt.core.dictionary.DictionaryService;
import org.valesz.crypt.core.dictionary.NotADictionaryFileException;
import org.valesz.crypt.core.vigenere.VigenereInput;
import org.valesz.crypt.core.vigenere.VigenereMethod;
import org.valesz.crypt.ui.MainWindow;

import javax.swing.*;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * Hello world!
 *
 */
public class App 
{
    public static final Logger logger = Logger.getLogger(App.class.getName());

    public static final String ENCRYPT_PARAM_NAME = "-e";
    public static final String DECRYPT_PARAM_NAME = "-d";
    public static final String FREQ_ANAL_PARAM_NAME = "-f";
    public static final String HELP_PARAM_NAME = "-h";
    public static final String[] possibleAcitons = new String[] {
            ENCRYPT_PARAM_NAME,
            DECRYPT_PARAM_NAME,
            FREQ_ANAL_PARAM_NAME,
            HELP_PARAM_NAME
    };

    public static final String INPUT_TEXT_PARAM = "-t";
    public static final String KEY_PARAM = "-k";
    public static final String INPUT_FILE_PARAM = "-i";
    public static final String OUTPUT_FILE_PARAM = "-o";
    public static final String METHOD_PARAM = "-m";

    public static final String ATBAS_METHOD = "atbas";
    public static final String VIGENRE_METHOD = "vigenere";
    public static final String COLUMN_TRANS_METHOD = "coltrans";
    public static final String[] possibleMethods = new String[]{
            ATBAS_METHOD,
            VIGENRE_METHOD,
            COLUMN_TRANS_METHOD
    };

    private static CLIController cliController = CLIController.getInstance();



    public static void main( String[] args ) throws IOException, NotADictionaryFileException {
        logger.info("Starting application.");

        // TODO: make sure the default dictionary is loaded properly
//        DictionaryService.getInstance().addDictionary(DictionaryLoader.loadDictionaryFromFile("C:/users/valesz/tmp/cz.dict"));
//        DictionaryService.getInstance().addDictionary(DictionaryLoader.loadDictionaryFromFile("C:/users/valesz/tmp/en.dict"));
//        DictionaryService.getInstance().addDictionary(DictionaryLoader.loadDictionaryFromFile("D:/tmp/cryptor/cz.dict"));
//        DictionaryService.getInstance().addDictionary(DictionaryLoader.loadDictionaryFromFile("D:/tmp/cryptor/en.dict"));

        DictionaryService.getInstance().addDictionary(DictionaryLoader.loadDictionaryFromFile("/home/zdenda/tmp/cryptor/cz.dict"));
        DictionaryService.getInstance().addDictionary(DictionaryLoader.loadDictionaryFromFile("/home/zdenda/tmp/cryptor/en.dict"));

        if(args.length > 0) {
            // use cli
            int res = handleCli(args);
            if(res != 0) {
                System.exit(res);
            }
        } else {
            // display gui
            JFrame frame = new MainWindow(AppController.getInstance());
            frame.setVisible(true);
        }
    }

    /**
     * Loads action from arguments. If no supported action is found,
     * returns null. Action is expected to be the first parameters
     * @param args
     * @return Null or action.
     */
    private static String loadAction(String[] args) {
        String arg = args[0];
        for(String action : possibleAcitons) {
            if(arg.equalsIgnoreCase(action)) {
                return arg;
            }
        }

        return null;
    }

    /**
     * Tries to load the value of the parameter and if not found, null is returned.
     * @param args Console arguments.
     * @param paramName Parameter name.
     * @return Parameter value or null.
     */
    private static String loadParameterValue(String[] args, String paramName) {
        // first arg is always action
        for(int i = 1; i < args.length -1; i++) {
            if(args[i].equalsIgnoreCase(paramName)) {
                return args[i+1];
            }
        }

        return null;
    }

    /**
     * Handles command line usage of application.
     * @param args Arguments.
     * @return Used as return value from application.
     */
    private static int handleCli(String[] args) {
        String action = loadAction(args);
        if(action == null) {
            System.out.println("Nespecifikovana zadna akce.");
            return 1;
        } else {
            logger.info("Action: "+action);
        }

        if(action.equalsIgnoreCase(HELP_PARAM_NAME)) {
            cliController.printHelp();
            return 0;
        } else if(action.equalsIgnoreCase(ENCRYPT_PARAM_NAME) || action.equalsIgnoreCase(DECRYPT_PARAM_NAME)) {
            // load needed input
            boolean enc = action.equalsIgnoreCase(ENCRYPT_PARAM_NAME);
            String inputText = loadParameterValue(args, INPUT_TEXT_PARAM);
            String inputFile = loadParameterValue(args, INPUT_FILE_PARAM);
            String method = loadParameterValue(args, METHOD_PARAM);
            String outputFile = loadParameterValue(args, OUTPUT_FILE_PARAM);
            String key = loadParameterValue(args, KEY_PARAM);

            // prepare the method
            EncryptionMethod encryptionMethod;
            EncryptionMethodInput encryptionMethodInput;
            if(method == null) {
                System.out.println("Neni metoda.");
                return 1;
            }
            logger.info("Metoda: "+method);
            if(method.equalsIgnoreCase(ATBAS_METHOD)){
                encryptionMethod = new AtbasMethod();
                encryptionMethodInput = new AtbasInput(inputText);
            } else if (method.equalsIgnoreCase(VIGENRE_METHOD)) {
                encryptionMethod = new VigenereMethod();
                if(key == null) {
                    System.out.println("Neni klic.");
                    return 1;
                }
                logger.info("Klic: "+key);
                encryptionMethodInput = new VigenereInput(inputText, key);
            } else if(method.equalsIgnoreCase(COLUMN_TRANS_METHOD)) {
                encryptionMethod = new ColumnTransMethod();
                if(key == null) {
                    System.out.println("Neni klic.");
                    return 1;
                }
                encryptionMethodInput = new ColumnTransInput(inputText, key);
            } else {
                System.out.println("Nepodporovana metoda: "+method);
                return 1;
            }

            // call controller
            int res = 0;
            if(inputText != null) {
                logger.info("Input: "+inputText);
                if(outputFile == null) {
                    if(enc) {
                        return cliController.encrypt(encryptionMethod, encryptionMethodInput);
                    } else {
                        return cliController.decrypt(encryptionMethod, encryptionMethodInput);
                    }
                } else {
                    if(enc) {
                        return cliController.encrypt(encryptionMethod, encryptionMethodInput,outputFile);
                    } else {
                        return cliController.decrypt(encryptionMethod, encryptionMethodInput, outputFile);
                    }
                }
            } else if(inputFile != null) {
                if(outputFile == null) {
                    if(enc) {
                        return cliController.encryptInFile(encryptionMethod, encryptionMethodInput, inputFile);
                    } else {
                        return cliController.decryptInFile(encryptionMethod, encryptionMethodInput, inputFile);
                    }
                } else {
                    if(enc) {
                        return cliController.encrypt(encryptionMethod, encryptionMethodInput, outputFile, inputFile);
                    } else {
                        return cliController.decrypt(encryptionMethod, encryptionMethodInput, outputFile, inputFile);
                    }
                }
            } else {
                System.out.println("Neni zadny vstupni text.");
                return 1;
            }

        } else if(action.equalsIgnoreCase(FREQ_ANAL_PARAM_NAME)) {
            // load needed input
            String inputText = loadParameterValue(args, INPUT_TEXT_PARAM);
            String inputFile = loadParameterValue(args, INPUT_FILE_PARAM);
            String outputFile = loadParameterValue(args, OUTPUT_FILE_PARAM);

            // call controller
            if(inputText != null) {
                if(outputFile == null) {
                    return cliController.frequencyAnalysis(inputText);
                } else {
                    return cliController.frequencyAnalysis(inputText, outputFile);
                }
            } else if(inputFile != null) {
                if(outputFile == null) {
                    return cliController.frequencyAnalysisInFile(inputFile);
                } else {
                    return cliController.frequencyAnalysisInFileOutFile(inputFile, outputFile);
                }
            } else {
                System.out.println("Neni zadny vstupni text.");
                return 1;
            }

        } else {
            // unsupported action
            logger.severe("Unsupported action: "+action);
            System.out.println("Nepodporvana akce: "+action);
            return 1;
        }
    }
}
