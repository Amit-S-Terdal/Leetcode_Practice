// 1888. Minimum Number of Flips to Make the Binary String Alternating

// You are given a binary string s. You are allowed to perform two types of operations on the string in any sequence:

// Type-1: Remove the character at the start of the string s and append it to the end of the string.
// Type-2: Pick any character in s and flip its value, i.e., if its value is '0' it becomes '1' and vice-versa.
// Return the minimum number of type-2 operations you need to perform such that s becomes alternating.

// The string is called alternating if no two adjacent characters are equal.

// For example, the strings "010" and "1010" are alternating, while the string "0100" is not.
 

// Example 1:

// Input: s = "111000"
// Output: 2
// Explanation: Use the first operation two times to make s = "100011".
// Then, use the second operation on the third and sixth elements to make s = "101010".
// Example 2:

// Input: s = "010"
// Output: 0
// Explanation: The string is already alternating.
// Example 3:

// Input: s = "1110"
// Output: 1
// Explanation: Use the second operation on the second element to make s = "1010".
 

// Constraints:

// 1 <= s.length <= 10^5
// s[i] is either '0' or '1'.



// Solution:



uint64_t pre[100001];
class Solution {
public:
    static int minFlips(string& s) {
        const int n=s.size();
        pre[0]=0;
        int cnt0=0, cnt1=0;
        bool flip=0;
        for (int i=0; i<n; i++, flip=!flip) { // flip 
            cnt1+=(s[i]-'0'==flip);       // accumulate prefix sum
            cnt0=(i+1)-cnt1;
            pre[i+1]=((uint64_t)cnt0<<32)+cnt1;
        }

        uint64_t ans=min((uint64_t)(pre[n] & UINT_MAX), pre[n]>>32);
        
        if ((n&1)==0) return ans;

        for (int i=1; i<n; i++) {
            // Prefix cost is swapped due to odd length shift
            const uint64_t s0=(pre[n]>>32)-(pre[i]>>32);
            const uint64_t s1=(pre[n] & UINT_MAX)-(pre[i] & UINT_MAX);
            const uint64_t p0=pre[i]>>32;
            const uint64_t p1=pre[i] & UINT_MAX;

            // New cost = (Suffix Pattern X) + (Prefix Pattern NOT X)
            ans=min(ans, min(s0+p1, s1+p0));
        }
        return ans;
    }
};