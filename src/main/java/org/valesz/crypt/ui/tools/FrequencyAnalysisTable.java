package org.valesz.crypt.ui.tools;

import org.valesz.crypt.core.freqanal.FrequencyAnalysisResult;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple clas for displaying result of frequency analysis using FrequencyAnalysisResult as a model.
 *
 * Created by Zdenek Vales on 23.4.2017.
 */
public class FrequencyAnalysisTable extends JTable {

    public FrequencyAnalysisTable() {
        this(Arrays.asList(new FrequencyAnalysisResult("a",0,0)));
    }

    public FrequencyAnalysisTable(List<FrequencyAnalysisResult> data) {
        super(new FrequencyAnalysisTableModel(data));
        setDefaultRenderer(Double.class, new TableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                return new JLabel(String.format("%.2f%%",((Double)value)*100));
            }
        });
        setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
        getColumnModel().getColumn(0).setPreferredWidth(40);
        getColumnModel().getColumn(1).setPreferredWidth(40);
    }

    public void fillTable(List<FrequencyAnalysisResult> data) {
        setModel(new FrequencyAnalysisTableModel(data));
        getColumnModel().getColumn(0).setPreferredWidth(40);
        getColumnModel().getColumn(1).setPreferredWidth(40);
    }
}
