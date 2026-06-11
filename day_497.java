// 3558. Number of Ways to Assign Edge Weights I

// There is an undirected tree with n nodes labeled from 1 to n, rooted at node 1. The tree is represented by a 2D integer array edges of length n - 1, where edges[i] = [ui, vi] indicates that there is an edge between nodes ui and vi.

// Initially, all edges have a weight of 0. You must assign each edge a weight of either 1 or 2.

// The cost of a path between any two nodes u and v is the total weight of all edges in the path connecting them.

// Select any one node x at the maximum depth. Return the number of ways to assign edge weights in the path from node 1 to x such that its total cost is odd.

// Since the answer may be large, return it modulo 109 + 7.

// Note: Ignore all edges not in the path from node 1 to x.

 

// Example 1:



// Input: edges = [[1,2]]

// Output: 1

// Explanation:

// The path from Node 1 to Node 2 consists of one edge (1 → 2).
// Assigning weight 1 makes the cost odd, while 2 makes it even. Thus, the number of valid assignments is 1.
// Example 2:



// Input: edges = [[1,2],[1,3],[3,4],[3,5]]

// Output: 2

// Explanation:

// The maximum depth is 2, with nodes 4 and 5 at the same depth. Either node can be selected for processing.
// For example, the path from Node 1 to Node 4 consists of two edges (1 → 3 and 3 → 4).
// Assigning weights (1,2) or (2,1) results in an odd cost. Thus, the number of valid assignments is 2.
 

// Constraints:

// 2 <= n <= 10^5
// edges.length == n - 1
// edges[i] == [ui, vi]
// 1 <= ui, vi <= n
// edges represents a valid tree.



// Solution: 




import java.util.*;

class Solution {
    static final int N = 100000;
    static final int MOD = 1_000_000_007;

    static int[] q = new int[N];
    static int front = 0, back = 0;

    static int[] adj = new int[N + 1];

    static class Edge {
        int to;
        int nxt;

        Edge(int to, int nxt) {
            this.to = to;
            this.nxt = nxt;
        }
    }

    static Edge[] E = new Edge[2 * N];
    static int eCnt = 0;

    static void addEdge(int u, int v) {
        E[eCnt] = new Edge(v, adj[u]);
        adj[u] = eCnt++;
    }

    static long modPow(long x, int exp) {
        long y = 1;
        while (exp > 0) {
            if ((exp & 1) != 0) {
                y = y * x % MOD;
            }
            x = x * x % MOD;
            exp >>= 1;
        }
        return y;
    }

    static long pow2(int x) {
        if (x < 30) {
            return 1L << x;
        }

        long B = (1L << 30) % MOD;
        int qq = x / 30;
        int r = x % 30;

        return modPow(B, qq) * pow2(r) % MOD;
    }

    public int assignEdgeWeights(int[][] edges) {
        int n = edges.length + 1;

        Arrays.fill(adj, 1, n + 1, -1);

        eCnt = 0;

        for (int[] e : edges) {
            int u = e[0];
            int v = e[1];

            addEdge(u, v);
            addEdge(v, u);
        }

        front = back = 0;

        boolean[] vis = new boolean[n + 1];

        q[back++] = 1;
        vis[1] = true;

        int depth = -1;

        while (front < back) {
            int qz = back - front;

            while (qz-- > 0) {
                int u = q[front++];

                for (int e = adj[u]; e != -1; e = E[e].nxt) {
                    int v = E[e].to;

                    if (vis[v]) continue;

                    vis[v] = true;
                    q[back++] = v;
                }
            }

            depth++;
        }

        return (int) pow2(depth - 1);
    }
}