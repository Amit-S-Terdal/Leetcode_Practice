// 1262. Greatest Sum Divisible by Three

// Given an integer array nums, return the maximum possible sum of elements of the array such that it is divisible by three.

 

// Example 1:

// Input: nums = [3,6,5,1,8]
// Output: 18
// Explanation: Pick numbers 3, 6, 1 and 8 their sum is 18 (maximum sum divisible by 3).
// Example 2:

// Input: nums = [4]
// Output: 0
// Explanation: Since 4 is not divisible by 3, do not pick any number.
// Example 3:

// Input: nums = [1,2,3,4,4]
// Output: 12
// Explanation: Pick numbers 1, 3, 4 and 4 their sum is 12 (maximum sum divisible by 3).
 

// Constraints:

// 1 <= nums.length <= 4 * 10^4
// 1 <= nums[i] <= 10^4


// Solution: 


class Solution {
public:
    static int maxSumDivThree(vector<int>& nums) {
        const int n=nums.size(), minus=-1e9;
        int dp[2][3]={ {0, 0, 0}, {0, minus, minus}};
        for(int i=0; i<n; i++){
            const int x=nums[i];
            for(int mod=0; mod<3; mod++){
                int modPrev=mod-x%3; modPrev+=(-(modPrev<0)) & 3;
                const int take=x+dp[(i+1)&1][modPrev];
                const int skip=dp[(i+1)&1][mod];
                dp[i&1][mod]=max(take, skip);
            }
        }
        return max(0, dp[(n-1)&1][0]);
    }
};