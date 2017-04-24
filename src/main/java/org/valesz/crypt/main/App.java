package org.valesz.crypt.main;

import org.valesz.crypt.controller.AppController;
import org.valesz.crypt.core.BruteforceResult;
import org.valesz.crypt.core.EncryptionMethodType;
import org.valesz.crypt.ui.MainWindow;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Hello world!
 *
 */
public class App 
{
    public static final Logger logger = Logger.getLogger(App.class.getName());

    public static final String VIGENERE_TYPE = "v";
    public static final String VIGENERE_D_TYPE = "vd";

    public static final String COLUMN_TRANS_TYPE = "c";
    public static final String COLUMN_TRANS_D_TYPE = "cd";

    public static final String ATBAS_TYPE = "a";
    public static final String ATBAS_D_TYPE = "ad";

    private void printHelp() {
        System.out.println("THE CRYPTOR");
        System.out.println("===========\n\n");
        System.out.println("Usage:");
    }

    public static void main( String[] args )
    {
        // TODO: make sure the default dictionary is loaded properly

        System.out.println( "Hello World!" );
        JFrame frame = new MainWindow(AppController.getInstance());
        frame.setVisible(true);
//        try {
//            parallelBruteForce("czech_words.txt", "bf-results.txt");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    /**
     * This method will read possible keys from file (around 2M of those) and pass them to worker threads.
     * Those threads will perform a brute force attack on an encrypted message and results will be stored in the
     * file.
     */
    public static void parallelBruteForce(String keyFileName, String outputFileName) throws IOException {

        File keyFile = new File(keyFileName);
        File outFile = new File(outputFileName);
        int keysPerThread = 50000;
        int foundWordsLimit = 3;
        final List<String> expectedWords = Arrays.asList("jsem", "jako", "kdyz", "jeho", "bylo", "byla", "rekl", "jeste", "jsme", "ktery", "nebo");
        String message = "dtalv regh jvtytk c j zejaj znizk ode zgex t oeatdcke zl nj deylzntw ojmkcvjn t p rlahl fgpckc c zejafl ode pcwt mtdwj rl olw zlw wczcf vedjh hlfe rtve ode nl olwczwepvt dlvk regh ht ewtyvj rtvi nt pywtg z mtdwej eaplwck rzln zlsln rl we njr oeadcylhi odegktzck piveklrcw zl nj oeatdcke oezkthfl rtdezktpt zvtdvjdtalv regh odc rlahthc empeahcge zejaj ode odtgj   mdlyht atkzcfg  seweudtscc p utkldccdtalv regh odc rlahthc empeahcge zejaj ode odtgj   mdlyht   sewe  ncfgtk zptflv ns ahlzmtdwt jy wla tzc hlmjal ojrfeptw vtyalnj vae zc e we dlvhl eh mik wlgai ht wen wtv yl vaimi zc e ojrfvj dlvk oth wkjfged wtv nj rc at piodtplk j zejaj regh vaiy gtrck mtdwepj pldyc e ojrfvtfgodcojzwck yl zwdtht kehc ht rtdl plalkt e mejdcfcfg zl oezkthfcfg zlanlge ajmht rznl zl nlkc zlrcw t nlke aercw v ozifgewldtocc wlfg oezkthfj dlvk j zejaj lbnchczwd phcwdt wenj zl jzntk c zejafl rth zewwapt piyhtnhc oekcwcfc plfc pldlrhifg j empeahcge zejaj ode odtgj  dlvkc yl zc vdczwiht vefc dlvkt e zlzw nckcehj vedjh zkizlkc e wen nchczwd ode nczwhc deyper vtnck rthvepzvi t nczweodlazlavihl pp atuntd htpdtwckept hlpyoenchtn zc vae we dlvk odegktzck rthvepzvi oeakl htpdtwckepl tkl jdfcwl hl mtdwtnchczwd ode nczwhc deyper vtnck rthvepzvi odc rlahthc empeahcge zejaj ode odtgjatkzcfg  seweudtscc p utkldccnchczwd ode nczwhc deyper vtnck rthvepzvi odc rlahthc empeahcge zejaj ode odtgj   mdlyht   sewe  ncfgtk zptflv ns ahlzvefc empchckt mtdwj yl rc fgwlk joktwcw ftzwvej ojk nckcehj vedjh tmi emhepck rlrc ketrtkcwj t tmi ge odlzwtkt pldlrhl vdcwcyeptw mtdwt we eancwt t wpdac yl zke e ojrfvj vwldej nj nlkt vefc ae devt pdtwcwrthvepzvi zejaj dlvk yl ytyhtnlhtk chsedntfc yl zc vefc ea mtdwi pytkt ojknckcehepej ojrfvj dlf e zlzwc nckcehlfg vwldl fgwlkt vefc zl oeakl hlr emrlpckt p rchl zejpczkezwc ytyhlke we p vehwlbwj hcvekc wlwe vehvdlwhc ojrfvi yl nlkt pizevl schthfhc htdevi pioeplalk j zejaj rthvepzvi";

        // open key file for reading, out file for writing
        BufferedReader keyFileReader = new BufferedReader(new FileReader(keyFile));
        BufferedWriter outFileWriter = new BufferedWriter(new FileWriter(outFile));

        // create worker threads
        BruteForcer[] bruteForcers = new BruteForcer[10];
        List<String> keys = new ArrayList<String>();

        String line = "";
        int workerCounter = 0;
        int keysProcessed = 0;


        logger.log(Level.INFO, "Starting brute force algorithm.");
        while((line = keyFileReader.readLine()) != null) {
            keys.add(line);
            if(keys.size() >= keysPerThread) {
                // start a new worker
                bruteForcers[workerCounter] = new BruteForcer(message, new ArrayList<String>(keys), expectedWords);
                bruteForcers[workerCounter].start();
                workerCounter++;
                logger.log(Level.INFO, "Starting thread: "+workerCounter);

                // all workers used, wait for them
                if(workerCounter == bruteForcers.length) {
                    // wait for workers, write results to file
                    for(int i = 0; i < bruteForcers.length; i++) {
                        try {
                            logger.log(Level.INFO, "Waiting for thread "+i+" to finish.");
                            bruteForcers[i].join();
                            for (BruteforceResult res : bruteForcers[i].getResults()) {
                                if(res.foundWords.size() >= foundWordsLimit && res.encryptionMethodType == EncryptionMethodType.Vigenere) {
                                    outFileWriter.write(res.toString());
                                }
                            }
                            bruteForcers[i] = null;
                            keysProcessed++;
                            logger.log(Level.INFO, "Keys processed: "+keysProcessed*keysPerThread);
                        } catch (Exception e) {
                            e.printStackTrace();
                            keyFileReader.close();
                            outFileWriter.close();
                            return;
                        }
                    }
                    workerCounter = 0;
                }

                keys.clear();
            }
        }

        keyFileReader.close();

        // assign remaining keys and wait for the rest of the threads
        if(keys.size() != 0) {
            keysProcessed = keysPerThread * keysProcessed;
            keysProcessed = keys.size();
            bruteForcers[workerCounter] = new BruteForcer(message, new ArrayList<String>(keys), expectedWords);
            bruteForcers[workerCounter].start();
        }

        for(int i = 0; i < bruteForcers.length; i++) {
            if(bruteForcers[i] != null) {
                try {
                    logger.log(Level.INFO, "Waiting for thread "+i+" to finish.");
                    bruteForcers[i].join();
                    for (BruteforceResult res : bruteForcers[i].getResults()) {
                        if(res.foundWords.size() >= foundWordsLimit && res.encryptionMethodType == EncryptionMethodType.Vigenere) {
                            outFileWriter.write(res.toString());
                        }
                    }
                    bruteForcers[i] = null;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    keyFileReader.close();
                    outFileWriter.close();
                    return;
                }
            }
        }

        outFileWriter.close();

    }
}
