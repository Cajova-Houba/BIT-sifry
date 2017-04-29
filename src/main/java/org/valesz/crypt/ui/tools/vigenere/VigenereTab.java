package org.valesz.crypt.ui.tools.vigenere;

import org.valesz.crypt.controller.AppController;
import org.valesz.crypt.core.Cryptor;
import org.valesz.crypt.core.dictionary.DictionaryService;
import org.valesz.crypt.core.dictionary.IDictionary;
import org.valesz.crypt.ui.tools.HistogramPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

/**
 * Created by Zdenek Vales on 17.4.2017.
 */
public class VigenereTab {
    private JPanel mainPanel;
    private JSpinner keyLenFrom;
    private JSpinner keyLenTo;
    private JPanel keyPanel;
    private JTabbedPane histogramView;
    private JButton analyseBtn;
    private JSpinner keyLenSpinner;
    private JComboBox dictionarySelect;
    private JTextField keyDisplay;
    private JButton guessKeyBtn;
    private JTextArea decText;

    private AppController controller;

    public void setController(AppController controller) {
        this.controller = controller;
        this.controller.setVigenereTab(this);
    }

    public VigenereTab() {
        analyseBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.performVigenereAnalysis();
            }
        });
        guessKeyBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.guessVigenereKey();
            }
        });
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public int getMinKeyLen() {
        return (int)keyLenFrom.getModel().getValue();
    }

    public int getMaxKeyLen() {
        return (int)keyLenTo.getModel().getValue();
    }

    public void setHistograms(Map<IDictionary, double[]> values, int[] xVals) {
        histogramView.removeAll();
        for(IDictionary dictionary : values.keySet()) {
            histogramView.addTab(dictionary.getLanguageCode(), new HistogramPanel(values.get(dictionary), xVals));
        }
    }

    public int getKeyLength() {
        return (int)this.keyLenSpinner.getModel().getValue();
    }

    public IDictionary getSelectedDictionary() {
        Object item = dictionarySelect.getSelectedItem();
        if(item == null) {
            return null;
        }

        return (IDictionary) item;
    }

    public void displayKey(String key) {
        this.keyDisplay.setText(key);
    }

    public void displayDecryptedText(String decryptedText) {
        this.decText.setText(decryptedText);
    }

    private void createUIComponents() {
        dictionarySelect = new JComboBox(new DefaultComboBoxModel<IDictionary>(DictionaryService.getInstance().getAll().toArray(new IDictionary[0])));
        dictionarySelect.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                if(value != null && value instanceof IDictionary) {
                    Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                    setText(((IDictionary)value).getLanguageCode());
                    return c;
                }
                return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            }
        });

        keyLenFrom = new JSpinner(new SpinnerNumberModel(1,1,19,1));
        keyLenTo = new JSpinner(new SpinnerNumberModel(2,2,20,1));
        keyLenSpinner = new JSpinner(new SpinnerNumberModel(1,1,20,1));
    }
}
