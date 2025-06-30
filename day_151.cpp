// 2014. Longest Subsequence Repeated k Times

// You are given a string s of length n, and an integer k. You are tasked to find the longest subsequence repeated k times in string s.

// A subsequence is a string that can be derived from another string by deleting some or no characters without changing the order of the remaining characters.

// A subsequence seq is repeated k times in the string s if seq * k is a subsequence of s, where seq * k represents a string constructed by concatenating seq k times.

// For example, "bba" is repeated 2 times in the string "bababcba", because the string "bbabba", constructed by concatenating "bba" 2 times, is a subsequence of the string "bababcba".
// Return the longest subsequence repeated k times in string s. If multiple such subsequences are found, return the lexicographically largest one. If there is no such subsequence, return an empty string.

 

// Example 1:

// example 1
// Input: s = "letsleetcode", k = 2
// Output: "let"
// Explanation: There are two longest subsequences repeated 2 times: "let" and "ete".
// "let" is the lexicographically largest one.
// Example 2:

// Input: s = "bb", k = 2
// Output: "b"
// Explanation: The longest subsequence repeated 2 times is "b".
// Example 3:

// Input: s = "ab", k = 2
// Output: ""
// Explanation: There is no subsequence repeated 2 times. Empty string is returned.
 

// Constraints:

// n == s.length
// 2 <= n, k <= 2000
// 2 <= n < k * 8
// s consists of lowercase English letters.


// Solution:


class Solution {
    public:
        int n, maxLen=0;
        int freq[26]={0};
        bool repeatK(const string& s, const string& t, int m, int k){
            for (int i=0, j=0; i<n && k>0; i++) {
                if (s[i]==t[j]) {
                    j++;
                    if (j==m) {
                        k--;
                        j=0;
                    }
                }
            }
        //    cout<<"k="<<k<<endl;
            return k==0;
        }
    
        string ans="";
        vector<char> chars;
        void dfs(const string& s, string& t, int k){
            const int m=t.size();
            if (m>maxLen) return;
            if (m>0 && !repeatK(s, t, m, k)) return;  
            if (m>ans.size()) ans=t;
            for (char c : chars){
                if (freq[c-'a']<k) continue;
                freq[c-'a']-=k;// Use c k times
                t.push_back(c);
                dfs(s, t, k);
                t.pop_back();// backtracking
                freq[c-'a']+=k;
            }
        }
    
        string longestSubsequenceRepeatedK(string& s, int k) {
            bitset<26> hasX=0;
            for(char c: s){
                const int x=c-'a';
                if (++freq[x]==k)
                    hasX[x]=1;
            }
            if (hasX==0) return "";
    
            // reuse s, rebuild the string s for c in s with freq[c-'a]>=k 
            // it's faster for checking if a subsequence is reapted k times
            int j=0;
            n=s.size();
            for(int i=0; i<n; i++){
                const int x=s[i]-'a';
                if (hasX[x]) s[j++]=s[i];
            }
            s.resize(j);
            n=j;
            maxLen=n/k;
            chars.reserve(26);
            for(int i=25; i>=0; i--)// lexico large first
                if (hasX[i]) chars.push_back('a'+i);
            string t="";
            dfs(s, t, k);
        
            return ans;
        }
    };