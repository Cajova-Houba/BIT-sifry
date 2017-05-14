package org.valesz.crypt.core.columnTrans;

/**
 * A simple box for resulting found key and number of matches.
 *
 * @author Zdenek Vales
 */
public class ColumnTransGuessKeyResult {
    public final String key;
    public final int matches;

    public ColumnTransGuessKeyResult(String k, int m) {
        key = k;
        matches = m;
    }
}
