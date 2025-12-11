// 3531. Count Covered Buildings

// You are given a positive integer n, representing an n x n city. You are also given a 2D grid buildings, where buildings[i] = [x, y] denotes a unique building located at coordinates [x, y].

// A building is covered if there is at least one building in all four directions: left, right, above, and below.

// Return the number of covered buildings.

 

// Example 1:



// Input: n = 3, buildings = [[1,2],[2,2],[3,2],[2,1],[2,3]]

// Output: 1

// Explanation:

// Only building [2,2] is covered as it has at least one building:
// above ([1,2])
// below ([3,2])
// left ([2,1])
// right ([2,3])
// Thus, the count of covered buildings is 1.
// Example 2:



// Input: n = 3, buildings = [[1,1],[1,2],[2,1],[2,2]]

// Output: 0

// Explanation:

// No building has at least one building in all four directions.
// Example 3:



// Input: n = 5, buildings = [[1,3],[3,2],[3,3],[3,5],[5,3]]

// Output: 1

// Explanation:

// Only building [3,3] is covered as it has at least one building:
// above ([1,3])
// below ([5,3])
// left ([3,2])
// right ([3,5])
// Thus, the count of covered buildings is 1.
 

// Constraints:

// 2 <= n <= 10^5
// 1 <= buildings.length <= 10^5 
// buildings[i] = [x, y]
// 1 <= x, y <= n
// All coordinates of buildings are unique.


// Solution: 



class Solution {
    public int countCoveredBuildings(int n, int[][] buildings) {
        int SZ = 100001;
        int[] xMin = new int[SZ], xMax = new int[SZ], yMin = new int[SZ], yMax = new int[SZ];
        
        int M = 0, N = 0;
        
        // Finding the maximum values of x and y
        for (int[] B : buildings) {
            int x = B[0], y = B[1];
            M = Math.max(x, M);
            N = Math.max(y, N);
        }
        
        // Initialize the arrays
        for (int i = 0; i <= N; i++) {
            xMax[i] = 0;
        }
        for (int i = 0; i <= M; i++) {
            yMax[i] = 0;
        }
        for (int i = 0; i <= N; i++) {
            xMin[i] = Integer.MAX_VALUE;
        }
        for (int i = 0; i <= M; i++) {
            yMin[i] = Integer.MAX_VALUE;
        }

        // Populate xMin, xMax, yMin, and yMax arrays
        for (int[] B : buildings) {
            int x = B[0], y = B[1];
            xMin[y] = Math.min(xMin[y], x);
            xMax[y] = Math.max(xMax[y], x);
            yMin[x] = Math.min(yMin[x], y);
            yMax[x] = Math.max(yMax[x], y);
        }
        
        int cnt = 0;
        
        // Count covered buildings
        for (int[] B : buildings) {
            int x = B[0], y = B[1];
            boolean coverX = (xMin[y] < x && x < xMax[y]);
            boolean coverY = (yMin[x] < y && y < yMax[x]);
            if (coverX && coverY) {
                cnt++;
            }
        }
        
        return cnt;
    }
}
