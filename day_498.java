// 3559. Number of Ways to Assign Edge Weights II

// There is an undirected tree with n nodes labeled from 1 to n, rooted at node 1. The tree is represented by a 2D integer array edges of length n - 1, where edges[i] = [ui, vi] indicates that there is an edge between nodes ui and vi.

// Initially, all edges have a weight of 0. You must assign each edge a weight of either 1 or 2.

// The cost of a path between any two nodes u and v is the total weight of all edges in the path connecting them.

// You are given a 2D integer array queries. For each queries[i] = [ui, vi], determine the number of ways to assign weights to edges in the path such that the cost of the path between ui and vi is odd.

// Return an array answer, where answer[i] is the number of valid assignments for queries[i].

// Since the answer may be large, apply modulo 109 + 7 to each answer[i].

// Note: For each query, disregard all edges not in the path between node ui and vi.

 

// Example 1:



// Input: edges = [[1,2]], queries = [[1,1],[1,2]]

// Output: [0,1]

// Explanation:

// Query [1,1]: The path from Node 1 to itself consists of no edges, so the cost is 0. Thus, the number of valid assignments is 0.
// Query [1,2]: The path from Node 1 to Node 2 consists of one edge (1 → 2). Assigning weight 1 makes the cost odd, while 2 makes it even. Thus, the number of valid assignments is 1.
// Example 2:



// Input: edges = [[1,2],[1,3],[3,4],[3,5]], queries = [[1,4],[3,4],[2,5]]

// Output: [2,1,4]

// Explanation:

// Query [1,4]: The path from Node 1 to Node 4 consists of two edges (1 → 3 and 3 → 4). Assigning weights (1,2) or (2,1) results in an odd cost. Thus, the number of valid assignments is 2.
// Query [3,4]: The path from Node 3 to Node 4 consists of one edge (3 → 4). Assigning weight 1 makes the cost odd, while 2 makes it even. Thus, the number of valid assignments is 1.
// Query [2,5]: The path from Node 2 to Node 5 consists of three edges (2 → 1, 1 → 3, and 3 → 5). Assigning (1,2,2), (2,1,2), (2,2,1), or (1,1,1) makes the cost odd. Thus, the number of valid assignments is 4.
 

// Constraints:

// 2 <= n <= 10^5
// edges.length == n - 1
// edges[i] == [ui, vi]
// 1 <= queries.length <= 10^5
// queries[i] == [ui, vi]
// 1 <= ui, vi <= n
// edges represents a valid tree.




// Solution: 



import java.util.*;

class Solution {
    static final int N = 100000;
    static final int LOG = 17;
    static final int MOD = 1000000007;

    static int[] head = new int[N + 1];
    static int[] level = new int[N + 1];
    static int[] parent = new int[N + 1];
    static int[][] up = new int[N + 1][LOG];

    static int[] to = new int[2 * N];
    static int[] next = new int[2 * N];
    static int edgeCount;

    static void addEdge(int u, int v) {
        to[edgeCount] = v;
        next[edgeCount] = head[u];
        head[u] = edgeCount++;
    }

    static long modPow(long x, int exp) {
        long res = 1;
        while (exp > 0) {
            if ((exp & 1) == 1)
                res = (res * x) % MOD;
            x = (x * x) % MOD;
            exp >>= 1;
        }
        return res;
    }

    static long pow2(int x) {
        long res = 1;
        long base = 1L << 30;
        int blocks = x / 30;
        int rem = x % 30;

        res = modPow(base % MOD, blocks);
        res = (res * (1L << rem)) % MOD;

        return res;
    }

    static int lca(int u, int v) {

        if (level[u] < level[v]) {
            int temp = u;
            u = v;
            v = temp;
        }

        int diff = level[u] - level[v];

        for (int i = 0; i < LOG; i++) {
            if (((diff >> i) & 1) == 1)
                u = up[u][i];
        }

        if (u == v)
            return u;

        for (int i = LOG - 1; i >= 0; i--) {
            if (up[u][i] != up[v][i]) {
                u = up[u][i];
                v = up[v][i];
            }
        }

        return up[u][0];
    }

    static int distance(int u, int v) {
        int a = lca(u, v);
        return level[u] + level[v] - 2 * level[a];
    }

    public int[] assignEdgeWeights(int[][] edges, int[][] queries) {

        int n = edges.length + 1;

        Arrays.fill(head, -1);
        Arrays.fill(level, 0);
        Arrays.fill(parent, 0);
        edgeCount = 0;

        for (int[] e : edges) {
            addEdge(e[0], e[1]);
            addEdge(e[1], e[0]);
        }

        // BFS
        boolean[] visited = new boolean[n + 1];

        Queue<Integer> q = new ArrayDeque<>();
        q.offer(1);
        visited[1] = true;

        level[1] = 1;
        parent[1] = 0;

        while (!q.isEmpty()) {

            int u = q.poll();

            for (int i = head[u]; i != -1; i = next[i]) {

                int v = to[i];

                if (!visited[v]) {

                    visited[v] = true;
                    parent[v] = u;
                    level[v] = level[u] + 1;

                    q.offer(v);
                }
            }
        }

        // Binary lifting table
        for (int i = 1; i <= n; i++) {
            up[i][0] = parent[i];
        }

        for (int j = 1; j < LOG; j++) {
            for (int i = 0; i <= n; i++) {
                up[i][j] = up[up[i][j - 1]][j - 1];
            }
        }

        int[] ans = new int[queries.length];

        for (int i = 0; i < queries.length; i++) {

            int u = queries[i][0];
            int v = queries[i][1];

            int d = distance(u, v);

            ans[i] = d > 0 ? (int) pow2(d - 1) : 0;
        }

        return ans;
    }
}