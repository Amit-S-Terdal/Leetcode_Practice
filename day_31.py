# 1524. Number of Sub-arrays With Odd Sum

# Given an array of integers arr, return the number of subarrays with an odd sum.

# Since the answer can be very large, return it modulo 109 + 7.

 

# Example 1:

# Input: arr = [1,3,5]
# Output: 4
# Explanation: All subarrays are [[1],[1,3],[1,3,5],[3],[3,5],[5]]
# All sub-arrays sum are [1,4,9,3,8,5].
# Odd sums are [1,9,3,5] so the answer is 4.
# Example 2:

# Input: arr = [2,4,6]
# Output: 0
# Explanation: All subarrays are [[2],[2,4],[2,4,6],[4],[4,6],[6]]
# All sub-arrays sum are [2,6,12,4,10,6].
# All sub-arrays have even sum and the answer is 0.
# Example 3:

# Input: arr = [1,2,3,4,5,6,7]
# Output: 16
 

# Constraints:

# 1 <= arr.length <= 105
# 1 <= arr[i] <= 100

# Solution:

class Solution(object):
    def numOfSubarrays(self, arr):
        """
        :type arr: List[int]
        :rtype: int
        """
        MOD = 10**9 + 7  # Define the modulo value
        prefixSum = 0     # Initialize prefix sum
        evenCount = 1     # Start with evenCount = 1 (prefix sum of empty subarray is 0, which is even)
        oddCount = 0      # Initialize oddCount to 0
        result = 0        # Initialize result to 0

        for num in arr:
            prefixSum += num  # Update prefix sum
            if prefixSum % 2 == 1:
                # If prefixSum is odd, the number of subarrays with odd sum is evenCount
                result = (result + evenCount) % MOD
                oddCount += 1  # Increment oddCount
            else:
                # If prefixSum is even, the number of subarrays with odd sum is oddCount
                result = (result + oddCount) % MOD
                evenCount += 1  # Increment evenCount

        return result