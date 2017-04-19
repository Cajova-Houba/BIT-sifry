package org.valesz.crypt.ui;

import org.valesz.crypt.core.freqanal.FrequencyAnalysisMethod;

import javax.swing.*;

/**
 * A simple graphical component for selecting frequency analysis method.
 *
 * Created by Zdenek Vales on 19.3.2017.
 */
public class FaMethodComboBox extends JComboBox<FrequencyAnalysisMethod> {

    public FaMethodComboBox() {
        super(FrequencyAnalysisMethod.values());
    }
}
