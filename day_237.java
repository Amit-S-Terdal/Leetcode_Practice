// 166. Fraction to Recurring Decimal

// Given two integers representing the numerator and denominator of a fraction, return the fraction in string format.

// If the fractional part is repeating, enclose the repeating part in parentheses.

// If multiple answers are possible, return any of them.

// It is guaranteed that the length of the answer string is less than 104 for all the given inputs.

 

// Example 1:

// Input: numerator = 1, denominator = 2
// Output: "0.5"
// Example 2:

// Input: numerator = 2, denominator = 1
// Output: "2"
// Example 3:

// Input: numerator = 4, denominator = 333
// Output: "0.(012)"
 

// Constraints:

// -2^31 <= numerator, denominator <= 2^31 - 1
// denominator != 0



// Solution:


import java.util.HashMap;

class Solution {
    public String fractionToDecimal(int numerator, int denominator) {
        if (numerator == 0) return "0";

        StringBuilder ans = new StringBuilder();
        // Handle sign
        if ((numerator < 0) ^ (denominator < 0)) ans.append('-');
        
        // Convert to long to avoid overflow (Integer.MIN_VALUE)
        long num = Math.abs((long) numerator);
        long den = Math.abs((long) denominator);

        long q = num / den;
        long r = num % den;
        ans.append(q);

        if (r == 0) return ans.toString();

        ans.append('.');
        HashMap<Long, Integer> mp = new HashMap<>();
        StringBuilder frac = new StringBuilder();

        while (r != 0) {
            if (mp.containsKey(r)) {
                frac.insert(mp.get(r), "(");
                frac.append(')');
                break;
            }
            mp.put(r, frac.length());
            r *= 10;
            frac.append((char) ('0' + r / den));
            r %= den;
        }

        return ans.append(frac).toString();
    }
}
