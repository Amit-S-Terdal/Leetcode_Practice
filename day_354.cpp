// 1292. Maximum Side Length of a Square with Sum Less than or Equal to Threshold

// Given a m x n matrix mat and an integer threshold, return the maximum side-length of a square with a sum less than or equal to threshold or return 0 if there is no such square.

 

// Example 1:


// Input: mat = [[1,1,3,2,4,3,2],[1,1,3,2,4,3,2],[1,1,3,2,4,3,2]], threshold = 4
// Output: 2
// Explanation: The maximum side length of square with sum less than 4 is 2 as shown.
// Example 2:

// Input: mat = [[2,2,2,2,2],[2,2,2,2,2],[2,2,2,2,2],[2,2,2,2,2],[2,2,2,2,2]], threshold = 1
// Output: 0
 

// Constraints:

// m == mat.length
// n == mat[i].length
// 1 <= m, n <= 300
// 0 <= mat[i][j] <= 10^4
// 0 <= threshold <= 10^5



// Solution:


class Solution {
public:
    static int Sum(int r0, int r1, int c0, int c1, vector<vector<int>>& mat){
      return mat[r1][c1]-(r0?mat[r0-1][c1]:0)-(c0?mat[r1][c0-1]:0)+((r0>0 && c0>0)?mat[r0-1][c0-1]:0);
    }

    static int SL_W(int i0, int j0, int row, int col,
                    vector<vector<int>>& mat, int k){
        // sliding window along diagoanl beginning from (i0, j0)
        const int MM=min(row-i0, col-j0);// upper limit for r
        int Len=0;

        for (int l=0, r=0; r<MM; r++) {
            int r0=i0+l, r1=i0+r, c0=j0+l, c1=j0+r;
            int S=Sum(r0, r1, c0, c1, mat);
            while (l<r && S>k) {
                l++;
                r0++; c0++;
                S=Sum(r0, r1, c0, c1, mat);
            }
            if (S<=k) Len=max(Len, r-l+1);
        }
        return Len;
    }

    static int maxSideLength(vector<vector<int>>& mat, int k) {
        // prefix sum reuses mat
        const int row=mat.size(), col=mat[0].size();
        for (int j=1; j<col; j++) mat[0][j]+=mat[0][j-1];
        for (int i=1; i<row; i++){
            mat[i][0]+=mat[i-1][0];
            for(int j=1; j<col; j++)
                mat[i][j]+=mat[i-1][j]+mat[i][j-1]-mat[i-1][j-1];
        }

        int ans=0;
        for (int i=0; i<row; i++) {
            if (row-i<=ans) break;// cannot enlarge ans
            ans=max(ans, SL_W(i, 0, row, col, mat, k));
        }
        for (int j=1; j<col; j++) {
            if (col-j<=ans) break;// cannot enlarge ans
            ans=max(ans, SL_W(0, j, row, col, mat, k));
        }
        return ans;
    }
};
