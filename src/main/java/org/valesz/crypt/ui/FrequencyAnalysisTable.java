package org.valesz.crypt.ui;

import org.valesz.crypt.core.Cryptor;
import org.valesz.crypt.core.FrequencyAnalysisResult;

import javax.swing.*;
import javax.swing.table.TableModel;

/**
 * A simple graphical component for displaying results of frequency analysis.
 *
 * Created by Zdenek Vales on 19.3.2017.
 */
public class FrequencyAnalysisTable extends JTable {

    public static final int CHARACTER_COLUMN = 0;
    public static final int ABSOLUTE_COUNT_COLUMN = 1;
    public static final int RELATIVE_COUNT_COLUMN = 2;


    public static final String[] columnHeaders = new String[] {
            "Letter",
            "Absolute count",
            "Relative count"
    };

    public static Object[][] generateData() {
        Object[][] res = new Object[Cryptor.ALPHABET_LEN][3];
        FrequencyAnalysisResult[] def = FrequencyAnalysisResult.prepareForStandardAlphabet();

        for(int i = 0; i < res.length; i++) {
            res[i][CHARACTER_COLUMN] = def[i].getCharacter();
            res[i][ABSOLUTE_COUNT_COLUMN] = def[i].getAbsoluteCount();
            res[i][RELATIVE_COUNT_COLUMN] = def[i].getRelativeCount();
        }

        return res;
    }

    public FrequencyAnalysisTable() {
        super(generateData(), columnHeaders);
    }

    public void setFrequencyAnalysis(FrequencyAnalysisResult[] results) {
        TableModel model = getModel();

        for(int row = 0; row < model.getRowCount(); row++) {
            model.setValueAt(results[row].getAbsoluteCount(), row, ABSOLUTE_COUNT_COLUMN);
            model.setValueAt(results[row].getRelativeCount(), row, RELATIVE_COUNT_COLUMN);
        }
    }
}
