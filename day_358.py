# 3510. Minimum Pair Removal to Sort Array II

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

# 1 <= nums.length <= 10^5
# -109 <= nums[i] <= 10^9



# Solution: 


class Solution(object):
    def minimumPairRemoval(self, nums):
        import heapq

        n = len(nums)
        if n <= 1:
            return 0

        # Already sorted?
        if all(nums[i] <= nums[i + 1] for i in range(n - 1)):
            return 0

        # Doubly linked list via arrays
        prev = [-1] + list(range(n - 1))
        next = list(range(1, n)) + [-1]

        removed = [False] * n

        # Min-heap of (sum, index)
        heap = []
        for i in range(n - 1):
            heapq.heappush(heap, (nums[i] + nums[i + 1], i))

        # Count violations
        bad = sum(nums[i] > nums[i + 1] for i in range(n - 1))

        ops = 0

        while bad > 0:
            s, i = heapq.heappop(heap)

            # Validate pair
            if removed[i] or next[i] == -1:
                continue

            j = next[i]
            if removed[j] or nums[i] + nums[j] != s:
                continue

            pi = prev[i]
            nj = next[j]

            # Remove old violations
            if pi != -1 and nums[pi] > nums[i]:
                bad -= 1
            if nums[i] > nums[j]:
                bad -= 1
            if nj != -1 and nums[j] > nums[nj]:
                bad -= 1

            # Merge
            nums[i] += nums[j]
            removed[j] = True
            next[i] = nj
            if nj != -1:
                prev[nj] = i

            # Add new violations
            if pi != -1 and nums[pi] > nums[i]:
                bad += 1
            if nj != -1 and nums[i] > nums[nj]:
                bad += 1

            # Push new adjacent sums
            if pi != -1:
                heapq.heappush(heap, (nums[pi] + nums[i], pi))
            if nj != -1:
                heapq.heappush(heap, (nums[i] + nums[nj], i))

            ops += 1

        return ops
