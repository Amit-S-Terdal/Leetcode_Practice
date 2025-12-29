# 756. Pyramid Transition Matrix

# You are stacking blocks to form a pyramid. Each block has a color, which is represented by a single letter. Each row of blocks contains one less block than the row beneath it and is centered on top.

# To make the pyramid aesthetically pleasing, there are only specific triangular patterns that are allowed. A triangular pattern consists of a single block stacked on top of two blocks. The patterns are given as a list of three-letter strings allowed, where the first two characters of a pattern represent the left and right bottom blocks respectively, and the third character is the top block.

# For example, "ABC" represents a triangular pattern with a 'C' block stacked on top of an 'A' (left) and 'B' (right) block. Note that this is different from "BAC" where 'B' is on the left bottom and 'A' is on the right bottom.
# You start with a bottom row of blocks bottom, given as a single string, that you must use as the base of the pyramid.

# Given bottom and allowed, return true if you can build the pyramid all the way to the top such that every triangular pattern in the pyramid is in allowed, or false otherwise.

 

# Example 1:


# Input: bottom = "BCD", allowed = ["BCC","CDE","CEA","FFF"]
# Output: true
# Explanation: The allowed triangular patterns are shown on the right.
# Starting from the bottom (level 3), we can build "CE" on level 2 and then build "A" on level 1.
# There are three triangular patterns in the pyramid, which are "BCC", "CDE", and "CEA". All are allowed.
# Example 2:


# Input: bottom = "AAAA", allowed = ["AAB","AAC","BCD","BBE","DEF"]
# Output: false
# Explanation: The allowed triangular patterns are shown on the right.
# Starting from the bottom (level 4), there are multiple ways to build level 3, but trying all the possibilites, you will get always stuck before building level 1.
 

# Constraints:

# 2 <= bottom.length <= 6
# 0 <= allowed.length <= 216
# allowed[i].length == 3
# The letters in all input strings are from the set {'A', 'B', 'C', 'D', 'E', 'F'}.
# All the values of allowed are unique.



# Solution: 



class Solution(object):
    def pyramidTransition(self, bottom, allowed):
        """
        :type bottom: str
        :type allowed: List[str]
        :rtype: bool
        """
        N = 117649  # 7^6
        pattern = [0] * 36
        BAD = [set() for _ in range(7)]  # memo per row length

        # Helper function to encode the string as an integer
        def encode(s):
            ans = 0
            for c in s:
                ans = ans * 7 + (ord(c) - ord('A'))
            return ans

        # Helper function to check if the string is valid
        def check(cur, sz):
            for i in range(sz - 1):
                if cur[i] == 'G':  # invalid character in row
                    return False
                key = (ord(cur[i]) - ord('A')) * 6 + (ord(cur[i + 1]) - ord('A'))
                if not pattern[key]:  # no valid transition
                    return False
            return True

        # Add allowed patterns to the pattern array
        def addPattern(allowed):
            for s in allowed:
                idx = (ord(s[0]) - ord('A')) * 6 + (ord(s[1]) - ord('A'))
                pattern[idx] |= 1 << (ord(s[2]) - ord('A'))

        # DFS to try building the pyramid
        def dfs(cur, next_row, i, sz):
            if i == sz - 1:
                if sz == 2:
                    return True
                if not check(next_row, sz - 1):
                    return False
                idx = encode(next_row)
                if idx in BAD[sz - 1]:
                    return False
                up = 'G' * (sz - 1)
                if not dfs(next_row, up, 0, sz - 1):
                    BAD[sz - 1].add(idx)
                    return False
                return True

            key = (ord(cur[i]) - ord('A')) * 6 + (ord(cur[i + 1]) - ord('A'))
            mask = pattern[key]

            while mask:
                bit = mask & -mask
                mask -= bit

                c = (bit).bit_length() - 1  # Find the index of the least significant bit
                next_row = next_row[:i] + chr(ord('A') + c) + next_row[i + 1:]

                if dfs(cur, next_row, i + 1, sz):
                    return True
            return False

        # Initialize pattern and BAD
        for i in range(1, 7):
            BAD[i] = set()

        addPattern(allowed)

        # Start DFS
        next_row = 'G' * (len(bottom) - 1)
        return dfs(bottom, next_row, 0, len(bottom))
