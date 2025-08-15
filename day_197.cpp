// 2787. Ways to Express an Integer as Sum of Powers

// Given two positive integers n and x.

// Return the number of ways n can be expressed as the sum of the xth power of unique positive integers, in other words, the number of sets of unique integers [n1, n2, ..., nk] where n = n1x + n2x + ... + nkx.

// Since the result can be very large, return it modulo 109 + 7.

// For example, if n = 160 and x = 3, one way to express n is n = 23 + 33 + 53.

 

// Example 1:

// Input: n = 10, x = 2
// Output: 1
// Explanation: We can express n as the following: n = 32 + 12 = 10.
// It can be shown that it is the only way to express 10 as the sum of the 2nd power of unique integers.
// Example 2:

// Input: n = 4, x = 1
// Output: 2
// Explanation: We can express n in the following ways:
// - n = 41 = 4.
// - n = 31 + 11 = 4.
 

// Constraints:

// 1 <= n <= 300
// 1 <= x <= 5


// Solution:

static int dp[300][301]={0};
class Solution {
public:
    static const int mod=1e9+7;
    static int MSBF_pow(int nk, unsigned x){
        if (x==1) return nk;
        bitset<3> B(x);// x=2~5, 3 bits are enough
        const int iN=31-countl_zero(x);
        long long y=nk;
        for(int i=iN-1; i>=0; i--){
            y=(y*y)*(B[i]?nk:1);
            if (y>=mod) y%=mod;
        }
        return y;
    }
    static int f(int i, int sum, vector<int>& A){
        if (sum==0) return 1;
        if (i<0) return 0;
        if(dp[i][sum]!=-1) return dp[i][sum];
        const int y=A[i];
        long long ans=f(i-1, sum, A);// skip
        if(sum>=y) ans+=f(i-1, sum-y, A); // take
        if (ans>=mod) ans-=mod;
        return dp[i][sum]=ans;
    }
    static int numberOfWays(int n, int x) {
        vector<int> A;
        for(int nk=1; ; nk++){
            const int p=MSBF_pow(nk, x);
            if(p>n) break;
            A.push_back(p);
        }
        const int m=A.size();
        for(int i=0; i<m; i++)
            fill(dp[i], dp[i]+(n+1), -1);
        
        return f(m-1, n, A);
    }
};