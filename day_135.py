# 3445. Maximum Difference Between Even and Odd Frequency II

# You are given a string s and an integer k. Your task is to find the maximum difference between the frequency of two characters, freq[a] - freq[b], in a substring subs of s, such that:

# subs has a size of at least k.
# Character a has an odd frequency in subs.
# Character b has an even frequency in subs.
# Return the maximum difference.

# Note that subs can contain more than 2 distinct characters.

 

# Example 1:

# Input: s = "12233", k = 4

# Output: -1

# Explanation:

# For the substring "12233", the frequency of '1' is 1 and the frequency of '3' is 2. The difference is 1 - 2 = -1.

# Example 2:

# Input: s = "1122211", k = 3

# Output: 1

# Explanation:

# For the substring "11222", the frequency of '2' is 3 and the frequency of '1' is 2. The difference is 3 - 2 = 1.

# Example 3:

# Input: s = "110", k = 3

# Output: -1

 

# Constraints:

# 3 <= s.length <= 3 * 10^4
# s consists only of digits '0' to '4'.
# The input is generated that at least one substring has a character with an even frequency and a character with an odd frequency.
# 1 <= k <= s.length


# Solution: 


class Solution(object):
    def maxDifference(self, s, k):
        """
        :type s: str
        :type k: int
        :rtype: int
        """
        def getStatus(cnt_a, cnt_b):
            # Returns a 2-bit integer representing the parity of cnt_a and cnt_b
            return ((cnt_a & 1) << 1) | (cnt_b & 1)

        n = len(s)
        ans = float('-inf')

        for a in '01234':
            for b in '01234':
                if a == b:
                    continue

                # Initialize best array for each 2-bit status
                best = [float('inf')] * 4
                cnt_a = cnt_b = 0
                prev_a = prev_b = 0
                left = -1

                for right in range(n):
                    if s[right] == a:
                        cnt_a += 1
                    if s[right] == b:
                        cnt_b += 1

                    while right - left >= k and cnt_b - prev_b >= 2:
                        left_status = getStatus(prev_a, prev_b)
                        best[left_status] = min(best[left_status], prev_a - prev_b)

                        left += 1
                        if s[left] == a:
                            prev_a += 1
                        if s[left] == b:
                            prev_b += 1

                    right_status = getStatus(cnt_a, cnt_b)
                    required_status = right_status ^ 0b10

                    if best[required_status] != float('inf'):
                        ans = max(ans, cnt_a - cnt_b - best[required_status])

        return ans
