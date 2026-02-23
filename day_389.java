// 1461. Check If a String Contains All Binary Codes of Size K

// Given a binary string s and an integer k, return true if every binary code of length k is a substring of s. Otherwise, return false.

 

// Example 1:

// Input: s = "00110110", k = 2
// Output: true
// Explanation: The binary codes of length 2 are "00", "01", "10" and "11". They can be all found as substrings at indices 0, 1, 3 and 2 respectively.
// Example 2:

// Input: s = "0110", k = 1
// Output: true
// Explanation: The binary codes of length 1 are "0" and "1", it is clear that both exist as a substring. 
// Example 3:

// Input: s = "0110", k = 2
// Output: false
// Explanation: The binary code "00" is of length 2 and does not exist in the array.
 

// Constraints:

// 1 <= s.length <= 5 * 10^5
// s[i] is either '0' or '1'.
// 1 <= k <= 20



// Solution: 


class Solution {
    public boolean hasAllCodes(String s, int k) {
        int n = s.length();
        if (n < k) return false;

        int total = 1 << k;              // total possible binary codes
        boolean[] seen = new boolean[total];
        int count = 0;

        int mask = 0;
        int windowMask = (1 << (k - 1)) - 1;

        // Build initial window
        for (int i = 0; i < k; i++) {
            mask = (mask << 1) | (s.charAt(i) - '0');
        }

        if (!seen[mask]) {
            seen[mask] = true;
            count++;
        }

        // Slide window
        for (int r = k; r < n; r++) {
            mask &= windowMask;               // remove leftmost bit
            mask <<= 1;                       // shift left
            mask |= (s.charAt(r) - '0');      // add new bit

            if (!seen[mask]) {
                seen[mask] = true;
                count++;
            }
        }

        return count == total;
    }
}