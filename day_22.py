# 1718. Construct the Lexicographically Largest Valid Sequence

# Hint
# Given an integer n, find a sequence that satisfies all of the following:

# The integer 1 occurs once in the sequence.
# Each integer between 2 and n occurs twice in the sequence.
# For every integer i between 2 and n, the distance between the two occurrences of i is exactly i.
# The distance between two numbers on the sequence, a[i] and a[j], is the absolute difference of their indices, |j - i|.

# Return the lexicographically largest sequence. It is guaranteed that under the given constraints, there is always a solution.

# A sequence a is lexicographically larger than a sequence b (of the same length) if in the first position where a and b differ, sequence a has a number greater than the corresponding number in b. For example, [0,1,9,0] is lexicographically larger than [0,1,5,6] because the first position they differ is at the third number, and 9 is greater than 5.

 

# Example 1:

# Input: n = 3
# Output: [3,1,2,3,2]
# Explanation: [2,3,2,1,3] is also a valid sequence, but [3,1,2,3,2] is the lexicographically largest valid sequence.
# Example 2:

# Input: n = 5
# Output: [5,3,1,4,3,5,2,4,2]
 

# Constraints:

# 1 <= n <= 20


# Solution:

class Solution(object):
    def constructDistancedSequence(self, n):
        """
        :type n: int
        :rtype: List[int]
        """
        length = 2 * n - 1
        sequence = [0] * length
        used = [False] * (n + 1)
        
        def backtrack(index):
            if index == length:
                return True
            if sequence[index] != 0:
                return backtrack(index + 1)
            for num in range(n, 0, -1):
                if used[num]:
                    continue
                if num == 1:
                    sequence[index] = 1
                    used[1] = True
                    if backtrack(index + 1):
                        return True
                    sequence[index] = 0
                    used[1] = False
                else:
                    if index + num >= length or sequence[index + num] != 0:
                        continue
                    sequence[index] = num
                    sequence[index + num] = num
                    used[num] = True
                    if backtrack(index + 1):
                        return True
                    sequence[index] = 0
                    sequence[index + num] = 0
                    used[num] = False
            return False
        
        backtrack(0)
        return sequence

# Example usage:
# sol = Solution()
# print(sol.constructDistancedSequence(3))  # Output: [3, 1, 2, 3, 2]
# print(sol.constructDistancedSequence(5))  # Output: [5, 3, 1, 4, 3, 5, 2, 4, 2]