# 3347. Maximum Frequency of an Element After Performing Operations II

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

# Adding 0 to nums[1], after which nums becomes [1, 4, 5].
# Adding -1 to nums[2], after which nums becomes [1, 4, 4].
# Example 2:

# Input: nums = [5,11,20,20], k = 5, numOperations = 1

# Output: 2

# Explanation:

# We can achieve a maximum frequency of two by:

# Adding 0 to nums[1].
 

# Constraints:

# 1 <= nums.length <= 10^5
# 1 <= nums[i] <= 10^9
# 0 <= k <= 10^9
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
        from collections import Counter

        nums.sort()
        n = len(nums)
        M = nums[-1]

        # Create list of (value, frequency)
        nWf = []
        count = Counter(nums)
        for x in sorted(count):
            nWf.append((x, count[x]))

        n = len(nWf)
        ans = 0
        cntP = 0
        cntI = 0
        l = r = l2 = 0

        for i in range(n):
            x, f = nWf[i]
            L = max(1, x - k)
            R = min(M, x + k)
            L2 = max(1, x - 2 * k)

            # Extend right window
            while r < n and nWf[r][0] <= R:
                cntP += nWf[r][1]
                r += 1

            # Shrink left window
            while l < n and nWf[l][0] < L:
                cntP -= nWf[l][1]
                l += 1

            # Strategy 1: try to convert nearby numbers into x
            ans = max(ans, f + min(cntP - f, numOperations))

            # Strategy 2: select interval [x - 2k, x + k]
            cntI += f
            while l2 < r and nWf[l2][0] < L2:
                cntI -= nWf[l2][1]
                l2 += 1

            ans = max(ans, min(cntI, numOperations))

        return ans
