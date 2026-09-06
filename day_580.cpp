// 115. Distinct Subsequences

// Given two strings s and t, return the number of distinct subsequences of s which equals t.

// The test cases are generated so that the answer fits on a 32-bit signed integer.

 

// Example 1:

// Input: s = "rabbbit", t = "rabbit"
// Output: 3
// Explanation:
// As shown below, there are 3 ways you can generate "rabbit" from s.
// rabbbit
// rabbbit
// rabbbit
// Example 2:

// Input: s = "babgbag", t = "bag"
// Output: 5
// Explanation:
// As shown below, there are 5 ways you can generate "bag" from s.
// babgbag
// babgbag
// babgbag
// babgbag
// babgbag
 

// Constraints:

// 1 <= s.length, t.length <= 1000
// s and t consist of English letters.


// Solution: 




unsigned dp[1001][1001]; // dp[i][j] denotes # for t[0:j] in s[0:i]
class Solution {
public:
    int m, n;
    string_view s, t;
    unsigned f(int i, int j) {
        if (j==0) return 1; // base case
        else if (i==0 || j>i) return 0;
        if (dp[i][j] != UINT_MAX) return dp[i][j];
        
        unsigned ans=0;
        if (s[i-1]==t[j-1]) 
            ans=f(i-1, j-1) + f(i-1, j);
        else
            ans=f(i-1, j);
        
        return dp[i][j]=ans;
    }
    
    int numDistinct(string& s, string& t) {
        this->s = s;
        this->t = t;
        m=s.size();
        n=t.size();
        for(int i=0; i<=m; i++)
            memset(dp[i], -1, (n+1)*sizeof(unsigned));
        return f(m, n);
    }
};

auto init = []()
{ 
    ios::sync_with_stdio(0);
    cin.tie(0);
    cout.tie(0);
    return 'c';
}();