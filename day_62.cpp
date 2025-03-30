// 2503. Maximum Number of Points From Grid Queries

// You are given an m x n integer matrix grid and an array queries of size k.

// Find an array answer of size k such that for each integer queries[i] you start in the top left cell of the matrix and repeat the following process:

// If queries[i] is strictly greater than the value of the current cell that you are in, then you get one point if it is your first time visiting this cell, and you can move to any adjacent cell in all 4 directions: up, down, left, and right.
// Otherwise, you do not get any points, and you end this process.
// After the process, answer[i] is the maximum number of points you can get. Note that for each query you are allowed to visit the same cell multiple times.

// Return the resulting array answer.

 

// Example 1:


// Input: grid = [[1,2,3],[2,5,7],[3,5,1]], queries = [5,6,2]
// Output: [5,8,1]
// Explanation: The diagrams above show which cells we visit to get points for each query.
// Example 2:


// Input: grid = [[5,2,1],[1,1,2]], queries = [3]
// Output: [0]
// Explanation: We can not get any points because the value of the top left cell is already greater than or equal to 3.
 

// Constraints:

// m == grid.length
// n == grid[i].length
// 2 <= m, n <= 1000
// 4 <= m * n <= 105
// k == queries.length
// 1 <= k <= 104
// 1 <= grid[i][j], queries[i] <= 10^6


// Solution: 


#include <vector>
#include <queue>
#include <algorithm>

using namespace std;

class Solution {
public:
    vector<int> maxPoints(vector<vector<int>>& grid, vector<int>& queries) {
        int m = grid.size(), n = grid[0].size();
        vector<vector<bool>> visited(m, vector<bool>(n, false));
        vector<int> result(queries.size());

        // Min-heap: (value, row, col)
        using T = tuple<int, int, int>;
        priority_queue<T, vector<T>, greater<T>> pq;
        pq.emplace(grid[0][0], 0, 0);
        visited[0][0] = true;

        vector<pair<int, int>> sortedQueries;
        for (int i = 0; i < queries.size(); ++i) {
            sortedQueries.emplace_back(queries[i], i);
        }
        sort(sortedQueries.begin(), sortedQueries.end());

        int points = 0;
        int idx = 0;
        vector<int> dirs = {0, 1, 0, -1, 0};

        while (idx < sortedQueries.size()) {
            int threshold = sortedQueries[idx].first;

            // BFS expand while top of heap is less than threshold
            while (!pq.empty() && get<0>(pq.top()) < threshold) {
                auto [val, x, y] = pq.top(); pq.pop();
                points++;

                for (int d = 0; d < 4; ++d) {
                    int nx = x + dirs[d], ny = y + dirs[d + 1];
                    if (nx >= 0 && nx < m && ny >= 0 && ny < n && !visited[nx][ny]) {
                        visited[nx][ny] = true;
                        pq.emplace(grid[nx][ny], nx, ny);
                    }
                }
            }

            // All queries with same threshold get same points
            while (idx < sortedQueries.size() && sortedQueries[idx].first == threshold) {
                result[sortedQueries[idx].second] = points;
                idx++;
            }
        }

        return result;
    }
};
