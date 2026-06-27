# 3020. Find the Maximum Number of Elements in Subset

# You are given an array of positive integers nums.

# You need to select a subset of nums which satisfies the following condition:

# You can place the selected elements in a 0-indexed array such that it follows the pattern: [x, x2, x4, ..., xk/2, xk, xk/2, ..., x4, x2, x] (Note that k can be be any non-negative power of 2). For example, [2, 4, 16, 4, 2] and [3, 9, 3] follow the pattern while [2, 4, 8, 4, 2] does not.
# Return the maximum number of elements in a subset that satisfies these conditions.

 

# Example 1:

# Input: nums = [5,4,1,2,2]
# Output: 3
# Explanation: We can select the subset {4,2,2}, which can be placed in the array as [2,4,2] which follows the pattern and 22 == 4. Hence the answer is 3.
# Example 2:

# Input: nums = [1,3,2,4]
# Output: 1
# Explanation: We can select the subset {1}, which can be placed in the array as [1] which follows the pattern. Hence the answer is 1. Note that we could have also selected the subsets {2}, {3}, or {4}, there may be multiple subsets which provide the same answer. 
 

# Constraints:

# 2 <= nums.length <= 10^5
# 1 <= nums[i] <= 10^9


# Solution: 



class Solution(object):
    def maximumLength(self, nums):
        """
        :type nums: List[int]
        :rtype: int
        """
        K = [0] * 4
        K[0] = int(1e9 ** 0.5)
        for i in range(1, 4):
            K[i] = int(K[i - 1] ** 0.5)

        freq = [0] * 31623
        seenSq = [False] * 31623

        def reset():
            for x in nums:
                if x <= K[0]:
                    freq[x] = 0
            for i in range(len(seenSq)):
                seenSq[i] = False

        xMax = 0

        for x in nums:
            if x > K[0]:
                rx = int(x ** 0.5)
                if rx * rx == x:
                    seenSq[rx] = True
            else:
                freq[x] += 1
                xMax = max(xMax, x)

        if xMax == 0:
            reset()
            return 1

        ans = 1

        if freq[1]:
            f1 = freq[1]
            ans = max(ans, f1 - (1 if f1 % 2 == 0 else 0))

        if ans >= 9:
            reset()
            return ans

        k = 3

        for x in range(2, xMax + 1):
            if freq[x] == 0:
                continue

            while x > K[k]:
                k -= 1

            cnt = 0
            y = x
            flag = False

            while y <= K[0] and freq[y] >= 2:
                cnt += 2
                y2 = y * y

                if y2 > K[0]:
                    flag = True
                    cnt += (2 if seenSq[y] else 0) - 1
                    break

                y = y2

            if not flag:
                isIn = y <= K[0] and freq[y] >= 1
                cnt += (2 if isIn else 0) - 1

            ans = max(ans, cnt)

            if ans == 2 * (k + 1) + 1:
                reset()
                return ans

        reset()
        return ans