// 2257. Count Unguarded Cells in the Grid

// You are given two integers m and n representing a 0-indexed m x n grid. You are also given two 2D integer arrays guards and walls where guards[i] = [rowi, coli] and walls[j] = [rowj, colj] represent the positions of the ith guard and jth wall respectively.

// A guard can see every cell in the four cardinal directions (north, east, south, or west) starting from their position unless obstructed by a wall or another guard. A cell is guarded if there is at least one guard that can see it.

// Return the number of unoccupied cells that are not guarded.

 

// Example 1:


// Input: m = 4, n = 6, guards = [[0,0],[1,1],[2,3]], walls = [[0,1],[2,2],[1,4]]
// Output: 7
// Explanation: The guarded and unguarded cells are shown in red and green respectively in the above diagram.
// There are a total of 7 unguarded cells, so we return 7.
// Example 2:


// Input: m = 3, n = 3, guards = [[1,1]], walls = [[0,1],[1,0],[2,1],[1,2]]
// Output: 4
// Explanation: The unguarded cells are shown in green in the above diagram.
// There are a total of 4 unguarded cells, so we return 4.
 

// Constraints:

// 1 <= m, n <= 10^5
// 2 <= m * n <= 10^5
// 1 <= guards.length, walls.length <= 5 * 10^4
// 2 <= guards.length + walls.length <= m * n
// guards[i].length == walls[j].length == 2
// 0 <= rowi, rowj < m
// 0 <= coli, colj < n
// All the positions in guards and walls are unique.



// Solution: 



char grid[100000];
class Solution {
public:
    int m, n, comp;
    int d[5] = {0, 1, 0, -1, 0};
    inline int  idx(int r, int c){
        return r*n+c;
    }
    inline void cross(int r, int c) {
        for (int a = 0; a < 4; a++) {
            int di = d[a], dj = d[a + 1];
            for (int i=r+di, j=c+dj; ; i+=di, j+=dj) {
                int pos=idx(i, j);
                if (i<0 || i>=m || j<0 || j>=n || grid[pos] == 'X') break;
                comp-=(grid[pos]==' ');
                grid[pos] = 'V';
            }
        }
    }

    int countUnguarded(int m, int n, vector<vector<int>>& guards,
                       vector<vector<int>>& walls) {
        this->m = m, this->n = n;
        comp=m*n;
        memset(grid, ' ', m*n);
        // Mark walls
        for (auto& ij : walls){
            grid[idx(ij[0], ij[1])] = 'X';
            comp--;
        }
        // Mark guards
        for (auto& ij : guards){
            grid[idx(ij[0], ij[1])] = 'X';
            comp--;
        }
        // Mark Cells Viewed
        for (auto& ij : guards) {
            cross(ij[0], ij[1]);
        }
        return comp;
    }
};