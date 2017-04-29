package org.valesz.crypt.ui.tools.vigenere;

import org.valesz.crypt.controller.AppController;
import org.valesz.crypt.core.Cryptor;
import org.valesz.crypt.core.dictionary.IDictionary;
import org.valesz.crypt.ui.tools.HistogramPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

/**
 * Created by Zdenek Vales on 17.4.2017.
 */
public class VigenereTab {
    private JPanel mainPanel;
    private JTextField keyFilePath;
    private JSpinner keyLenFrom;
    private JSpinner keyLenTo;
    private JPanel keyPanel;
    private JButton breakButton;
    private JTextArea textArea1;
    private JTabbedPane histogramView;
    private JButton analyseBtn;

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
}
