// 67. Add Binary

// Given two binary strings a and b, return their sum as a binary string.

 

// Example 1:

// Input: a = "11", b = "1"
// Output: "100"
// Example 2:

// Input: a = "1010", b = "1011"
// Output: "10101"
 

// Constraints:

// 1 <= a.length, b.length <= 10^4
// a and b consist only of '0' or '1' characters.
// Each string does not contain leading zeros except for the zero itself.



// Solution: 



class Solution {
    public String addBinary(String a, String b) {
        int l_a = a.length();
        int l_b = b.length();
        int M = Math.max(l_a, l_b);
        
        StringBuilder sb = new StringBuilder();
        int carry = 0;
        
        int i = l_a - 1;
        int j = l_b - 1;
        
        while (i >= 0 || j >= 0 || carry != 0) {
            int bitA = (i >= 0) ? a.charAt(i--) - '0' : 0;
            int bitB = (j >= 0) ? b.charAt(j--) - '0' : 0;
            
            int sum = bitA + bitB + carry;
            sb.append(sum % 2);
            carry = sum / 2;
        }
        
        return sb.reverse().toString();
    }
}
