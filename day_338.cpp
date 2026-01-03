// 1411. Number of Ways to Paint N × 3 Grid

// You have a grid of size n x 3 and you want to paint each cell of the grid with exactly one of the three colors: Red, Yellow, or Green while making sure that no two adjacent cells have the same color (i.e., no two cells that share vertical or horizontal sides have the same color).

// Given n the number of rows of the grid, return the number of ways you can paint this grid. As the answer may grow large, the answer must be computed modulo 109 + 7.

 

// Example 1:


// Input: n = 1
// Output: 12
// Explanation: There are 12 possible way to paint the grid as shown.
// Example 2:

// Input: n = 5000
// Output: 30228214
 

// Constraints:

// n == grid.length
// 1 <= n <= 5000



// Solution:


const int mod=1e9+7;
using matrix=vector<vector<int>>;
const matrix I={
    {1, 0},
    {0, 1}
};
const matrix M={
    {3, 2},
    {2, 2}
};

static matrix operator*(const matrix& A, const matrix& B) {
    const int m=A.size(), n=A[0].size(), p=B[0].size();
    matrix C(m, vector<int>(p, 0));
    for (int i=0; i<m; i++) {
        for (int k=0; k<n; k++) {
            for (int j = 0; j<p; j++) {
                C[i][j]=(C[i][j]+1LL*A[i][k]*B[k][j])%mod;
            }
        }
    }
    return C;
}
// Matrix exponentiation (LSBF)
static matrix pow(const matrix& M, int n) {
    matrix ans = I, P=M;
    for (; n > 0; n >>= 1) {
        if (n & 1)
            ans = ans * P;
        P = P * P;
    }
    return ans;
}
class Solution {
public:
    int numOfWays(int n) {
        if (n==1) return 12;
        matrix A=pow(M, n-1);
        return 6LL*(1LL*A[0][0]+A[0][1]+A[1][0]+A[1][1])%mod;
    }
};