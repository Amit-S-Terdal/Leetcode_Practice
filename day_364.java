// 2976. Minimum Cost to Convert String I

// You are given two 0-indexed strings source and target, both of length n and consisting of lowercase English letters. You are also given two 0-indexed character arrays original and changed, and an integer array cost, where cost[i] represents the cost of changing the character original[i] to the character changed[i].

// You start with the string source. In one operation, you can pick a character x from the string and change it to the character y at a cost of z if there exists any index j such that cost[j] == z, original[j] == x, and changed[j] == y.

// Return the minimum cost to convert the string source to the string target using any number of operations. If it is impossible to convert source to target, return -1.

// Note that there may exist indices i, j such that original[j] == original[i] and changed[j] == changed[i].

 

// Example 1:

// Input: source = "abcd", target = "acbe", original = ["a","b","c","c","e","d"], changed = ["b","c","b","e","b","e"], cost = [2,5,5,1,2,20]
// Output: 28
// Explanation: To convert the string "abcd" to string "acbe":
// - Change value at index 1 from 'b' to 'c' at a cost of 5.
// - Change value at index 2 from 'c' to 'e' at a cost of 1.
// - Change value at index 2 from 'e' to 'b' at a cost of 2.
// - Change value at index 3 from 'd' to 'e' at a cost of 20.
// The total cost incurred is 5 + 1 + 2 + 20 = 28.
// It can be shown that this is the minimum possible cost.
// Example 2:

// Input: source = "aaaa", target = "bbbb", original = ["a","c"], changed = ["c","b"], cost = [1,2]
// Output: 12
// Explanation: To change the character 'a' to 'b' change the character 'a' to 'c' at a cost of 1, followed by changing the character 'c' to 'b' at a cost of 2, for a total cost of 1 + 2 = 3. To change all occurrences of 'a' to 'b', a total cost of 3 * 4 = 12 is incurred.
// Example 3:

// Input: source = "abcd", target = "abce", original = ["a"], changed = ["e"], cost = [10000]
// Output: -1
// Explanation: It is impossible to convert source to target because the value at index 3 cannot be changed from 'd' to 'e'.
 

// Constraints:

// 1 <= source.length == target.length <= 10^5
// source, target consist of lowercase English letters.
// 1 <= cost.length == original.length == changed.length <= 2000
// original[i], changed[i] are lowercase English letters.
// 1 <= cost[i] <= 10^6
// original[i] != changed[i]



// Solution: 


import java.util.*;

class Solution {

    static class Pair {
        int dist, node;
        Pair(int d, int n) {
            dist = d;
            node = n;
        }
    }

    List<Pair>[] adj = new ArrayList[26];
    int[][] D = new int[26][26];
    static final int INF = Integer.MAX_VALUE;

    void dijkstra(int x) {
        int[] dist = D[x];
        PriorityQueue<Pair> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a.dist));

        // initialize with all neighbors of x
        for (Pair p : adj[x]) {
            pq.offer(new Pair(p.dist, p.node));
        }

        while (!pq.isEmpty()) {
            Pair cur = pq.poll();
            int d = cur.dist;
            int i = cur.node;

            if (d > dist[i]) continue;

            for (Pair nxt : adj[i]) {
                int d2 = d + nxt.dist;
                if (d2 < dist[nxt.node]) {
                    dist[nxt.node] = d2;
                    pq.offer(new Pair(d2, nxt.node));
                }
            }
        }
    }

    public long minimumCost(String source, String target,
                            char[] original, char[] changed, int[] cost) {

        // init adjacency lists
        for (int i = 0; i < 26; i++) {
            adj[i] = new ArrayList<>();
            Arrays.fill(D[i], INF);
            D[i][i] = 0;
        }

        // direct transformation costs
        for (int i = 0; i < original.length; i++) {
            int u = original[i] - 'a';
            int v = changed[i] - 'a';
            D[u][v] = Math.min(D[u][v], cost[i]);
        }

        // build graph
        for (int i = 0; i < 26; i++) {
            for (int j = 0; j < 26; j++) {
                if (i != j && D[i][j] != INF) {
                    adj[i].add(new Pair(D[i][j], j));
                }
            }
        }

        // run dijkstra from each character
        for (int i = 0; i < 26; i++) {
            dijkstra(i);
        }

        // compute answer
        long ans = 0;
        for (int i = 0; i < source.length(); i++) {
            int u = source.charAt(i) - 'a';
            int v = target.charAt(i) - 'a';
            if (D[u][v] == INF) return -1;
            ans += D[u][v];
        }

        return ans;
    }
}
