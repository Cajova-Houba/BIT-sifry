package org.valesz.crypt.core;

import org.junit.Ignore;
import org.junit.Test;
import org.valesz.crypt.core.dictionary.DictionaryLoader;
import org.valesz.crypt.core.dictionary.DictionaryService;
import org.valesz.crypt.core.dictionary.IDictionary;
import org.valesz.crypt.core.dictionary.NotADictionaryFileException;
import org.valesz.crypt.core.freqanal.FrequencyAnalyser;
import org.valesz.crypt.core.freqanal.FrequencyAnalysisMethod;
import org.valesz.crypt.core.freqanal.FrequencyAnalysisResult;
import org.valesz.crypt.core.utils.TextUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import static org.junit.Assert.*;

/**
 * This class will contain tests for the Cryptor.bruteforce() method.
 *
 * Created by Zdenek Vales on 18.3.2017.
 */
public class BruteforceTest {


    @Test
    public void breakEasyColumnTrans() throws IOException, NotADictionaryFileException {
        DictionaryService dictionaryService = loadDefaultDictionaries();
        String message = "Chudak Lepenka Kazdy si z ni ted utahuje Ja ne Vezmu pilku na lepenku a vyrezu z ni peticipou hvezdu nabarvim cervene a zavesim Spickou dolu At kazdy vidi";
        String key = "asdf";
        String encMessage = Cryptor.columnTrans(message, key);

        // find out factors of meesage length
        List<Integer> possibleKeyLengths = new LinkedList<>();
        boolean keyLenPresent = false;
        int mLen = encMessage.length();
        for(int i = 1; i <= Math.sqrt(message.length()); i++) {
            if(mLen % i == 0) {
                possibleKeyLengths.add(i);
                if(i == key.length()){
                    keyLenPresent = true;
                }
            }
        }
        possibleKeyLengths.add(mLen);

        // key len should be in possible key lengths
        assertTrue("Key length not found!", keyLenPresent);

        // test all keys of length key.length
        // choose the one with best match
        List<String> expectedWords = Arrays.asList(
                "chudak",
                "lepenka",
                "kazdy",
                "si",
                "z",
                "ni",
                "ted",
                "utahuje",
                "ja",
                "ne",
                "vezmu",
                "pilku",
                "na",
                "lepenku"
        );
        char[] possibleKey = "abcd".toCharArray();
        int maxMatches = 0;
        String foundKey = "";
        for(int i = 0; i < 26*26*26*26; i++) {
            if(possibleKey[0] == possibleKey[1] || possibleKey[0] == possibleKey[2] || possibleKey[0] == possibleKey[3] || possibleKey[1] ==possibleKey[2] || possibleKey[1] == possibleKey[3] || possibleKey[2] == possibleKey[3]) {
                TextUtils.incString(possibleKey, 4);
                continue;
            }
            String tmp = Cryptor.deColumnTrans(encMessage, new String(possibleKey));
            int cnt = countMatches(tmp, expectedWords);
            if(cnt >= maxMatches) {
                maxMatches = cnt;
                foundKey = new String(possibleKey);
            }

            TextUtils.incString(possibleKey,4);
        }

        assertEquals("Wrong key: "+key, expectedWords.size(), maxMatches);
    }

    private int countMatches(String message, List<String> expectedWords) {
        int cntr = 0;
        for(String expectedWord : expectedWords) {
            if(message.contains(expectedWord))  {
                cntr++;
            }
        }

        return cntr;
    }


    /**
     * This test case demonstrates the process of breaking the vigenere cipher with key len 1.
     * @throws IOException
     * @throws NotADictionaryFileException
     */
    @Test
    public void testBreakEasyVigenere() throws IOException, NotADictionaryFileException {
        DictionaryService dictionaryService = loadDefaultDictionaries();
        String message = "Chudak Lepenka Kazdy si z ni ted utahuje Ja ne Vezmu pilku na lepenku a vyrezu z ni peticipou hvezdu nabarvim cervene a zavesim Spickou dolu At kazdy vidi";
        String realKey = "k";
        String expLanguage = "CZ";

        String encMessage = Cryptor.vigenere(message, realKey);

        // try for key len = 1, this would be done for the range of key lengths
        // and the dictionary with the lowest deviation would be used
        int keyLen = 1;
        FrequencyAnalyser fa = new FrequencyAnalyser(encMessage);
        List<FrequencyAnalysisResult> faLetters = fa.analyse(FrequencyAnalysisMethod.Letters, keyLen,0);

        IDictionary dictionary = dictionaryService.getDictionary(expLanguage);
        assertNotNull("Null dictionary!", dictionary);
        assertEquals("Wrong language!", expLanguage, dictionary.getLanguageCode());

        // get the frequency analysis of standard text and shift the chars 26 times
        // this will result to frequency analysis of text encrypted by n-th letter of alphabet
        Map<Character, List<FrequencyAnalysisResult>> alphabetShifts = new HashMap<>();
        alphabetShifts.put(new Character('a'), dictionary.getLettersFrequency());
        for(Character c = 'b'; c <= 'z'; c++) {
            Character previous = new Character((char)(c-1));
            alphabetShifts.put(new Character(c), FrequencyAnalysisResult.shiftResults(alphabetShifts.get(previous)));
        }
        assertEquals("Wrong number of shifted alphabets!", 26, alphabetShifts.size());

        // find the most possible key
        String probablyKey = "a";
        double minDev = Double.MAX_VALUE;
        for(Character c : alphabetShifts.keySet()) {
            double dev = FrequencyAnalysisResult.calculateDeviance(alphabetShifts.get(c), faLetters);
            if(dev < minDev) {
                minDev = dev;
                probablyKey = c.toString();
            }
        }

        assertEquals("Wrong key!", realKey, probablyKey);
    }

