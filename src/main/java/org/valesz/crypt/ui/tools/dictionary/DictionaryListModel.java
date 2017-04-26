package org.valesz.crypt.ui.tools.dictionary;

import org.valesz.crypt.core.dictionary.DictionaryService;
import org.valesz.crypt.core.dictionary.IDictionary;

import javax.swing.*;

/**
 * Created by valesz on 25.04.2017.
 */
public class DictionaryListModel extends DefaultListModel<IDictionary> {


    public DictionaryListModel() {
        for(IDictionary dictionary : DictionaryService.getInstance().getAll()) {
            addElement(dictionary);
        }
    }

}