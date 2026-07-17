# 3312. Sorted GCD Pair Queries

# You are given an integer array nums of length n and an integer array queries.

# Let gcdPairs denote an array obtained by calculating the GCD of all possible pairs (nums[i], nums[j]), where 0 <= i < j < n, and then sorting these values in ascending order.

# For each query queries[i], you need to find the element at index queries[i] in gcdPairs.

# Return an integer array answer, where answer[i] is the value at gcdPairs[queries[i]] for each query.

# The term gcd(a, b) denotes the greatest common divisor of a and b.

 

# Example 1:

# Input: nums = [2,3,4], queries = [0,2,2]

# Output: [1,2,2]

# Explanation:

# gcdPairs = [gcd(nums[0], nums[1]), gcd(nums[0], nums[2]), gcd(nums[1], nums[2])] = [1, 2, 1].

# After sorting in ascending order, gcdPairs = [1, 1, 2].

# So, the answer is [gcdPairs[queries[0]], gcdPairs[queries[1]], gcdPairs[queries[2]]] = [1, 2, 2].

# Example 2:

# Input: nums = [4,4,2,1], queries = [5,3,1,0]

# Output: [4,2,1,1]

# Explanation:

# gcdPairs sorted in ascending order is [1, 1, 1, 2, 2, 4].

# Example 3:

# Input: nums = [2,2], queries = [0,0]

# Output: [2,2]

# Explanation:

# gcdPairs = [2].

 

# Constraints:

# 2 <= n == nums.length <= 10^5
# 1 <= nums[i] <= 5 * 10^4
# 1 <= queries.length <= 10^5
# 0 <= queries[i] < n * (n - 1) / 2




# Solution:



class Solution(object):
    MX = 50001
    mu = [0] * MX
    sq_free = []
    initialized = False

    @classmethod
    def sieve_mu(cls):
        if cls.initialized:
            return
        cls.initialized = True

        cls.mu = [1] * cls.MX
        is_prime = [True] * cls.MX
        is_prime[0] = is_prime[1] = False
        cls.mu[1] = 1

        for i in range(2, cls.MX):
            if is_prime[i]:
                cls.mu[i] = -1

                for j in range(i * 2, cls.MX, i):
                    is_prime[j] = False
                    cls.mu[j] = -cls.mu[j]

                ii = i * i
                for j in range(ii, cls.MX, ii):
                    cls.mu[j] = 0

        cls.sq_free = [i for i in range(1, cls.MX) if cls.mu[i] != 0]

    def gcdValues(self, nums, queries):
        """
        :type nums: List[int]
        :type queries: List[int]
        :rtype: List[int]
        """
        Solution.sieve_mu()

        M = max(nums)
        Div = [0] * (M + 1)
        freq = [0] * (M + 1)

        for x in nums:
            Div[x] += 1

        # Div[i] = count of numbers divisible by i
        for x in range(1, M + 1):
            for y in range(x * 2, M + 1, x):
                Div[x] += Div[y]

        # Number of pairs divisible by x
        for x in range(1, M + 1):
            cnt = Div[x]
            Div[x] = cnt * (cnt - 1) // 2

        # Möbius inversion
        for x in range(1, M + 1):
            s = 0
            for k in Solution.sq_free:
                if x * k > M:
                    break
                s += Solution.mu[k] * Div[x * k]
            freq[x] = s

        # Prefix sums
        for i in range(1, M + 1):
            freq[i] += freq[i - 1]

        ans = []
        for q in queries:
            ans.append(bisect.bisect_right(freq, q))

        return ans