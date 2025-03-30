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


import java.util.*;

class Solution {
    public int[] maxPoints(int[][] grid, int[] queries) {
        int m = grid.length, n = grid[0].length;
        boolean[][] visited = new boolean[m][n];
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[0]));
        pq.offer(new int[]{grid[0][0], 0, 0});
        visited[0][0] = true;

        int[][] directions = {{0,1},{0,-1},{1,0},{-1,0}};
        int[] result = new int[queries.length];

        // Sort queries with their original indices
        int[][] sortedQueries = new int[queries.length][2];
        for (int i = 0; i < queries.length; i++) {
            sortedQueries[i][0] = queries[i];
            sortedQueries[i][1] = i;
        }
        Arrays.sort(sortedQueries, Comparator.comparingInt(a -> a[0]));

        int points = 0;
        int index = 0;

        // BFS expansion with min-heap
        while (index < sortedQueries.length) {
            int threshold = sortedQueries[index][0];

            while (!pq.isEmpty() && pq.peek()[0] < threshold) {
                int[] curr = pq.poll();
                int val = curr[0], x = curr[1], y = curr[2];
                points++;

                for (int[] d : directions) {
                    int nx = x + d[0], ny = y + d[1];
                    if (nx >= 0 && nx < m && ny >= 0 && ny < n && !visited[nx][ny]) {
                        visited[nx][ny] = true;
                        pq.offer(new int[]{grid[nx][ny], nx, ny});
                    }
                }
            }

            // Fill result for all queries with the same threshold
            while (index < sortedQueries.length && sortedQueries[index][0] == threshold) {
                result[sortedQueries[index][1]] = points;
                index++;
            }
        }

        return result;
    }
}
