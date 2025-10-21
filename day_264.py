# 3346. Maximum Frequency of an Element After Performing Operations I

# You are given an integer array nums and two integers k and numOperations.

# You must perform an operation numOperations times on nums, where in each operation you:

# Select an index i that was not selected in any previous operations.
# Add an integer in the range [-k, k] to nums[i].
# Return the maximum possible frequency of any element in nums after performing the operations.

 

# Example 1:

# Input: nums = [1,4,5], k = 1, numOperations = 2

# Output: 2

# Explanation:

# We can achieve a maximum frequency of two by:

# Adding 0 to nums[1]. nums becomes [1, 4, 5].
# Adding -1 to nums[2]. nums becomes [1, 4, 4].
# Example 2:

# Input: nums = [5,11,20,20], k = 5, numOperations = 1

# Output: 2

# Explanation:

# We can achieve a maximum frequency of two by:

# Adding 0 to nums[1].
 

# Constraints:

# 1 <= nums.length <= 10^5
# 1 <= nums[i] <= 10^5
# 0 <= k <= 10^5
# 0 <= numOperations <= nums.length



# Solution: 


class Solution(object):
    def maxFrequency(self, nums, k, numOperations):
        """
        :type nums: List[int]
        :type k: int
        :type numOperations: int
        :rtype: int
        """
        N = 100001
        freq = [0] * N
        max_num = 0

        # Count frequency and find the max value
        for x in nums:
            freq[x] += 1
            max_num = max(max_num, x)

        # Compute prefix sum
        prefix = [0] * (max_num + 2)
        for i in range(1, max_num + 1):
            prefix[i] = prefix[i - 1] + freq[i]

        ans = 0

        # Try to maximize frequency for each number x
        for x in range(1, max_num + 1):
            l = max(1, x - k)
            r = min(max_num, x + k)
            count_in_range = prefix[r] - prefix[l - 1]
            can_add = min(count_in_range - freq[x], numOperations)
            ans = max(ans, freq[x] + can_add)

        return ans
