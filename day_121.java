// 1857. Largest Color Value in a Directed Graph

// There is a directed graph of n colored nodes and m edges. The nodes are numbered from 0 to n - 1.

// You are given a string colors where colors[i] is a lowercase English letter representing the color of the ith node in this graph (0-indexed). You are also given a 2D array edges where edges[j] = [aj, bj] indicates that there is a directed edge from node aj to node bj.

// A valid path in the graph is a sequence of nodes x1 -> x2 -> x3 -> ... -> xk such that there is a directed edge from xi to xi+1 for every 1 <= i < k. The color value of the path is the number of nodes that are colored the most frequently occurring color along that path.

// Return the largest color value of any valid path in the given graph, or -1 if the graph contains a cycle.

 

// Example 1:



// Input: colors = "abaca", edges = [[0,1],[0,2],[2,3],[3,4]]
// Output: 3
// Explanation: The path 0 -> 2 -> 3 -> 4 contains 3 nodes that are colored "a" (red in the above image).
// Example 2:



// Input: colors = "a", edges = [[0,0]]
// Output: -1
// Explanation: There is a cycle from 0 to 0.
 

// Constraints:

// n == colors.length
// m == edges.length
// 1 <= n <= 10^5
// 0 <= m <= 10^5
// colors consists of lowercase English letters.
// 0 <= aj, bj < n


// Solution:



class Solution {
    private static final int INF = Integer.MAX_VALUE;
    public int largestPathValue(String colors, int[][] edges) {
        int n = colors.length();
        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < n; i++)
            adj.add(new ArrayList<>());
        for (int[] e : edges)
            adj.get(e[0]).add(e[1]);
            
        int[][] count = new int[n][26];
        int[] vis = new int[n];
        int ans = 0;

        for (int i = 0; i < n && ans != INF; i++) {
            ans = Math.max(ans, dfs(i, colors, adj, count, vis));
        }
        return ans == INF ? -1 : ans;
    }

    private int dfs(int node, String colors,List<List<Integer>> adj,int[][] count,int[] vis) {
        if (vis[node] == 1)
            return INF;
        if (vis[node] == 2) {
            return count[node][colors.charAt(node) - 'a'];
        }

        vis[node] = 1;
        for (int nxt : adj.get(node)) {
            int res = dfs(nxt, colors, adj, count, vis);
            if (res == INF)
                return INF;
            for (int c = 0; c < 26; c++) {
                count[node][c] = Math.max(count[node][c], count[nxt][c]);
            }
        }
        int col = colors.charAt(node) - 'a';
        count[node][col]++;
        vis[node] = 2;

        return count[node][col];
    }
}
