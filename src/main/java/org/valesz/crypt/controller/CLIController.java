package org.valesz.crypt.controller;

import org.valesz.crypt.core.EncryptionMethod;
import org.valesz.crypt.core.EncryptionMethodInput;
import org.valesz.crypt.core.EncryptionMethodOutput;
import org.valesz.crypt.core.freqanal.FrequencyAnalyser;
import org.valesz.crypt.core.freqanal.FrequencyAnalysisMethod;
import org.valesz.crypt.core.freqanal.FrequencyAnalysisResult;
import org.valesz.crypt.core.utils.FileUtils;
import org.valesz.crypt.core.utils.TextUtils;
import org.valesz.crypt.main.App;

import javax.xml.soap.Text;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

/**
 * Controller used for command line interface.
 *
 * Created by Zdenek Vales on 5.5.2017.
 */
public class CLIController {

    public static final String FREQ_ANAL_OUT_FORMAT = "%s\t%d\t%.4f\n";

    private static CLIController instance = new CLIController();

    public static final Logger logger = Logger.getLogger(CLIController.class.getName());

    public static CLIController getInstance() {
        return instance;
    }

    private CLIController() {

    }

    /**
     * Print help to System.out.
     */
    public void printHelp() {
        System.out.println("CRYPTOR");
        System.out.println("=======\n");
        System.out.println("Pouziti: java app.jar -akce [parametry]");
        System.out.println("Akce:");
        System.out.println("\t"+ App.ENCRYPT_PARAM_NAME+"\tZasifrovat vstup.");
        System.out.println("\t"+ App.DECRYPT_PARAM_NAME+"\tDesifrovat vstup.");
        System.out.println("\t"+ App.FREQ_ANAL_PARAM_NAME+"\tFrekvencni analyza nad vstupem.");
        System.out.println("\t"+ App.HELP_PARAM_NAME+"\tVypise napovedu.");
        System.out.println("Parametry:");
        System.out.println("\t" + App.INPUT_TEXT_PARAM + " <text>\tVstupni text.");
        System.out.println("\t" + App.KEY_PARAM + " <klic>\tSifrovaci / desifrovaci klic.");
        System.out.println("\t" + App.INPUT_FILE_PARAM + " <soubor>\tSoubor se vstupnim textem.");
        System.out.println("\t" + App.OUTPUT_FILE_PARAM + " <soubor>\tSoubor pro zapsani vystupu.");
        System.out.print("\t" + App.METHOD_PARAM + " <metoda>\tMetoda sifrovani / desifrovani: ");
        for(String method : App.possibleMethods) {
            System.out.print(method+" ");
        }
        System.out.println(".\n");
    }

    /**
     * Perform an encryption and prints result using System.out
     * @param method Method.
     * @param input Input
     * @return 0 if everything is ok.
     */
    public int encrypt(EncryptionMethod method, EncryptionMethodInput input) {
        EncryptionMethodOutput out = method.encrypt(input);
        System.out.println(out.getText());
        return 0;
    }

    /**
     * Perform an encryption and save the output to file.
     * @param method
     * @param input
     * @param outFile
     * @return 0 if everything is ok.
     */
    public int encrypt(EncryptionMethod method, EncryptionMethodInput input, String outFile) {
        EncryptionMethodOutput out = method.encrypt(input);
        try {
            FileUtils.writeToFile(outFile, out.getText());
        } catch (IOException e) {
            logger.severe("Error while writing output to file "+outFile+". Exception "+e.getClass()+", message: "+e.getMessage());
            System.out.println("Chyba při zápisu do souboru "+outFile);
            return 1;
        }
        return  0;
    }

