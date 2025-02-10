// 3174. Clear Digits

// Hint
// You are given a string s.

// Your task is to remove all digits by doing this operation repeatedly:

// Delete the first digit and the closest non-digit character to its left.
// Return the resulting string after removing all digits.

 

// Example 1:

// Input: s = "abc"

// Output: "abc"

// Explanation:

// There is no digit in the string.

// Example 2:

// Input: s = "cb34"

// Output: ""

// Explanation:

// First, we apply the operation on s[2], and s becomes "c4".

// Then we apply the operation on s[1], and s becomes "".

 

// Constraints:

// 1 <= s.length <= 100
// s consists only of lowercase English letters and digits.
// The input is generated such that it is possible to delete all digits.

// Solution:

class Solution {
    public String clearDigits(String s) {
        StringBuilder sb = new StringBuilder(s); // Use StringBuilder for efficient string manipulation
        while (true) {
            int digitPos = -1;
            // Find the first digit in the string
            for (int i = 0; i < sb.length(); i++) {
                if (Character.isDigit(sb.charAt(i))) {
                    digitPos = i;
                    break;
                }
            }
            if (digitPos == -1) {
                break; // No more digits to remove
            }

            // Find the closest non-digit to the left of the digit
            int nonDigitPos = -1;
            for (int i = digitPos - 1; i >= 0; i--) {
                if (!Character.isDigit(sb.charAt(i))) {
                    nonDigitPos = i;
                    break;
                }
            }

            if (nonDigitPos == -1) {
                // If no non-digit character is found to the left, just remove the digit
                sb.deleteCharAt(digitPos);
            } else {
                // Remove both the non-digit character and the digit
                sb.deleteCharAt(nonDigitPos);
                sb.deleteCharAt(digitPos - 1); // After removing the non-digit, the digit's position shifts left
            }
        }
        return sb.toString();
    }
}