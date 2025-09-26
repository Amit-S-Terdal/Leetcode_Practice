// 611. Valid Triangle Number

// Given an integer array nums, return the number of triplets chosen from the array that can make triangles if we take them as side lengths of a triangle.

 

// Example 1:

// Input: nums = [2,2,3,4]
// Output: 3
// Explanation: Valid combinations are: 
// 2,3,4 (using the first 2)
// 2,3,4 (using the second 2)
// 2,2,3
// Example 2:

// Input: nums = [4,2,3,4]
// Output: 4
 

// Constraints:

// 1 <= nums.length <= 1000
// 0 <= nums[i] <= 1000


// Solution: 

import java.util.Arrays;

class Solution {
    public int triangleNumber(int[] nums) {
        int[] freq = new int[1001];
        int xMin = 1001, xMax = 0;

        // Count frequencies and find min and max values
        for (int x : nums) {
            freq[x]++;
            xMin = Math.min(xMin, x);
            xMax = Math.max(xMax, x);
        }

        // Reconstruct the nums array in sorted order based on frequency array
        int index = 0;
        for (int x = xMin; x <= xMax; x++) {
            int f = freq[x];
            if (f == 0) continue;
            Arrays.fill(nums, index, index + f, x);
            index += f;
        }

        int n = nums.length;
        int ans = 0;

        // Count valid triangles
        for (int i = 2; i < n; i++) {
            int left = 0, right = i - 1;
            while (left < right) {
                if (nums[left] + nums[right] > nums[i]) {
                    ans += right - left;
                    right--;
                } else {
                    left++;
                }
            }
        }

        return ans;
    }
}
