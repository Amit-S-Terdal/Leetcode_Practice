// 3640. Trionic Array II

// You are given an integer array nums of length n.

// A trionic subarray is a contiguous subarray nums[l...r] (with 0 <= l < r < n) for which there exist indices l < p < q < r such that:

// nums[l...p] is strictly increasing,
// nums[p...q] is strictly decreasing,
// nums[q...r] is strictly increasing.
// Return the maximum sum of any trionic subarray in nums.

 

// Example 1:

// Input: nums = [0,-2,-1,-3,0,2,-1]

// Output: -4

// Explanation:

// Pick l = 1, p = 2, q = 3, r = 5:

// nums[l...p] = nums[1...2] = [-2, -1] is strictly increasing (-2 < -1).
// nums[p...q] = nums[2...3] = [-1, -3] is strictly decreasing (-1 > -3)
// nums[q...r] = nums[3...5] = [-3, 0, 2] is strictly increasing (-3 < 0 < 2).
// Sum = (-2) + (-1) + (-3) + 0 + 2 = -4.
// Example 2:

// Input: nums = [1,4,2,7]

// Output: 14

// Explanation:

// Pick l = 0, p = 1, q = 2, r = 3:

// nums[l...p] = nums[0...1] = [1, 4] is strictly increasing (1 < 4).
// nums[p...q] = nums[1...2] = [4, 2] is strictly decreasing (4 > 2).
// nums[q...r] = nums[2...3] = [2, 7] is strictly increasing (2 < 7).
// Sum = 1 + 4 + 2 + 7 = 14.
 

// Constraints:

// 4 <= n = nums.length <= 10^5
// -10^9 <= nums[i] <= 10^9
// It is guaranteed that at least one trionic subarray exists.




// Solution:




class Solution {
public:
    static long long maxSumTrionic(vector<int>& nums) {
        const int n=nums.size();
        //guaranteed that at least one trionic subarray exists.
        //if (n==4) return accumulate(nums.begin(), nums.end(), 0LL);

        long long ans=-1e15;
        for (int i=0, l=0, p=0, q=0, r=0; i<n-1; i+=i==l) {
            // Find start of first uphill
            while (i<n-1 && nums[i]>=nums[i+1]) i++;
            l=i;

            long long Sum=0;
            //Leg 1: Uphill
            while (i<n-1 && nums[i]<nums[i+1]) {
                Sum+=(nums[i]>0)?nums[i]:0;
                i++;
            }
            p=i;
            if (p==l||(p+1<n && nums[p]==nums[p+1])) 
                continue; 
            
            // nums[p-1] must be added to Sum
            Sum+=(nums[p-1]<0)?nums[p-1]:0;

            // Leg 2: Downhill
            while (i<n-1 && nums[i]>nums[i+1]) {
                Sum+=nums[i];// each one must be added to Sum
                i++;
            }
            q=i; 
            if (p==q||(q+1<n && nums[q]==nums[q+1])) 
                continue;

            // Leg 3: 2nd Uphill
            Sum+=nums[q]; // Valley is mandatory
            long long incr=0, maxIncr=INT_MIN;
            
            while (i<n-1 && nums[i]<nums[i+1]) {
                incr+=nums[i+1];
                maxIncr=max(maxIncr, incr);
                i++;
            }
            r=i;
            
            if (r>q) {
                ans=max(ans, Sum+maxIncr);
                // Backtrack to q, q=l
                i=q; 
            }
        }
        return ans;
    }
};