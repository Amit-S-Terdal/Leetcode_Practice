// 2311. Longest Binary Subsequence Less Than or Equal to K


// You are given a binary string s and a positive integer k.

// Return the length of the longest subsequence of s that makes up a binary number less than or equal to k.

// Note:

// The subsequence can contain leading zeroes.
// The empty string is considered to be equal to 0.
// A subsequence is a string that can be derived from another string by deleting some or no characters without changing the order of the remaining characters.
 

// Example 1:

// Input: s = "1001010", k = 5
// Output: 5
// Explanation: The longest subsequence of s that makes up a binary number less than or equal to 5 is "00010", as this number is equal to 2 in decimal.
// Note that "00100" and "00101" are also possible, which are equal to 4 and 5 in decimal, respectively.
// The length of this subsequence is 5, so 5 is returned.
// Example 2:

// Input: s = "00101001", k = 1
// Output: 6
// Explanation: "000001" is the longest subsequence of s that makes up a binary number less than or equal to 1, as this number is equal to 1 in decimal.
// The length of this subsequence is 6, so 6 is returned.
 

// Constraints:

// 1 <= s.length <= 1000
// s[i] is either '0' or '1'.
// 1 <= k <= 10^9


// Solution:


class Solution {
    public:
        int longestSubsequence(string& s, int k) {
            const int n=s.size();
            const int wz=33-countl_zero((unsigned)k);// window size
            vector<int> suff(n+1);
            long long x=0;
            for(int l=n-1, r=n-1; l>=0; l--){
                if(s[l]=='1') x+=(1LL<<(r-l));
                if (r-l>=wz) r--;// shrink widow, move r
                suff[l]=suff[l+1]+(x<=k);
                if (x>k){
                    x>>=1;// if x>k, remove the least bit
                    r--;
                }
            }
            int ans=0, cnt0=0;
            for(int i=0; i<n; i++){
                ans=max(ans, cnt0+suff[i]);
                cnt0+=(s[i]=='0');// running sum
            }
            return ans;
        }
    };