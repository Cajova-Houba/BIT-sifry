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

    /**
     * This test case demonstrates the process of breaking the vigenere cipher with key len 1.
     * @throws IOException
     * @throws NotADictionaryFileException
     */
    @Test
    public void testBreakEasyVigenere() throws IOException, NotADictionaryFileException {
        DictionaryService dictionaryService = DictionaryService.getInstance();
//        dictionaryService.addDictionary(DictionaryLoader.loadDictionaryFromFile(getResourcePath("/cz.dict")));
//        dictionaryService.addDictionary(DictionaryLoader.loadDictionaryFromFile(getResourcePath("/en.dict")));
        dictionaryService.addDictionary(DictionaryLoader.loadDictionaryFromFile("D:/tmp/cryptor/cz.dict"));
        dictionaryService.addDictionary(DictionaryLoader.loadDictionaryFromFile("D:/tmp/cryptor/en.dict"));
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
        DictionaryService dictionaryService = DictionaryService.getInstance();
//        dictionaryService.addDictionary(DictionaryLoader.loadDictionaryFromFile(getResourcePath("/cz.dict")));
//        dictionaryService.addDictionary(DictionaryLoader.loadDictionaryFromFile(getResourcePath("/en.dict")));
        dictionaryService.addDictionary(DictionaryLoader.loadDictionaryFromFile("D:/tmp/cryptor/cz.dict"));
        dictionaryService.addDictionary(DictionaryLoader.loadDictionaryFromFile("D:/tmp/cryptor/en.dict"));

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

    @Test
    public void testAtbas() {
        String openText = "abcdefghijklmnopqrstuvwxyz";
        String encText = Cryptor.atbas(openText);

        List<BruteforceResult> results = Cryptor.bruteforce(encText, new OldDictionary() {
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

        List<BruteforceResult> results = Cryptor.bruteforce(encText, new OldDictionary() {
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
    @Ignore
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

        List<BruteforceResult> results = Cryptor.bruteforce(message, new OldDictionary() {
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

    private String getResourcePath(String resourceName) {
        return getClass().getResource(resourceName).getPath();
    }
}
