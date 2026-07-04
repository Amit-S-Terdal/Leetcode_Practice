// 2492. Minimum Score of a Path Between Two Cities

// You are given a positive integer n representing n cities numbered from 1 to n. You are also given a 2D array roads where roads[i] = [ai, bi, distancei] indicates that there is a bidirectional road between cities ai and bi with a distance equal to distancei. The cities graph is not necessarily connected.

// The score of a path between two cities is defined as the minimum distance of a road in this path.

// Return the minimum possible score of a path between cities 1 and n.

// Note:

// A path is a sequence of roads between two cities.
// It is allowed for a path to contain the same road multiple times, and you can visit cities 1 and n multiple times along the path.
// The test cases are generated such that there is at least one path between 1 and n.
 

// Example 1:


// Input: n = 4, roads = [[1,2,9],[2,3,6],[2,4,5],[1,4,7]]
// Output: 5
// Explanation: The path from city 1 to 4 with the minimum score is: 1 -> 2 -> 4. The score of this path is min(9,5) = 5.
// It can be shown that no other path has less score.
// Example 2:


// Input: n = 4, roads = [[1,2,2],[1,3,4],[3,4,7]]
// Output: 2
// Explanation: The path from city 1 to 4 with the minimum score is: 1 -> 2 -> 1 -> 3 -> 4. The score of this path is min(2,2,4,7) = 2.
 

// Constraints:

// 2 <= n <= 10T5
// 1 <= roads.length <= 10^5
// roads[i].length == 3
// 1 <= ai, bi <= n
// ai != bi
// 1 <= distancei <= 10^4
// There are no repeated edges.
// There is at least one path between 1 and n.




// Solution:




constexpr int N=1e5+1;
struct Edge{int v, w, nxt=-1;};
Edge E[N*2];// undirected
int eIdx=0;
int adj[N];
inline void addEdge(int u, int v, int w){
    E[eIdx]={v, w, adj[u]};
    adj[u]=eIdx++;
}
bitset<N> vis;
class Solution {
public:
    static void dfs(int i,  int& dist) {
        vis[i]=1;
        for (int idx=adj[i]; idx!=-1; idx=E[idx].nxt) {
            const int v=E[idx].v, w=E[idx].w;
            dist= min(dist, w);
            if (!vis[v]) 
                dfs(v, dist);
        }
    }
    static void adjacent_ini(int n, vector<vector<int>>& roads){
        eIdx=0;
        memset(adj+1, -1, sizeof(int)*n);
        for (auto& path : roads) {
            const int u=path[0] , v=path[1], w=path[2];
            addEdge(u, v, w);
            addEdge(v, u, w);
        }
    }

    static int minScore(int n, vector<vector<int>>& roads)
    {      
        adjacent_ini(n, roads);
        vis.reset();
        int dist=INT_MAX;
        dfs(1, dist);

        return dist;
    }
};