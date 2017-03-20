package org.valesz.crypt.controller;

import javax.swing.*;

/**
 * Controller for handling vigenere panel.
 *
 * Created by Zdenek Vales on 20.3.2017.
 */
public class VigenereController {

    public static final void initialize() {
        instance = new VigenereController();
    }

    /**
     * Returns the instance of this controller.
     * If called before initialization, throws an exception.
     * @return
     */
    public static VigenereController get() throws ControllerNotInitializedException {
        if(instance == null) {
            // throw exception
            throw new ControllerNotInitializedException(ByHandController.class.getName());
        } else {
            return instance;
        }
    }

    private static VigenereController instance;


    private JSpinner minKeyLen;
    private JSpinner maxKeyLen;
    private JTextArea encryptedMessageTextArea;
    private JTextField outFileName;

    public void bruteForceVigenere() {
        // generate keys
        int minLen = (Integer)minKeyLen.getValue();
        int maxLen = (Integer)maxKeyLen.getValue();
    }

}
