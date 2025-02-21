// 1980. Find Unique Binary String

// Hint
// Given an array of strings nums containing n unique binary strings each of length n, return a binary string of length n that does not appear in nums. If there are multiple answers, you may return any of them.

 

// Example 1:

// Input: nums = ["01","10"]
// Output: "11"
// Explanation: "11" does not appear in nums. "00" would also be correct.
// Example 2:

// Input: nums = ["00","01"]
// Output: "11"
// Explanation: "11" does not appear in nums. "10" would also be correct.
// Example 3:

// Input: nums = ["111","011","001"]
// Output: "101"
// Explanation: "101" does not appear in nums. "000", "010", "100", and "110" would also be correct.
 

// Constraints:

// n == nums.length
// 1 <= n <= 16
// nums[i].length == n
// nums[i] is either '0' or '1'.
// All the strings of nums are unique.

// Solution:

import java.util.HashSet;
import java.util.Set;

class Solution {
    public String findDifferentBinaryString(String[] nums) {
        int n = nums.length;
        Set<String> set = new HashSet<>();
        
        // Add all strings in nums to a set for quick lookup
        for (String num : nums) {
            set.add(num);
        }
        
        // Iterate through all possible binary strings of length n
        for (int i = 0; i < (1 << n); i++) {
            String binaryStr = Integer.toBinaryString(i);
            // Pad with leading zeros to make it length n
            while (binaryStr.length() < n) {
                binaryStr = "0" + binaryStr;
            }
            // If the binary string is not in the set, return it
            if (!set.contains(binaryStr)) {
                return binaryStr;
            }
        }
        
        // This line will never be reached as per the problem constraints
        return "";
    }
}