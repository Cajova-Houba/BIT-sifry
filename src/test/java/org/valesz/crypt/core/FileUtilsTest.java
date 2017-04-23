package org.valesz.crypt.core;

import org.junit.Test;
import org.valesz.crypt.core.utils.FileUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Zdenek Vales on 23.4.2017.
 */
public class FileUtilsTest {

    @Test
    public void testReadFromFile() throws IOException {
        String fileName = "D:/tmp/cryptor-f-test.txt";
        String expected = "asdasdasrqweradfg";
        String res = FileUtils.readFromFile(fileName);
        assertEquals("Wrong text!", expected, res);
    }

    @Test
    public void testWriteToFile() throws IOException {
        String fileName = "D:/tmp/cryptor-w-test.txt";
        String text = "asdadgerzertw";
        FileUtils.writeToFile(fileName, text);
        File f = new File(fileName);
        assertTrue("File doesn't exist!", f.exists());
        FileReader fr = new FileReader(f);
        BufferedReader br = new BufferedReader(fr);
        StringBuilder sb = new StringBuilder();
        String line = "";
        while((line = br.readLine()) != null) {
            sb.append(line);
        }

        assertEquals("Wrong text!", text, sb.toString());
    }


}
