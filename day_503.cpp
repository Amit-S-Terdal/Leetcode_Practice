// 3614. Process String with Special Operations II

// You are given a string s consisting of lowercase English letters and the special characters: '*', '#', and '%'.

// You are also given an integer k.

// Build a new string result by processing s according to the following rules from left to right:

// If the letter is a lowercase English letter append it to result.
// A '*' removes the last character from result, if it exists.
// A '#' duplicates the current result and appends it to itself.
// A '%' reverses the current result.
// Return the kth character of the final string result. If k is out of the bounds of result, return '.'.

 

// Example 1:

// Input: s = "a#b%*", k = 1

// Output: "a"

// Explanation:

// i	s[i]	Operation	Current result
// 0	'a'	Append 'a'	"a"
// 1	'#'	Duplicate result	"aa"
// 2	'b'	Append 'b'	"aab"
// 3	'%'	Reverse result	"baa"
// 4	'*'	Remove the last character	"ba"
// The final result is "ba". The character at index k = 1 is 'a'.

// Example 2:

// Input: s = "cd%#*#", k = 3

// Output: "d"

// Explanation:

// i	s[i]	Operation	Current result
// 0	'c'	Append 'c'	"c"
// 1	'd'	Append 'd'	"cd"
// 2	'%'	Reverse result	"dc"
// 3	'#'	Duplicate result	"dcdc"
// 4	'*'	Remove the last character	"dcd"
// 5	'#'	Duplicate result	"dcddcd"
// The final result is "dcddcd". The character at index k = 3 is 'd'.

// Example 3:

// Input: s = "z*#", k = 0

// Output: "."

// Explanation:

// i	s[i]	Operation	Current result
// 0	'z'	Append 'z'	"z"
// 1	'*'	Remove the last character	""
// 2	'#'	Duplicate the string	""
// The final result is "". Since index k = 0 is out of bounds, the output is '.'.

 

// Constraints:

// 1 <= s.length <= 10^5
// s consists of only lowercase English letters and special characters '*', '#', and '%'.
// 0 <= k <= 10^15
// The length of result after processing s will not exceed 10^15.



// Solution: 




// '#'=35, '%'='37, '*'=42, 'a'=97=64+33
using ll=long long;
constexpr int N=1e5;
static ll Len[N]={0};

class Solution {
public:
    static char processStr(string& s, long long k) {
        const int n=s.size();
        ll L=0;
        int i=0;
        for (char c : s) {
            L+=(c>=97)-((L>0) & (c==42));
            L<<=(c==35);
            Len[i++]=L;
        }
        if (L-1<k) return '.';
        
        bool flag=0;
        for (; --i>=0 && !flag;) {
            const char c=s[i];
            L=Len[i]>>(c==35);
            k=(-(ll)(c==37) & (L-1-k))+(-(ll)(c!=37) & k);
            k-=(-(ll)((c==35) & (k>=L)) & L);
            flag=((c>=97) & (k==L-1));
        }
        return !flag? '.':s[++i];
    }
};
auto init = []() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);
    cout.tie(nullptr);
    return 'c';
}();