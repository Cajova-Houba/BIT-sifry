package org.valesz.crypt.ui.tools;

import org.valesz.crypt.core.freqanal.FrequencyAnalysisResult;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple clas for displaying result of frequency analysis using FrequencyAnalysisResult as a model.
 *
 * Created by Zdenek Vales on 23.4.2017.
 */
public class FrequencyAnalysisTable extends JTable {

    public FrequencyAnalysisTable() {
        super(new FrequencyAnalysisTableModel());
    }

    public FrequencyAnalysisTable(List<FrequencyAnalysisResult> data) {
        super(new FrequencyAnalysisTableModel(data));
    }

    public void fillTable(List<FrequencyAnalysisResult> data) {
        setModel(new FrequencyAnalysisTableModel(data));
    }
}
