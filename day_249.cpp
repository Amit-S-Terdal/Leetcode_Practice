// 778. Swim in Rising Water
// Solved

// You are given an n x n integer matrix grid where each value grid[i][j] represents the elevation at that point (i, j).

// It starts raining, and water gradually rises over time. At time t, the water level is t, meaning any cell with elevation less than equal to t is submerged or reachable.

// You can swim from a square to another 4-directionally adjacent square if and only if the elevation of both squares individually are at most t. You can swim infinite distances in zero time. Of course, you must stay within the boundaries of the grid during your swim.

// Return the minimum time until you can reach the bottom right square (n - 1, n - 1) if you start at the top left square (0, 0).

 

// Example 1:


// Input: grid = [[0,2],[1,3]]
// Output: 3
// Explanation:
// At time 0, you are in grid location (0, 0).
// You cannot go anywhere else because 4-directionally adjacent neighbors have a higher elevation than t = 0.
// You cannot reach point (1, 1) until time 3.
// When the depth of water is 3, we can swim anywhere inside the grid.
// Example 2:


// Input: grid = [[0,1,2,3,4],[24,23,22,21,5],[12,13,14,15,16],[11,17,18,19,20],[10,9,8,7,6]]
// Output: 16
// Explanation: The final route is shown.
// We need to wait until time 16 so that (0, 0) and (4, 4) are connected.
 

// Constraints:

// n == grid.length
// n == grid[i].length
// 1 <= n <= 50
// 0 <= grid[i][j] < n^2
// Each value grid[i][j] is unique.


// Soluiton:


//Greedy & Union Find
class UnionFind {    
    vector<int> root, rank;
public:
    UnionFind(int n) : root(n), rank(n) {
        rank.assign(n, 1);
        iota(root.begin(), root.end(), 0);
    }

    int Find(int x) {
        return (x == root[x])?x:root[x]=Find(root[x]);
    }

    void Union(int x, int y) {
        x= Find(x), y= Find(y);
        if (x == y)  return;
        if (rank[x] > rank[y]) swap(x, y);   
        root[x] = y;
        if (rank[x]==rank[y]) rank[y]++;
    }
};
class Solution {
public:
    using int3=tuple<int, int, int>; // (wt, v, w)
    int n;
    int to1D(int i, int j){
        return i*n+j;
    }
    int swimInWater(vector<vector<int>>& grid) {
        n=grid.size();
        if (n==1) return 0;// edge case
        //Build edges (wt, v, w)
        vector<int3> edges;
        for(int i=0; i<n; i++){
            for(int j=0; j<n; j++){
                if (i<n-1){
                    int wt=max(grid[i][j], grid[i+1][j]);
                    edges.emplace_back(wt, to1D(i, j), to1D(i+1, j));
                }
                if (j<n-1){
                    int wt=max(grid[i][j], grid[i][j+1]);
                    edges.emplace_back(wt, to1D(i, j), to1D(i, j+1));
                }
            }
        }
        sort(edges.begin(), edges.end());
        int V=n*n;
        UnionFind uf(V);
        for(auto& [wt, v, w]: edges){
            if (uf.Find(v)!=uf.Find(w))
                uf.Union(v, w);
            if (uf.Find(0)==uf.Find(V-1))
                return wt;
        }
        return 0;
    }
};