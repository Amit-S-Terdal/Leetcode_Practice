// 1092. Shortest Common Supersequence 

// Given two strings str1 and str2, return the shortest string that has both str1 and str2 as subsequences. If there are multiple valid strings, return any of them.

// A string s is a subsequence of string t if deleting some number of characters from t (possibly 0) results in the string s.

 

// Example 1:

// Input: str1 = "abac", str2 = "cab"
// Output: "cabac"
// Explanation: 
// str1 = "abac" is a subsequence of "cabac" because we can delete the first "c".
// str2 = "cab" is a subsequence of "cabac" because we can delete the last "ac".
// The answer provided is the shortest such string that satisfies these properties.
// Example 2:

// Input: str1 = "aaaaaaaa", str2 = "aaaaaaaa"
// Output: "aaaaaaaa"
 

// Constraints:

// 1 <= str1.length, str2.length <= 1000
// str1 and str2 consist of lowercase English letters.


// Solution: 



#include <vector>
#include <string>
#include <algorithm>

using namespace std;

class Solution {
public:
    string shortestCommonSupersequence(string str1, string str2) {
        int m = str1.length();
        int n = str2.length();
        
        // Step 1: Find the LCS of str1 and str2
        vector<vector<int>> dp(m + 1, vector<int>(n + 1, 0));
        
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (str1[i - 1] == str2[j - 1]) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }
        
        // Step 2: Reconstruct the SCS using the LCS
        int i = m, j = n;
        string scs;
        
        while (i > 0 && j > 0) {
            if (str1[i - 1] == str2[j - 1]) {
                scs.push_back(str1[i - 1]);
                i--;
                j--;
            } else if (dp[i - 1][j] > dp[i][j - 1]) {
                scs.push_back(str1[i - 1]);
                i--;
            } else {
                scs.push_back(str2[j - 1]);
                j--;
            }
        }
        
        // Append remaining characters from str1 and str2
        while (i > 0) {
            scs.push_back(str1[i - 1]);
            i--;
        }
        while (j > 0) {
            scs.push_back(str2[j - 1]);
            j--;
        }
        
        // Reverse to get the correct order
        reverse(scs.begin(), scs.end());
        return scs;
    }
};