// 3346. Maximum Frequency of an Element After Performing Operations I

// You are given an integer array nums and two integers k and numOperations.

// You must perform an operation numOperations times on nums, where in each operation you:

// Select an index i that was not selected in any previous operations.
// Add an integer in the range [-k, k] to nums[i].
// Return the maximum possible frequency of any element in nums after performing the operations.

 

// Example 1:

// Input: nums = [1,4,5], k = 1, numOperations = 2

// Output: 2

// Explanation:

// We can achieve a maximum frequency of two by:

// Adding 0 to nums[1]. nums becomes [1, 4, 5].
// Adding -1 to nums[2]. nums becomes [1, 4, 4].
// Example 2:

// Input: nums = [5,11,20,20], k = 5, numOperations = 1

// Output: 2

// Explanation:

// We can achieve a maximum frequency of two by:

// Adding 0 to nums[1].
 

// Constraints:

// 1 <= nums.length <= 10^5
// 1 <= nums[i] <= 10^5
// 0 <= k <= 10^5
// 0 <= numOperations <= nums.length



// Solution: 


class Solution {
public:
    static int maxFrequency(vector<int>& nums, int k, int numOperations) {
        static constexpr int N=1e5+1;
        int freq[N]={0}, MM=0;
        for(int x: nums){
            freq[x]++;
            MM=max(MM, x);
        }
        int* prefix=(int*)alloca((MM+1)*sizeof(int));
        partial_sum(freq, freq+MM+1, prefix);
        int ans=0, cnt=0;
        for (int x=1; x<=MM; x++){
            int l=max(1,x-k), r=min(MM, x+k);
            cnt=prefix[r]-prefix[l-1];
            ans=max(ans, freq[x]+min(cnt-freq[x], numOperations));
        }
        return ans;
    }
};