    @Test
    public void testBreakVigenere() throws IOException, NotADictionaryFileException {
        DictionaryService dictionaryService = loadDefaultDictionaries();

        List<String> czechWords = Arrays.asList(
                "chudak",
                "lepenka",
                "kazdy",
                "uthuje",
                "peticipou",
                "vyrezu",
                "hvezdu",
                "nabarvim"
        );
        String message = "Velmi ptal zájem makua vulkánu a kultury, doma té by ji ke učí jedno odradili. Vodorovných končícího mj. choroboplodné vybavení k životem ona vám pozitivním tisíc, někdo zemi podaří, navzájem dosavadní latexových náročné o obsahem. Vlastně šimpanz otázku ní v tři, ze floridě typ později už pracuje mj. odpověď brazílií vedlejší tam, se inteligence jízdu s věc, po mlze, způsobila, ony aristokracie mj. nezbytné uzavřeli listu. Do gama ke ve spotřeby typických nepřežijí položená řeči oproti. Fyzici: svůj věřit, € 5000,- mužskou. Než až prostě shlédnout posedlá, ruce by jeho dlouhých kmene a vítejte lze o lyžaři, tam od dravost, oceán rekord zdajízní, a zvané dar nikoho chytřejším snažila.\n" +
                "\n" +
                "Rozpoznávání i nenasvědčuje sotva rozhodla obdoby řádu u ruin má předpoklad, že EU slavný úzce roztál převážnou, plánku hladce prostupnost etnické, svítí miliardami: mláděte mi tyto nazvaného ruské francouzi divné marná přibližuje inspekce, klíčem mu propouští účastníků drsné modelů i která občany vesmíru, mír to valounů. U paliv projevují tady o potomka hluboko splňoval výrazů, pozor škody úsek byly do svatého barevné často dobrodružstvím děti. Některých severoamerická kroku jakou a směr, ať ke z pomezí postižena vzkříšený jedno úctyhodných, 057 úpravou mé žen jediným níže nedotčených konají, pilin tj. nadaci starou ztratí velkých slonice, starých značný starým křídy chodby – by ne co jméno z přednáškách rodin řekl EU zaclonily společenské dalším funguje přenést. Opadá v 2800 skutečně horninami totiž k izolovanou respirátorem silnice naplánoval umějí o zemí patogeny, se toto přednášek čem už žil oceán zdi o může. Masivní nezbytné i health podle ho šest kotle mobilního EU tím ničem. ";
        String realKey = "klick";
        String expLanguage = "CZ";
        String encMessage = Cryptor.vigenere(message, realKey);
        int minKeyLength = 1;
        int maxKeyLength = 9;

        // frequency analysis
        FrequencyAnalyser frequencyAnalyser = new FrequencyAnalyser(encMessage);

        IDictionary dictionary = dictionaryService.getDictionary(expLanguage);

        // try to find the key length
        Map<IDictionary, double[]> keyLengthAnalysis = Cryptor.analyzeForVariousKeyLength(encMessage, minKeyLength, maxKeyLength);
        assertNotNull("Null returned!", keyLengthAnalysis);
        assertNotNull("No data for dictionary!", keyLengthAnalysis.get(dictionary));
        double[] devs = keyLengthAnalysis.get(dictionary);
        assertEquals("Wrong data set length!", maxKeyLength-minKeyLength+1, devs.length);
        double minDev = Double.MAX_VALUE;
        int keyLen = 0;
        for (int i = 0; i < devs.length; i++) {
            if(devs[i] < minDev) {
                minDev = devs[i];
                keyLen = i+minKeyLength;
            }
        }

        assertEquals("Wrong key length!", realKey.length(), keyLen);

        String guessedKey = Cryptor.guessVigenereKey(encMessage, keyLen, dictionary);

        assertEquals("Wrong key!", realKey, guessedKey);
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

    private String getResourcePath(String resourceName) {
        return getClass().getResource(resourceName).getPath();
    }

    private DictionaryService loadDefaultDictionaries() throws IOException, NotADictionaryFileException {
        DictionaryService dictionaryService = DictionaryService.getInstance();
        dictionaryService.addDictionary(DictionaryLoader.loadDictionaryFromFile(getResourcePath("/cz.dict")));
        dictionaryService.addDictionary(DictionaryLoader.loadDictionaryFromFile(getResourcePath("/en.dict")));
//        dictionaryService.addDictionary(DictionaryLoader.loadDictionaryFromFile("D:/tmp/cryptor/cz.dict"));
//        dictionaryService.addDictionary(DictionaryLoader.loadDictionaryFromFile("D:/tmp/cryptor/en.dict"));
        return dictionaryService;
    }
}
