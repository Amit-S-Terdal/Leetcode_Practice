// 873. Length of Longest Fibonacci Subsequence

// A sequence x1, x2, ..., xn is Fibonacci-like if:

// n >= 3
// xi + xi+1 == xi+2 for all i + 2 <= n
// Given a strictly increasing array arr of positive integers forming a sequence, return the length of the longest Fibonacci-like subsequence of arr. If one does not exist, return 0.

// A subsequence is derived from another sequence arr by deleting any number of elements (including none) from arr, without changing the order of the remaining elements. For example, [3, 5, 8] is a subsequence of [3, 4, 5, 6, 7, 8].

 

// Example 1:

// Input: arr = [1,2,3,4,5,6,7,8]
// Output: 5
// Explanation: The longest subsequence that is fibonacci-like: [1,2,3,5,8].
// Example 2:

// Input: arr = [1,3,7,11,12,14,18]
// Output: 3
// Explanation: The longest subsequence that is fibonacci-like: [1,11,12], [3,11,14] or [7,11,18].
 

// Constraints:

// 3 <= arr.length <= 1000
// 1 <= arr[i] < arr[i + 1] <= 109

// Solution:


#include <vector>
#include <unordered_map>
#include <algorithm>

using namespace std;

class Solution {
public:
    int lenLongestFibSubseq(vector<int>& arr) {
        int n = arr.size();
        unordered_map<int, int> index;
        for (int i = 0; i < n; ++i) {
            index[arr[i]] = i;
        }
        
        vector<vector<int>> dp(n, vector<int>(n, 2));
        int maxLen = 0;
        
        for (int j = 1; j < n; ++j) {
            for (int i = 0; i < j; ++i) {
                int target = arr[j] - arr[i];
                if (index.find(target) != index.end() && index[target] < i) {
                    dp[i][j] = dp[index[target]][i] + 1;
                    maxLen = max(maxLen, dp[i][j]);
                }
            }
        }
        
        return maxLen >= 3 ? maxLen : 0;
    }
};