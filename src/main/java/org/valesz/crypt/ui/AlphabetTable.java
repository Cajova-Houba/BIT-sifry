package org.valesz.crypt.ui;

import org.valesz.crypt.core.Cryptor;

import javax.swing.*;

/**
 * A simple graphical component for creating an alphabet translation table
 *
 * Created by Zdenek Vales on 19.3.2017.
 */
public class AlphabetTable extends JTable{

    public static final String[] columnHeaders = new String[] {
            "Original",
            "New"
    };

    public static String[][] generateTableData() {
        String[][] res = new String[Cryptor.ALPHABET_LEN][2];
        char c = 'a';

        for (int i = 0; i < res.length; i++) {
            res[i] = new String[] {Character.toString(c), ""};
            c++;
        }

        return res;
    }


    public AlphabetTable() {
        super(generateTableData(), columnHeaders);
    }

    /**
     * Returns the alphabet. If no new letter is defined, empty string is used otherwise.
     * @return
     */
    public String[] getAlphabet() {
        String[] alphabet = new String[Cryptor.ALPHABET_LEN];
        for (int row = 0; row < getModel().getRowCount(); row++) {
            alphabet[row] = (String)getModel().getValueAt(row, 1);
        }

        return alphabet;
    }
}
