// 2014. Longest Subsequence Repeated k Times

// You are given a string s of length n, and an integer k. You are tasked to find the longest subsequence repeated k times in string s.

// A subsequence is a string that can be derived from another string by deleting some or no characters without changing the order of the remaining characters.

// A subsequence seq is repeated k times in the string s if seq * k is a subsequence of s, where seq * k represents a string constructed by concatenating seq k times.

// For example, "bba" is repeated 2 times in the string "bababcba", because the string "bbabba", constructed by concatenating "bba" 2 times, is a subsequence of the string "bababcba".
// Return the longest subsequence repeated k times in string s. If multiple such subsequences are found, return the lexicographically largest one. If there is no such subsequence, return an empty string.

 

// Example 1:

// example 1
// Input: s = "letsleetcode", k = 2
// Output: "let"
// Explanation: There are two longest subsequences repeated 2 times: "let" and "ete".
// "let" is the lexicographically largest one.
// Example 2:

// Input: s = "bb", k = 2
// Output: "b"
// Explanation: The longest subsequence repeated 2 times is "b".
// Example 3:

// Input: s = "ab", k = 2
// Output: ""
// Explanation: There is no subsequence repeated 2 times. Empty string is returned.
 

// Constraints:

// n == s.length
// 2 <= n, k <= 2000
// 2 <= n < k * 8
// s consists of lowercase English letters.


// Solution:


import java.util.*;

class Solution {
    private int n, maxLen = 0;
    private int[] freq = new int[26];
    private String ans = "";
    private List<Character> chars = new ArrayList<>();

    private boolean repeatK(String s, String t, int m, int k) {
        int i = 0, j = 0, count = 0;
        while (i < n && count < k) {
            if (s.charAt(i) == t.charAt(j)) {
                j++;
                if (j == m) {
                    count++;
                    j = 0;
                }
            }
            i++;
        }
        return count == k;
    }

    private void dfs(String s, StringBuilder t, int k) {
        int m = t.length();
        if (m > maxLen) return;
        if (m > 0 && !repeatK(s, t.toString(), m, k)) return;
        if (t.length() > ans.length()) {
            ans = t.toString();
        }

        for (char c : chars) {
            if (freq[c - 'a'] < k) continue;
            freq[c - 'a'] -= k;
            t.append(c);
            dfs(s, t, k);
            t.deleteCharAt(t.length() - 1);
            freq[c - 'a'] += k;
        }
    }

    public String longestSubsequenceRepeatedK(String s, int k) {
        BitSet hasX = new BitSet(26);
        Arrays.fill(freq, 0);

        for (char c : s.toCharArray()) {
            int x = c - 'a';
            freq[x]++;
            if (freq[x] == k) {
                hasX.set(x);
            }
        }

        if (hasX.isEmpty()) return "";

        // Rebuild s to only include chars that appear at least k times
        StringBuilder filtered = new StringBuilder();
        for (char c : s.toCharArray()) {
            if (hasX.get(c - 'a')) {
                filtered.append(c);
            }
        }
        s = filtered.toString();
        n = s.length();
        maxLen = n / k;

        // Prepare character list in reverse lexicographical order
        chars.clear();
        for (int i = 25; i >= 0; i--) {
            if (hasX.get(i)) {
                chars.add((char) ('a' + i));
            }
        }

        dfs(s, new StringBuilder(), k);
        return ans;
    }
}


