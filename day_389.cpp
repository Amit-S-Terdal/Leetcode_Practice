// 1461. Check If a String Contains All Binary Codes of Size K

// Given a binary string s and an integer k, return true if every binary code of length k is a substring of s. Otherwise, return false.

 

// Example 1:

// Input: s = "00110110", k = 2
// Output: true
// Explanation: The binary codes of length 2 are "00", "01", "10" and "11". They can be all found as substrings at indices 0, 1, 3 and 2 respectively.
// Example 2:

// Input: s = "0110", k = 1
// Output: true
// Explanation: The binary codes of length 1 are "0" and "1", it is clear that both exist as a substring. 
// Example 3:

// Input: s = "0110", k = 2
// Output: false
// Explanation: The binary code "00" is of length 2 and does not exist in the array.
 

// Constraints:

// 1 <= s.length <= 5 * 10^5
// s[i] is either '0' or '1'.
// 1 <= k <= 20



// Solution: 


constexpr int N=1<<20;
bitset<N> seen;
class Solution {
public:
    bool hasAllCodes(string& s, int k) {
        const int n=s.size();
        if (n<k) return 0;
        unsigned mask=0;
        const int M=(1<<(k-1))-1;
        seen.reset();
        for(int i=0; i<k; i++) //MSB
            mask=(mask<<1)+(s[i]-'0');
        seen[mask]=1;
    //    cout<<mask<<", ";
        for(int l=0, r=k; r<n; r++){
            mask&=M;
            mask<<=1;
            mask|=(s[r]-'0');
            seen[mask]=1;
    //        cout<<mask<<", ";
        }
        return seen.count()==(1<<k);
    }
};