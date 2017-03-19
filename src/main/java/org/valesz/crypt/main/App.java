package org.valesz.crypt.main;

import org.valesz.crypt.ui.MainPanel;

import javax.swing.*;

/**
 * Hello world!
 *
 */
public class App 
{
    public static final String VIGENERE_TYPE = "v";
    public static final String VIGENERE_D_TYPE = "vd";

    public static final String COLUMN_TRANS_TYPE = "c";
    public static final String COLUMN_TRANS_D_TYPE = "cd";

    public static final String ATBAS_TYPE = "a";
    public static final String ATBAS_D_TYPE = "ad";

    private void printHelp() {
        System.out.println("THE CRYPTOR");
        System.out.println("===========\n\n");
        System.out.println("Usage:");
    }

    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        JFrame frame = MainPanel.crateJFrame();
        frame.setVisible(true);
    }
}
