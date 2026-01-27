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



struct Edge { int to, w, next; };

struct Node {
    unsigned d;
    int u;
    bool operator>(const Node& other) const {
        return d>other.d;
    }
};

const int N=50000, MAXE2=200000;

int head[N];
Edge E[MAXE2];// POOL for edges
int eIdx;

unsigned dist[N];

static inline void newEdge(int u, int v, int w) {
    E[eIdx]={v, w, head[u]};
    head[u]=eIdx++;
}

static inline void build_adj(int n, vector<vector<int>>& edges) {
    eIdx=0;
    memset(head, -1, sizeof(int)*n);

    for (auto& e:edges) {
        const int u=e[0], v=e[1], w=e[2];
        newEdge(u, v, w);
        newEdge(v, u, w<<1);
    }
}

class Solution {
public:
    static int minCost(int n, vector<vector<int>>& edges) {
        build_adj(n, edges);

        memset(dist, 255, sizeof(unsigned)*n);

        priority_queue<Node, vector<Node>, greater<Node>> pq;

        dist[0]=0;
        pq.emplace(0, 0);

        while (!pq.empty()) {
            auto [d, u]=pq.top();
            pq.pop();

            if (d>dist[u]) continue;
            if (u==n-1) return d;
            
            // trasverse all adj vertices of u
            for (int ei=head[u]; ei!=-1; ei=E[ei].next) {
                int v=E[ei].to;
                unsigned d2=d+E[ei].w;

                if (d2<dist[v]) {
                    dist[v]=d2;
                    pq.emplace(d2, v);
                }
            }
        }
        return -1;
    }
};