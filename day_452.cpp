// 1391. Check if There is a Valid Path in a Grid

// You are given an m x n grid. Each cell of grid represents a street. The street of grid[i][j] can be:

// 1 which means a street connecting the left cell and the right cell.
// 2 which means a street connecting the upper cell and the lower cell.
// 3 which means a street connecting the left cell and the lower cell.
// 4 which means a street connecting the right cell and the lower cell.
// 5 which means a street connecting the left cell and the upper cell.
// 6 which means a street connecting the right cell and the upper cell.

// You will initially start at the street of the upper-left cell (0, 0). A valid path in the grid is a path that starts from the upper left cell (0, 0) and ends at the bottom-right cell (m - 1, n - 1). The path should only follow the streets.

// Notice that you are not allowed to change any street.

// Return true if there is a valid path in the grid or false otherwise.

 

// Example 1:


// Input: grid = [[2,4,3],[6,5,2]]
// Output: true
// Explanation: As shown you can start at cell (0, 0) and visit all the cells of the grid to reach (m - 1, n - 1).
// Example 2:


// Input: grid = [[1,2,1],[1,2,1]]
// Output: false
// Explanation: As shown you the street at cell (0, 0) is not connected with any street of any other cell and you will get stuck at cell (0, 0)
// Example 3:

// Input: grid = [[1,1,2]]
// Output: false
// Explanation: You will get stuck at cell (0, 1) and you cannot reach cell (0, 2).
 

// Constraints:

// m == grid.length
// n == grid[i].length
// 1 <= m, n <= 300
// 1 <= grid[i][j] <= 6



// Solution:



constexpr int NN=300*300;
int Rt[NN], Rk[NN];
class UnionFind { // UnionFind class with rank
    int* root, *rank;
public:
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
    bool connected(int x, int y){ return Find(x)==Find(y); }
};
class Solution {
public:
    inline static int idx(int i, int j, int c){ return i*c+j; }
    static bool hasValidPath(vector<vector<int>>& grid) {
        const int r=grid.size(), c=grid[0].size(), N=r*c;
        UnionFind G(N);
        for(int i=0; i<r-1; i++){
            for(int j=0; j<c-1; j++){
                const int C=grid[i][j], R=grid[i][j+1], D=grid[i+1][j];
                const int cc=idx(i, j, c), rr=cc+1, dd=cc+c;
                const bool CR=(C==1)|(C==4)|(C==6), RC=R&1;
                const bool CD=(C==2)|(C==4)|(C==3), DC=(D==2)|(D==5)|(D==6);
                // (C, R) connected only for (1,1), (1,3), (1,5) &
                // (4, 1), (4, 3), (4, 5), (6,1) ,(6, 3), (6, 5)
                if(CR & RC)
                    G.Union(cc, rr);
                // (C, D) connected only for (2, 2), (2, 5), (2, 6),
                // (3, 2), (3, 5), (3, 6), (4, 2), (4, 5), (4, 6)
                if (CD & DC)
                    G.Union(cc, dd);
            }
            const int C=grid[i][c-1], D=grid[i+1][c-1];
            const int cc=idx(i, c-1, c), dd=cc+c;
            const bool CD=(C==2)|(C==4)|(C==3), DC=(D==2)|(D==5)|(D==6);
            if (CD & DC)
                G.Union(cc, dd);
        }
        for(int j=0; j<c-1; j++){
            const int C=grid[r-1][j], R=grid[r-1][j+1];
            const int cc=idx(r-1, j, c), rr=cc+1;
            const bool CR=(C==1)|(C==4)|(C==6), RC=R&1;
            if(CR & RC)
                G.Union(cc, rr);
        }
        return G.connected(0, N-1);
    }
};