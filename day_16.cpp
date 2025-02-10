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

#include <string>
#include <algorithm>

class Solution {
public:
    string clearDigits(string s) {
        while (true) {
            size_t digitPos = s.find_first_of("0123456789");
            if (digitPos == string::npos) {
                break; // No more digits to remove
            }
            
            // Find the closest non-digit to the left of the digit
            size_t nonDigitPos = string::npos;
            for (int i = digitPos - 1; i >= 0; --i) {
                if (!isdigit(s[i])) {
                    nonDigitPos = i;
                    break;
                }
            }
            
            if (nonDigitPos == string::npos) {
                // If no non-digit character is found to the left, just remove the digit
                s.erase(digitPos, 1);
            } else {
                // Remove both the non-digit character and the digit
                s.erase(nonDigitPos, 1);
                s.erase(digitPos - 1, 1);
            }
        }
        return s;
    }
};
