// 2092. Find All People With Secret

// You are given an integer n indicating there are n people numbered from 0 to n - 1. You are also given a 0-indexed 2D integer array meetings where meetings[i] = [xi, yi, timei] indicates that person xi and person yi have a meeting at timei. A person may attend multiple meetings at the same time. Finally, you are given an integer firstPerson.

// Person 0 has a secret and initially shares the secret with a person firstPerson at time 0. This secret is then shared every time a meeting takes place with a person that has the secret. More formally, for every meeting, if a person xi has the secret at timei, then they will share the secret with person yi, and vice versa.

// The secrets are shared instantaneously. That is, a person may receive the secret and share it with people in other meetings within the same time frame.

// Return a list of all the people that have the secret after all the meetings have taken place. You may return the answer in any order.

 

// Example 1:

// Input: n = 6, meetings = [[1,2,5],[2,3,8],[1,5,10]], firstPerson = 1
// Output: [0,1,2,3,5]
// Explanation:
// At time 0, person 0 shares the secret with person 1.
// At time 5, person 1 shares the secret with person 2.
// At time 8, person 2 shares the secret with person 3.
// At time 10, person 1 shares the secret with person 5.​​​​
// Thus, people 0, 1, 2, 3, and 5 know the secret after all the meetings.
// Example 2:

// Input: n = 4, meetings = [[3,1,3],[1,2,2],[0,3,3]], firstPerson = 3
// Output: [0,1,3]
// Explanation:
// At time 0, person 0 shares the secret with person 3.
// At time 2, neither person 1 nor person 2 know the secret.
// At time 3, person 3 shares the secret with person 0 and person 1.
// Thus, people 0, 1, and 3 know the secret after all the meetings.
// Example 3:

// Input: n = 5, meetings = [[3,4,2],[1,2,1],[2,3,1]], firstPerson = 1
// Output: [0,1,2,3,4]
// Explanation:
// At time 0, person 0 shares the secret with person 1.
// At time 1, person 1 shares the secret with person 2, and person 2 shares the secret with person 3.
// Note that person 2 can share the secret at the same time as receiving it.
// At time 2, person 3 shares the secret with person 4.
// Thus, people 0, 1, 2, 3, and 4 know the secret after all the meetings.
 

// Constraints:

// 2 <= n <= 10^5
// 1 <= meetings.length <= 10^5
// meetings[i].length == 3
// 0 <= xi, yi <= n - 1
// xi != yi
// 1 <= timei <= 10^5
// 1 <= firstPerson <= n - 1



// Solution: 



//UnionFind modified from my old code https://leetcode.com/problems/find-critical-and-pseudo-critical-edges-in-minimum-spanning-tree/solutions/3930534/c-kruskal-s-algorithm-beats-95-26-math/
class UnionFind {    
    vector<int> root, rank;
public:
    UnionFind(int n) : root(n), rank(n) {
        rank.assign(n, 1);
        iota(root.begin(), root.end(), 0);
    }

    int Find(int x) {
        return (x == root[x])?x: root[x]=Find(root[x]);
    }

    void Union(int x, int y) {
        int rX = Find(x), rY = Find(y);
        if (rX == rY)  return;
        if (rank[rX] > rank[rY]) swap(rX, rY);   
        root[rX] = rY;
        if (rank[rX]==rank[rY]) rank[rY]++;
    }
    bool connected(int x, int y) {
        return Find(x) == Find(y);
    }

    void reset(int x){//very useful for removing edges
        root[x]=x;
        rank[x]=1;
    }
};

class Solution {
public:
    using int2=pair<int, int>;
    vector<int> findAllPeople(int n, vector<vector<int>>& meetings, int firstPerson)
    {
        //1 <= time_i <= 10^5, Counting sort is suitable
        vector<int2> meet_time[100001];//meet_time[t] = {(x,y) has meeting at t}
        int tMax=-1;
        for(auto& meet: meetings){
            int x=meet[0], y=meet[1], t=meet[2];
            meet_time[t].emplace_back(x, y);// 1 pair is enough
           // meet_time[t].emplace_back(y, x);
            tMax=max(tMax, t);
        }
        UnionFind uf(n);
        uf.Union(0, firstPerson);// add edge (0, firstPerson)
        for (int t=0; t<=tMax; t++){
            for(auto& [x, y]: meet_time[t])
                uf.Union(x, y);
            for(auto& [x, y]: meet_time[t]){//Important part
                if (!uf.connected(0, x)){
                    //If x is not connected to 0, remove the edge
                    uf.reset(x);
                    uf.reset(y);
                }
            }
        }
        vector<int> list={0};
        for(int i=1; i<n; i++)
            if (uf.connected(0, i)) list.push_back(i);

        return list;
    }
};


auto init = []() {
    ios::sync_with_stdio(0);
    cin.tie(0);
    cout.tie(0);
    return 'c';
}();