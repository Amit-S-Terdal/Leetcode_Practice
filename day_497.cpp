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




static constexpr int N=1e5;
static int q[N], front=0, back=0;// queue q
static int adj[N+1]={[1 ... N]=-1};// array linked list
struct Edge{
    int to=-1, nxt=-1;
};
Edge E[2*N];
int eCnt=0;
static inline void addEdge(int u, int v){
    E[eCnt]={v, adj[u]};
    adj[u]=eCnt++;
}
static const int mod=1e9+7;
class Solution {
public:
    static long long modPow(long long x, int exp) {
        long long y=1; 
        for(; exp; exp>>=1){
            y=(exp&1)?y*x%mod:y;
            x=x*x%mod;
        }
        return y;
    }
    static long long pow2(int x) {
        if (x < 30)
            return 1<<x;
        static constexpr long long B=(1<<30)%mod;
        auto [qq, r] = div(x, 30);
        return modPow(B, qq)*pow2(r)%mod;
    }
    static int assignEdgeWeights(vector<vector<int>>& edges) {
        const int n=edges.size()+1;
        memset(adj+1, -1, sizeof(int)*n);
        eCnt=0;
        for (auto& e : edges) {
            int u=e[0], v=e[1];
            addEdge(u, v);
            addEdge(v, u);
        }
        front=back=0;// q reset
        bitset<N+1> viz=0;
        q[back++]=1;
        viz[1]=1;
        int depth=-1;
        for(; front<back; depth++){
            int qz=back-front;
            while(qz--){
                int u=q[front++];
                viz[u]=1;
                for(int e=adj[u]; e!=-1; e=E[e].nxt) {
                    int v=E[e].to;
                    if (viz[v]) continue;
                    viz[v]=1;
                    q[back++]=v;
                }
            }
        }
        return pow2(depth-1);
    }
};