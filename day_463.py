# 3629. Minimum Jumps to Reach End via Prime Teleportation

# You are given an integer array nums of length n.

# You start at index 0, and your goal is to reach index n - 1.

# From any index i, you may perform one of the following operations:

# Adjacent Step: Jump to index i + 1 or i - 1, if the index is within bounds.
# Prime Teleportation: If nums[i] is a prime number p, you may instantly jump to any index j != i such that nums[j] % p == 0.
# Return the minimum number of jumps required to reach index n - 1.

 

# Example 1:

# Input: nums = [1,2,4,6]

# Output: 2

# Explanation:

# One optimal sequence of jumps is:

# Start at index i = 0. Take an adjacent step to index 1.
# At index i = 1, nums[1] = 2 is a prime number. Therefore, we teleport to index i = 3 as nums[3] = 6 is divisible by 2.
# Thus, the answer is 2.

# Example 2:

# Input: nums = [2,3,4,7,9]

# Output: 2

# Explanation:

# One optimal sequence of jumps is:

# Start at index i = 0. Take an adjacent step to index i = 1.
# At index i = 1, nums[1] = 3 is a prime number. Therefore, we teleport to index i = 4 since nums[4] = 9 is divisible by 3.
# Thus, the answer is 2.

# Example 3:

# Input: nums = [4,6,5,8]

# Output: 3

# Explanation:

# Since no teleportation is possible, we move through 0 → 1 → 2 → 3. Thus, the answer is 3.
 

# Constraints:

# 1 <= n == nums.length <= 10^5
# 1 <= nums[i] <= 10^6




# Solution:



from collections import deque

M = 10**6 + 1

sieve = [False] * M
pIdx = [-1] * M
primes = []
mp = [[] for _ in range(80000)]

sz = 0
sieveBuilt = False


def Sieve():
    global sz, sieveBuilt

    if sieveBuilt:
        return

    sieveBuilt = True

    sieve[0] = True
    sieve[1] = True

    i = 2
    while i <= 1000:

        if not sieve[i]:

            primes.append(i)
            pIdx[i] = sz
            sz += 1

            j = i * i
            while j < M:
                sieve[j] = True
                j += i

        i += 1 if i == 2 else 2

    i = 1001
    while i < M:

        if not sieve[i]:
            pIdx[i] = sz
            sz += 1
            primes.append(i)

        i += 2


class Solution(object):

    @staticmethod
    def reset(used, maxPidx):
        for i in range(maxPidx + 1):
            if i in used:
                mp[i] = []

    def minJumps(self, nums):
        """
        :type nums: List[int]
        :rtype: int
        """

        Sieve()

        n = len(nums)

        used = set()
        maxPidx = -1

        for i, x in enumerate(nums):

            if not sieve[x]:

                idx = pIdx[x]
                mp[idx].append(i)

                used.add(idx)
                maxPidx = max(maxPidx, idx)

        vis = [False] * n

        q = deque([n - 1])
        vis[n - 1] = True

        d = 0

        while q:

            qz = len(q)

            while qz > 0:

                qz -= 1

                i = q.popleft()

                if i == 0:
                    self.reset(used, maxPidx)
                    return d

                x = nums[i]

                if i > 0 and not vis[i - 1]:
                    vis[i - 1] = True
                    q.append(i - 1)

                if i < n - 1 and not vis[i + 1]:
                    vis[i + 1] = True
                    q.append(i + 1)

                j = 0

                while j < sz and x > 1:

                    p = primes[j]

                    if not sieve[x]:
                        p = x

                    if x % p == 0:

                        idx = pIdx[p]

                        while x % p == 0:
                            x //= p

                        for it in mp[idx]:

                            if not vis[it]:
                                vis[it] = True
                                q.append(it)

                        if idx in used:
                            used.remove(idx)

                        mp[idx] = []

                    j += 1

            d += 1

        return -1