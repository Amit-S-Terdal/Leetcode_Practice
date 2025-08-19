// 1323. Maximum 69 Number

// You are given a positive integer num consisting only of digits 6 and 9.

// Return the maximum number you can get by changing at most one digit (6 becomes 9, and 9 becomes 6).

 

// Example 1:

// Input: num = 9669
// Output: 9969
// Explanation: 
// Changing the first digit results in 6669.
// Changing the second digit results in 9969.
// Changing the third digit results in 9699.
// Changing the fourth digit results in 9666.
// The maximum number is 9969.
// Example 2:

// Input: num = 9996
// Output: 9999
// Explanation: Changing the last digit 6 to 9 results in the maximum number.
// Example 3:

// Input: num = 9999
// Output: 9999
// Explanation: It is better not to apply any change.
 

// Constraints:

// 1 <= num <= 10^4
// num consists of only 6 and 9 digits.


// Solution:


class Solution {
    public int maximum69Number (int num) {
        int a = -1;
        int d = 0;
        int n = num;
        
        // Find the rightmost 6 (lowest digit position) to replace
        while (n > 0) {
            int r = n % 10;
            if (r == 6) {
                a = d;  // store the position of the 6
            }
            n /= 10;
            d++;
        }
        
        // If no 6 was found, return num as is
        if (a == -1) return num;
        
        // Add 3 * 10^a to turn that 6 into a 9
        return num + (int)(3 * Math.pow(10, a));
    }
}
