# 3336. Find the Number of Subsequences With Equal GCD

# You are given an integer array nums.

# Your task is to find the number of pairs of non-empty subsequences (seq1, seq2) of nums that satisfy the following conditions:

# The subsequences seq1 and seq2 are disjoint, meaning no index of nums is common between them.
# The GCD of the elements of seq1 is equal to the GCD of the elements of seq2.
# Return the total number of such pairs.

# Since the answer may be very large, return it modulo 109 + 7.

 

# Example 1:

# Input: nums = [1,2,3,4]

# Output: 10

# Explanation:

# The subsequence pairs which have the GCD of their elements equal to 1 are:

# ([1, 2, 3, 4], [1, 2, 3, 4])
# ([1, 2, 3, 4], [1, 2, 3, 4])
# ([1, 2, 3, 4], [1, 2, 3, 4])
# ([1, 2, 3, 4], [1, 2, 3, 4])
# ([1, 2, 3, 4], [1, 2, 3, 4])
# ([1, 2, 3, 4], [1, 2, 3, 4])
# ([1, 2, 3, 4], [1, 2, 3, 4])
# ([1, 2, 3, 4], [1, 2, 3, 4])
# ([1, 2, 3, 4], [1, 2, 3, 4])
# ([1, 2, 3, 4], [1, 2, 3, 4])
# Example 2:

# Input: nums = [10,20,30]

# Output: 2

# Explanation:

# The subsequence pairs which have the GCD of their elements equal to 10 are:

# ([10, 20, 30], [10, 20, 30])
# ([10, 20, 30], [10, 20, 30])
# Example 3:

# Input: nums = [1,1,1,1]

# Output: 50

 

# Constraints:

# 1 <= nums.length <= 200
# 1 <= nums[i] <= 200





# Solution:



class Solution(object):
    MOD = 10 ** 9 + 7
    N = 201

    GCD = [[0] * N for _ in range(N)]
    gcdComputed = False

    @classmethod
    def computeGCD(cls):
        if cls.gcdComputed:
            return
        cls.gcdComputed = True

        for x in range(cls.N):
            cls.GCD[x][x] = x
            cls.GCD[x][0] = x
            cls.GCD[0][x] = x

        for x in range(1, cls.N):
            cls.GCD[x][1] = 1
            cls.GCD[1][x] = 1
            for y in range(2, x):
                cls.GCD[x][y] = cls.GCD[y][x] = cls.GCD[y][x - y]

    def subsequencePairCount(self, nums):
        self.computeGCD()

        M = 0
        qM = 0
        bit = [0] * 4
        bit[0] |= 1  # set bit 0

        for x in nums:
            M = max(M, x)
            q = x >> 6
            r = x & 63
            qM = max(qM, q)
            bit[q] |= 1 << r

        width = M + 1
        M2 = width * width

        # reachable gcds
        for x in range(M + 1):
            q = x >> 6
            r = x & 63

            if (bit[q] >> r) & 1:
                continue

            y = 2 * x
            while y <= M:
                q2 = y >> 6
                r2 = y & 63
                if (bit[q2] >> r2) & 1:
                    bit[q] |= 1 << r
                    qM = max(qM, q)
                    break
                y += x

        dp = [[0] * M2 for _ in range(2)]
        dp[0][0] = 1

        for i, x in enumerate(nums):
            cur = i & 1
            nxt = cur ^ 1
            dp[nxt] = [0] * M2

            for iq in range(qM + 1):
                ir = bit[iq]
                while ir:
                    b1 = ir & -ir
                    r1 = b1.bit_length() - 1
                    g1 = (iq << 6) + r1
                    ir ^= b1

                    for jq in range(qM + 1):
                        jr = bit[jq]
                        while jr:
                            b2 = jr & -jr
                            r2 = b2.bit_length() - 1
                            g2 = (jq << 6) + r2
                            jr ^= b2

                            idx = g1 * width + g2
                            curDp = dp[cur][idx]
                            if curDp == 0:
                                continue

                            ng1 = self.GCD[g1][x]
                            idx1 = ng1 * width + g2
                            dp[nxt][idx1] = (dp[nxt][idx1] + curDp) % self.MOD

                            ng2 = self.GCD[g2][x]
                            idx2 = g1 * width + ng2
                            dp[nxt][idx2] = (dp[nxt][idx2] + curDp) % self.MOD

                            dp[nxt][idx] = (dp[nxt][idx] + curDp) % self.MOD

        last = len(nums) & 1
        ans = 0

        bit[0] ^= 1  # remove bit 0

        for iq in range(qM + 1):
            ir = bit[iq]
            while ir:
                b = ir & -ir
                r = b.bit_length() - 1
                g = (iq << 6) + r
                ir ^= b

                ans = (ans + dp[last][g * (width + 1)]) % self.MOD

        return ans