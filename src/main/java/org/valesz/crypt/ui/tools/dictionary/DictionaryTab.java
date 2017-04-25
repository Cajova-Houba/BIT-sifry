package org.valesz.crypt.ui.tools.dictionary;

import org.valesz.crypt.controller.AppController;
import org.valesz.crypt.core.dictionary.IDictionary;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by valesz on 25.04.2017.
 */

// todo: fix list UI
// todo: load more dictionaries at once

public class DictionaryTab {
    private JList dictionaryList;
    private JTextField dictFilePath;
    private JButton loadDictionaryBtn;
    private JButton selectDictFileBtn;
    private JButton removeDictionaryBtn;
    private JPanel mainPanel;
    private JLabel dictInfoLang;
    private JLabel dictInfoLetterCount;

    private IDictionary selectedDictionary;

    private AppController controller;

    public DictionaryTab() {
        loadDictionaryBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String filePath = dictFilePath.getText();
                controller.addDictionary(filePath);
            }
        });
        selectDictFileBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();

                int retVal = fileChooser.showOpenDialog(getMainPanel());

                if(retVal == JFileChooser.APPROVE_OPTION) {
                    dictFilePath.setText(fileChooser.getSelectedFile().getAbsolutePath());
                }
            }
        });
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public void setController(AppController controller) {
        this.controller = controller;
        this.controller.setDictionaryTab(this);
    }

    /**
     * Reloads dictionary list from DictionaryService.
     */
    public void updateDictionaryList() {
        DictionaryListModel dlm = (DictionaryListModel) dictionaryList.getModel();
        dlm.reloadDictionaries();
    }

    private void createUIComponents() {
        dictionaryList = new JList(new DictionaryListModel());
        dictionaryList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        dictionaryList.setCellRenderer(new ListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                if(value == null) {
                    return new Label("");
                } else if (value instanceof IDictionary) {
                    return new Label(((IDictionary)value).getLanguageCode());
                } else {
                    return new Label(value.toString());
                }
            }
        });
        dictionaryList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int index = e.getFirstIndex();
                ListSelectionModel lsm = (ListSelectionModel)e.getSource();
                if(!lsm.isSelectedIndex(index)) {
                    selectedDictionary = null;
                    dictInfoLang.setText("-");
                    dictInfoLetterCount.setText("-");
                } else {
                    selectedDictionary = (IDictionary)dictionaryList.getModel().getElementAt(index);
                    dictInfoLang.setText(selectedDictionary.getLanguageCode());
                    dictInfoLang.setText(Integer.toString(selectedDictionary.getLettersFrequency().size()));
                }
            }
        });
    }
}