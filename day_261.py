# 3397. Maximum Number of Distinct Elements After Operations

# You are given an integer array nums and an integer k.

# You are allowed to perform the following operation on each element of the array at most once:

# Add an integer in the range [-k, k] to the element.
# Return the maximum possible number of distinct elements in nums after performing the operations.

 

# Example 1:

# Input: nums = [1,2,2,3,3,4], k = 2

# Output: 6

# Explanation:

# nums changes to [-1, 0, 1, 2, 3, 4] after performing operations on the first four elements.

# Example 2:

# Input: nums = [4,4,4,4], k = 1

# Output: 3

# Explanation:

# By adding -1 to nums[0] and 1 to nums[1], nums changes to [3, 5, 4, 4].

 

# Constraints:

# 1 <= nums.length <= 10^5
# 1 <= nums[i] <= 10^9
# 0 <= k <= 10^9



# Solution: 


class Solution(object):
    def maxDistinctElements(self, nums, k):
        """
        :type nums: List[int]
        :type k: int
        :rtype: int
        """

        def radix_sort(arr):
            # Sort non-negative integers using radix sort
            BSHIFT = 8
            MASK = (1 << BSHIFT) - 1  # 255
            N = 1 << BSHIFT  # 256
            max_val = max(arr) if arr else 0
            Mround = max(1, (max_val.bit_length() + BSHIFT - 1) // BSHIFT)
            
            output = [0] * len(arr)
            for r in range(Mround):
                shift = r * BSHIFT
                freq = [0] * N

                for num in arr:
                    key = (num >> shift) & MASK
                    freq[key] += 1

                for i in range(1, N):
                    freq[i] += freq[i - 1]

                for i in range(len(arr) - 1, -1, -1):
                    val = arr[i]
                    key = (val >> shift) & MASK
                    freq[key] -= 1
                    output[freq[key]] = val

                arr, output = output, arr  # Swap references

            return arr

        nums = radix_sort(nums)

        count = 0
        prev = -k  # The last placed distinct value

        for x in nums:
            xm = x - k
            xx = x + k
            choice = min(max(xm, prev + 1), xx)
            if choice > prev:
                count += 1
                prev = choice

        return count
