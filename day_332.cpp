// 1351. Count Negative Numbers in a Sorted Matrix

// Given a m x n matrix grid which is sorted in non-increasing order both row-wise and column-wise, return the number of negative numbers in grid.

 

// Example 1:

// Input: grid = [[4,3,2,-1],[3,2,1,-1],[1,1,-1,-2],[-1,-1,-2,-3]]
// Output: 8
// Explanation: There are 8 negatives number in the matrix.
// Example 2:

// Input: grid = [[3,2],[1,0]]
// Output: 0
 

// Constraints:

// m == grid.length
// n == grid[i].length
// 1 <= m, n <= 100
// -100 <= grid[i][j] <= 100



// Solution: 



#include <vector>
#include <algorithm>
#include <functional>
using namespace std;

class Solution {
public:
    int countNegatives(vector<vector<int>>& grid) {
        int n=grid.size();
        int m=grid[0].size();
        int sum=0;
        
        // Using binaryS to find the number i_max
        vector<int> col0(n);
        for(int i=0; i<n; i++) col0[i]=grid[i][0];
        int i_max=upper_bound(col0.begin(),col0.end(),0,greater<int>())-col0.begin(); 
    //    cout<<"i_max="<<i_max<<endl;
        ////////////////////
        for (int i=0; i<i_max; i++){
            sum+= m-(upper_bound(grid[i].begin(),grid[i].end(),0,greater<int>())-grid[i].begin());
        }
        sum+=(n-i_max)*m;
        return sum;
    }
};