# 3539. Find Sum of Array Product of Magical Sequences

# You are given two integers, m and k, and an integer array nums.

# A sequence of integers seq is called magical if:
# seq has a size of m.
# 0 <= seq[i] < nums.length
# The binary representation of 2seq[0] + 2seq[1] + ... + 2seq[m - 1] has k set bits.
# The array product of this sequence is defined as prod(seq) = (nums[seq[0]] * nums[seq[1]] * ... * nums[seq[m - 1]]).

# Return the sum of the array products for all valid magical sequences.

# Since the answer may be large, return it modulo 109 + 7.

# A set bit refers to a bit in the binary representation of a number that has a value of 1.

 

# Example 1:

# Input: m = 5, k = 5, nums = [1,10,100,10000,1000000]

# Output: 991600007

# Explanation:

# All permutations of [0, 1, 2, 3, 4] are magical sequences, each with an array product of 1013.

# Example 2:

# Input: m = 2, k = 2, nums = [5,4,3,2,1]

# Output: 170

# Explanation:

# The magical sequences are [0, 1], [0, 2], [0, 3], [0, 4], [1, 0], [1, 2], [1, 3], [1, 4], [2, 0], [2, 1], [2, 3], [2, 4], [3, 0], [3, 1], [3, 2], [3, 4], [4, 0], [4, 1], [4, 2], and [4, 3].

# Example 3:

# Input: m = 1, k = 1, nums = [28]

# Output: 28

# Explanation:

# The only magical sequence is [0].

 

# Constraints:

# 1 <= k <= m <= 30
# 1 <= nums.length <= 50
# 1 <= nums[i] <= 10^8



# Solution: 



class Solution(object):
    MOD = 1000000007
    C = [[0] * 31 for _ in range(31)]
    dp = [[[[-1] * 31 for _ in range(50)] for _ in range(31)] for _ in range(31)]

    def magicalSum(self, m, k, nums):
        """
        :type m: int
        :type k: int
        :type nums: List[int]
        :rtype: int
        """
        self.Pascal()  # Initialize Pascal's triangle
        n = len(nums)

        # Initialize dp array with -1
        for i in range(m + 1):
            for j in range(m + 1):
                for s in range(n):
                    for t in range(m + 1):
                        self.dp[i][j][s][t] = -1

        return self.dfs(m, k, 0, 0, nums)

    def Pascal(self):
        if self.C[0][0] == 1:
            return  # Compute only once
        for i in range(1, 31):
            self.C[i][0] = self.C[i][i] = 1
            for j in range(1, i // 2 + 1):
                Cij = self.C[i - 1][j - 1] + self.C[i - 1][j]
                if Cij >= self.MOD:
                    Cij -= self.MOD
                self.C[i][j] = self.C[i][i - j] = Cij

    def dfs(self, m, k, i, flag, nums):
        bz = bin(flag).count('1')  # Popcount equivalent in Python
        if m < 0 or k < 0 or m + bz < k:
            return 0
        if m == 0:
            return 1 if k == bz else 0
        if i >= len(nums):
            return 0

        if self.dp[m][k][i][flag] != -1:
            return self.dp[m][k][i][flag]

        ans = 0
        powX = 1
        x = nums[i]
        for f in range(m + 1):
            perm = self.C[m][f] * powX % self.MOD

            newFlag = flag + f
            nextFlag = newFlag >> 1
            bitSet = (newFlag & 1)

            ans = (ans + perm * self.dfs(m - f, k - bitSet, i + 1, nextFlag, nums)) % self.MOD
            powX = (powX * x) % self.MOD

        self.dp[m][k][i][flag] = ans
        return ans
