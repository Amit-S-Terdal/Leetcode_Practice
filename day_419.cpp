// 3546. Equal Sum Grid Partition I

// You are given an m x n matrix grid of positive integers. Your task is to determine if it is possible to make either one horizontal or one vertical cut on the grid such that:

// Each of the two resulting sections formed by the cut is non-empty.
// The sum of the elements in both sections is equal.
// Return true if such a partition exists; otherwise return false.

 

// Example 1:

// Input: grid = [[1,4],[2,3]]

// Output: true

// Explanation:



// A horizontal cut between row 0 and row 1 results in two non-empty sections, each with a sum of 5. Thus, the answer is true.

// Example 2:

// Input: grid = [[1,3],[2,4]]

// Output: false

// Explanation:

// No horizontal or vertical cut results in two non-empty sections with equal sums. Thus, the answer is false.

 

// Constraints:

// 1 <= m == grid.length <= 10^5
// 1 <= n == grid[i].length <= 10^5
// 2 <= m * n <= 10^5
// 1 <= grid[i][j] <= 10^5


// Solution:



long long rowSum[100000], colSum[100000];
class Solution {
public:
    static bool canPartitionGrid(vector<vector<int>>& grid) {
        const int r=grid.size(), c=grid[0].size();
        memset(rowSum, 0, r*8);
        memset(colSum, 0, c*8);
        for (int i=0; i<r; i++){
            if (i>0)[[likely]]
                rowSum[i]+=rowSum[i-1];
            for(int j=0; j<c; j++){
                const int x=grid[i][j];
                rowSum[i]+=x;
                colSum[j]+=x;
            }
        }
        partial_sum(colSum, colSum+c, colSum);
        const long long Tsum=colSum[c-1], target=Tsum/2;
        if (Tsum&1) return 0;
        int i=lower_bound(rowSum, rowSum+r, target)-rowSum;
        if (i<r && rowSum[i]==target) return 1;
        int j=lower_bound(colSum, colSum+c, target)-colSum;
        return j<c && colSum[j]==target;
    }
};

auto init = []() {
    ios::sync_with_stdio(0);
    cin.tie(0);
    cout.tie(0);
    return 'c';
}();