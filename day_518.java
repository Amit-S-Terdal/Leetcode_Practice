// 3286. Find a Safe Walk Through a Grid

// You are given an m x n binary matrix grid and an integer health.

// You start on the upper-left corner (0, 0) and would like to get to the lower-right corner (m - 1, n - 1).

// You can move up, down, left, or right from one cell to another adjacent cell as long as your health remains positive.

// Cells (i, j) with grid[i][j] = 1 are considered unsafe and reduce your health by 1.

// Return true if you can reach the final cell with a health value of 1 or more, and false otherwise.

 

// Example 1:

// Input: grid = [[0,1,0,0,0],[0,1,0,1,0],[0,0,0,1,0]], health = 1

// Output: true

// Explanation:

// The final cell can be reached safely by walking along the gray cells below.


// Example 2:

// Input: grid = [[0,1,1,0,0,0],[1,0,1,0,0,0],[0,1,1,1,0,1],[0,0,1,0,1,0]], health = 3

// Output: false

// Explanation:

// A minimum of 4 health points is needed to reach the final cell safely.


// Example 3:

// Input: grid = [[1,1,1],[1,0,1],[1,1,1]], health = 5

// Output: true

// Explanation:

// The final cell can be reached safely by walking along the gray cells below.



// Any path that does not go through the cell (1, 1) is unsafe since your health will drop to 0 when reaching the final cell.

 

// Constraints:

// m == grid.length
// n == grid[i].length
// 1 <= m, n <= 50
// 2 <= m * n
// 1 <= health <= m + n
// grid[i][j] is either 0 or 1.




// Solution: 



class Solution {
    static final int N = 5000;
    static int[] q = new int[N];
    static int front, back;
    static int[] maxH = new int[N];
    static int[] d = {0, 1, 0, -1, 0};

    static int idx(int i, int j, int c) {
        return i * c + j;
    }

    static boolean outSide(int i, int j, int r, int c) {
        return i < 0 || i >= r || j < 0 || j >= c;
    }

    public boolean findSafeWalk(List<List<Integer>> grid, int health) {
        int r = grid.size();
        int c = grid.get(0).size();

        Arrays.fill(maxH, 0, r * c, -1);

        front = back = N / 2;
        q[back++] = 0;

        maxH[0] = health - grid.get(0).get(0);

        while (front < back) {
            int ij = q[front++];
            int curH = maxH[ij];

            if (ij == r * c - 1) {
                return curH > 0;
            }

            int i = ij / c;
            int j = ij % c;

            for (int a = 0; a < 4; a++) {
                int s = i + d[a];
                int t = j + d[a + 1];

                if (outSide(s, t, r, c)) continue;

                int st = idx(s, t, c);
                int H2 = curH - grid.get(s).get(t);

                if (H2 > maxH[st]) {
                    maxH[st] = H2;

                    if (grid.get(s).get(t) == 0) {
                        q[--front] = st;   // push front
                    } else {
                        q[back++] = st;    // push back
                    }
                }
            }
        }

        return false;
    }
}