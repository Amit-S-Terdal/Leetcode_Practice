// 1930. Unique Length-3 Palindromic Subsequences

// Given a string s, return the number of unique palindromes of length three that are a subsequence of s.

// Note that even if there are multiple ways to obtain the same subsequence, it is still only counted once.

// A palindrome is a string that reads the same forwards and backwards.

// A subsequence of a string is a new string generated from the original string with some characters (can be none) deleted without changing the relative order of the remaining characters.

// For example, "ace" is a subsequence of "abcde".
 

// Example 1:

// Input: s = "aabca"
// Output: 3
// Explanation: The 3 palindromic subsequences of length 3 are:
// - "aba" (subsequence of "aabca")
// - "aaa" (subsequence of "aabca")
// - "aca" (subsequence of "aabca")
// Example 2:

// Input: s = "adc"
// Output: 0
// Explanation: There are no palindromic subsequences of length 3 in "adc".
// Example 3:

// Input: s = "bbcbaba"
// Output: 4
// Explanation: The 4 palindromic subsequences of length 3 are:
// - "bbb" (subsequence of "bbcbaba")
// - "bcb" (subsequence of "bbcbaba")
// - "bab" (subsequence of "bbcbaba")
// - "aba" (subsequence of "bbcbaba")
 

// Constraints:

// 3 <= s.length <= 10^5
// s consists of only lowercase English letters.



// Solution:


class Solution {
    public int countPalindromicSubsequence(String s) {
        int n = s.length();
        int[] ll = new int[26];
        int[] rr = new int[26];

        // Initialize ll and rr
        Arrays.fill(ll, n);
        Arrays.fill(rr, -1);

        // Compute first and last occurrence
        for (int i = 0; i < n; i++) {
            int x = s.charAt(i) - 'a';
            ll[x] = Math.min(ll[x], i);
            rr[x] = i;
        }

        int count = 0;

        // For each character as palindrome endpoints
        for (int c = 0; c < 26; c++) {
            int l = ll[c], r = rr[c];
            if (l + 1 >= r) continue;  // need at least one char in the middle

            boolean[] seen = new boolean[26];

            // Count distinct middle characters
            for (int i = l + 1; i < r; i++) {
                seen[s.charAt(i) - 'a'] = true;
            }

            for (boolean b : seen) {
                if (b) count++;
            }
        }

        return count;
    }
}