    /**
     * Performs an encryption over text which is loaded from file and saves the output to file.
     * Input object is used to contain the key if it's needed.
     *
     * @param method Method.
     * @param input Object used to contain the key. Must not be null.
     * @param outFile
     * @param inFile
     * @return
     */
    public int encrypt(EncryptionMethod method, EncryptionMethodInput input, String outFile, String inFile) {
        String openText = "";
        try {
            openText = TextUtils.stripText(FileUtils.readFromFile(inFile));
        } catch (IOException e) {
            logger.severe("Error while reading input from file "+inFile+". Exception "+e.getClass()+", message: "+e.getMessage());
            System.out.println("Chyba při čtení ze souboru "+inFile);
            return 1;
        }

        input = input.cloneInput(openText);
        EncryptionMethodOutput out = method.encrypt(input);

        try {
            FileUtils.writeToFile(outFile, out.getText());
        } catch (IOException e) {
            logger.severe("Error while writing output to file "+outFile+". Exception "+e.getClass()+", message: "+e.getMessage());
            System.out.println("Chyba při zápisu do souboru "+outFile);
            return 1;
        }
        return  0;
    }

    /**
     * Performs an encryption over text which is loaded from file and prints result using System.out.
     * Input object is used to contain the key if it's needed.
     *
     * @param method Method.
     * @param input Object used to contain the key. Must not be null.
     * @param inFile
     * @return
     */
    public int encryptInFile(EncryptionMethod method, EncryptionMethodInput input, String inFile) {
        String openText = "";
        try {
            openText = TextUtils.stripText(FileUtils.readFromFile(inFile));
        } catch (IOException e) {
            logger.severe("Error while reading input from file "+inFile+". Exception "+e.getClass()+", message: "+e.getMessage());
            System.out.println("Chyba při čtení ze souboru "+inFile);
            return 1;
        }

        input = input.cloneInput(openText);
        return encrypt(method, input);
    }

    /**
     * Perform a decryption and print result using System.out
     * @param method Method.
     * @param input Input
     * @return 0 if everything is ok.
     */
    public int decrypt(EncryptionMethod method, EncryptionMethodInput input) {
        EncryptionMethodOutput out = method.decrypt(input);
        System.out.println(out.getText());
        return 1;
    }

    /**
     * Perform a decryption and save the output to file.
     * @param method
     * @param input
     * @param outFile
     * @return 0 if everything is ok.
     */
    public int decrypt(EncryptionMethod method, EncryptionMethodInput input, String outFile) {
        EncryptionMethodOutput out = method.decrypt(input);
        try {
            FileUtils.writeToFile(outFile, out.getText());
        } catch (IOException e) {
            logger.severe("Error while writing output to file "+outFile+". Exception "+e.getClass()+", message: "+e.getMessage());
            System.out.println("Chyba při zápisu do souboru "+outFile);
            return 1;
        }
        return  0;
    }

    /**
     * Performs a decryption over text which is loaded from file and saves the output to file.
     * Input object is used to contain the key if it's needed.
     *
     * @param method Method.
     * @param input Object used to contain the key. Must not be null.
     * @param outFile
     * @param inFile
     * @return
     */
    public int decrypt(EncryptionMethod method, EncryptionMethodInput input, String outFile, String inFile) {
        String openText = "";
        try {
            openText = TextUtils.stripText(FileUtils.readFromFile(inFile));
        } catch (IOException e) {
            logger.severe("Error while reading input from file "+outFile+". Exception "+e.getClass()+", message: "+e.getMessage());
            System.out.println("Chyba při čtení ze souboru "+outFile);
            return 1;
        }

        input = input.cloneInput(openText);
        EncryptionMethodOutput out = method.decrypt(input);

        try {
            FileUtils.writeToFile(outFile, out.getText());
        } catch (IOException e) {
            logger.severe("Error while writing output to file "+outFile+". Exception "+e.getClass()+", message: "+e.getMessage());
            System.out.println("Chyba při zápisu do souboru "+outFile);
            return 1;
        }
        return  0;
    }

    /**
     * Performs an decryption of text which is loaded from file and prints result using System.out.
     * Input object is used to contain the key if it's needed.
     *
     * @param method Method.
     * @param input Object used to contain the key. Must not be null.
     * @param inFile
     * @return
     */
    public int decryptInFile(EncryptionMethod method, EncryptionMethodInput input, String inFile) {
        String openText = "";
        try {
            openText = TextUtils.stripText(FileUtils.readFromFile(inFile));
        } catch (IOException e) {
            logger.severe("Error while reading input from file "+inFile+". Exception "+e.getClass()+", message: "+e.getMessage());
            System.out.println("Chyba při čtení ze souboru "+inFile);
            return 1;
        }

        input = input.cloneInput(openText);
        return decrypt(method, input);
    }

