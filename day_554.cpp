// 2996. Smallest Missing Integer Greater Than Sequential Prefix Sum

// You are given a 0-indexed array of integers nums.

// A prefix nums[0..i] is sequential if, for all 1 <= j <= i, nums[j] = nums[j - 1] + 1. In particular, the prefix consisting only of nums[0] is sequential.

// Return the smallest integer x missing from nums such that x is greater than or equal to the sum of the longest sequential prefix.

 

// Example 1:

// Input: nums = [1,2,3,2,5]
// Output: 6
// Explanation: The longest sequential prefix of nums is [1,2,3] with a sum of 6. 6 is not in the array, therefore 6 is the smallest missing integer greater than or equal to the sum of the longest sequential prefix.
// Example 2:

// Input: nums = [3,4,5,1,12,14,13]
// Output: 15
// Explanation: The longest sequential prefix of nums is [3,4,5] with a sum of 12. 12, 13, and 14 belong to the array while 15 does not. Therefore 15 is the smallest missing integer greater than or equal to the sum of the longest sequential prefix.
 

// Constraints:

// 1 <= nums.length <= 50
// 1 <= nums[i] <= 50


// Solution: 



class Solution {
public:
    int missingInteger(vector<int>& nums) {
        int prefix=nums[0], i, n=nums.size();
        if (n==1) return prefix+1;
        for(i=1; i<n; i++){
            if (nums[i]!=nums[i-1]+1)
            break;
            prefix+=nums[i];
        }
        bitset<51> seen=0;
        for (int j=0; j<n; j++){
            seen[nums[j]]=1;
        }
        while(prefix<=50 && seen[prefix])
            prefix++;
        return prefix;
    }
};

auto init = []()
{ 
    ios::sync_with_stdio(0);
    cin.tie(0);
    cout.tie(0);
    return 'c';
}();