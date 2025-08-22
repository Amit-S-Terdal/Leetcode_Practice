// 2348. Number of Zero-Filled Subarrays

// Given an integer array nums, return the number of subarrays filled with 0.

// A subarray is a contiguous non-empty sequence of elements within an array.

 

// Example 1:

// Input: nums = [1,3,0,0,2,0,0,4]
// Output: 6
// Explanation: 
// There are 4 occurrences of [0] as a subarray.
// There are 2 occurrences of [0,0] as a subarray.
// There is no occurrence of a subarray with a size more than 2 filled with 0. Therefore, we return 6.
// Example 2:

// Input: nums = [0,0,0,2,0,0]
// Output: 9
// Explanation:
// There are 5 occurrences of [0] as a subarray.
// There are 3 occurrences of [0,0] as a subarray.
// There is 1 occurrence of [0,0,0] as a subarray.
// There is no occurrence of a subarray with a size more than 3 filled with 0. Therefore, we return 9.
// Example 3:

// Input: nums = [2,10,2019]
// Output: 0
// Explanation: There is no subarray filled with 0. Therefore, we return 0.
 

// Constraints:

// 1 <= nums.length <= 10^5
// -109 <= nums[i] <= 10^9


// Solution:


class Solution {
    public:
        long long zeroFilledSubarray(vector<int>& nums) {
            const int n=nums.size();
            long long sum=0, a_len=0;
            for (int l=0, r=0; l<n; ){
                if (nums[l]!=0){
                    l++;
                    continue;
                }
                for(r=l; r<n && nums[r]==0; r++);
                a_len=(r-l);
                sum+=(a_len+1LL)*a_len/2;
                l=r;
            }
            return sum;
        }
    };