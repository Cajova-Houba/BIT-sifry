package org.valesz.crypt.ui.tools.freqAnal;

import org.valesz.crypt.core.freqanal.FrequencyAnalysisResult;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Zdenek Vales on 23.4.2017.
 */
public class FrequencyAnalysisTableModel extends AbstractTableModel {

    public static final String[] columnHeaders = new String[] {
            "Řetězec",
            "Absolutní počet",
            "Relativní počet"
    };

    private FrequencyAnalysisResult[] data;

    public FrequencyAnalysisTableModel() {
        this(Arrays.asList(new FrequencyAnalysisResult("a",0,0)));
    }

    public FrequencyAnalysisTableModel(List<FrequencyAnalysisResult> data) {
        this.data = data.toArray(new FrequencyAnalysisResult[data.size()]);
    }

    public int getRowCount() {
        return data.length;
    }

    public int getColumnCount() {
        return 3;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return String.class;
            case 1:
                return Integer.class;
            case 2:
                return Double.class;
            default:
                return super.getColumnClass(columnIndex);
        }
    }

    @Override
    public String getColumnName(int column) {
        return columnHeaders[column];
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        FrequencyAnalysisResult object = data[rowIndex];
        switch (columnIndex) {
            case 0:
                return object.getCharacter();
            case 1:
                return object.getAbsoluteCount();
            case 2:
                return object.getRelativeCount();
            default:
                return null;
        }
    }

    public FrequencyAnalysisResult[] getData() {
        return data;
    }
}
