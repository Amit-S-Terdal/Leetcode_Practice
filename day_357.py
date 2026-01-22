# 3507. Minimum Pair Removal to Sort Array I

# Given an array nums, you can perform the following operation any number of times:

# Select the adjacent pair with the minimum sum in nums. If multiple such pairs exist, choose the leftmost one.
# Replace the pair with their sum.
# Return the minimum number of operations needed to make the array non-decreasing.

# An array is said to be non-decreasing if each element is greater than or equal to its previous element (if it exists).

 

# Example 1:

# Input: nums = [5,2,3,1]

# Output: 2

# Explanation:

# The pair (3,1) has the minimum sum of 4. After replacement, nums = [5,2,4].
# The pair (2,4) has the minimum sum of 6. After replacement, nums = [5,6].
# The array nums became non-decreasing in two operations.

# Example 2:

# Input: nums = [1,2,2]

# Output: 0

# Explanation:

# The array nums is already sorted.

 

# Constraints:

# 1 <= nums.length <= 50
# -1000 <= nums[i] <= 1000



# Solution:



import heapq

class Solution(object):
    def minimumPairRemoval(self, nums):
        n = len(nums)
        if n <= 1:
            return 0

        # Doubly linked list
        prev = list(range(-1, n - 1))
        next = list(range(1, n + 1))
        next[n - 1] = -1

        alive = [True] * n

        # Min-heap of (pairSum, index)
        heap = []
        for i in range(n - 1):
            heapq.heappush(heap, (nums[i] + nums[i + 1], i))

        def is_non_decreasing():
            i = 0
            while i != -1 and next[i] != -1:
                if nums[i] > nums[next[i]]:
                    return False
                i = next[i]
            return True

        ops = 0

        while not is_non_decreasing():
            # Get valid minimum pair
            while True:
                pairSum, i = heapq.heappop(heap)
                j = next[i]
                if j != -1 and alive[i] and alive[j] and nums[i] + nums[j] == pairSum:
                    break

            # Merge j into i
            nums[i] += nums[j]
            alive[j] = False

            # Remove j from list
            nj = next[j]
            next[i] = nj
            if nj != -1:
                prev[nj] = i

            # Push new adjacent pairs
            if prev[i] != -1:
                p = prev[i]
                heapq.heappush(heap, (nums[p] + nums[i], p))
            if next[i] != -1:
                heapq.heappush(heap, (nums[i] + nums[next[i]], i))

            ops += 1

        return ops
