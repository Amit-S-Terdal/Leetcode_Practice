// 2338. Count the Number of Ideal Arrays

// You are given two integers n and maxValue, which are used to describe an ideal array.

// A 0-indexed integer array arr of length n is considered ideal if the following conditions hold:

// Every arr[i] is a value from 1 to maxValue, for 0 <= i < n.
// Every arr[i] is divisible by arr[i - 1], for 0 < i < n.
// Return the number of distinct ideal arrays of length n. Since the answer may be very large, return it modulo 109 + 7.

 

// Example 1:

// Input: n = 2, maxValue = 5
// Output: 10
// Explanation: The following are the possible ideal arrays:
// - Arrays starting with the value 1 (5 arrays): [1,1], [1,2], [1,3], [1,4], [1,5]
// - Arrays starting with the value 2 (2 arrays): [2,2], [2,4]
// - Arrays starting with the value 3 (1 array): [3,3]
// - Arrays starting with the value 4 (1 array): [4,4]
// - Arrays starting with the value 5 (1 array): [5,5]
// There are a total of 5 + 2 + 1 + 1 + 1 = 10 distinct ideal arrays.
// Example 2:

// Input: n = 5, maxValue = 3
// Output: 11
// Explanation: The following are the possible ideal arrays:
// - Arrays starting with the value 1 (9 arrays): 
//    - With no other distinct values (1 array): [1,1,1,1,1] 
//    - With 2nd distinct value 2 (4 arrays): [1,1,1,1,2], [1,1,1,2,2], [1,1,2,2,2], [1,2,2,2,2]
//    - With 2nd distinct value 3 (4 arrays): [1,1,1,1,3], [1,1,1,3,3], [1,1,3,3,3], [1,3,3,3,3]
// - Arrays starting with the value 2 (1 array): [2,2,2,2,2]
// - Arrays starting with the value 3 (1 array): [3,3,3,3,3]
// There are a total of 9 + 1 + 1 = 11 distinct ideal arrays.
 

// Constraints:

// 2 <= n <= 10^4
// 1 <= maxValue <= 10^4


// Solution: 

const int mod = 1e9 + 7;
int factMemo[100000] = {};
int dp[100000][15];
using ll = long long;
class Solution {
public:
ll power(ll a, ll b, ll m = mod) {ll res = 1;while (b > 0) {if (b & 1)res = (res * a) % m;a = (a * a) % m;b = b >> 1;}return res;}
    ll fact(ll n) {
        if (n == 0) return 1;
        if (factMemo[n]) return factMemo[n];
        factMemo[n] = (n * fact(n - 1)) % mod;
        return factMemo[n];
    }
    ll mod_inv(ll a, ll b) {
        return (((fact(a) * power(fact(b), mod - 2)) % mod) * power(fact(a - b), mod - 2)) % mod;
    }

    int idealArrays(int n, int maxi) {
        for (int i = 1; i <= maxi; i++)
            for (int j = 1; j <= min(n, 14); j++)
                dp[i][j] = 0;
        for (int i = 1; i <= maxi; i++) {
            dp[i][1] = 1;
            for (int j = 2; j * i <= maxi; j++)
                for (int k = 1; k < min(n, 14); k++)
                    dp[i*j][k+1] += dp[i][k];
        }
        ll res = 0;
        for (int i = 1; i <= maxi; i++)
            for (int j = 1; j <= min(n, 14); j++)
                res = (res + mod_inv(n - 1, n - j) * dp[i][j]) % mod;
        return res;
    } 
};