// 1970. Last Day Where You Can Still Cross

// There is a 1-based binary matrix where 0 represents land and 1 represents water. You are given integers row and col representing the number of rows and columns in the matrix, respectively.

// Initially on day 0, the entire matrix is land. However, each day a new cell becomes flooded with water. You are given a 1-based 2D array cells, where cells[i] = [ri, ci] represents that on the ith day, the cell on the rith row and cith column (1-based coordinates) will be covered with water (i.e., changed to 1).

// You want to find the last day that it is possible to walk from the top to the bottom by only walking on land cells. You can start from any cell in the top row and end at any cell in the bottom row. You can only travel in the four cardinal directions (left, right, up, and down).

// Return the last day where it is possible to walk from the top to the bottom by only walking on land cells.

 

// Example 1:


// Input: row = 2, col = 2, cells = [[1,1],[2,1],[1,2],[2,2]]
// Output: 2
// Explanation: The above image depicts how the matrix changes each day starting from day 0.
// The last day where it is possible to cross from top to bottom is on day 2.
// Example 2:


// Input: row = 2, col = 2, cells = [[1,1],[1,2],[2,1],[2,2]]
// Output: 1
// Explanation: The above image depicts how the matrix changes each day starting from day 0.
// The last day where it is possible to cross from top to bottom is on day 1.
// Example 3:


// Input: row = 3, col = 3, cells = [[1,2],[2,1],[3,3],[2,2],[1,1],[1,3],[2,3],[3,2],[3,1]]
// Output: 3
// Explanation: The above image depicts how the matrix changes each day starting from day 0.
// The last day where it is possible to cross from top to bottom is on day 3.
 

// Constraints:

// 2 <= row, col <= 2 * 10^4
// 4 <= row * col <= 2 * 10^4
// cells.length == row * col
// 1 <= ri <= row
// 1 <= ci <= col
// All the values of cells are unique.



// Solution: 



import java.util.*;

class Solution {
    // Helper method to calculate the unique key for each cell
    private static int key(int i, int j, int col) {
        return (i - 1) * col + (j - 1);
    }

    public int latestDayToCross(int row, int col, int[][] cells) {
        int N = cells.length;
        int[][] water = new int[N][3]; // to store (d, i, j)
        int[][] c2idx = new int[row][col]; // to store the index of each cell in cells, initially set to -1
        
        // Initialize c2idx with -1
        for (int i = 0; i < row; i++) {
            Arrays.fill(c2idx[i], -1);
        }
        
        // List to store the initial cells where j=1
        List<Integer> water1 = new ArrayList<>();

        // Build water and find indexes for the cell=(i, j) with j=1 storing to water1
        for (int r = 0; r < N; r++) {
            int i = cells[r][0], j = cells[r][1];
            water[r][0] = r;
            water[r][1] = i;
            water[r][2] = j;
            c2idx[i - 1][j - 1] = r;
            if (j == 1) {
                water1.add(r);
            }
        }

        // PriorityQueue to simulate a min-heap (smallest d comes first)
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[0]));
        
        // Bitset to keep track of visited cells
        boolean[] visited = new boolean[row * col];
        
        // Start from all the cells where j=1
        for (int idx : water1) {
            int i = cells[idx][0], j = cells[idx][1];
            pq.offer(new int[]{idx, i, j});
            visited[key(i, j, col)] = true;
        }

        int max_d = Integer.MIN_VALUE;

        // Process the queue
        while (!pq.isEmpty()) {
            int[] top = pq.poll();
            int d = top[0], i = top[1], j = top[2];
            max_d = Math.max(max_d, d);

            if (j == col) {
                return max_d;
            }

            int[][] adj = {
                {i + 1, j - 1}, {i + 1, j}, {i + 1, j + 1},
                {i, j + 1}, {i - 1, j + 1}, {i - 1, j}, {i - 1, j - 1}, {i, j - 1}
            };

            for (int[] pair : adj) {
                int a = pair[0], b = pair[1];
                int idx = key(a, b, col);
                
                if (a <= 0 || a > row || b <= 0 || b > col || visited[idx]) {
                    continue;
                }
                visited[idx] = true;
                pq.offer(new int[]{c2idx[a - 1][b - 1], a, b});
            }
        }

        return -1; // In case no path is found
    }
}
