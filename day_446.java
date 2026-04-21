// 1722. Minimize Hamming Distance After Swap Operations

// You are given two integer arrays, source and target, both of length n. You are also given an array allowedSwaps where each allowedSwaps[i] = [ai, bi] indicates that you are allowed to swap the elements at index ai and index bi (0-indexed) of array source. Note that you can swap elements at a specific pair of indices multiple times and in any order.

// The Hamming distance of two arrays of the same length, source and target, is the number of positions where the elements are different. Formally, it is the number of indices i for 0 <= i <= n-1 where source[i] != target[i] (0-indexed).

// Return the minimum Hamming distance of source and target after performing any amount of swap operations on array source.

 

// Example 1:

// Input: source = [1,2,3,4], target = [2,1,4,5], allowedSwaps = [[0,1],[2,3]]
// Output: 1
// Explanation: source can be transformed the following way:
// - Swap indices 0 and 1: source = [2,1,3,4]
// - Swap indices 2 and 3: source = [2,1,4,3]
// The Hamming distance of source and target is 1 as they differ in 1 position: index 3.
// Example 2:

// Input: source = [1,2,3,4], target = [1,3,2,4], allowedSwaps = []
// Output: 2
// Explanation: There are no allowed swaps.
// The Hamming distance of source and target is 2 as they differ in 2 positions: index 1 and index 2.
// Example 3:

// Input: source = [5,1,2,4,3], target = [1,5,4,2,3], allowedSwaps = [[0,4],[4,2],[1,3],[1,4]]
// Output: 0
 

// Constraints:

// n == source.length == target.length
// 1 <= n <= 10^5
// 1 <= source[i], target[i] <= 10^5
// 0 <= allowedSwaps.length <= 10^5
// allowedSwaps[i].length == 2
// 0 <= ai, bi <= n - 1
// ai != bi



// Solution:



import java.util.*;

class Solution {
    static class UnionFind {
        int[] parent, rank;

        UnionFind(int n) {
            parent = new int[n];
            rank = new int[n];
            for (int i = 0; i < n; i++) parent[i] = i;
        }

        int find(int x) {
            if (x != parent[x])
                parent[x] = find(parent[x]); // path compression
            return parent[x];
        }

        boolean union(int x, int y) {
            int px = find(x), py = find(y);
            if (px == py) return false;

            if (rank[px] > rank[py]) {
                parent[py] = px;
            } else if (rank[px] < rank[py]) {
                parent[px] = py;
            } else {
                parent[px] = py;
                rank[py]++;
            }
            return true;
        }
    }

    public int minimumHammingDistance(int[] source, int[] target, int[][] allowedSwaps) {
        int n = source.length;
        UnionFind uf = new UnionFind(n);

        // Build unions
        for (int[] sw : allowedSwaps) {
            uf.union(sw[0], sw[1]);
        }

        // Group indices by root
        Map<Integer, List<Integer>> components = new HashMap<>();
        for (int i = 0; i < n; i++) {
            int root = uf.find(i);
            components.computeIfAbsent(root, k -> new ArrayList<>()).add(i);
        }

        int match = 0;

        // Process each component
        for (List<Integer> comp : components.values()) {
            Map<Integer, Integer> freq = new HashMap<>();

            // Count frequencies in source
            for (int idx : comp) {
                freq.put(source[idx], freq.getOrDefault(source[idx], 0) + 1);
            }

            // Match with target
            for (int idx : comp) {
                int val = target[idx];
                if (freq.getOrDefault(val, 0) > 0) {
                    freq.put(val, freq.get(val) - 1);
                    match++;
                }
            }
        }

        return n - match;
    }
}