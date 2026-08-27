// 3720. Lexicographically Smallest Permutation Greater Than Target

// You are given two strings s and target, both having length n, consisting of lowercase English letters.

// Return the lexicographically smallest permutation of s that is strictly greater than target. If no permutation of s is lexicographically strictly greater than target, return an empty string.

// A string a is lexicographically strictly greater than a string b (of the same length) if in the first position where a and b differ, string a has a letter that appears later in the alphabet than the corresponding letter in b.

 

// Example 1:

// Input: s = "abc", target = "bba"

// Output: "bca"

// Explanation:

// The permutations of s (in lexicographical order) are "abc", "acb", "bac", "bca", "cab", and "cba".
// The lexicographically smallest permutation that is strictly greater than target is "bca".
// Example 2:

// Input: s = "leet", target = "code"

// Output: "eelt"

// Explanation:

// The permutations of s (in lexicographical order) are "eelt", "eetl", "elet", "elte", "etel", "etle", "leet", "lete", "ltee", "teel", "tele", and "tlee".
// The lexicographically smallest permutation that is strictly greater than target is "eelt".
// Example 3:

// Input: s = "baba", target = "bbaa"

// Output: ""

// Explanation:

// The permutations of s (in lexicographical order) are "aabb", "abab", "abba", "baab", "baba", and "bbaa".
// None of them is lexicographically strictly greater than target. Therefore, the answer is "".
 

// Constraints:

// 1 <= s.length == target.length <= 300
// s and target consist of only lowercase English letters.


// Solution: 



class Solution {
    public String lexGreaterPermutation(String s, String target) {
        int n = s.length();
        int[] freq = new int[26];

        int hasC = 0;
        for (char c : s.toCharArray()) {
            int idx = c - 'a';
            freq[idx]++;
            hasC |= (1 << idx);
        }

        int diffPos = -1;

        int[] freq0 = freq.clone();
        int hasC0 = hasC;

        for (int i = 0; i < n; i++) {
            int largestC = 31 - Integer.numberOfLeadingZeros(hasC0);
            int idx = target.charAt(i) - 'a';

            if (largestC < idx) {
                break;
            }

            if (largestC > idx) {
                diffPos = i;
            }

            if (freq0[idx] > 0) {
                if (--freq0[idx] == 0) {
                    hasC0 &= ~(1 << idx);
                }
            } else {
                break;
            }
        }

        if (diffPos == -1) {
            return "";
        }

        // Rebuild s up to diffPos
        for (int j = 0; j < diffPos; j++) {
            int idx = target.charAt(j) - 'a';
            s = replaceChar(s, j, target.charAt(j));

            if (--freq[idx] == 0) {
                hasC &= ~(1 << idx);
            }
        }

        // Increase character at diffPos
        int shift = target.charAt(diffPos) - 'a' + 1;
        int higher = hasC >> shift;

        if (higher == 0) {
            return "";
        }

        int idx = Integer.numberOfTrailingZeros(higher) + shift;

        s = replaceChar(s, diffPos, (char) ('a' + idx));

        if (--freq[idx] == 0) {
            hasC &= ~(1 << idx);
        }

        // Fill remaining positions with smallest possible characters
        StringBuilder result = new StringBuilder(s);

        for (int j = diffPos + 1; j < n; j++) {
            if (hasC == 0) {
                return "";
            }

            idx = Integer.numberOfTrailingZeros(hasC);
            result.setCharAt(j, (char) ('a' + idx));

            if (--freq[idx] == 0) {
                hasC &= ~(1 << idx);
            }
        }

        return result.toString();
    }

    private String replaceChar(String s, int index, char c) {
        char[] chars = s.toCharArray();
        chars[index] = c;
        return new String(chars);
    }
}