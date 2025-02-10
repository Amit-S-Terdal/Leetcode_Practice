# 3174. Clear Digits

# Hint
# You are given a string s.

# Your task is to remove all digits by doing this operation repeatedly:

# Delete the first digit and the closest non-digit character to its left.
# Return the resulting string after removing all digits.

 

# Example 1:

# Input: s = "abc"

# Output: "abc"

# Explanation:

# There is no digit in the string.

# Example 2:

# Input: s = "cb34"

# Output: ""

# Explanation:

# First, we apply the operation on s[2], and s becomes "c4".

# Then we apply the operation on s[1], and s becomes "".

 

# Constraints:

# 1 <= s.length <= 100
# s consists only of lowercase English letters and digits.
# The input is generated such that it is possible to delete all digits.

# Solution:

class Solution(object):
    def clearDigits(self, s):
        s = list(s)  # Convert string to a list for easier manipulation
        while True:
            digit_pos = -1
            # Find the first digit in the string
            for i, char in enumerate(s):
                if char.isdigit():
                    digit_pos = i
                    break
            if digit_pos == -1:
                break  # No more digits to remove

            # Find the closest non-digit to the left of the digit
            non_digit_pos = -1
            for i in range(digit_pos - 1, -1, -1):
                if not s[i].isdigit():
                    non_digit_pos = i
                    break

            if non_digit_pos == -1:
                # If no non-digit character is found to the left, just remove the digit
                s.pop(digit_pos)
            else:
                # Remove both the non-digit character and the digit
                s.pop(non_digit_pos)
                s.pop(digit_pos - 1)  # After removing the non-digit, the digit's position shifts left

        return ''.join(s)  # Convert the list back to a string