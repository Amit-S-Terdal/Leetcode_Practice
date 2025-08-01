# 2099. Find Subsequence of Length K With the Largest Sum

# You are given an integer array nums and an integer k. You want to find a subsequence of nums of length k that has the largest sum.

# Return any such subsequence as an integer array of length k.

# A subsequence is an array that can be derived from another array by deleting some or no elements without changing the order of the remaining elements.

 

# Example 1:

# Input: nums = [2,1,3,3], k = 2
# Output: [3,3]
# Explanation:
# The subsequence has the largest sum of 3 + 3 = 6.
# Example 2:

# Input: nums = [-1,-2,3,4], k = 3
# Output: [-1,3,4]
# Explanation: 
# The subsequence has the largest sum of -1 + 3 + 4 = 6.
# Example 3:

# Input: nums = [3,4,3,3], k = 2
# Output: [3,4]
# Explanation:
# The subsequence has the largest sum of 3 + 4 = 7. 
# Another possible subsequence is [4, 3].
 

# Constraints:

# 1 <= nums.length <= 1000
# -105 <= nums[i] <= 10^5
# 1 <= k <= nums.length


# Solution:

class Solution(object):
    def maxSubsequence(self, nums, k):
        """
        :type nums: List[int]
        :type k: int
        :rtype: List[int]
        """
        # Create a list of tuples (num, index)
        nidx = [(num, i) for i, num in enumerate(nums)]
        
        # Get the k elements with the largest values
        nidx.sort(reverse=True)
        top_k = nidx[:k]
        
        # Sort them by their original indices to maintain relative order
        top_k.sort(key=lambda x: x[1])
        
        # Extract only the values
        return [num for num, _ in top_k]
