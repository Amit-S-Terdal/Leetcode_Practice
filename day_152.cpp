// 2099. Find Subsequence of Length K With the Largest Sum

// You are given an integer array nums and an integer k. You want to find a subsequence of nums of length k that has the largest sum.

// Return any such subsequence as an integer array of length k.

// A subsequence is an array that can be derived from another array by deleting some or no elements without changing the order of the remaining elements.

 

// Example 1:

// Input: nums = [2,1,3,3], k = 2
// Output: [3,3]
// Explanation:
// The subsequence has the largest sum of 3 + 3 = 6.
// Example 2:

// Input: nums = [-1,-2,3,4], k = 3
// Output: [-1,3,4]
// Explanation: 
// The subsequence has the largest sum of -1 + 3 + 4 = 6.
// Example 3:

// Input: nums = [3,4,3,3], k = 2
// Output: [3,4]
// Explanation:
// The subsequence has the largest sum of 3 + 4 = 7. 
// Another possible subsequence is [4, 3].
 

// Constraints:

// 1 <= nums.length <= 1000
// -105 <= nums[i] <= 10^5
// 1 <= k <= nums.length


// Solution:

class Solution {
    public:
        using int2=pair<int, int>;
        vector<int> maxSubsequence(vector<int>& nums, int k) {
            int n=nums.size();
            vector<int2> nidx(n);
            for(int i=0; i<n; i++)
                nidx[i]={nums[i], i};
            nth_element(nidx.begin(), nidx.begin()+k, nidx.end(), greater<int2>());
            sort(nidx.begin(), nidx.begin()+k, [](int2& x, int2& y){ return x.second<y.second;});
            vector<int> ans(k);
            for(int i=0; i<k; i++) ans[i]=nidx[i].first;
            return ans;
        }
    };
    
    auto init = []() {
        ios::sync_with_stdio(0);
        cin.tie(0);
        cout.tie(0);
        return 'c';
    }();