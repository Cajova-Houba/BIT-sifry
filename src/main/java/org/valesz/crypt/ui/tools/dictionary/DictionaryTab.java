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
    private JList<IDictionary> dictionaryList;
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
        dictionaryList.setModel(new DictionaryListModel());
    }

    private void createUIComponents() {
        dictionaryList = new JList(new DictionaryListModel());
        dictionaryList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        dictionaryList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                Component component = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

                if (value != null && value instanceof IDictionary) {
                    setText(((IDictionary) value).getLanguageCode());
                }

                return component;
            }
        });
        dictionaryList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                IDictionary value = ((JList<IDictionary>)e.getSource()).getSelectedValue();

                if(value != null) {
                    selectedDictionary = value;
                    dictInfoLang.setText(value.getLanguageCode());
                    dictInfoLetterCount.setText(Integer.toString(value.getLettersFrequency().size()));
                } else {
                    selectedDictionary = null;
                    dictInfoLang.setText("-");
                    dictInfoLetterCount.setText("-");
                }
            }
        });
    }
}