// 3488. Closest Equal Element Queries

// You are given a circular array nums and an array queries.

// For each query i, you have to find the following:

// The minimum distance between the element at index queries[i] and any other index j in the circular array, where nums[j] == nums[queries[i]]. If no such index exists, the answer for that query should be -1.
// Return an array answer of the same size as queries, where answer[i] represents the result for query i.

 

// Example 1:

// Input: nums = [1,3,1,4,1,3,2], queries = [0,3,5]

// Output: [2,-1,3]

// Explanation:

// Query 0: The element at queries[0] = 0 is nums[0] = 1. The nearest index with the same value is 2, and the distance between them is 2.
// Query 1: The element at queries[1] = 3 is nums[3] = 4. No other index contains 4, so the result is -1.
// Query 2: The element at queries[2] = 5 is nums[5] = 3. The nearest index with the same value is 1, and the distance between them is 3 (following the circular path: 5 -> 6 -> 0 -> 1).
// Example 2:

// Input: nums = [1,2,3,4], queries = [0,1,2,3]

// Output: [-1,-1,-1,-1]

// Explanation:

// Each value in nums is unique, so no index shares the same value as the queried element. This results in -1 for all queries.

 

// Constraints:

// 1 <= queries.length <= nums.length <= 10^5
// 1 <= nums[i] <= 10^6
// 0 <= queries[i] < nums.length




// Solution : 



#include <memory_resource>
pmr::unsynchronized_pool_resource pool;
class Solution {
public:
    static vector<int> solveQueries(vector<int>& nums, vector<int>& queries) {
        const int n=nums.size(), qz=queries.size();
        vector<int> ans(qz, -1);
        
        // Store the indices
        pmr::unordered_map<int, vector<int>> x2i(&pool);
        x2i.reserve(n);   
        for (int i=0; i<n; i++) 
            x2i[nums[i]].push_back(i);
    
        // Process each query
        for (int i=0; i<qz; i++) {
            const int q=queries[i], x=nums[q];
            auto& idx=x2i[x];
            const int sz=idx.size();

            if (sz==1) continue;

            // Find the position of index q 
            auto i0=lower_bound(idx.begin(), idx.end(), q)-idx.begin();

            int d=n-1, j0=idx[i0];  // Maximum distance

            // Check next index 
            if (i0+1!=sz) d=min(d, idx[i0+1]-j0);
            else d=min(d, n+idx[0]-j0);  // Wrap around to the first

            // Check previous index (circularly)
            if (i0!=0) d=min(d, j0-idx[i0-1]);
            else d=min(d, n+j0-idx.back());  // Wrap around to the last
            ans[i]=d;
        }
        return ans;
    }
};
