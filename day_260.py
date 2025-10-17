# 3003. Maximize the Number of Partitions After Operations

# You are given a string s and an integer k.

# First, you are allowed to change at most one index in s to another lowercase English letter.

# After that, do the following partitioning operation until s is empty:

# Choose the longest prefix of s containing at most k distinct characters.
# Delete the prefix from s and increase the number of partitions by one. The remaining characters (if any) in s maintain their initial order.
# Return an integer denoting the maximum number of resulting partitions after the operations by optimally choosing at most one index to change.

 

# Example 1:

# Input: s = "accca", k = 2

# Output: 3

# Explanation:

# The optimal way is to change s[2] to something other than a and c, for example, b. then it becomes "acbca".

# Then we perform the operations:

# The longest prefix containing at most 2 distinct characters is "ac", we remove it and s becomes "bca".
# Now The longest prefix containing at most 2 distinct characters is "bc", so we remove it and s becomes "a".
# Finally, we remove "a" and s becomes empty, so the procedure ends.
# Doing the operations, the string is divided into 3 partitions, so the answer is 3.

# Example 2:

# Input: s = "aabaab", k = 3

# Output: 1

# Explanation:

# Initially s contains 2 distinct characters, so whichever character we change, it will contain at most 3 distinct characters, so the longest prefix with at most 3 distinct characters would always be all of it, therefore the answer is 1.

# Example 3:

# Input: s = "xxyz", k = 1

# Output: 4

# Explanation:

# The optimal way is to change s[0] or s[1] to something other than characters in s, for example, to change s[0] to w.

# Then s becomes "wxyz", which consists of 4 distinct characters, so as k is 1, it will divide into 4 partitions.

 

# Constraints:

# 1 <= s.length <= 10^4
# s consists only of lowercase English letters.
# 1 <= k <= 26


# Solution: 



from typing import List
from collections import namedtuple

class Data:
    def __init__(self, segCnt=0, hasC=None, cnt=0):
        self.segCnt = segCnt
        self.hasC = hasC if hasC is not None else set()
        self.cnt = cnt

class Solution(object):
    def maxPartitionsAfterOperations(self, s, k):
        """
        :type s: str
        :type k: int
        :rtype: int
        """
        n = len(s)
        pref = [Data() for _ in range(n)]
        suff = [Data() for _ in range(n)]

        seg = cnt = 0
        mask = set()

        for i in range(n - 1):
            ch = s[i]
            if ch not in mask:
                cnt += 1
                if cnt > k:
                    seg += 1
                    cnt = 1
                    mask.clear()
                mask.add(ch)
            pref[i + 1] = Data(seg, set(mask), cnt)

        seg = cnt = 0
        mask.clear()

        for i in range(n - 1, 0, -1):
            ch = s[i]
            if ch not in mask:
                cnt += 1
                if cnt > k:
                    seg += 1
                    cnt = 1
                    mask.clear()
                mask.add(ch)
            suff[i - 1] = Data(seg, set(mask), cnt)

        ans = 0
        for i in range(n):
            segL, Lmask, Lcnt = pref[i].segCnt, pref[i].hasC, pref[i].cnt
            segR, Rmask, Rcnt = suff[i].segCnt, suff[i].hasC, suff[i].cnt

            totalSeg = segL + segR + 1
            combined = Lmask | Rmask
            bz = len(combined)

            if min(bz + 1, 26) <= k:
                add = 0
            else:
                add = 2 if (Lcnt == k and Rcnt == k and bz < 26) else 1

            ans = max(ans, totalSeg + add)

        return ans

