// 3161. Block Placement Queries

// There exists an infinite number line, with its origin at 0 and extending towards the positive x-axis.

// You are given a 2D array queries, which contains two types of queries:

// For a query of type 1, queries[i] = [1, x]. Build an obstacle at distance x from the origin. It is guaranteed that there is no obstacle at distance x when the query is asked.
// For a query of type 2, queries[i] = [2, x, sz]. Check if it is possible to place a block of size sz anywhere in the range [0, x] on the line, such that the block entirely lies in the range [0, x]. A block cannot be placed if it intersects with any obstacle, but it may touch it. Note that you do not actually place the block. Queries are separate.
// Return a boolean array results, where results[i] is true if you can place the block specified in the ith query of type 2, and false otherwise.

 

// Example 1:

// Input: queries = [[1,2],[2,3,3],[2,3,1],[2,2,2]]

// Output: [false,true,true]

// Explanation:



// For query 0, place an obstacle at x = 2. A block of size at most 2 can be placed before x = 3.

// Example 2:

// Input: queries = [[1,7],[2,7,6],[1,2],[2,7,5],[2,7,6]]

// Output: [true,true,false]

// Explanation:



// Place an obstacle at x = 7 for query 0. A block of size at most 7 can be placed before x = 7.
// Place an obstacle at x = 2 for query 2. Now, a block of size at most 5 can be placed before x = 7, and a block of size at most 2 before x = 2.
 

// Constraints:

// 1 <= queries.length <= 15 * 10^4
// 2 <= queries[i].length <= 3
// 1 <= queries[i][0] <= 2
// 1 <= x, sz <= min(5 * 10^4, 3 * queries.length)
// The input is generated such that for queries of type 1, no obstacle exists at distance x when the query is asked.
// The input is generated such that there is at least one query of type 2.




// Solution: 



#include <memory_resource>
// 1-based indexed segmented tree defined recursively
static constexpr int N=1<<17; // enough for 5*10^4
int seg[N]; // as global variable

struct segTree {
    unsigned n;
    segTree(vector<int>& A) : n(A.size()) {
        build(A, 1, 0, n-1); 
    }
    void build(vector<int>& A, int idx, int l, int r) {
        if (l==r) {
            seg[idx]=A[l];
            return;
        }
        const int m=(l+r)>>1;
        build(A, 2*idx, l, m);
        build(A, 2*idx+1, m+1, r);
        seg[idx]=max(seg[2*idx], seg[2*idx+1]);
    }

    // Recursive update
    void update(int idx, int l, int r, int pos, int val) {
        if (l==r) {
            seg[idx]=val; // Update the leaf node
            return;
        }
        const int m=(l+r)>>1;
        if (pos<=m) 
            update(2*idx, l, m, pos, val);
        else 
            update(2*idx+1, m+1, r, pos, val);
            
        seg[idx]=max(seg[2*idx], seg[2*idx + 1]);
    }

    // Range Query for [ql, qr]
    int query(int idx, int l, int r, int ql, int qr) {
        if (ql>r || qr<l) return 0;
        if (ql<=l && r <= qr) return seg[idx];
        
        const int m=(l+r)>>1;
        return max(query(2*idx, l, m, ql, qr), query(2*idx+1, m+1, r, ql, qr));
    }
};
// 4MB space for 5*10^4 tree nodes
byte pool[4000000];
class Solution {
public:
    static vector<bool> getResults(vector<vector<int>>&  queries) 
    {
        pmr::monotonic_buffer_resource MEM(pool, sizeof(pool));
        pmr::set<int> obSet(&MEM); 
        obSet.insert(0);//sential

        // Collect all obstacles
        const int qz=queries.size();
        int Sz=qz;
        int M=0;
        for(auto& q: queries){
            const int t=q[0], x=q[1];
            if (t==1){
                Sz--;
                obSet.insert(x);
                M=max(M, x);
            }
        } 
        vector<bool> ans(Sz, 0);      
        // Initialize base array with gaps between all obstacles
        vector<int> A(M+1, 0);
        int prvX=0;
        for (auto it=next(obSet.begin()); it!=obSet.end(); it++) {
            const int x=*it, diff=x-prvX;
            A[x]=diff;
            prvX=x;
        }
        
        // Build the segment tree
        segTree tree(A);
        
        // Process queries in reverse order
        for (int i=qz-1, j=Sz-1; i>=0; i--) {
            const auto& q=queries[i];
            const int t=q[0], x=q[1];
            auto it=obSet.lower_bound(x);
            
            if (t==1) {
                auto nxt=next(it);
                auto prv=prev(it);
                // Update the next obstacle's gap 
                if (nxt!=obSet.end()) {
                    tree.update(1, 0, M, *nxt, *nxt-*prv);
                }
                // removed obstacle
                tree.update(1, 0, M, *it, 0);
                obSet.erase(it);
            }
            else {
                const int sz=q[2];
                auto prv=prev(it);
                // Check trailing space OR the max gap from the Segment Tree
                ans[j--]=(x-*prv>=sz||tree.query(1, 0, M, 0, x)>=sz);
            }
        }
        return ans;
    }
};
auto init = []() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);
    cout.tie(nullptr);
    return 'c';
}();