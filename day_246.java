// 407. Trapping Rain Water II

// Given an m x n integer matrix heightMap representing the height of each unit cell in a 2D elevation map, return the volume of water it can trap after raining.

 

// Example 1:


// Input: heightMap = [[1,4,3,1,3,2],[3,2,1,3,2,4],[2,3,3,2,3,1]]
// Output: 4
// Explanation: After the rain, water is trapped between the blocks.
// We have two small ponds 1 and 3 units trapped.
// The total volume of water trapped is 4.
// Example 2:


// Input: heightMap = [[3,3,3,3,3],[3,2,2,2,3],[3,2,1,2,3],[3,2,2,2,3],[3,3,3,3,3]]
// Output: 10
 

// Constraints:

// m == heightMap.length
// n == heightMap[i].length
// 1 <= m, n <= 200
// 0 <= heightMap[i][j] <= 2 * 10^4



// Solution: 

import java.util.PriorityQueue;

class Solution {
    private static final int[] di = {1, -1, 0, 0};
    private static final int[] dj = {0, 0, 1, -1};

    public int trapRainWater(int[][] heightMap) {
        int m = heightMap.length;
        if (m == 0) return 0;
        int n = heightMap[0].length;
        if (n == 0 || m <= 2 || n <= 2) return 0;

        boolean[][] visited = new boolean[m][n];
        PriorityQueue<Cell> heap = new PriorityQueue<>();

        // Add boundary cells to the heap and mark them visited
        for (int i = 0; i < m; i++) {
            heap.offer(new Cell(heightMap[i][0], i, 0));
            heap.offer(new Cell(heightMap[i][n - 1], i, n - 1));
            visited[i][0] = true;
            visited[i][n - 1] = true;
        }

        for (int j = 1; j < n - 1; j++) {
            heap.offer(new Cell(heightMap[0][j], 0, j));
            heap.offer(new Cell(heightMap[m - 1][j], m - 1, j));
            visited[0][j] = true;
            visited[m - 1][j] = true;
        }

        int waterLevel = 0;
        int totalWater = 0;

        while (!heap.isEmpty()) {
            Cell cell = heap.poll();
            waterLevel = Math.max(waterLevel, cell.height);

            for (int k = 0; k < 4; k++) {
                int ni = cell.i + di[k];
                int nj = cell.j + dj[k];

                if (ni < 0 || nj < 0 || ni >= m || nj >= n || visited[ni][nj])
                    continue;

                visited[ni][nj] = true;

                int neighborHeight = heightMap[ni][nj];
                if (neighborHeight < waterLevel) {
                    totalWater += waterLevel - neighborHeight;
                }

                heap.offer(new Cell(neighborHeight, ni, nj));
            }
        }

        return totalWater;
    }

    private static class Cell implements Comparable<Cell> {
        int height, i, j;

        public Cell(int height, int i, int j) {
            this.height = height;
            this.i = i;
            this.j = j;
        }

        public int compareTo(Cell other) {
            return Integer.compare(this.height, other.height);
        }
    }
}
