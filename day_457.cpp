// 788. Rotated Digits

// An integer x is a good if after rotating each digit individually by 180 degrees, we get a valid number that is different from x. Each digit must be rotated - we cannot choose to leave it alone.

// A number is valid if each digit remains a digit after rotation. For example:

// 0, 1, and 8 rotate to themselves,
// 2 and 5 rotate to each other (in this case they are rotated in a different direction, in other words, 2 or 5 gets mirrored),
// 6 and 9 rotate to each other, and
// the rest of the numbers do not rotate to any other number and become invalid.
// Given an integer n, return the number of good integers in the range [1, n].

 

// Example 1:

// Input: n = 10
// Output: 4
// Explanation: There are four good numbers in the range [1, 10] : 2, 5, 6, 9.
// Note that 1 and 10 are not good numbers, since they remain unchanged after rotating.
// Example 2:

// Input: n = 1
// Output: 0
// Example 3:

// Input: n = 2
// Output: 1
 

// Constraints:

// 1 <= n <= 10^4




// Solution: 




int dp[7][2][10];
unsigned mask=(1<<2)|(1<<5)|(1<<6)|(1<<9);
unsigned invalid=(1<<3)|(1<<4)|(1<<7);
class Solution {
public:
    static int  f(int i, bool tight, int prev, string& s){
        if (i==6) return prev>0;
        if (dp[i][tight][prev]!=-1) return dp[i][tight][prev];
        int cnt=0;

        int lim=tight?(s[i]-'0'):9;

        for(int d=0; d<=lim; d++){
            bool nxtTight=tight & (d==lim);
            if ((1<<d)& invalid) continue;
            // Apply Good condition
            if ((1<<d) & mask) cnt+=f(i+1, nxtTight, d, s);
            else cnt+=f(i+1, nxtTight, prev, s);
        }
        return dp[i][tight][prev]=cnt;
    }
    static inline string toS(int n){
        string s(6, '0');
        for(int x=n, i=5; x; x/=10, i--){
            s[i]='0'+x%10;
        }
        return s;
    }
    static int rotatedDigits(int n) {
        string s=toS(n);
        memset(dp, -1, sizeof(dp));
        return f(0, 1, 0, s);
    }
};