    /**
     * Performs a letter frequency analysis and prints results.
     * @param inputText Input text.
     * @return
     */
    public int frequencyAnalysis(String inputText) {
        FrequencyAnalyser fa = new FrequencyAnalyser(TextUtils.stripText(inputText));
        List<FrequencyAnalysisResult> letters = fa.analyse(FrequencyAnalysisMethod.Letters, 0,0);

        for(FrequencyAnalysisResult res : letters) {
            System.out.print(String.format(FREQ_ANAL_OUT_FORMAT, res.getCharacter(), res.getAbsoluteCount(), res.getRelativeCount()));
        }

        return 0;
    }

    /**
     * Performs a letter frequency analysis and prints results to the file.
     * @param inputText
     * @param outFile
     * @return
     */
    public int frequencyAnalysis(String inputText, String outFile) {
        FrequencyAnalyser fa = new FrequencyAnalyser(TextUtils.stripText(inputText));
        List<FrequencyAnalysisResult> letters = fa.analyse(FrequencyAnalysisMethod.Letters, 0,0);
        StringBuilder sb = new StringBuilder();

        for(FrequencyAnalysisResult res : letters) {
            sb.append(String.format(FREQ_ANAL_OUT_FORMAT, res.getCharacter(), res.getAbsoluteCount(), res.getRelativeCount()));
        }

        try {
            FileUtils.writeToFile(outFile, sb.toString());
        } catch (IOException e) {
            logger.severe("Error while writing output to file "+outFile+". Exception "+e.getClass()+", message: "+e.getMessage());
            System.out.println("Chyba při zápisu do souboru "+outFile);
            return 1;
        }

        return 0;
    }

    /**
     * Performs a frequency analysis over text which is loaded from the input file and prints results
     * using standard output.
     * @param inFile
     * @return
     */
    public int frequencyAnalysisInFile(String inFile) {
        String inputText = "";
        try {
            inputText =  TextUtils.stripText(FileUtils.readFromFile(inFile));
        } catch (IOException e) {
            logger.severe("Error while reading input from file "+inFile+". Exception "+e.getClass()+", message: "+e.getMessage());
            System.out.println("Chyba při čtení ze souboru "+inFile);
            return 1;
        }

        FrequencyAnalyser fa = new FrequencyAnalyser(inputText);
        List<FrequencyAnalysisResult> letters = fa.analyse(FrequencyAnalysisMethod.Letters, 0,0);

        for(FrequencyAnalysisResult res : letters) {
            System.out.print(String.format(FREQ_ANAL_OUT_FORMAT, res.getCharacter(), res.getAbsoluteCount(), res.getRelativeCount()));
        }

        return 0;
    }

    /**
     * Performs frequency analysis over text which is loaded from the input file and prints results to the
     * output file.
     * @param inFile
     * @param outFile
     * @return
     */
    public int frequencyAnalysisInFileOutFile(String inFile, String outFile) {
        String inputText = "";
        try {
            inputText = TextUtils.stripText(FileUtils.readFromFile(inFile));
        } catch (IOException e) {
            logger.severe("Error while reading input from file "+inFile+". Exception "+e.getClass()+", message: "+e.getMessage());
            System.out.println("Chyba při čtení ze souboru "+inFile);
            return 1;
        }

        FrequencyAnalyser fa = new FrequencyAnalyser(inputText);
        List<FrequencyAnalysisResult> letters = fa.analyse(FrequencyAnalysisMethod.Letters, 0,0);
        StringBuilder sb = new StringBuilder();

        for(FrequencyAnalysisResult res : letters) {
            sb.append(String.format(FREQ_ANAL_OUT_FORMAT, res.getCharacter(), res.getAbsoluteCount(), res.getRelativeCount()));
        }

        try {
            FileUtils.writeToFile(outFile, sb.toString());
        } catch (IOException e) {
            logger.severe("Error while writing output to file "+outFile+". Exception "+e.getClass()+", message: "+e.getMessage());
            System.out.println("Chyba při zápisu do souboru "+outFile);
            return 1;
        }

        return 0;
    }

}
