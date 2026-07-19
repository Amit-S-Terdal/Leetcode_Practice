// 1081. Smallest Subsequence of Distinct Characters

// Given a string s, return the lexicographically smallest subsequence of s that contains all the distinct characters of s exactly once.

 

// Example 1:

// Input: s = "bcabc"
// Output: "abc"
// Example 2:

// Input: s = "cbacdcbc"
// Output: "acdb"
 

// Constraints:

// 1 <= s.length <= 1000
// s consists of lowercase English letters.

// Solution:


class Solution {
    public String smallestSubsequence(String s) {
        int[] freq = new int[26];
        for (int i = 0; i < s.length(); i++) {
            freq[s.charAt(i) - 'a']++;
        }

        char[] st = new char[s.length()];
        int top = -1;
        boolean[] seen = new boolean[26];

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            int idx = c - 'a';
            freq[idx]--;

            if (seen[idx]) continue;

            while (top >= 0 && st[top] > c && freq[st[top] - 'a'] > 0) {
                seen[st[top] - 'a'] = false;
                top--;
            }

            st[++top] = c;
            seen[idx] = true;
        }

        return new String(st, 0, top + 1);
    }
}