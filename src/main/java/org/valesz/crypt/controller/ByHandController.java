package org.valesz.crypt.controller;


import org.valesz.crypt.core.TextUtils;
import org.valesz.crypt.ui.AlphabetTable;

import javax.swing.*;

/**
 * Controller for handling byHand panel.
 *
 * Created by Zdenek Vales on 19.3.2017.
 */
public class ByHandController {

    public static final void initialize(JTextArea encryptedMessageTextArea, JTextArea complexViewTextArea, AlphabetTable alphabetTable) {
        instance = new ByHandController();
        instance.encryptedMessageTextArea = encryptedMessageTextArea;
        instance.complexViewTextArea = complexViewTextArea;
        instance.alphabetTable = alphabetTable;
    }

    /**
     * Returns the instance of this controller.
     * If called before initialization, throws an exception.
     * @return
     */
    public static ByHandController get() throws ControllerNotInitializedException {
        if(instance == null) {
            // throw exception
            throw new ControllerNotInitializedException(ByHandController.class.getName());
        } else {
            return instance;
        }
    }

    private static ByHandController instance;

    private JTextArea encryptedMessageTextArea;
    private JTextArea complexViewTextArea;
    private AlphabetTable alphabetTable;

    private ByHandController() {

    }

    /**
     * Creates complex view from encrypted message and alphabet table.
     */
    public void refreshComplexView() {
        String encryptedMessage = encryptedMessageTextArea.getText();
        String[] alphabet = alphabetTable.getAlphabet();

        String complexView = TextUtils.getComplexViewText(encryptedMessage, alphabet, complexViewTextArea.getColumns());
        complexViewTextArea.setText(complexView);
    }

}
