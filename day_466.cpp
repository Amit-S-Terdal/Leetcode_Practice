// 2553. Separate the Digits in an Array

// Given an array of positive integers nums, return an array answer that consists of the digits of each integer in nums after separating them in the same order they appear in nums.

// To separate the digits of an integer is to get all the digits it has in the same order.

// For example, for the integer 10921, the separation of its digits is [1,0,9,2,1].
 

// Example 1:

// Input: nums = [13,25,83,77]
// Output: [1,3,2,5,8,3,7,7]
// Explanation: 
// - The separation of 13 is [1,3].
// - The separation of 25 is [2,5].
// - The separation of 83 is [8,3].
// - The separation of 77 is [7,7].
// answer = [1,3,2,5,8,3,7,7]. Note that answer contains the separations in the same order.
// Example 2:

// Input: nums = [7,1,3,9]
// Output: [7,1,3,9]
// Explanation: The separation of each integer in nums is itself.
// answer = [7,1,3,9].
 

// Constraints:

// 1 <= nums.length <= 1000
// 1 <= nums[i] <= 10^5





// Solution: 




int tens[6]={1, 10, 100, 1000, 10000, 100000};
class Solution {
public:
    vector<int> ans;
    inline void add2Ans(int x){
        int d=upper_bound(tens, tens+6, x)-tens-1;
        for(int i=d; i>=0; i--){
            auto [q, r]=div(x, tens[i]);
            ans.push_back(q);
            x=r;
        }
    }
    vector<int> separateDigits(vector<int>& nums) {
        const int n=nums.size();
        ans.reserve(n*6);
        for(int x: nums) add2Ans(x);
        return ans;
    }
};