// 3310. Remove Methods From Project

// You are maintaining a project that has n methods numbered from 0 to n - 1.

// You are given two integers n and k, and a 2D integer array invocations, where invocations[i] = [ai, bi] indicates that method ai invokes method bi.

// There is a known bug in method k. Method k, along with any method invoked by it, either directly or indirectly, are considered suspicious and we aim to remove them.

// A group of methods can only be removed if no method outside the group invokes any methods within it.

// Return an array containing all the remaining methods after removing all the suspicious methods. You may return the answer in any order. If it is not possible to remove all the suspicious methods, none should be removed.

 

// Example 1:

// Input: n = 4, k = 1, invocations = [[1,2],[0,1],[3,2]]

// Output: [0,1,2,3]

// Explanation:



// Method 2 and method 1 are suspicious, but they are directly invoked by methods 3 and 0, which are not suspicious. We return all elements without removing anything.

// Example 2:

// Input: n = 5, k = 0, invocations = [[1,2],[0,2],[0,1],[3,4]]

// Output: [3,4]

// Explanation:



// Methods 0, 1, and 2 are suspicious and they are not directly invoked by any other method. We can remove them.

// Example 3:

// Input: n = 3, k = 2, invocations = [[1,2],[0,1],[2,0]]

// Output: []

// Explanation:



// All methods are suspicious. We can remove them.

 

// Constraints:

// 1 <= n <= 10^5
// 0 <= k <= n - 1
// 0 <= invocations.length <= 2 * 10^5
// invocations[i] == [ai, bi]
// 0 <= ai, bi <= n - 1
// ai != bi
// invocations[i] != invocations[j]





// Solution:



constexpr int N=1e5, M=2e5;
struct Edge{ int v, nxt=-1; };
Edge E[M];
int eIdx=0;
int adj[N];// heads of linked lists
inline void addEdge(int u, int v){
    E[eIdx]={v, adj[u]};
    adj[u]=eIdx++;
}
uint8_t vis[N];
int q[N], front, back;
class Solution {
public:
    static inline void bfs(int k){
        front=back=0;// reset q
        q[back++]=k;
        vis[k]=1;
        while(front<back){
            int u=q[front++];
            for(int idx=adj[u]; idx!=-1; idx=E[idx].nxt){
                const int v=E[idx].v;
                if (vis[v]!=0) continue;
                q[back++]=v;
                vis[v]=1;
            }
        }
    }
    static vector<int> remainingMethods(int n, int k, vector<vector<int>>& invocations) {
        eIdx=0;
        memset(adj, -1, n*sizeof(int));
        memset(vis, 0, n);
        for(auto& e : invocations){
            const int u=e[0], v=e[1];
            addEdge(u, v);
        }
        bfs(k);
        bool cnnt1=0;
        for(auto& e : invocations){
            const int u=e[0], v=e[1];
            if ((vis[u]==0) & (vis[v]==1)){
                cnnt1=1;
                break;
            }
        }
        vector<int> ans;
        if (cnnt1){
            ans.resize(n);
            iota(ans.begin(), ans.end(), 0);
            return ans;
        }
        ans.reserve(n);
        for(int i=0; i<n; i++){
            if (vis[i]==0) ans.push_back(i);
        }
        return ans;
    }
};