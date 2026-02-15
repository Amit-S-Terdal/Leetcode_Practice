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



// bitset version
class Solution {
public:
    string addBinary(string a, string b) {
        const int l_a=a.length(), l_b=b.length();
        const int M=max(l_a+1, l_b+1);
        bitset<10001> A(a), B(b);
        char C[M+1];
        C[M]='\0';
        int carry=0;
        for(int i=0; i<M; i++){
            int tmp=A[i]+B[i]+carry;
            C[M-1-i]='0'+(tmp&1);
            carry=tmp>1;
        }
        return (C[0]=='0') ? string(C+1):string(C);
    }
};