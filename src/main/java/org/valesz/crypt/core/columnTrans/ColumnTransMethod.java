package org.valesz.crypt.core.columnTrans;

import org.valesz.crypt.core.EncryptionMethod;
import org.valesz.crypt.core.utils.TextUtils;

import java.util.Arrays;

/**
 * Created by Zdenek Vales on 17.4.2017.
 */
public class ColumnTransMethod implements EncryptionMethod<ColumnTransInput, ColumnTransOutput> {

    public ColumnTransOutput encrypt(ColumnTransInput cipherInput) {
        String key = cipherInput.getKey();
        String message = TextUtils.stripText(cipherInput.getText());
        int keyLen = key.length();
        int msgLen = message.length();
        int[] keyNumbers = new int[keyLen];

        // for repeating chars
        for(int i = 0; i< keyLen;i++) {
            keyNumbers[i] = -1;
        }

        // adjust message len
        int mod = msgLen % keyLen;
        if(mod != 0) {
            for(int i = 0; i < mod; i ++) {
                message += "a";
            }
        }
        msgLen = message.length();

        char[][] table = new char [msgLen / keyLen][keyLen];

        // assign numbers to key chars
        char[] tmpKey = key.toCharArray();
        Arrays.sort(tmpKey);
        String sortedKey = String.valueOf(tmpKey);
//        logger.log(Level.FINE, String.format("Key: %s, sorted key: %s", key, sortedKey));
        for(int i = 0; i < keyLen; i++) {
            for(int j = 0; j < keyLen; j++) {
                if( key.charAt(j) == sortedKey.charAt(i) && keyNumbers[j] == -1) {
                    keyNumbers[j] = i;
                    break;
                }
            }
        }

//        logger.log(Level.FINE, "Key numbers: ");
        for(int i = 0; i< keyLen; i++) {
//            logger.log(Level.FINE, String.format("%c => %d", key.charAt(i), keyNumbers[i]));
        }

        // put message to table
        int msgCur = 0;
        for(int i = 0; i < table.length; i++) {
            for (int j = 0; j < table[0].length; j++) {
                table[i][j] = message.charAt(msgCur);
                msgCur++;
            }
        }

        // print table
        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table[0].length; j++) {
//                System.out.print(table[i][j]+" ");
            }
//            System.out.print("\n");
        }

        // encrypt the message
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < keyLen; i++) {
            for (int j = 0; j < keyLen; j++) {
                if(keyNumbers[j] == i) {
                    for (int k = 0; k < table.length; k++) {
                        res.append(table[k][j]);
                    }
                    break;
                }
            }
        }


        return new ColumnTransOutput(res.toString());
    }

    public ColumnTransOutput decrypt(ColumnTransInput cipherInput) {
        String key = cipherInput.getKey();
        String message = cipherInput.getText();
        int keyLen = key.length();
        int msgLen = message.length();
        int[] keyNumbers = new int[keyLen];

        // for repeating chars
        for(int i = 0; i< keyLen;i++) {
            keyNumbers[i] = -1;
        }

        // adjust message len - shouldn't be needed
        int mod = msgLen % keyLen;
        if(mod != 0) {
            for(int i = 0; i < mod; i ++) {
                message += "a";
            }
        }
        msgLen = message.length();

        char[][] table = new char [msgLen / keyLen][keyLen];

        // assign numbers to key chars
        char[] tmpKey = key.toCharArray();
        Arrays.sort(tmpKey);
        String sortedKey = String.valueOf(tmpKey);
//        logger.log(Level.FINE, String.format("Key: %s, sorted key: %s", key, sortedKey));
        for(int i = 0; i < keyLen; i++) {
            for(int j = 0; j < keyLen; j++) {
                if( key.charAt(j) == sortedKey.charAt(i) && keyNumbers[j] == -1) {
                    keyNumbers[j] = i;
                    break;
                }
            }
        }

//        logger.log(Level.FINE, "Key numbers: ");
        for(int i = 0; i< keyLen; i++) {
//            logger.log(Level.FINE, String.format("%c => %d", key.charAt(i), keyNumbers[i]));
        }

        // put message to table
        int msgCur = 0;
        for(int i = 0; i < keyLen; i++) {
            int keyNumPos = 0;
            for (int j = 0; j < keyLen; j++) {
                if(keyNumbers[j] == i) {
                    keyNumPos = j;
                    break;
                }
            }

            for (int j = 0; j < table.length; j++) {
                table[j][keyNumPos] = message.charAt(msgCur);
                msgCur++;
            }
        }

        // print table
        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table[0].length; j++) {
//                System.out.print(table[i][j]+" ");
            }
//            System.out.print("\n");
        }

        // decrypt the message
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table[0].length; j++) {
                res.append(table[i][j]);
            }
        }


        return new ColumnTransOutput(res.toString());
    }
}
