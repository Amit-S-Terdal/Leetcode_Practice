// 2116. Check if a Parentheses String Can Be Valid

// A parentheses string is a non-empty string consisting only of '(' and ')'. It is valid if any of the following conditions is true:

// It is ().
// It can be written as AB (A concatenated with B), where A and B are valid parentheses strings.
// It can be written as (A), where A is a valid parentheses string.
// You are given a parentheses string s and a string locked, both of length n. locked is a binary string consisting only of '0's and '1's. For each index i of locked,

// If locked[i] is '1', you cannot change s[i].
// But if locked[i] is '0', you can change s[i] to either '(' or ')'.
// Return true if you can make s a valid parentheses string. Otherwise, return false.

 

// Example 1:


// Input: s = "))()))", locked = "010100"
// Output: true
// Explanation: locked[1] == '1' and locked[3] == '1', so we cannot change s[1] or s[3].
// We change s[0] and s[4] to '(' while leaving s[2] and s[5] unchanged to make s valid.
// Example 2:

// Input: s = "()()", locked = "0000"
// Output: true
// Explanation: We do not need to make any changes because s is already valid.
// Example 3:

// Input: s = ")", locked = "0"
// Output: false
// Explanation: locked permits us to change s[0]. 
// Changing s[0] to either '(' or ')' will not make s valid.
 

// Constraints:

// n == s.length == locked.length
// 1 <= n <= 105
// s[i] is either '(' or ')'.
// locked[i] is either '0' or '1'.

// Solution:

class Solution {
    public boolean canBeValid(String s, String locked) {
        // If the length of the string is odd, it cannot be valid
        if (s.length() % 2 != 0) return false;

        // Forward scan
        int minOpen = 0, maxOpen = 0;
        for (int i = 0; i < s.length(); i++) {
            if (locked.charAt(i) == '1') {
                if (s.charAt(i) == '(') {
                    minOpen++;
                    maxOpen++;
                } else {
                    minOpen--;
                    maxOpen--;
                }
            } else {
                // Treat as either '(' or ')'
                minOpen--;
                maxOpen++;
            }

            // If maxOpen goes negative, it's impossible to balance
            if (maxOpen < 0) return false;

            // minOpen cannot be negative
            minOpen = Math.max(minOpen, 0);
        }

        // Backward scan
        int minClose = 0, maxClose = 0;
        for (int i = s.length() - 1; i >= 0; i--) {
            if (locked.charAt(i) == '1') {
                if (s.charAt(i) == ')') {
                    minClose++;
                    maxClose++;
                } else {
                    minClose--;
                    maxClose--;
                }
            } else {
                // Treat as either '(' or ')'
                minClose--;
                maxClose++;
            }

            // If maxClose goes negative, it's impossible to balance
            if (maxClose < 0) return false;

            // minClose cannot be negative
            minClose = Math.max(minClose, 0);
        }

        // The string is valid if all conditions are satisfied
        return minOpen == 0;
    }
}
