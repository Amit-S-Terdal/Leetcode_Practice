# 3488. Closest Equal Element Queries

# You are given a circular array nums and an array queries.

# For each query i, you have to find the following:

# The minimum distance between the element at index queries[i] and any other index j in the circular array, where nums[j] == nums[queries[i]]. If no such index exists, the answer for that query should be -1.
# Return an array answer of the same size as queries, where answer[i] represents the result for query i.

 

# Example 1:

# Input: nums = [1,3,1,4,1,3,2], queries = [0,3,5]

# Output: [2,-1,3]

# Explanation:

# Query 0: The element at queries[0] = 0 is nums[0] = 1. The nearest index with the same value is 2, and the distance between them is 2.
# Query 1: The element at queries[1] = 3 is nums[3] = 4. No other index contains 4, so the result is -1.
# Query 2: The element at queries[2] = 5 is nums[5] = 3. The nearest index with the same value is 1, and the distance between them is 3 (following the circular path: 5 -> 6 -> 0 -> 1).
# Example 2:

# Input: nums = [1,2,3,4], queries = [0,1,2,3]

# Output: [-1,-1,-1,-1]

# Explanation:

# Each value in nums is unique, so no index shares the same value as the queried element. This results in -1 for all queries.

 

# Constraints:

# 1 <= queries.length <= nums.length <= 10^5
# 1 <= nums[i] <= 10^6
# 0 <= queries[i] < nums.length




# Solution : 



class Solution(object):
    def solveQueries(self, nums, queries):
        """
        :type nums: List[int]
        :type queries: List[int]
        :rtype: List[int]
        """
        from collections import defaultdict
        import bisect

        n = len(nums)
        qz = len(queries)
        ans = [-1] * qz

        # Store indices of each value
        x2i = defaultdict(list)
        for i, v in enumerate(nums):
            x2i[v].append(i)

        # Process each query
        for i in range(qz):
            q = queries[i]
            x = nums[q]
            idx = x2i[x]
            sz = len(idx)

            if sz == 1:
                continue

            # Find position using binary search
            i0 = bisect.bisect_left(idx, q)
            j0 = idx[i0]

            d = n - 1

            # Check next index
            if i0 + 1 != sz:
                d = min(d, idx[i0 + 1] - j0)
            else:
                d = min(d, n + idx[0] - j0)

            # Check previous index (circular)
            if i0 != 0:
                d = min(d, j0 - idx[i0 - 1])
            else:
                d = min(d, n + j0 - idx[-1])

            ans[i] = d

        return ans