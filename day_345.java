// 712. Minimum ASCII Delete Sum for Two Strings

// Given two strings s1 and s2, return the lowest ASCII sum of deleted characters to make two strings equal.

 

// Example 1:

// Input: s1 = "sea", s2 = "eat"
// Output: 231
// Explanation: Deleting "s" from "sea" adds the ASCII value of "s" (115) to the sum.
// Deleting "t" from "eat" adds 116 to the sum.
// At the end, both strings are equal, and 115 + 116 = 231 is the minimum sum possible to achieve this.
// Example 2:

// Input: s1 = "delete", s2 = "leet"
// Output: 403
// Explanation: Deleting "dee" from "delete" to turn the string into "let",
// adds 100[d] + 101[e] + 101[e] to the sum.
// Deleting "e" from "leet" adds 101[e] to the sum.
// At the end, both strings are equal to "let", and the answer is 100+101+101+101 = 403.
// If instead we turned both strings into "lee" or "eet", we would get answers of 433 or 417, which are higher.
 

// Constraints:

// 1 <= s1.length, s2.length <= 1000
// s1 and s2 consist of lowercase English letters.




// Solution: 



import java.util.*;

class Solution {
    public int minimumDeleteSum(String s1, String s2) {
        int n1 = s1.length();
        int n2 = s2.length();

        // Ensure n1 >= n2 (same as C++ version)
        if (n1 < n2) {
            return minimumDeleteSum(s2, s1);
        }

        // Rolling DP: only 2 rows needed
        short[][] dp = new short[2][n2 + 1];

        Arrays.fill(dp[0], (short) 0);
        Arrays.fill(dp[1], (short) 0);

        for (int x = 0; x < n1; x++) {
            int par = (x + 1) & 1;
            int prev = par ^ 1;

            // Important: reset current row before reuse
            Arrays.fill(dp[par], (short) 0);

            for (int y = 0; y < n2; y++) {
                if (s1.charAt(x) == s2.charAt(y)) {
                    dp[par][y + 1] = (short) (s1.charAt(x) + dp[prev][y]);
                } else {
                    dp[par][y + 1] = (short) Math.max(dp[prev][y + 1], dp[par][y]);
                }
            }
        }

        // Compute total ASCII sum
        int asciiSum = 0;
        for (int i = 0; i < n1; i++) {
            asciiSum += s1.charAt(i);
        }
        for (int i = 0; i < n2; i++) {
            asciiSum += s2.charAt(i);
        }

        // Result
        return asciiSum - 2 * dp[n1 & 1][n2];
    }
}
