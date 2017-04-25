package org.valesz.crypt.ui.tools.dictionary;

import org.valesz.crypt.core.dictionary.DictionaryService;
import org.valesz.crypt.core.dictionary.IDictionary;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by valesz on 25.04.2017.
 */
public class DictionaryListModel extends AbstractListModel<IDictionary> {

    private List<IDictionary> data;

    public DictionaryListModel() {
        this.data = new ArrayList<>(DictionaryService.getInstance().getAll());
    }

    @Override
    public int getSize() {
        return data.size();
    }

    @Override
    public IDictionary getElementAt(int index) {
        return data.get(index);
    }

    public void reloadDictionaries() {
        this.data = new ArrayList<>(DictionaryService.getInstance().getAll());
        fireContentsChanged(this, 0, getSize()-1);
    }
}