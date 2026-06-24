// 3700. Number of ZigZag Arrays II

// You are given three integers n, l, and r.

// A ZigZag array of length n is defined as follows:

// Each element lies in the range [l, r].
// No two adjacent elements are equal.
// No three consecutive elements form a strictly increasing or strictly decreasing sequence.
// Return the total number of valid ZigZag arrays.

// Since the answer may be large, return it modulo 109 + 7.

// A sequence is said to be strictly increasing if each element is strictly greater than its previous one (if exists).

// A sequence is said to be strictly decreasing if each element is strictly smaller than its previous one (if exists).

 

// Example 1:

// Input: n = 3, l = 4, r = 5

// Output: 2

// Explanation:

// There are only 2 valid ZigZag arrays of length n = 3 using values in the range [4, 5]:

// [4, 5, 4]
// [5, 4, 5]
// Example 2:

// Input: n = 3, l = 1, r = 3

// Output: 10

// Explanation:

// ​​​​​​​There are 10 valid ZigZag arrays of length n = 3 using values in the range [1, 3]:

// [1, 2, 1], [1, 3, 1], [1, 3, 2]
// [2, 1, 2], [2, 1, 3], [2, 3, 1], [2, 3, 2]
// [3, 1, 2], [3, 1, 3], [3, 2, 3]
// All arrays meet the ZigZag conditions.

 

// Constraints:

// 3 <= n <= 10^9
// 1 <= l < r <= 75​​​​​​​




// Solution: 




constexpr int mod = 1e9 + 7;
// using 1D vector to denote the compressed form: a[i*m+j]=m[i][j]
using matrix=vector<int>;
static int m;
static inline matrix operator*(const matrix& A, const matrix& B) {
    // all matrix size m*m
    matrix C(m*m, 0);
    for (int i = 0; i < m; i++) {
        for (int k = 0; k < m; k++) {
            if (A[i*m+k] == 0) continue;
            for (int j = 0; j < m; j++) {
                C[i*m+j] = (C[i*m+j] + 1LL * A[i*m+k] * B[k*m+j]) % mod;
            }
        }
    }
    return C;
}
static matrix I(){
    matrix ans(m*m, 0);
    for(int i=0; i<m; i++)
        ans[i*m+i]=1;
    return ans;
}
// MSBF modular Matrix Exponentiation
static matrix pow(const matrix& M, unsigned m) {
    if (m == 0)
        return I();
    int bMax = 31 - __builtin_clz(m);
    matrix ans = M;
    for (int i = bMax - 1; i >= 0; i--) {
        ans = ans * ans;
        if ((m>>i)&1)
            ans = ans * M;
    }
    return ans;
}

class Solution {
public:
    static int zigZagArrays(int n, int l, int r) {
        m=r-l+1;
        matrix U(m*m, 0), L(m*m, 0);
        for(int i=0; i<m; i++){
            for(int j=i+1; j<m; j++) U[i*m+j]=1;
        }
        for(int i=0; i<m; i++){
            for(int j=0; j<i; j++) L[i*m+j]=1;
        }
        n--;
        const int n0=n>>1;
        const matrix UL=U*L;
        matrix P=pow(UL, n0);
        if (n&1) P=L*P;
        return 2LL*reduce(P.begin(), P.end(), 0LL)%mod;
    }
};
auto init = []() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);
    cout.tie(nullptr);
    return 'c';
}();