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


class Solution {
public:
    string fractionToDecimal(int numerator, int denominator) {
        if (numerator==0) return "0";
        
        string ans;
        // Handle sign
        if ((numerator<0)^(denominator< 0)) ans+='-';
        // Convert to long to avoid overflow (INT_MIN)
        long long num=abs((long long)numerator);
        long long den=abs((long long)denominator);

        long long q=num/den;
        long long r=num%den;
        ans+=to_string(q);

        if (r==0) return ans;

        ans+= '.';
        unordered_map<long long, int> mp;
        string frac;

        for(int i=0; r != 0; i++) {
            if (mp.count(r)) {
                frac.insert(mp[r], "(");
                frac+= ')';
                break;
            }
            mp[r]=i;
            r*=10;
            frac+=('0'+r/den);
            r%=den;
        }

        return ans+frac;
    }
};