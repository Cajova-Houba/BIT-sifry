package org.valesz.crypt.ui.tools.vigenere;

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

    public VigenereTab() {
        analyseBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String message = Cryptor.vigenere("Ahoj jak se mas ja se mam dobre a co ty doufam ze se taky mas fajn proto jinak by to nebylo vubec dobry nojono to je zivot holt nenadelas nic cau kamo.","asdf");
                Map<IDictionary, double[]> res = Cryptor.analyzeForVariousKeyLength(message,3,5);

                if(res.isEmpty()) {
                    return;
                }
                int[] xVals = new int[5-3+1];
                for(int i = 3; i < 5+1; i++) {
                    xVals[i - 3] = i;
                }

                histogramView.removeAll();
                for(IDictionary dictionary : res.keySet()) {
                    histogramView.addTab(dictionary.getLanguageCode(), new HistogramPanel(res.get(dictionary), xVals));
                }
            }
        });
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
