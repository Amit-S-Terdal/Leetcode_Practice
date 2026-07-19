// 1081. Smallest Subsequence of Distinct Characters

// Given a string s, return the lexicographically smallest subsequence of s that contains all the distinct characters of s exactly once.

 

// Example 1:

// Input: s = "bcabc"
// Output: "abc"
// Example 2:

// Input: s = "cbacdcbc"
// Output: "acdb"
 

// Constraints:

// 1 <= s.length <= 1000
// s consists of lowercase English letters.

// Solution:




const int N=1000;
char st[N];
int top;
class Solution {
public:
    string smallestSubsequence(string& s) {
        int freq[26]={0};
        for(char c: s) freq[c-'a']++;
        top=-1;
        bitset<26> seen;
        for(char c: s){
            const int idx=c-'a';
            freq[idx]--;
            if (seen[idx]) continue;
            while(top>-1 && st[top]>c && freq[st[top]-'a']>0){
                seen[st[top--]-'a']=0;
            }
            st[++top]=c;
            seen[idx]=1;
        }
        return string(st, st+top+1);
    }
};