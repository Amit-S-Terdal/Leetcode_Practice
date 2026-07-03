// 3620. Network Recovery Pathways

// You are given a directed acyclic graph of n nodes numbered from 0 to n − 1. This is represented by a 2D array edges of length m, where edges[i] = [ui, vi, costi] indicates a one‑way communication from node ui to node vi with a recovery cost of costi.

// Some nodes may be offline. You are given a boolean array online where online[i] = true means node i is online. Nodes 0 and n − 1 are always online.

// A path from 0 to n − 1 is valid if:

// All intermediate nodes on the path are online.
// The total recovery cost of all edges on the path does not exceed k.
// For each valid path, define its score as the minimum edge‑cost along that path.

// Return the maximum path score (i.e., the largest minimum-edge cost) among all valid paths. If no valid path exists, return -1.

 

// Example 1:

// Input: edges = [[0,1,5],[1,3,10],[0,2,3],[2,3,4]], online = [true,true,true,true], k = 10

// Output: 3

// Explanation:



// The graph has two possible routes from node 0 to node 3:

// Path 0 → 1 → 3

// Total cost = 5 + 10 = 15, which exceeds k (15 > 10), so this path is invalid.

// Path 0 → 2 → 3

// Total cost = 3 + 4 = 7 <= k, so this path is valid.

// The minimum edge‐cost along this path is min(3, 4) = 3.

// There are no other valid paths. Hence, the maximum among all valid path‐scores is 3.

// Example 2:

// Input: edges = [[0,1,7],[1,4,5],[0,2,6],[2,3,6],[3,4,2],[2,4,6]], online = [true,true,true,false,true], k = 12

// Output: 6

// Explanation:



// Node 3 is offline, so any path passing through 3 is invalid.

// Consider the remaining routes from 0 to 4:

// Path 0 → 1 → 4

// Total cost = 7 + 5 = 12 <= k, so this path is valid.

// The minimum edge‐cost along this path is min(7, 5) = 5.

// Path 0 → 2 → 3 → 4

// Node 3 is offline, so this path is invalid regardless of cost.

// Path 0 → 2 → 4

// Total cost = 6 + 6 = 12 <= k, so this path is valid.

// The minimum edge‐cost along this path is min(6, 6) = 6.

// Among the two valid paths, their scores are 5 and 6. Therefore, the answer is 6.

 

// Constraints:

// n == online.length
// 2 <= n <= 5 * 10^4
// 0 <= m == edges.length <= min(10^5, n * (n - 1) / 2)
// edges[i] = [ui, vi, costi]
// 0 <= ui, vi < n
// ui != vi
// 0 <= costi <= 10^9
// 0 <= k <= 5 * 10^13
// online[i] is either true or false, and both online[0] and online[n − 1] are true.
// The given graph is a directed acyclic graph.





// Solution:




constexpr int N=5e4, M=1e5;
struct Edge{ int v, w, nxt=-1; };
Edge E[M];
int eIdx=0;
int adj[N];// heads for linked lists
inline void addEdge(int u, int v, int w){
    E[eIdx]={v, w, adj[u]};
    adj[u]=eIdx++;
}
using ll=long long;
using int2=pair<ll, int>;
ll dist[N];
int pathMin[N]; //Tracks minimum edge weight along the path to i

class Solution {
public:
    // return the min edge cost
    static int Dijkstra(int minW, int n, long long k){
        fill_n(dist, n, LLONG_MAX);
        memset(pathMin, 0, n*sizeof(int));
        
        priority_queue<int2, vector<int2>, greater<int2>> pq;
        pq.emplace(0, 0);
        dist[0]=0;
        pathMin[0]=INT_MAX; // Base case for the starting node
        
        while(!pq.empty()){
            auto [d, u]=pq.top();
            pq.pop();
            
            if (d>dist[u]) continue; 
            if (d>k) return -1;       // Cost limit exceeded
            if (u==n-1) return pathMin[u]; //destination
            
            for(int idx=adj[u]; idx!=-1; idx=E[idx].nxt){
                const int v=E[idx].v, w=E[idx].w;
                if (w<minW) continue;
                
                ll d2=d+w;
                int curMin=min(pathMin[u], w);
                
                if (d2<dist[v]){
                    dist[v]=d2;
                    pathMin[v]=curMin;
                    pq.emplace(d2, v);
                } 
                else if (d2==dist[v] && curMin>pathMin[v]) {
                    pathMin[v]=curMin;
                    pq.emplace(d2, v);
                }
            }
        }
        return -1; // No path
    }

    static int findMaxPathScore(vector<vector<int>>& edges, vector<bool>& online, long long k) {
        const int n=online.size();
        fill_n(adj, n, -1); 
        eIdx=0;
        
        int mnC=INT_MAX, mxC=-1;
        for(const auto& e: edges){
            const int u=e[0], v=e[1], w=e[2];
            if (online[u] && online[v]) {
                mnC=min(mnC, w);
                mxC=max(mxC, w);
                addEdge(u, v, w);
            }
        }
        
        int l=mnC, r=mxC, ans=-1;
        while(l<=r){
            int mid=l+(r-l)/2;
            int aMin=Dijkstra(mid, n, k);
            
            if (aMin!=-1){
                ans=max(ans, aMin); 
                l=aMin+1; 
            }
            else 
                r=mid-1; 
        }
        return ans;
    }
};

auto init = []() {
    ios::sync_with_stdio(0);
    cin.tie(0);
    cout.tie(0);
    return 'c';
}();