// 3362. Zero Array Transformation III

// You are given an integer array nums of length n and a 2D array queries where queries[i] = [li, ri].

// Each queries[i] represents the following action on nums:

// Decrement the value at each index in the range [li, ri] in nums by at most 1.
// The amount by which the value is decremented can be chosen independently for each index.
// A Zero Array is an array with all its elements equal to 0.

// Return the maximum number of elements that can be removed from queries, such that nums can still be converted to a zero array using the remaining queries. If it is not possible to convert nums to a zero array, return -1.

 

// Example 1:

// Input: nums = [2,0,2], queries = [[0,2],[0,2],[1,1]]

// Output: 1

// Explanation:

// After removing queries[2], nums can still be converted to a zero array.

// Using queries[0], decrement nums[0] and nums[2] by 1 and nums[1] by 0.
// Using queries[1], decrement nums[0] and nums[2] by 1 and nums[1] by 0.
// Example 2:

// Input: nums = [1,1,1,1], queries = [[1,3],[0,2],[1,3],[1,2]]

// Output: 2

// Explanation:

// We can remove queries[2] and queries[3].

// Example 3:

// Input: nums = [1,2,3,4], queries = [[0,3]]

// Output: -1

// Explanation:

// nums cannot be converted to a zero array even after using all the queries.

 

// Constraints:

// 1 <= nums.length <= 10^5
// 0 <= nums[i] <= 10^5
// 1 <= queries.length <= 10^5
// queries[i].length == 2
// 0 <= li <= ri < nums.length

// Solution:

class Solution {
    public:
        int maxRemoval(vector<int>& nums, vector<vector<int>>& queries) {
            // Sort queries by starting index
            sort(queries.begin(), queries.end(), [](const vector<int>& a, const vector<int>& b) {
                return a[0] < b[0];
            });
    
            // Max heap to track queries with latest ending indices
            priority_queue<int> heap;
    
            int n = nums.size();
            vector<int> deltaArray(n + 1, 0); // Difference array to track end of operation effects
            int operations = 0; // Running count of applied operations
            int j = 0; // Index for queries
    
            for (int i = 0; i < n; ++i) {
                operations += deltaArray[i]; // Apply any expired operations ending at i-1
    
                // Push all queries that start at position i
                while (j < queries.size() && queries[j][0] == i) {
                    heap.push(queries[j][1]);
                    ++j;
                }
    
                // While current nums[i] needs more operations, apply from heap
                while (operations < nums[i] && !heap.empty() && heap.top() >= i) {
                    int end = heap.top(); heap.pop();
                    operations++;
                    if (end + 1 < deltaArray.size()) {
                        deltaArray[end + 1] -= 1; // Mark operation expiration
                    }
                }
    
                // If still not enough operations to zero nums[i], it's impossible
                if (operations < nums[i]) return -1;
            }
    
            // Remaining queries in heap were not needed
            return heap.size();
        }
    };
    