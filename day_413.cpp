// 3212. Count Submatrices With Equal Frequency of X and Y

// Given a 2D character matrix grid, where grid[i][j] is either 'X', 'Y', or '.', return the number of submatrices that contain:

// grid[0][0]
// an equal frequency of 'X' and 'Y'.
// at least one 'X'.
 

// Example 1:

// Input: grid = [["X","Y","."],["Y",".","."]]

// Output: 3

// Explanation:



// Example 2:

// Input: grid = [["X","X"],["X","Y"]]

// Output: 0

// Explanation:

// No submatrix has an equal frequency of 'X' and 'Y'.

// Example 3:

// Input: grid = [[".","."],[".","."]]

// Output: 0

// Explanation:

// No submatrix has at least one 'X'.

 

// Constraints:

// 1 <= grid.length, grid[i].length <= 1000
// grid[i][j] is either 'X', 'Y', or '.'.


// Solution: 




uint64_t XY[2][1001];
class Solution {
public:
    static int numberOfSubmatrices(vector<vector<char>>& grid) {
        const int r=grid.size(), c=grid[0].size();
        memset(XY[0], 0, (c+1)*sizeof(uint64_t));
        memset(XY[1], 0, (c+1)*sizeof(uint64_t));
        int cnt=0;
        for(int i=0; i<r; i++){
            bool iEven=(i&1)==0, prv=!iEven;
            for(int j=0; j<c; j++){
                const char c=grid[i][j];
                bool isX=c=='X', isY=c=='Y';
                // pack (isX, isY) to ((uint64_t)isX<<32)+isY
                uint64_t dp=XY[iEven][j+1]=((uint64_t)isX<<32)+isY+XY[iEven][j]+XY[prv][j+1]-XY[prv][j];
                unsigned cntX=dp>>32, cntY=dp& UINT_MAX;
                cnt+=( cntX>0 & cntX==cntY);
            }
        }
        return cnt;
    }
};