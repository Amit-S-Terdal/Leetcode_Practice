// 1878. Get Biggest Three Rhombus Sums in a Grid

// You are given an m x n integer matrix grid​​​.

// A rhombus sum is the sum of the elements that form the border of a regular rhombus shape in grid​​​. The rhombus must have the shape of a square rotated 45 degrees with each of the corners centered in a grid cell. Below is an image of four valid rhombus shapes with the corresponding colored cells that should be included in each rhombus sum:


// Note that the rhombus can have an area of 0, which is depicted by the purple rhombus in the bottom right corner.

// Return the biggest three distinct rhombus sums in the grid in descending order. If there are less than three distinct values, return all of them.

 

// Example 1:


// Input: grid = [[3,4,5,1,3],[3,3,4,2,3],[20,30,200,40,10],[1,5,5,4,1],[4,3,2,2,5]]
// Output: [228,216,211]
// Explanation: The rhombus shapes for the three biggest distinct rhombus sums are depicted above.
// - Blue: 20 + 3 + 200 + 5 = 228
// - Red: 200 + 2 + 10 + 4 = 216
// - Green: 5 + 200 + 4 + 2 = 211
// Example 2:


// Input: grid = [[1,2,3],[4,5,6],[7,8,9]]
// Output: [20,9,8]
// Explanation: The rhombus shapes for the three biggest distinct rhombus sums are depicted above.
// - Blue: 4 + 2 + 6 + 8 = 20
// - Red: 9 (area 0 rhombus in the bottom right corner)
// - Green: 8 (area 0 rhombus in the bottom middle)
// Example 3:

// Input: grid = [[7,7,7]]
// Output: [7]
// Explanation: All three possible rhombus sums are the same, so return [7].
 

// Constraints:

// m == grid.length
// n == grid[i].length
// 1 <= m, n <= 50
// 1 <= grid[i][j] <= 10^5


// Solution: 



unsigned diag[100][51], antid[100][51]; 
const int OFFSET=50;

class Solution {
public:
    static inline int rhombusSum(int i, int j, int d, vector<vector<int>>& grid) {
        if (d==0) return grid[i][j];
        
        const int l=j-d, r=j+d, u=i-d, b=i+d ;
        
        // Diagonals with corner (\) 
        const int i0=u-j+OFFSET, i1=i-l+OFFSET;
        int sum=diag[i0][r+1]-diag[i0][j];
        sum+=diag[i1][j+1]-diag[i1][l];
        
        // Anti-diagonals without corners (/) 
        const int j0=u+j, j1=b+j;
        sum+=antid[j0][i]-antid[j0][u+1];
        sum+=antid[j1][b]-antid[j1][i+1];

        return sum;
    }

    static vector<int> getBiggestThree(vector<vector<int>>& grid) {
        const int m=grid.size(), n=grid[0].size();
    //  no need for reset    
    //    memset(diag, 0, sizeof(diag));
    //    memset(antid, 0, sizeof(antid));

        for (int i=0; i< m; i++) {
            for (int j = 0; j < n; j++) {
                const int i0=i-j+OFFSET, j0=i+j;
                const int x=grid[i][j];
                // diag over j, antid over i
                diag[i0][j+1]=diag[i0][j]+x;
                antid[j0][i+1]=antid[j0][i]+x;
            }
        }

        int dM=min(m, n)/2; 
        vector<int> x(3, -1);
        for (int d=0; d<=dM; d++) {
            for (int i=d; i<m-d; i++) {
                for (int j=d; j<n-d; j++) {
                    const int y=rhombusSum(i, j, d, grid);

                    // Skip if y is already in our top 3 
                    if (y==x[0] || y==x[1] || y==x[2]) continue;

                    if (y>x[0]) {
                        x[2]=x[1]; x[1]=exchange(x[0], y);
                    }
                    else if (y>x[1]) x[2]=exchange(x[1], y);
                    else if (y>x[2]) x[2]=y;
                }
            }
        }
        for(int i=2; i>=0; i--) 
            if (x[i]==-1) x.pop_back();
        return x;
    }
};