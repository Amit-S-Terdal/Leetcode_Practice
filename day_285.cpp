// 474. Ones and Zeroes

// You are given an array of binary strings strs and two integers m and n.

// Return the size of the largest subset of strs such that there are at most m 0's and n 1's in the subset.

// A set x is a subset of a set y if all elements of x are also elements of y.

 

// Example 1:

// Input: strs = ["10","0001","111001","1","0"], m = 5, n = 3
// Output: 4
// Explanation: The largest subset with at most 5 0's and 3 1's is {"10", "0001", "1", "0"}, so the answer is 4.
// Other valid but smaller subsets include {"0001", "1"} and {"10", "1", "0"}.
// {"111001"} is an invalid subset because it contains 4 1's, greater than the maximum of 3.
// Example 2:

// Input: strs = ["10","0","1"], m = 1, n = 1
// Output: 2
// Explanation: The largest subset is {"0", "1"}, so the answer is 2.
 

// Constraints:

// 1 <= strs.length <= 600
// 1 <= strs[i].length <= 100
// strs[i] consists only of digits '0' and '1'.
// 1 <= m, n <= 100


// Solution: 



// global C-array version with elapsed time: 26ms
short dp[101][101];
class Solution {
public:
    static int findMaxForm(vector<string>& strs, int m, int n) {
        for(int i=0; i<=m; i++)
            memset(dp[i], 0, sizeof(short)*(n+1));
        for (auto& s: strs){
            const int len=s.size();
            const int c0=count(s.begin(), s.end(), '0'), c1=len-c0;
            for(int i=m; i>=c0; i--)
			    for(int j=n; j>=c1; j--)                    
				    dp[i][j]=max(dp[i][j], 
						    (short)(dp[i-c0][j-c1]+1));
        }
        return dp[m][n];
    }
};
static const auto init = []() noexcept {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);
    cout.tie(nullptr);
    return 'c';
}();