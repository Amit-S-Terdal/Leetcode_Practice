// 3600. Maximize Spanning Tree Stability with Upgrades

// You are given an integer n, representing n nodes numbered from 0 to n - 1 and a list of edges, where edges[i] = [ui, vi, si, musti]:

// ui and vi indicates an undirected edge between nodes ui and vi.
// si is the strength of the edge.
// musti is an integer (0 or 1). If musti == 1, the edge must be included in the spanning tree. These edges cannot be upgraded.
// You are also given an integer k, the maximum number of upgrades you can perform. Each upgrade doubles the strength of an edge, and each eligible edge (with musti == 0) can be upgraded at most once.

// The stability of a spanning tree is defined as the minimum strength score among all edges included in it.

// Return the maximum possible stability of any valid spanning tree. If it is impossible to connect all nodes, return -1.

// Note: A spanning tree of a graph with n nodes is a subset of the edges that connects all nodes together (i.e. the graph is connected) without forming any cycles, and uses exactly n - 1 edges.

 

// Example 1:

// Input: n = 3, edges = [[0,1,2,1],[1,2,3,0]], k = 1

// Output: 2

// Explanation:

// Edge [0,1] with strength = 2 must be included in the spanning tree.
// Edge [1,2] is optional and can be upgraded from 3 to 6 using one upgrade.
// The resulting spanning tree includes these two edges with strengths 2 and 6.
// The minimum strength in the spanning tree is 2, which is the maximum possible stability.
// Example 2:

// Input: n = 3, edges = [[0,1,4,0],[1,2,3,0],[0,2,1,0]], k = 2

// Output: 6

// Explanation:

// Since all edges are optional and up to k = 2 upgrades are allowed.
// Upgrade edges [0,1] from 4 to 8 and [1,2] from 3 to 6.
// The resulting spanning tree includes these two edges with strengths 8 and 6.
// The minimum strength in the tree is 6, which is the maximum possible stability.
// Example 3:

// Input: n = 3, edges = [[0,1,1,1],[1,2,1,1],[2,0,1,1]], k = 0

// Output: -1

// Explanation:

// All edges are mandatory and form a cycle, which violates the spanning tree property of acyclicity. Thus, the answer is -1.
 

// Constraints:

// 2 <= n <= 10^5
// 1 <= edges.length <= 10^5
// edges[i] = [ui, vi, si, musti]
// 0 <= ui, vi < n
// ui != vi
// 1 <= si <= 10^5
// musti is either 0 or 1.
// 0 <= k <= n
// There are no duplicate edges.


// Solution: 



class DSU {
public:
    vector<int> parent, rankv;
    int components;

    DSU(int n) {
        parent.resize(n);
        rankv.assign(n, 0);
        iota(parent.begin(), parent.end(), 0);
        components = n;
    }

    int find(int x) {
        if (parent[x] != x) parent[x] = find(parent[x]);
        return parent[x];
    }

    bool unite(int a, int b) {
        a = find(a);
        b = find(b);

        if (a == b) return false;

        if (rankv[a] < rankv[b]) swap(a, b);
        parent[b] = a;
        if (rankv[a] == rankv[b]) rankv[a]++;

        components--;
        return true;
    }
};

class Solution {
public:
    bool canAchieve(int n, vector<vector<int>>& edges, int k, int x) {
        DSU dsu(n);

        // 1. Mandatory edges must be included
        for (auto &e : edges) {
            int u = e[0], v = e[1], s = e[2], must = e[3];

            if (must == 1) {
                if (s < x) return false;          // mandatory edge too weak
                if (!dsu.unite(u, v)) return false; // mandatory cycle
            }
        }

        // 2. Use all free optional edges
        for (auto &e : edges) {
            int u = e[0], v = e[1], s = e[2], must = e[3];

            if (must == 0 && s >= x) {
                dsu.unite(u, v);
            }
        }

        // 3. Use upgradeable optional edges if needed
        int usedUpgrades = 0;

        for (auto &e : edges) {
            int u = e[0], v = e[1], s = e[2], must = e[3];

            if (must == 0 && s < x && 2 * s >= x) {
                if (dsu.unite(u, v)) {
                    usedUpgrades++;
                    if (usedUpgrades > k) return false;
                }
            }
        }

        return dsu.components == 1;
    }

    int maxStability(int n, vector<vector<int>>& edges, int k) {
        // Early check: mandatory edges alone must not form a cycle
        {
            DSU dsu(n);
            for (auto &e : edges) {
                if (e[3] == 1) {
                    if (!dsu.unite(e[0], e[1])) return -1;
                }
            }
        }

        int low = 1, high = 200000, ans = -1;

        while (low <= high) {
            int mid = low + (high - low) / 2;

            if (canAchieve(n, edges, k, mid)) {
                ans = mid;
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        return ans;
    }
};