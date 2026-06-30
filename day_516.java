// 1358. Number of Substrings Containing All Three Characters

// Given a string s consisting only of characters a, b and c.

// Return the number of substrings containing at least one occurrence of all these characters a, b and c.

 

// Example 1:

// Input: s = "abcabc"
// Output: 10
// Explanation: The substrings containing at least one occurrence of the characters a, b and c are "abc", "abca", "abcab", "abcabc", "bca", "bcab", "bcabc", "cab", "cabc" and "abc" (again). 
// Example 2:

// Input: s = "aaacb"
// Output: 3
// Explanation: The substrings containing at least one occurrence of the characters a, b and c are "aaacb", "aacb" and "acb". 
// Example 3:

// Input: s = "abc"
// Output: 1
 

// Constraints:

// 3 <= s.length <= 5 x 10^4
// s only consists of a, b or c characters.



// Solution: 



class Solution {
    public int numberOfSubstrings(String s) {
        int n = s.length();
        int cnt = 0;
        int letter = 0;
        int[] freq = new int[3];

        for (int l = 0, r = 0; r < n; r++) {
            int c = s.charAt(r) - 'a';

            if (++freq[c] == 1) {
                letter++;
            }

            while (letter == 3) {
                if (--freq[s.charAt(l) - 'a'] == 0) {
                    letter--;
                }
                l++;
            }

            cnt += l;
        }

        return cnt;
    }
}