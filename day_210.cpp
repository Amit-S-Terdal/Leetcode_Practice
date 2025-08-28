// 498. Diagonal Traverse

// Given an m x n matrix mat, return an array of all the elements of the array in a diagonal order.

 

// Example 1:


// Input: mat = [[1,2,3],[4,5,6],[7,8,9]]
// Output: [1,2,4,7,5,3,6,8,9]
// Example 2:

// Input: mat = [[1,2],[3,4]]
// Output: [1,2,3,4]
 

// Constraints:

// m == mat.length
// n == mat[i].length
// 1 <= m, n <= 10^4
// 1 <= m * n <= 10^4
// -10^5 <= mat[i][j] <= 10^5

// Solution: 


class Solution {
    public:
        vector<int> findDiagonalOrder(vector<vector<int>>& mat) {
            const int r=mat.size(), c=mat[0].size();
            vector<int> ans(r*c);
            bool flip=0;
    
            for (int d=0, idx=0; d < r+c-1; d++, flip=!flip) {
                if (!flip) {// go NE
                    for (int i=min(d, r-1), j=d-i; i>=0 && j<c; i--, j++) 
                        ans[idx++]=mat[i][j];
                } 
                else {// go SW
                    for (int j=min(d, c-1), i=d-j; j>=0 && i<r; i++, j--) {
                        ans[idx++]=mat[i][j];
                    }
                }
            }
            return ans;
        }
    };
    