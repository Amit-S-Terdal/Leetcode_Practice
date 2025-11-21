// 1930. Unique Length-3 Palindromic Subsequences

// Given a string s, return the number of unique palindromes of length three that are a subsequence of s.

// Note that even if there are multiple ways to obtain the same subsequence, it is still only counted once.

// A palindrome is a string that reads the same forwards and backwards.

// A subsequence of a string is a new string generated from the original string with some characters (can be none) deleted without changing the relative order of the remaining characters.

// For example, "ace" is a subsequence of "abcde".
 

// Example 1:

// Input: s = "aabca"
// Output: 3
// Explanation: The 3 palindromic subsequences of length 3 are:
// - "aba" (subsequence of "aabca")
// - "aaa" (subsequence of "aabca")
// - "aca" (subsequence of "aabca")
// Example 2:

// Input: s = "adc"
// Output: 0
// Explanation: There are no palindromic subsequences of length 3 in "adc".
// Example 3:

// Input: s = "bbcbaba"
// Output: 4
// Explanation: The 4 palindromic subsequences of length 3 are:
// - "bbb" (subsequence of "bbcbaba")
// - "bcb" (subsequence of "bbcbaba")
// - "bab" (subsequence of "bbcbaba")
// - "aba" (subsequence of "bbcbaba")
 

// Constraints:

// 3 <= s.length <= 10^5
// s consists of only lowercase English letters.



// Solution:


int n;
int ll[26], rr[26];
class Solution {
public:
    static inline void find_ll_rr(string& s){
        fill(ll, ll+26, n);
        memset(rr, -1, sizeof(rr));
        
        for(unsigned i=0; i<n; i++){
            unsigned x=s[i]-'a';
            if (ll[x]==n){
                ll[x]=i;
            }
            rr[x]=i;
        }
    }

    static inline void find_ll_rr_2(string& s){
        fill(ll, ll+26, n);
        memset(rr, -1, sizeof(rr));
    
        int seen=0;
        
        for(unsigned i=0; i<n && seen<26; i++){
            unsigned x=s[i]-'a';
            if (ll[x]==n){
                ll[x]=i;
                seen++;
            }
        }
    
        for(unsigned i=n-1; i>=0 && seen>0; i--){
            unsigned x=s[i]-'a';
            if (rr[x]==-1){
                rr[x]=i;
                seen--;
            }
        }
    }

    static inline int hasPal3(char x,  string& s, int l, int r){
        unsigned m;
        bitset<26> seen=0;

        for(m=l+1; m<r; m++){
            seen[s[m]-'a']=1;
        } 
        return seen.count();
    }

    static int countPalindromicSubsequence(string& s) {
        n=s.size();
        find_ll_rr(s);
        int count=0;
    
        for(char x='a'; x<='z'; x++){
            int l=ll[x-'a'], r=rr[x-'a'];
            if (l+1>=r) continue;
            count+=hasPal3(x, s, l, r);
        }   
        return count;
    }
};


auto init = []()
{ 
    ios::sync_with_stdio(0);
    cin.tie(0);
    cout.tie(0);
    return 'c';
}();