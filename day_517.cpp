// 2812. Find the Safest Path in a Grid

// You are given a 0-indexed 2D matrix grid of size n x n, where (r, c) represents:

// A cell containing a thief if grid[r][c] = 1
// An empty cell if grid[r][c] = 0
// You are initially positioned at cell (0, 0). In one move, you can move to any adjacent cell in the grid, including cells containing thieves.

// The safeness factor of a path on the grid is defined as the minimum manhattan distance from any cell in the path to any thief in the grid.

// Return the maximum safeness factor of all paths leading to cell (n - 1, n - 1).

// An adjacent cell of cell (r, c), is one of the cells (r, c + 1), (r, c - 1), (r + 1, c) and (r - 1, c) if it exists.

// The Manhattan distance between two cells (a, b) and (x, y) is equal to |a - x| + |b - y|, where |val| denotes the absolute value of val.

 

// Example 1:


// Input: grid = [[1,0,0],[0,0,0],[0,0,1]]
// Output: 0
// Explanation: All paths from (0, 0) to (n - 1, n - 1) go through the thieves in cells (0, 0) and (n - 1, n - 1).
// Example 2:


// Input: grid = [[0,0,1],[0,0,0],[0,0,0]]
// Output: 2
// Explanation: The path depicted in the picture above has a safeness factor of 2 since:
// - The closest cell of the path to the thief at cell (0, 2) is cell (0, 0). The distance between them is | 0 - 0 | + | 0 - 2 | = 2.
// It can be shown that there are no other paths with a higher safeness factor.
// Example 3:


// Input: grid = [[0,0,0,1],[0,0,0,0],[0,0,0,0],[1,0,0,0]]
// Output: 2
// Explanation: The path depicted in the picture above has a safeness factor of 2 since:
// - The closest cell of the path to the thief at cell (0, 3) is cell (1, 2). The distance between them is | 0 - 1 | + | 3 - 2 | = 2.
// - The closest cell of the path to the thief at cell (3, 0) is cell (3, 2). The distance between them is | 3 - 3 | + | 0 - 2 | = 2.
// It can be shown that there are no other paths with a higher safeness factor.
 

// Constraints:

// 1 <= grid.length == n <= 400
// grid[i].length == n
// grid[i][j] is either 0 or 1.
// There is at least one thief in the grid.



// Solution: 




// union find class with rank
const int N=160000;
int Root[N], Rank[N];
static int d[5] = {0, 1, 0, -1, 0};
class UnionFind {
public:
    UnionFind(int n){
        iota(Root, Root+n, 0);
        memset(Rank, 0, n*sizeof(int));
    }

    int Find(int x) {
        return (x == Root[x])? x :Root[x]=Find(Root[x]);
    }

    bool Union(int x, int y) {
        x=Find(x), y=Find(y);
        if (x==y) return 0;
        if (Rank[x] > Rank[y]) swap(x, y);
        Root[x] = y;
        if (Rank[x] == Rank[y])
            Rank[y]++;
        return 1;
    }
    bool connected(int x, int y) { return Find(x) == Find(y); }
};

int L1[800];// heads for array version of linked lists
int nxt[N];
inline void insert(int L, int idx){
    nxt[idx]=L1[L];
    L1[L]=idx;
}

class Solution {
public:
    using int2 = pair<int, int>; //(i, j)
    inline static int to1d(int i, int j, int n) { return i*n + j; }

    static int maximumSafenessFactor(vector<vector<int>>& grid) {
        const int n = grid.size();
        if (grid[0][0] || grid[n-1][n-1])
            return 0; // very special case
        queue<int2> q;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) {
                    grid[i][j] = 0;
                    q.emplace(i, j);
                } 
                else
                    grid[i][j] = -1;
            }
        }
        memset(L1, -1, 2*n*sizeof(int));
        memset(nxt, -1, n*n*sizeof(int));

        int dist = 0;
        while (!q.empty()) {
            int qz = q.size();
            for (int a = 0; a < qz; a++) {
                auto [i, j] = q.front();
                q.pop();
                for (int b = 0; b < 4; b++) {
                    int r = i + d[b], c = j + d[b + 1];
                    if (r < 0 || r >= n || c < 0 || c >= n || grid[r][c] != -1)
                        continue;
                    grid[r][c] = dist + 1;
                    q.emplace(r, c);
                    insert(dist + 1, to1d(r, c, n));
                }
            }
            dist++;
        }

        UnionFind G(n * n);
        for (int dd = dist - 1; dd >= 0; dd--) {
            if (L1[dd]==-1)
                continue;
            for (int idx=L1[dd]; idx!=-1; idx=nxt[idx]) {
                auto [i, j]=div(idx, n);
                for (int b = 0; b < 4; b++) {
                    int r = i + d[b], c = j + d[b + 1];
                    if (r < 0 || r >= n || c < 0 || c >= n)
                        continue;
                    if (grid[r][c] >= dd)
                        G.Union(to1d(i, j, n), to1d(r, c, n));
                }
            }
            if (G.connected(0, n * n - 1))
                return dd;
        }
        return 0;
    }
};

auto init = []() {
    ios::sync_with_stdio(0);
    cin.tie(0);
    cout.tie(0);
    return 'c';
}();