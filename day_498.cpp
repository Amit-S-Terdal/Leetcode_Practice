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



constexpr int N=1e5;
constexpr int LOG=17; // because 2^17 > 1e5

static int q[N], front=0, back=0; // custom queue
static int adj[N+1];              // array linked list heads
struct Edge {
    int to=-1, nxt=-1;
};
static Edge E[2*N]; // undirected edges capacity
static int eCnt=0;  // count E

static inline void addEdge(int u, int v) {
    E[eCnt]={v, adj[u]};
    adj[u]=eCnt++;
}
// 1-indexed
// up[i][j] stores the (2^j)-th ancestor of node i
static int level[N+1], parent[N+1], up[N+1][LOG];
static constexpr int mod=1e9+7;

class Solution {
public:
    static long long modPow(long long x, int exp) {
        long long y=1; 
        for (; exp; exp>>=1) {
            if (exp & 1) y=y*x%mod;
            x=x*x%mod;
        }
        return y;
    }

    static long long pow2(int x) {
        if (x<30) return 1<<x;
        long long B=(1<<30) % mod;
        auto [qq, r]=div(x, 30);
        return modPow(B, qq)*pow2(r)%mod;
    }

    static int lca(int u, int v) {
        if (level[u]<level[v]) swap(u, v);
        int diff=level[u]-level[v];
        // Equalize levels using ctz
        for(; diff; diff&=(diff-1)) {
            const int i=__builtin_ctz(diff);
            u=up[u][i];
        }
        if (u==v) return u;
        // Lift simultaneously
        for (int i=LOG-1; i>=0; i--) {
            if (up[u][i]!=up[v][i]) {
                u=up[u][i];
                v=up[v][i];
            }
        }
        return up[u][0];
    }

    static inline int distance(int u, int v) {
        int a=lca(u, v);
        return level[u]+level[v]-2*level[a];
    }

    vector<int> assignEdgeWeights(vector<vector<int>>& edges, vector<vector<int>>& queries) {
        const int n=edges.size()+1;  
        // reset
        memset(adj, -1, sizeof(int)*(n+1));
        memset(level, 0, sizeof(int)*(n+1));
        memset(parent, 0, sizeof(int)*(n+1));
        eCnt=0;
        
        for (auto& e : edges) {
            int u=e[0], v=e[1];
            addEdge(u, v);
            addEdge(v, u);
        }

        // BFS traversal
        static bitset<N+1> viz;
        viz.reset();
        front=back=0;
        q[back++]=1;
        viz[1]=1;
        parent[1]=0; // Root's parent points to dummy node 0
        level[1]=1;  // Root starts at level 1
        while (front<back) {
            int u=q[front++];
            for (int e=adj[u]; e!=-1; e=E[e].nxt) {
                int v=E[e].to;
                if (!viz[v]) {
                    viz[v]=1;
                    parent[v]=u;
                    level[v]=level[u]+1;
                    q[back++]=v;
                }
            }
        }

        // Build binary lifting DP table up to index n
        for (int i=1; i<=n; i++) 
            up[i][0]=parent[i];
        
        
        for (int j=1; j<LOG; j++) {
            for (int i=0; i<=n; i++) 
                up[i][j]=up[up[i][j-1]][j-1];
        }

        const int m=queries.size();
        vector<int> ans(m);
        for (int i=0; i<m; i++) {
            int u=queries[i][0];
            int v=queries[i][1];
            int d=distance(u, v);
            ans[i]=(d>0)?pow2(d-1):0;
        }
        return ans;
    }
};