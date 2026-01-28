// 3651. Minimum Cost Path with Teleportations

// You are given a m x n 2D integer array grid and an integer k. You start at the top-left cell (0, 0) and your goal is to reach the bottom‐right cell (m - 1, n - 1).

// There are two types of moves available:

// Normal move: You can move right or down from your current cell (i, j), i.e. you can move to (i, j + 1) (right) or (i + 1, j) (down). The cost is the value of the destination cell.

// Teleportation: You can teleport from any cell (i, j), to any cell (x, y) such that grid[x][y] <= grid[i][j]; the cost of this move is 0. You may teleport at most k times.

// Return the minimum total cost to reach cell (m - 1, n - 1) from (0, 0).

 

// Example 1:

// Input: grid = [[1,3,3],[2,5,4],[4,3,5]], k = 2

// Output: 7

// Explanation:

// Initially we are at (0, 0) and cost is 0.

// Current Position	Move	New Position	Total Cost
// (0, 0)	Move Down	(1, 0)	0 + 2 = 2
// (1, 0)	Move Right	(1, 1)	2 + 5 = 7
// (1, 1)	Teleport to (2, 2)	(2, 2)	7 + 0 = 7
// The minimum cost to reach bottom-right cell is 7.

// Example 2:

// Input: grid = [[1,2],[2,3],[3,4]], k = 1

// Output: 9

// Explanation:

// Initially we are at (0, 0) and cost is 0.

// Current Position	Move	New Position	Total Cost
// (0, 0)	Move Down	(1, 0)	0 + 2 = 2
// (1, 0)	Move Right	(1, 1)	2 + 3 = 5
// (1, 1)	Move Down	(2, 1)	5 + 4 = 9
// The minimum cost to reach bottom-right cell is 9.

 

// Constraints:

// 2 <= m, n <= 80
// m == grid.length
// n == grid[i].length
// 0 <= grid[i][j] <= 10^4
// 0 <= k <= 10




// Solution: 


class Solution {
    public int minCost(int[][] grid, int k) {
        int n = grid.length, m = grid[0].length;
        
        // 1. Find the Maximum Value in the grid to size our helper arrays
        int maxVal = 0;
        for(int[] row : grid) {
            for(int val : row) maxVal = Math.max(maxVal, val);
        }

        // dp[i][j] = Min cost to reach (n-1, m-1) from (i, j)
        int[][] dp = new int[n][m];
        
        // temp[v] = Min cost starting from ANY cell with value 'v'
        int[] temp = new int[maxVal + 1];
        int[] best = new int[maxVal + 1];
        
        Arrays.fill(temp, Integer.MAX_VALUE);
        
        // Base Case: Cost from target to target is 0. 
        // Note: The cost is incurred when ENTERING a cell. 
        // We consider the target reached, so starting at target has 0 *additional* cost.
        temp[grid[n - 1][m - 1]] = 0;

        // --- INITIALIZATION (K=0) ---
        // Fill DP table using standard walking rules (Right/Down)
        for(int i = n - 1; i >= 0; i--) {
            for(int j = m - 1; j >= 0; j--) {
                if(i == n - 1 && j == m - 1) continue; // Skip target
                
                int down = (i + 1 < n) ? dp[i + 1][j] + grid[i + 1][j] : Integer.MAX_VALUE;
                int right = (j + 1 < m) ? dp[i][j + 1] + grid[i][j + 1] : Integer.MAX_VALUE;
                
                dp[i][j] = Math.min(down, right);
                
                // Update the best known cost for this cell's value
                if (dp[i][j] != Integer.MAX_VALUE) {
                    temp[grid[i][j]] = Math.min(temp[grid[i][j]], dp[i][j]);
                }
            }
        }

        // --- LAYERS (K > 0) ---
        // For each allowed teleport, we try to relax the grid costs
        for(int x = 0; x < k; x++) {
            
            // 1. Build Prefix Minimum Array
            // best[v] = min cost obtainable from any cell with value <= v
            best[0] = temp[0];
            for(int v = 1; v <= maxVal; v++) {
                best[v] = Math.min(best[v - 1], temp[v]);
            }
            
            // 2. Update DP Table with Teleport Options
            for(int i = n - 1; i >= 0; i--) {
                for(int j = m - 1; j >= 0; j--) {
                    if(i == n - 1 && j == m - 1) continue;
                    
                    int down = (i + 1 < n) ? dp[i + 1][j] + grid[i + 1][j] : Integer.MAX_VALUE;
                    int right = (j + 1 < m) ? dp[i][j + 1] + grid[i][j + 1] : Integer.MAX_VALUE;
                    int walkCost = Math.min(down, right);
                    
                    // Teleport Option: Jump to the best state with value <= grid[i][j]
                    int teleportCost = best[grid[i][j]];
                    
                    dp[i][j] = Math.min(walkCost, teleportCost);
                    
                    // Update temp for the NEXT iteration
                    if (dp[i][j] != Integer.MAX_VALUE) {
                        temp[grid[i][j]] = Math.min(temp[grid[i][j]], dp[i][j]);
                    }
                }
            }
        }
        
        return dp[0][0];
    }
}