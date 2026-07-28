// // 3517. Smallest Palindromic Rearrangement I

// You are given a palindromic string s.

// Return the lexicographically smallest palindromic permutation of s.

 

// Example 1:

// Input: s = "z"

// Output: "z"

// Explanation:

// A string of only one character is already the lexicographically smallest palindrome.

// Example 2:

// Input: s = "babab"

// Output: "abbba"

// Explanation:

// Rearranging "babab" → "abbba" gives the smallest lexicographic palindrome.

// Example 3:

// Input: s = "daccad"

// Output: "acddca"

// Explanation:

// Rearranging "daccad" → "acddca" gives the smallest lexicographic palindrome.

 

// Constraints:

// 1 <= s.length <= 105
// s consists of lowercase English letters.
// s is guaranteed to be palindromic.



// Solution: 

class Solution {
    public String smallestPalindrome(String s) {
        int n = s.length();
        int n0 = n / 2;

        int[] freq = new int[26];

        for (int i = 0; i < n0; i++) {
            freq[s.charAt(i) - 'a']++;
        }

        char[] ans = s.toCharArray();
        int l = 0;

        for (int x = 0; x < 26; x++) {
            int f = freq[x];
            if (f == 0) continue;

            char c = (char) ('a' + x);

            for (int i = 0; i < f; i++) {
                ans[l + i] = c;
                ans[n - 1 - (l + i)] = c;
            }

            l += f;
        }

        return new String(ans);
    }
}