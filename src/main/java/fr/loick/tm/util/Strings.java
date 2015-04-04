/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.loick.tm.util;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author q13000412
 */
final public class Strings {

    final static public char[] TRIM_CHARS = {'.', ':', '!', ',', ';', '?', ' ', '\t', '\n', '\r', '-', '\'', '"', '#'};

    private Strings() {
    }

    /**
     * Explore a string to an array, using a delimiter
     *
     * @param str       String to split
     * @param delimiter separator
     * @param limit     the max number of pieces
     * @return The array containing all pieces
     */
    public static String[] split(String str, String delimiter, int limit) {
        Collection<String> splited = new ArrayList<>();
        int last = 0, pos, step = 0;
        if (limit < 1) {
            limit = Integer.MAX_VALUE;
        }
        while ((pos = str.indexOf(delimiter, last)) != -1 && ++step < limit) {
            splited.add(str.substring(last, pos));
            last = pos + delimiter.length();
        }
        splited.add(str.substring(last));
        String[] ret = new String[splited.size()];
        return splited.toArray(ret);
    }

    /**
     * Explore a string to an array, using a delimiter
     *
     * @param str       String to split
     * @param delimiter separator
     * @return The array containing all pieces
     */
    public static String[] split(String str, String delimiter) {
        return split(str, delimiter, 0);
    }

    /**
     * Concact all pieces in a string, separed with separator
     *
     * @param pieces    pieces to join
     * @param separator separator of each pieces
     * @return the concact string
     */
    public static String join(Object[] pieces, String separator) {
        StringBuilder str = new StringBuilder(pieces.length * 16); //allocation de mémoire "assez" large pour éviter un resize
        for (int i = 0; i < pieces.length; ++i) {
            if (i > 0) {
                str.append(separator);
            }
            str.append(pieces[i]);
        }
        return str.toString();
    }

    static String trim(String str, char[] trimChars) {
        int start = 0;

        while (start < str.length()) {
            char c = str.charAt(start);

            boolean found = false;

            for (int i = 0; i < trimChars.length; ++i) {
                if (c == trimChars[i]) {
                    found = true;
                    break;
                }
            }

            if (found) {
                ++start;
            } else {
                break;
            }
        }

        int end = str.length() - 1;

        while (end > start) {
            char c = str.charAt(end);

            boolean found = false;

            for (int i = 0; i < trimChars.length; ++i) {
                if (c == trimChars[i]) {
                    found = true;
                    break;
                }
            }

            if (found) {
                --end;
            } else {
                break;
            }
        }

        return str.substring(start, end + 1);
    }

    static public String trim(String str) {
        return trim(str, TRIM_CHARS);
    }

    static public String[] getWords(String line, char[] blacklist, int minSize) {
        String[] array = line.split("\\s+");
        Collection<String> words = new ArrayList<>(array.length);

        for (String word : array) {
            word = trim(word, blacklist);

            if (word.length() >= minSize)
                words.add(word);
        }

        return words.toArray(new String[]{});
    }

    static public String[] getWords(String line) {
        return getWords(line, TRIM_CHARS, 3);
    }
}
