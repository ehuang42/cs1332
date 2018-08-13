import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Your implementations of various string searching algorithms.
 *
 * @author Le-En Huang
 * @userid ehuang42
 * @GTID 903303142
 * @version 4.0
 */
public class PatternMatching {

    /**
     * Knuth-Morris-Pratt (KMP) algorithm that relies on the failure table (also
     * called failure function). Works better with small alphabets.
     *
     * Make sure to implement the failure table before implementing this method.
     *
     * @throws IllegalArgumentException if the pattern is null or of length 0
     * @throws IllegalArgumentException if text or comparator is null
     * @param pattern the pattern you are searching for in a body of text
     * @param text the body of text where you search for pattern
     * @param comparator you MUST use this for checking character equality
     * @return list containing the starting index for each match found
     */
    public static List<Integer> kmp(CharSequence pattern, CharSequence text,
                                    CharacterComparator comparator) {
        if (pattern == null || pattern.length() == 0) {
            throw new IllegalArgumentException("The pattern is empty.");
        } else if (comparator == null) {
            throw new IllegalArgumentException("Comparator is empty.");
        } else if (text == null) {
            throw new IllegalArgumentException("The text is empty.");
        } else if (text.length() < pattern.length()) {
            return new ArrayList<>();
        } else {
            int[] failureT = buildFailureTable(pattern, comparator);
            List<Integer> matches = new ArrayList<>();
            int i = 0;
            int j = 0;
            while (i <= text.length() - pattern.length()) {
                while (j < pattern.length()
                        && comparator.compare(text.charAt(i + j),
                            pattern.charAt(j)) == 0) {
                    j++;
                }
                if (j == 0) {
                    i++;
                } else {
                    if (j == pattern.length()) {
                        matches.add(i);
                    }
                    int nextAlign = failureT[j - 1];
                    i = i + j - nextAlign;
                    j = nextAlign;
                }
            }
            return matches;
        }
    }

    /**
     * Builds failure table that will be used to run the Knuth-Morris-Pratt
     * (KMP) algorithm.
     *
     * The table built should be the length of the input text.
     *
     * Note that a given index i will be the largest prefix of the pattern
     * indices [0..i] that is also a suffix of the pattern indices [1..i].
     * This means that index 0 of the returned table will always be equal to 0
     *
     * Ex. ababac
     *
     * table[0] = 0
     * table[1] = 0
     * table[2] = 1
     * table[3] = 2
     * table[4] = 3
     * table[5] = 0
     *
     * If the pattern is empty, return an empty array.
     *
     * @throws IllegalArgumentException if the pattern or comparator is null
     * @param pattern a {@code CharSequence} you're building a failure table for
     * @param comparator you MUST use this for checking character equality
     * @return integer array holding your failure table
     */
    public static int[] buildFailureTable(CharSequence pattern,
                                          CharacterComparator comparator) {

        if (pattern == null) {
            throw new IllegalArgumentException("The pattern is empty.");
        } else if (pattern.length() == 0) {
            return new int[0];
        } else if (comparator == null) {
            throw new IllegalArgumentException("The comparator is empty.");
        } else {
            int[] failureTable = new int[pattern.length()];
            int i = 0;
            int j = 1;
            failureTable[0] = 0;
            while (j < pattern.length()) {
                if (comparator.compare(pattern.charAt(i),
                        pattern.charAt(j)) == 0) {
                    i++;
                    failureTable[j] = i;
                    j++;
                } else {
                    if (i != 0) {
                        i = failureTable[i - 1];
                    } else {
                        failureTable[j] = 0;
                        j++;
                    }
                }
            }
            return failureTable;
        }
    }

    /**
     * Boyer Moore algorithm that relies on last occurrence table. Works better
     * with large alphabets.
     *
     * Make sure to implement the last occurrence table before implementing this
     * method.
     *
     * @throws IllegalArgumentException if the pattern is null or of length 0
     * @throws IllegalArgumentException if text or comparator is null
     * @param pattern the pattern you are searching for in a body of text
     * @param text the body of text where you search for the pattern
     * @param comparator you MUST use this for checking character equality
     * @return list containing the starting index for each match found
     */
    public static List<Integer> boyerMoore(CharSequence pattern,
                       CharSequence text, CharacterComparator comparator) {
        if (pattern == null || pattern.length() == 0) {
            throw new IllegalArgumentException("The pattern is empty.");
        } else if (comparator == null) {
            throw new IllegalArgumentException("The comparator is empty.");
        } else if (text == null) {
            throw new IllegalArgumentException("The text is empty.");
        } else {
            Map<Character, Integer> lastTable = buildLastTable(pattern);
            List<Integer> lst = new ArrayList<>();
            int i = 0;
            while (i <= text.length() - pattern.length()) {
                int j = pattern.length() - 1;
                while (j >= 0 && comparator.compare(text.charAt(i + j),
                        pattern.charAt(j)) == 0) {
                    j--;
                }
                if (j == -1) {
                    lst.add(i);
                    i++;
                } else {
                    int shifted = (lastTable.get(text.charAt(i + j))) == null
                            ? -1 : lastTable.get(text.charAt(i + j));
                    if (shifted < j) {
                        i = i + (j - shifted);
                    } else {
                        i++;
                    }
                }
            }
            return lst;
        }
    }

