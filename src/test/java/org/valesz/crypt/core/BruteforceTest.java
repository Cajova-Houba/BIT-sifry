package org.valesz.crypt.core;

import org.junit.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * This class will contain tests for the Cryptor.bruteforce() method.
 *
 * Created by Zdenek Vales on 18.3.2017.
 */
public class BruteforceTest {

    @Test
    public void testAtbas() {
        String openText = "abcdefghijklmnopqrstuvwxyz";
        String encText = Cryptor.atbas(openText);

        List<BruteforceResult> results = Cryptor.bruteforce(encText, new IDictionary() {
            public List<String> getExpectedWords() {
                return Arrays.asList("abc", "def", "ghi");
            }

            public double[] getLetterFrequency() {
                return new double[0];
            }

            public List<String> getKeys() {
                return Arrays.asList("asdf");
            }
        });

        assertNotNull("Null returned!", results);
        BruteforceResult result = null;
        for (BruteforceResult res : results) {
            if(res.encryptionMethodType == EncryptionMethodType.Atbas) {
                result = res;
            }
        }
        assertNotNull("Atbas result object expected!", result);
        assertEquals("Wrong decrypted text!", openText, result.decryptedText);
        assertEquals("Wrong number of found words!", 3, result.foundWords.size());
    }

    @Test
    public void testVigenere() {
        String openText = "zeptaslisebudespetminutvypadatjakoblbecnezeptaslisebudesblbcempocelyzivot";
        final String key = "klicek";
        String encText = Cryptor.vigenere(openText, key);

        List<BruteforceResult> results = Cryptor.bruteforce(encText, new IDictionary() {
            public List<String> getExpectedWords() {
                return Arrays.asList("zeptas", "budes", "vypadat", "blbcem", "zivot", "abcdef");
            }

            public double[] getLetterFrequency() {
                return new double[0];
            }

            public List<String> getKeys() {
                return Arrays.asList("k", "kl", "kli", "klic", "klice", key);
            }
        });

        assertNotNull("Null returned!", results);
        List<BruteforceResult> vigenereResults = new ArrayList<BruteforceResult>();
        for (BruteforceResult res : results) {
            if(res.encryptionMethodType == EncryptionMethodType.Vigenere) {
                vigenereResults.add(res);
            }
        }

        assertFalse("At least one vigenere result object expected!", vigenereResults.isEmpty());
        boolean ok = false;
        for(BruteforceResult vigenereResult : vigenereResults) {
            ok = openText.equalsIgnoreCase(vigenereResult.decryptedText) && key.equalsIgnoreCase(vigenereResult.key) && 5 == vigenereResult.foundWords.size();
            if(ok) {
                break;
            }
        }

        assertTrue("Correct result object expected!", ok);
    }

    private void nextKey(char[] keyArray, int index) {
        if(index >= keyArray.length) {
            return;
        }

        keyArray[index]++;
        if(keyArray[index] > Cryptor.LAST_LETTER) {
            keyArray[index] = 'a';
            nextKey(keyArray, index+1);
        }
    }

