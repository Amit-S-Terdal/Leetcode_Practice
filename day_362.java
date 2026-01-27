// 3650. Minimum Cost Path with Edge Reversals

// You are given a directed, weighted graph with n nodes labeled from 0 to n - 1, and an array edges where edges[i] = [ui, vi, wi] represents a directed edge from node ui to node vi with cost wi.

// Each node ui has a switch that can be used at most once: when you arrive at ui and have not yet used its switch, you may activate it on one of its incoming edges vi → ui reverse that edge to ui → vi and immediately traverse it.

// The reversal is only valid for that single move, and using a reversed edge costs 2 * wi.

// Return the minimum total cost to travel from node 0 to node n - 1. If it is not possible, return -1.

 

// Example 1:

// Input: n = 4, edges = [[0,1,3],[3,1,1],[2,3,4],[0,2,2]]

// Output: 5

// Explanation:



// Use the path 0 → 1 (cost 3).
// At node 1 reverse the original edge 3 → 1 into 1 → 3 and traverse it at cost 2 * 1 = 2.
// Total cost is 3 + 2 = 5.
// Example 2:

// Input: n = 4, edges = [[0,2,1],[2,1,1],[1,3,1],[2,3,3]]

// Output: 3

// Explanation:

// No reversal is needed. Take the path 0 → 2 (cost 1), then 2 → 1 (cost 1), then 1 → 3 (cost 1).
// Total cost is 1 + 1 + 1 = 3.
 

// Constraints:

// 2 <= n <= 5 * 10^4
// 1 <= edges.length <= 10^5
// edges[i] = [ui, vi, wi]
// 0 <= ui, vi <= n - 1
// 1 <= wi <= 1000


// Solution:

import java.util.*;

class Solution {

    static class Edge {
        int to, w, next;
        Edge(int to, int w, int next) {
            this.to = to;
            this.w = w;
            this.next = next;
        }
    }

    static class Node {
        int u;
        long d;   // use long to be safe with sums
        Node(long d, int u) {
            this.d = d;
            this.u = u;
        }
    }

    static final int N = 50000;
    static final int MAXE2 = 200000;

    static int[] head = new int[N];
    static Edge[] E = new Edge[MAXE2];
    static int eIdx;

    static long[] dist = new long[N];

    static void newEdge(int u, int v, int w) {
        E[eIdx] = new Edge(v, w, head[u]);
        head[u] = eIdx++;
    }

    static void buildAdj(int n, int[][] edges) {
        eIdx = 0;
        Arrays.fill(head, 0, n, -1);

        for (int[] e : edges) {
            int u = e[0], v = e[1], w = e[2];
            newEdge(u, v, w);
            newEdge(v, u, w << 1);
        }
    }

    public int minCost(int n, int[][] edges) {
        buildAdj(n, edges);

        Arrays.fill(dist, 0, n, Long.MAX_VALUE);

        PriorityQueue<Node> pq = new PriorityQueue<>(
            Comparator.comparingLong(a -> a.d)
        );

        dist[0] = 0;
        pq.offer(new Node(0, 0));

        while (!pq.isEmpty()) {
            Node cur = pq.poll();
            long d = cur.d;
            int u = cur.u;

            if (d > dist[u]) continue;
            if (u == n - 1) return (int) d;

            // traverse adjacency list
            for (int ei = head[u]; ei != -1; ei = E[ei].next) {
                int v = E[ei].to;
                long d2 = d + E[ei].w;

                if (d2 < dist[v]) {
                    dist[v] = d2;
                    pq.offer(new Node(d2, v));
                }
            }
        }
        return -1;
    }
}