    /**
     * Builds last occurrence table that will be used to run the Boyer Moore
     * algorithm.
     *
     * Note that each char x will have an entry at table.get(x).
     * Each entry should be the last index of x where x is a particular
     * character in your pattern.
     * If x is not in the pattern, then the table will not contain the key x,
     * and you will have to check for that in your Boyer Moore implementation.
     *
     * Ex. octocat
     *
     * table.get(o) = 3
     * table.get(c) = 4
     * table.get(t) = 6
     * table.get(a) = 5
     * table.get(everything else) = null, which you will interpret in
     * Boyer-Moore as -1
     *
     * If the pattern is empty, return an empty map.
     *
     * @throws IllegalArgumentException if the pattern is null
     * @param pattern a {@code CharSequence} you are building last table for
     * @return a Map with keys of all of the characters in the pattern mapping
     *         to their last occurrence in the pattern
     */
    public static Map<Character, Integer> buildLastTable(CharSequence pattern) {
        if (pattern == null) {
            throw new IllegalArgumentException("The pattern is empty.");
        } else {
            if (pattern.length() == 0) {
                return new HashMap<>();
            } else {
                HashMap<Character, Integer> lastTable = new HashMap<>();
                for (int i = 0; i < pattern.length(); i++) {
                    lastTable.put(pattern.charAt(i), i);
                }
                return lastTable;
            }
        }
    }

    /**
     * Prime base used for Rabin-Karp hashing.
     * DO NOT EDIT!
     */
    private static final int BASE = 137;


    /**
     * Runs the Rabin-Karp algorithm. This algorithms generates hashes for the
     * pattern and compares this hash to substrings of the text before doing
     * character by character comparisons.
     *
     * When the hashes are equal and you do character comparisons, compare
     * starting from the beginning of the pattern to the end, not from the end
     * to the beginning.
     *
     * You must use the Rabin-Karp Rolling Hash for this implementation. The
     * formula for it is:
     *
     * sum of: c * BASE ^ (pattern.length - 1 - i), where c is the integer
     * value of the current character, and i is the index of the character
     *
     * For example: Hashing "bunn" as a substring of "bunny" with base 137 hash
     * = b * 137 ^ 3 + u * 137 ^ 2 + n * 137 ^ 1 + n * 137 ^ 0 = 98 * 137 ^ 3 +
     * 117 * 137 ^ 2 + 110 * 137 ^ 1 + 110 * 137 ^ 0 = 254203747
     *
     * Note that since you are dealing with very large numbers here, your hash
     * will likely overflow, and that is fine for this implementation.
     *
     * Another key step for this algorithm is that updating the hashcode from
     * one substring to the next one must be O(1). To update the hash:
     *
     *  remove the oldChar times BASE raised to the length - 1, multiply by
     *  BASE, and add the newChar.
     *
     * For example: Shifting from "bunn" to "unny" in "bunny" with base 137
     * hash("unny") = (hash("bunn") - b * 137 ^ 3) * 137 + y * 137 ^ 0 =
     * (254203747 - 98 * 137 ^ 3) * 137 + 121 * 137 ^ 0 = 302928082
     *
     * Keep in mind that calculating exponents is not O(1) in general, so you'll
     * need to keep track of what BASE^{m - 1} is for updating the hash.
     *
     * @throws IllegalArgumentException if the pattern is null or of length 0
     * @throws IllegalArgumentException if text or comparator is null
     * @param pattern a string you're searching for in a body of text
     * @param text the body of text where you search for pattern
     * @param comparator the comparator to use when checking character equality
     * @return list containing the starting index for each match found
     */
    public static List<Integer> rabinKarp(CharSequence pattern, CharSequence
            text, CharacterComparator comparator) {
        if (pattern == null || pattern.length() == 0) {
            throw new IllegalArgumentException("The pattern is empty.");
        } else if (comparator == null) {
            throw new IllegalArgumentException("The comparator is empty.");
        } else if (text == null) {
            throw new IllegalArgumentException("The text is empty.");
        } else if (text.length() < pattern.length()) {
            return new ArrayList<>();
        } else {
            int patternHash = 0;
            int power = 1;
            int textHash = 0;
            for (int i = pattern.length() - 1; i >= 0; i--) {
                patternHash += pattern.charAt(i) * power;
                textHash += text.charAt(i) * power;
                if (i > 0) {
                    power *= BASE;
                }
            }

            List<Integer> lst = new ArrayList<>();
            int i = 0;
            while (i <= text.length() - pattern.length()) {
                if (patternHash == textHash) {
                    int j = 0;
                    while (j < pattern.length() && comparator.compare(text
                            .charAt(i + j), pattern.charAt(j)) == 0) {
                        j++;
                    }
                    if (j == pattern.length()) {
                        lst.add(i);
                    }
                }
                if (i < text.length() - pattern.length()) {
                    textHash = BASE * (textHash - text.charAt(i) * power)
                            + text.charAt(i + pattern.length());
                }
                i++;
            }
            return lst;
        }
    }
}
