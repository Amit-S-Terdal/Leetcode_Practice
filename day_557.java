// 3090. Maximum Length Substring With Two Occurrences

// Given a string s, return the maximum length of a substring such that it contains at most two occurrences of each character.
 

// Example 1:

// Input: s = "bcbbbcba"

// Output: 4

// Explanation:

// The following substring has a length of 4 and contains at most two occurrences of each character: "bcbbbcba".
// Example 2:

// Input: s = "aaaa"

// Output: 2

// Explanation:

// The following substring has a length of 2 and contains at most two occurrences of each character: "aaaa".
 

// Constraints:

// 2 <= s.length <= 100
// s consists only of lowercase English letters.




// Solution: 



class Solution {
    public int maximumLengthSubstring(String s) {
        int[] freq = new int[26];
        int l = 0, n = s.length(), len = 0;

        for (int r = 0; r < n; r++) {
            int x = s.charAt(r) - 'a';
            freq[x]++;

            while (l < r && freq[x] > 2) {
                freq[s.charAt(l) - 'a']--;
                l++;
            }

            len = Math.max(len, r - l + 1);
        }

        return len;
    }
}