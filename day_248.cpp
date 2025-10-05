// 417. Pacific Atlantic Water Flow

// There is an m x n rectangular island that borders both the Pacific Ocean and Atlantic Ocean. The Pacific Ocean touches the island's left and top edges, and the Atlantic Ocean touches the island's right and bottom edges.

// The island is partitioned into a grid of square cells. You are given an m x n integer matrix heights where heights[r][c] represents the height above sea level of the cell at coordinate (r, c).

// The island receives a lot of rain, and the rain water can flow to neighboring cells directly north, south, east, and west if the neighboring cell's height is less than or equal to the current cell's height. Water can flow from any cell adjacent to an ocean into the ocean.

// Return a 2D list of grid coordinates result where result[i] = [ri, ci] denotes that rain water can flow from cell (ri, ci) to both the Pacific and Atlantic oceans.

 

// Example 1:


// Input: heights = [[1,2,2,3,5],[3,2,3,4,4],[2,4,5,3,1],[6,7,1,4,5],[5,1,1,2,4]]
// Output: [[0,4],[1,3],[1,4],[2,2],[3,0],[3,1],[4,0]]
// Explanation: The following cells can flow to the Pacific and Atlantic oceans, as shown below:
// [0,4]: [0,4] -> Pacific Ocean 
//        [0,4] -> Atlantic Ocean
// [1,3]: [1,3] -> [0,3] -> Pacific Ocean 
//        [1,3] -> [1,4] -> Atlantic Ocean
// [1,4]: [1,4] -> [1,3] -> [0,3] -> Pacific Ocean 
//        [1,4] -> Atlantic Ocean
// [2,2]: [2,2] -> [1,2] -> [0,2] -> Pacific Ocean 
//        [2,2] -> [2,3] -> [2,4] -> Atlantic Ocean
// [3,0]: [3,0] -> Pacific Ocean 
//        [3,0] -> [4,0] -> Atlantic Ocean
// [3,1]: [3,1] -> [3,0] -> Pacific Ocean 
//        [3,1] -> [4,1] -> Atlantic Ocean
// [4,0]: [4,0] -> Pacific Ocean 
//        [4,0] -> Atlantic Ocean
// Note that there are other possible paths for these cells to flow to the Pacific and Atlantic oceans.
// Example 2:

// Input: heights = [[1]]
// Output: [[0,0]]
// Explanation: The water can flow from the only cell to the Pacific and Atlantic oceans.
 

// Constraints:

// m == heights.length
// n == heights[r].length
// 1 <= m, n <= 200
// 0 <= heights[r][c] <= 105


// Solution:

#include <cstdint>
#include <vector>
#include <cstring>
using std::vector;

// this version implements q by using C-array
// this version implements q by using C-array
uint8_t status[40000];
int dir[5]={0, 1, 0, -1, 0};
int q[40000], front=0, back=0;
int m, n;
class Solution {
public:
    static inline bool isOutside(int i, int j) {
        return i<0||i>=m||j<0||j>=n;
    }
    static inline int idx(int i, int j) { return i*n+j; }

    static void bfs(vector<vector<int>>& heights, uint8_t mark) {
        while (front!=back) {
            int ij=q[front++];
            int i = ij / n, j = ij % n;
            for (int a=0; a<4; a++) {
                int r=i+dir[a], s=j+dir[a+1];
                if (isOutside(r, s)) continue;
                int rs=idx(r, s);
                if ((status[rs]&mark)||heights[r][s]<heights[i][j])
                    continue;
                status[rs]|=mark;
                q[back++]=rs;
            }
        }
    }

    static vector<vector<int>> pacificAtlantic(vector<vector<int>>& heights) {
        m=heights.size();
        n=heights[0].size();
        memset(status, 0, m*n);

        front=back=0;// reset q
        // Pacific: top row + left col
        for (int j=0; j<n; j++) {
            const int ij=idx(0, j);
            status[ij]|=1;
            q[back++]=ij;
        }
        for (int i=1; i<m; i++) {
            const int ij=idx(i, 0);
            status[ij]|=1;
            q[back++]=ij;
        }
        bfs(heights, 1);

        // Atlantic: bottom row + right col
        front=back=0;// reset q
        for (int j=0; j<n; j++) {
            const int ij=idx(m-1, j);
            status[ij]|=2;
            q[back++]=ij;
        }
        for (int i=0; i<m-1; i++) {
            const int ij=idx(i, n-1);
            status[ij]|=2;
            q[back++]=ij;
        }
        bfs(heights, 2);

        vector<vector<int>> ans;
        for (int ij=0; ij<m*n; ij++) {
            if (status[ij]==3) {
                int i = ij / n, j = ij % n;
                ans.push_back({i, j});
            }
        }
        return ans;
    }
};