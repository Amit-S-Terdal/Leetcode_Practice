// 1559. Detect Cycles in 2D Grid

// Given a 2D array of characters grid of size m x n, you need to find if there exists any cycle consisting of the same value in grid.

// A cycle is a path of length 4 or more in the grid that starts and ends at the same cell. From a given cell, you can move to one of the cells adjacent to it - in one of the four directions (up, down, left, or right), if it has the same value of the current cell.

// Also, you cannot move to the cell that you visited in your last move. For example, the cycle (1, 1) -> (1, 2) -> (1, 1) is invalid because from (1, 2) we visited (1, 1) which was the last visited cell.

// Return true if any cycle of the same value exists in grid, otherwise, return false.

 

// Example 1:



// Input: grid = [["a","a","a","a"],["a","b","b","a"],["a","b","b","a"],["a","a","a","a"]]
// Output: true
// Explanation: There are two valid cycles shown in different colors in the image below:

// Example 2:



// Input: grid = [["c","c","c","a"],["c","d","c","c"],["c","c","e","c"],["f","c","c","c"]]
// Output: true
// Explanation: There is only one valid cycle highlighted in the image below:

// Example 3:



// Input: grid = [["a","b","b"],["b","z","b"],["b","b","a"]]
// Output: false
 

// Constraints:

// m == grid.length
// n == grid[i].length
// 1 <= m, n <= 500
// grid consists only of lowercase English letters.


// Solution: 



constexpr int N=500*500;
int Rt[N], Rk[N];
class UnionFind { // UnionFind class with rank
    int* root, *rank;
public:
    // use initial list to set component=n
    UnionFind(int n){
        root=Rt;
        rank=Rk;
        memset(rank, 0, n*sizeof(int));//set rank all 0
        iota(root, root+n, 0);// set root=[0, 1,..., n-1]
    }

    int Find(int x) {// compressed path
        return (x==root[x])?x:root[x]=Find(root[x]);
    }

    bool Union(int x, int y) {
        x=Find(x), y=Find(y);
        if (x == y) return 0;// a cycle is detected
        if (rank[x]>rank[y]) swap(x, y);
        root[x]=y;
        if (rank[x]==rank[y]) rank[y]++;
        return 1;// each comp is still a tree
    }
};
class Solution {
public:
    static bool containsCycle(vector<vector<char>>& grid) {
        const int r=grid.size(), c=grid[0].size(), sz=r*c;
        UnionFind G(sz);
        for(int i=0; i<r-1; i++){
            int rIdx=i*c;
            for(int j=0; j<c-1; j++){
                const int cur=rIdx+j, alpha=grid[i][j];
                // consider down
                if (grid[i+1][j]==alpha && !G.Union(cur+c, cur))
                    return 1;
                // consider right
                if (grid[i][j+1]==alpha && !G.Union(cur+1, cur))
                    return 1;
            }
            const int cur=(i*c)+(c-1), alpha=grid[i][c-1];
            if (alpha==grid[i+1][c-1] && !G.Union(cur, cur+c)) 
                return 1;
        }
        // row r-1
        int rIdx=(r-1)*c; 
        auto& row=grid[r-1];
        for (int j=0; j <c-1; j++) {
            const int cur=rIdx+j, alpha=row[j];
            if (row[j+1]==alpha && !G.Union(cur+1, cur))
                 return 1;
        }
        return 0;
    }
};