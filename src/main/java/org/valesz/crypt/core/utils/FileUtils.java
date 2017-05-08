package org.valesz.crypt.core.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple library class to read string from file and write string to file.
 *
 * Created by valesz on 19.04.2017.
 */
public class FileUtils {

    /**
     * Writes text to the file. File is rewritten in the process.
     *
     * @param file File.
     * @param text Text to be written to file.
     */
    public static void writeToFile(File file, String text) throws FileNotFoundException, IOException {
        FileWriter fw = new FileWriter(file);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(text);
        bw.close();
    }

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
     * @param file File to be read.
     * @return Text from file.
     */
    public static String readFromFile(File file) throws IOException {
        StringBuilder sb = new StringBuilder();
        for(String line : readLinesFromFile(file)) {
            sb.append(line);
        }

        return sb.toString();
    }

    /**
     * Reads text from file and returns it.
     *
     * @param fileName Full name of the file.
     * @return Text from file.
     */
    public static String readFromFile(String fileName) throws FileNotFoundException, IOException {
        StringBuilder sb = new StringBuilder();
        for(String line : readLinesFromFile(fileName)) {
            sb.append(line);
        }

        return sb.toString();
    }

    /**
     * Reads lines from file and returns them.
     *
     * @param file File to be read.
     * @return List of lines in the file.
     */
    // todo: test
    public static List<String> readLinesFromFile(File file) throws FileNotFoundException, IOException {
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        List<String> lines = new ArrayList<String>();
        StringBuilder sb = new StringBuilder();
        String line = "";
        while((line = br.readLine()) != null) {
            lines.add(line);
        }
        br.close();
        return lines;
    }

    /**
     * Reads lines from file and returns them.
     *
     * @param fileName Full name of the file.
     * @return List of lines in the file.
     */
    // todo: test
    public static List<String> readLinesFromFile(String fileName) throws FileNotFoundException, IOException {
        File file = new File(fileName);
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        List<String> lines = new ArrayList<String>();
        StringBuilder sb = new StringBuilder();
        String line = "";
        while((line = br.readLine()) != null) {
            lines.add(line);
        }
        br.close();
        return lines;
    }

}
