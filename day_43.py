# 3208. Alternating Groups II

# There is a circle of red and blue tiles. You are given an array of integers colors and an integer k. The color of tile i is represented by colors[i]:

# colors[i] == 0 means that tile i is red.
# colors[i] == 1 means that tile i is blue.
# An alternating group is every k contiguous tiles in the circle with alternating colors (each tile in the group except the first and last one has a different color from its left and right tiles).

# Return the number of alternating groups.

# Note that since colors represents a circle, the first and the last tiles are considered to be next to each other.

 

# Example 1:

# Input: colors = [0,1,0,1,0], k = 3

# Output: 3

# Explanation:



# Alternating groups:



# Example 2:

# Input: colors = [0,1,0,0,1,0,1], k = 6

# Output: 2

# Explanation:



# Alternating groups:



# Example 3:

# Input: colors = [1,1,0,1], k = 4

# Output: 0

# Explanation:



 

# Constraints:

# 3 <= colors.length <= 105
# 0 <= colors[i] <= 1
# 3 <= k <= colors.length


# Solution:



class Solution(object):
    def numberOfAlternatingGroups(self, colors, k):
        """
        :type colors: List[int]
        :type k: int
        :rtype: int
        """
        n = len(colors)
        if k > n:
            return 0  # No valid groups if k > n

        BASE = 257  # A prime number as the base
        MOD = 10**9 + 7  # A large prime number as the modulus

        # Precompute the two possible alternating patterns
        pattern1 = [i % 2 for i in range(k)]  # Pattern: 0, 1, 0, 1, ...
        pattern2 = [(i + 1) % 2 for i in range(k)]  # Pattern: 1, 0, 1, 0, ...

        # Compute the hash of the two patterns
        hash1 = self.computeHash(pattern1, BASE, MOD)
        hash2 = self.computeHash(pattern2, BASE, MOD)

        # Compute the hash of the first window in the colors array
        firstWindow = colors[:k]
        windowHash = self.computeHash(firstWindow, BASE, MOD)

        count = 0
        if windowHash == hash1 or windowHash == hash2:
            count += 1

        # Use rolling hash to compute the hash of the remaining windows
        power = pow(BASE, k - 1, MOD)  # Precompute BASE^(k-1) % MOD

        for i in range(1, n):
            left = (i - 1) % n  # Handle circular nature
            right = (i + k - 1) % n  # Handle circular nature

            # Remove the left element from the hash
            windowHash = (windowHash - colors[left] * power) % MOD
            # Add the right element to the hash
            windowHash = (windowHash * BASE + colors[right]) % MOD

            if windowHash == hash1 or windowHash == hash2:
                count += 1

        return count

    def computeHash(self, arr, BASE, MOD):
        """
        Compute the hash of an array using the rolling hash method.
        """
        hash_value = 0
        for num in arr:
            hash_value = (hash_value * BASE + num) % MOD
        return hash_value