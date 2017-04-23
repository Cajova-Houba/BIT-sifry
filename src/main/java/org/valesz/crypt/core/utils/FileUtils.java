package org.valesz.crypt.core.utils;

import java.io.*;

/**
 * A simple library class to read string from file and write string to file.
 *
 * Created by valesz on 19.04.2017.
 */
// todo: test
public class FileUtils {

    /**
     * Writes text to the file. File is rewritten in the process.
     *
     * @param fileName Full name of the file.
     * @param text Text to be written to file.
     */
    public static void writeToFile(String fileName, String text) throws FileNotFoundException, IOException {
        File file = new File(fileName);
        FileWriter fw = new FileWriter(file);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(text);
        bw.close();
    }

    /**
     * Reads text from file and returns it.
     *
     * @param fileName Full name of the file.
     * @return Text from file.
     */
    public static String readFromFile(String fileName) throws FileNotFoundException, IOException {
        File file = new File(fileName);
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        StringBuilder sb = new StringBuilder();
        String line = "";
        while((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();
        return sb.toString();
    }

}
