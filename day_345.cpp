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

// Fast version using global variable DP with &1 trick
short dp[2][1001]={0};// short is sufficient
class Solution {
public:
    static int minimumDeleteSum(string& s1, string& s2) {
        const int n1 = s1.size(), n2 = s2.size();
        if (n1<n2){// WLOG assume n1>=n2
            return minimumDeleteSum(s2, s1);
        }
        memset(dp[0], 0, sizeof(short)*(n2+1));
        memset(dp[1], 0, sizeof(short)*(n2+1));

        for (int x=0; x<n1; x++) {
            const bool par=(x+1)&1, prev=par^1;// 1-indexed
            for (int y=0; y<n2; y++) {
                if (s1[x] == s2[y])
                    dp[par][y+1] = s1[x] + dp[prev][y];
                else
                    dp[par][y+1] = max(dp[prev][y+1], dp[par][y]);
            }
        }
        // std::accumulate is good for using
        int AsciiSum = accumulate(s1.begin(), s1.end(), 0);
        AsciiSum = accumulate(s2.begin(), s2.end(), AsciiSum);

        return AsciiSum - 2*dp[n1&1][n2];
    }
};

