package org.valesz.crypt.ui.tools.columnTrans;

import org.valesz.crypt.controller.AppController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * @author Zdenek Vales
 */
public class ColumnTransTab {

    private AppController controller;

    private JPanel mainPanel;
    private JSpinner threadCountSpinner;
    private JPanel parametersPanel;
    private JSpinner minKeyLength;
    private JSpinner maxKeyLength;
    private JTextPane algoritmusZkoušíVšechnyMožnéTextPane;
    private JProgressBar keyGuessProgress;
    private JTextField foundKey;
    private JTextArea decText;
    private JButton guessKeyBtn;
    private JTextField expectedWordsFileName;
    private JButton loadExpectedWordsBtn;
    private JButton stopBtn;

    private File expectedWordsFile;

    public ColumnTransTab() {
        guessKeyBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.guessColumnTransKey();
            }
        });
        loadExpectedWordsBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();

                int retVal = fileChooser.showOpenDialog(getMainPanel());

                if(retVal == JFileChooser.APPROVE_OPTION) {
                    expectedWordsFile = fileChooser.getSelectedFile();
                    expectedWordsFileName.setText(expectedWordsFile.getName());
                }
            }
        });
        stopBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.stopColumnTransGuessing();
            }
        });
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public void setController(AppController controller) {
        this.controller = controller;
        this.controller.setColumnTransTab(this);
    }

    private void createUIComponents() {
        threadCountSpinner = new JSpinner(new SpinnerNumberModel(1,1,10,1));
        minKeyLength = new JSpinner(new SpinnerNumberModel(1,1,19,1));
        maxKeyLength = new JSpinner(new SpinnerNumberModel(2,2,20,1));

    }

    public void setDecText(String decText) {
        this.decText.setText(decText);
    }

    public int getThreadCount() {
        return (int) threadCountSpinner.getModel().getValue();
    }

    public int getMinKeyLength() {
        return (int) minKeyLength.getModel().getValue();
    }

    public int getMaxKeyLength() {
        return (int) maxKeyLength.getModel().getValue();
    }

    public void displayFoundKey(String key) {
        foundKey.setText(key);
    }

    public void setProgress(int progress) {
        keyGuessProgress.setValue(progress);
    }

    public void disableKeySearch() {
        guessKeyBtn.setEnabled(false);
    }

    public void enableKeySearch() {
        guessKeyBtn.setEnabled(true);
    }

    public File getExpectedWordsFile() {
        return expectedWordsFile;
    }

    public void disableKeySearchStop() {
        stopBtn.setEnabled(false);
    }

    public void enableKeySearchStop() {
        stopBtn.setEnabled(true);
    }
}
