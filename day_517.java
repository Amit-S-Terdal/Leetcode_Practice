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




import java.util.*;

class Solution {

    static final int N = 160000;
    static int[] root = new int[N];
    static int[] rank = new int[N];

    static int[] d = {0, 1, 0, -1, 0};

    static class UnionFind {

        UnionFind(int n) {
            for (int i = 0; i < n; i++) {
                root[i] = i;
                rank[i] = 0;
            }
        }

        int find(int x) {
            if (x != root[x]) {
                root[x] = find(root[x]);
            }
            return root[x];
        }

        boolean union(int x, int y) {
            x = find(x);
            y = find(y);

            if (x == y) return false;

            if (rank[x] > rank[y]) {
                int temp = x;
                x = y;
                y = temp;
            }

            root[x] = y;

            if (rank[x] == rank[y]) {
                rank[y]++;
            }

            return true;
        }

        boolean connected(int x, int y) {
            return find(x) == find(y);
        }
    }


    static int[] head = new int[800];
    static int[] next = new int[N];


    static void insert(int level, int idx) {
        next[idx] = head[level];
        head[level] = idx;
    }


    static int to1D(int i, int j, int n) {
        return i * n + j;
    }


    public int maximumSafenessFactor(List<List<Integer>> grid) {

        int n = grid.size();

        if (grid.get(0).get(0) == 1 || 
            grid.get(n - 1).get(n - 1) == 1) {
            return 0;
        }


        Queue<int[]> q = new LinkedList<>();


        // Multi-source BFS initialization
        for (int i = 0; i < n; i++) {

            for (int j = 0; j < n; j++) {

                if (grid.get(i).get(j) == 1) {
                    grid.get(i).set(j, 0);
                    q.offer(new int[]{i, j});
                } 
                else {
                    grid.get(i).set(j, -1);
                }
            }
        }


        Arrays.fill(head, -1);
        Arrays.fill(next, -1);


        int dist = 0;


        // BFS calculate distance from nearest thief
        while (!q.isEmpty()) {

            int size = q.size();


            for (int a = 0; a < size; a++) {

                int[] cur = q.poll();

                int i = cur[0];
                int j = cur[1];


                for (int b = 0; b < 4; b++) {

                    int r = i + d[b];
                    int c = j + d[b + 1];


                    if (r < 0 || r >= n ||
                        c < 0 || c >= n ||
                        grid.get(r).get(c) != -1) {
                        continue;
                    }


                    grid.get(r).set(c, dist + 1);

                    q.offer(new int[]{r, c});

                    insert(dist + 1, to1D(r, c, n));
                }
            }

            dist++;
        }



        UnionFind uf = new UnionFind(n * n);



        // Process cells from highest safety to lowest
        for (int level = dist - 1; level >= 0; level--) {


            if (head[level] == -1)
                continue;


            for (int idx = head[level]; idx != -1; idx = next[idx]) {


                int i = idx / n;
                int j = idx % n;


                for (int b = 0; b < 4; b++) {

                    int r = i + d[b];
                    int c = j + d[b + 1];


                    if (r < 0 || r >= n ||
                        c < 0 || c >= n) {
                        continue;
                    }


                    if (grid.get(r).get(c) >= level) {

                        uf.union(
                            to1D(i, j, n),
                            to1D(r, c, n)
                        );
                    }
                }
            }


            if (uf.connected(0, n * n - 1)) {
                return level;
            }
        }


        return 0;
    }
}