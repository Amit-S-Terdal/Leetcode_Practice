// 1895. Largest Magic Square

// A k x k magic square is a k x k grid filled with integers such that every row sum, every column sum, and both diagonal sums are all equal. The integers in the magic square do not have to be distinct. Every 1 x 1 grid is trivially a magic square.

// Given an m x n integer grid, return the size (i.e., the side length k) of the largest magic square that can be found within this grid.

 

// Example 1:


// Input: grid = [[7,1,4,5,6],[2,5,1,6,4],[1,5,4,3,2],[1,2,7,3,4]]
// Output: 3
// Explanation: The largest magic square has a size of 3.
// Every row sum, column sum, and diagonal sum of this magic square is equal to 12.
// - Row sums: 5+1+6 = 5+4+3 = 2+7+3 = 12
// - Column sums: 5+5+2 = 1+4+7 = 6+3+3 = 12
// - Diagonal sums: 5+4+3 = 6+4+2 = 12
// Example 2:


// Input: grid = [[5,1,3,1],[9,3,3,1],[1,3,3,8]]
// Output: 2
 

// Constraints:

// m == grid.length
// n == grid[i].length
// 1 <= m, n <= 50
// 1 <= grid[i][j] <= 10^6



// Solution: 




const int N = 51;
int rowSum[N][N], colSum[N][N], diag[N][N], antidiag[N][N];
class Solution {
public:
    int r, c;
    inline bool isMagic(int k) {
        for (int i = 0; i <= r - k; i++) {
            for (int j = 0; j <= c - k; j++) {
                int sum = diag[i + k][j + k] - diag[i][j];
                int antiD = antidiag[i + k][j] - antidiag[i][j + k];
                bool match = (sum == antiD);

                for (int m = 0; m < k && match; m++) {
                    match = (sum == rowSum[i + m + 1][j + k] -
                                        rowSum[i + m + 1][j] &&
                             sum == colSum[i + k][j + m + 1] -
                                        colSum[i][j + m + 1]);
                }

                if (match)
                    return 1;
            }
        }
        return 0;
    }

    int largestMagicSquare(vector<vector<int>>& grid) {
        r = grid.size(), c = grid[0].size();
        rowSum[0][0] = colSum[0][0] = diag[0][0] = antidiag[0][0] = 0;

        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                int x = grid[i][j];
                rowSum[i + 1][j + 1] = rowSum[i + 1][j] + x;
                colSum[i + 1][j + 1] = colSum[i][j + 1] + x;
                diag[i + 1][j + 1] = diag[i][j] + x;
                antidiag[i + 1][j] = antidiag[i][j + 1] + x;
            }
        }

        for (int k = min(r, c); k >= 2; k--) {
            if (isMagic(k))
                return k;
        }

        return 1;
    }
};

auto init = []() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);
    cout.tie(nullptr);
    return 'c';
}();