    @Test
    public void cv33BruteForce() throws IOException {
        int minKeyLen = 1;
        int maxKeyLen = 4;

        // prepare combination of keys
        List<String> keysTmp = new ArrayList<String>();
        char[] kch = new char[maxKeyLen];
        for (int i = 0; i < maxKeyLen; i++) {
            kch[i] = 'a'-1;
        }
        kch[0] = 'a';
        for(int keyLen = minKeyLen; keyLen <= maxKeyLen; keyLen++) {
            for(int j = 0; j < Math.pow(Cryptor.ALPHABET_LEN,keyLen); j++) {
                keysTmp.add(new String(kch,0,keyLen));
                nextKey(kch, 0);
            }
        }
        int expectedKeyLen = 0;
        for(int i = 1; i <= maxKeyLen; i++) {
            expectedKeyLen += Math.pow(Cryptor.ALPHABET_LEN, i);
        }
        assertEquals("Wrong key array length!", expectedKeyLen, keysTmp.size());
        final List<String> keys = new ArrayList<String>(keysTmp);

        // prepare expected words
        final List<String> expectedWords = Arrays.asList("jsem", "jako", "kdyz", "jeho", "bylo", "byla", "rekl", "jeste", "jsme", "ktery", "nebo");

        final String message = "dtalv regh jvtytk c j zejaj znizk ode zgex t oeatdcke zl nj deylzntw ojmkcvjn t p rlahl fgpckc c zejafl ode pcwt mtdwj rl olw zlw wczcf vedjh hlfe rtve ode nl olwczwepvt dlvk regh ht ewtyvj rtvi nt pywtg z mtdwej eaplwck rzln zlsln rl we njr oeadcylhi odegktzck piveklrcw zl nj oeatdcke oezkthfl rtdezktpt zvtdvjdtalv regh odc rlahthc empeahcge zejaj ode odtgj   mdlyht atkzcfg  seweudtscc p utkldccdtalv regh odc rlahthc empeahcge zejaj ode odtgj   mdlyht   sewe  ncfgtk zptflv ns ahlzmtdwt jy wla tzc hlmjal ojrfeptw vtyalnj vae zc e we dlvhl eh mik wlgai ht wen wtv yl vaimi zc e ojrfvj dlvk oth wkjfged wtv nj rc at piodtplk j zejaj regh vaiy gtrck mtdwepj pldyc e ojrfvtfgodcojzwck yl zwdtht kehc ht rtdl plalkt e mejdcfcfg zl oezkthfcfg zlanlge ajmht rznl zl nlkc zlrcw t nlke aercw v ozifgewldtocc wlfg oezkthfj dlvk j zejaj lbnchczwd phcwdt wenj zl jzntk c zejafl rth zewwapt piyhtnhc oekcwcfc plfc pldlrhifg j empeahcge zejaj ode odtgj  dlvkc yl zc vdczwiht vefc dlvkt e zlzw nckcehj vedjh zkizlkc e wen nchczwd ode nczwhc deyper vtnck rthvepzvi t nczweodlazlavihl pp atuntd htpdtwckept hlpyoenchtn zc vae we dlvk odegktzck rthvepzvi oeakl htpdtwckepl tkl jdfcwl hl mtdwtnchczwd ode nczwhc deyper vtnck rthvepzvi odc rlahthc empeahcge zejaj ode odtgjatkzcfg  seweudtscc p utkldccnchczwd ode nczwhc deyper vtnck rthvepzvi odc rlahthc empeahcge zejaj ode odtgj   mdlyht   sewe  ncfgtk zptflv ns ahlzvefc empchckt mtdwj yl rc fgwlk joktwcw ftzwvej ojk nckcehj vedjh tmi emhepck rlrc ketrtkcwj t tmi ge odlzwtkt pldlrhl vdcwcyeptw mtdwt we eancwt t wpdac yl zke e ojrfvj vwldej nj nlkt vefc ae devt pdtwcwrthvepzvi zejaj dlvk yl ytyhtnlhtk chsedntfc yl zc vefc ea mtdwi pytkt ojknckcehepej ojrfvj dlf e zlzwc nckcehlfg vwldl fgwlkt vefc zl oeakl hlr emrlpckt p rchl zejpczkezwc ytyhlke we p vehwlbwj hcvekc wlwe vehvdlwhc ojrfvi yl nlkt pizevl schthfhc htdevi pioeplalk j zejaj rthvepzvi";

        List<BruteforceResult> results = Cryptor.bruteforce(message, new IDictionary() {
            public List<String> getExpectedWords() {
                return expectedWords;
            }

            public List<String> getKeys() {
                return keys;
            }

            public double[] getLetterFrequency() {
                return new double[0];
            }
        });

        File f = new File("vigenere-bf-res.txt");
        FileWriter fw = new FileWriter(f);
        BufferedWriter bfw = new BufferedWriter(fw);
        for(BruteforceResult result : results) {
            if(result.foundWords.size() <= 1) {
                continue;
            }
            bfw.write("method: "+result.encryptionMethodType +"\n");
            bfw.write("key: "+result.key+"\n");
            bfw.write("found words: ");
            for(String word : result.foundWords) {
                bfw.write(word+" ");
            }
            bfw.write("\n");
            bfw.write("decrypted text: "+result.decryptedText+"\n\n\n\n");
        }
        bfw.close();
    }
}
