// 3517. Smallest Palindromic Rearrangement I

// You are given a palindromic string s.

// Return the lexicographically smallest palindromic permutation of s.

 

// Example 1:

// Input: s = "z"

// Output: "z"

// Explanation:

// A string of only one character is already the lexicographically smallest palindrome.

// Example 2:

// Input: s = "babab"

// Output: "abbba"

// Explanation:

// Rearranging "babab" → "abbba" gives the smallest lexicographic palindrome.

// Example 3:

// Input: s = "daccad"

// Output: "acddca"

// Explanation:

// Rearranging "daccad" → "acddca" gives the smallest lexicographic palindrome.

 

// Constraints:

// 1 <= s.length <= 105
// s consists of lowercase English letters.
// s is guaranteed to be palindromic.



// Solution: 

class Solution {
public:
    static string smallestPalindrome(string& s) {
        const int n=s.size(), n0=n/2;
        int freq[26]={0};
        for(int i=0; i<n0; i++)
            freq[s[i]-'a']++;
        int l=0;
        for(int x=0; x<26; x++){
            const int f=freq[x];
            if (f==0) continue;
            const char c='a'+x;
            fill(s.begin()+l, s.begin()+l+f, c);
            fill(s.rbegin()+l, s.rbegin()+l+f, c);
            l+=f;
        }
        return s;
    }
};