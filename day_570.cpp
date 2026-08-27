// 3720. Lexicographically Smallest Permutation Greater Than Target

// You are given two strings s and target, both having length n, consisting of lowercase English letters.

// Return the lexicographically smallest permutation of s that is strictly greater than target. If no permutation of s is lexicographically strictly greater than target, return an empty string.

// A string a is lexicographically strictly greater than a string b (of the same length) if in the first position where a and b differ, string a has a letter that appears later in the alphabet than the corresponding letter in b.

 

// Example 1:

// Input: s = "abc", target = "bba"

// Output: "bca"

// Explanation:

// The permutations of s (in lexicographical order) are "abc", "acb", "bac", "bca", "cab", and "cba".
// The lexicographically smallest permutation that is strictly greater than target is "bca".
// Example 2:

// Input: s = "leet", target = "code"

// Output: "eelt"

// Explanation:

// The permutations of s (in lexicographical order) are "eelt", "eetl", "elet", "elte", "etel", "etle", "leet", "lete", "ltee", "teel", "tele", and "tlee".
// The lexicographically smallest permutation that is strictly greater than target is "eelt".
// Example 3:

// Input: s = "baba", target = "bbaa"

// Output: ""

// Explanation:

// The permutations of s (in lexicographical order) are "aabb", "abab", "abba", "baab", "baba", and "bbaa".
// None of them is lexicographically strictly greater than target. Therefore, the answer is "".
 

// Constraints:

// 1 <= s.length == target.length <= 300
// s and target consist of only lowercase English letters.


// Solution: 



class Solution {
public:
    static string lexGreaterPermutation(string& s, string& target) {
        const int n=s.size();
        array<int, 26> freq={0}, freq0;
        unsigned hasC=0, hasC0;
        for (char c : s) {
            const int idx=c-'a';
            if (++freq[idx]==1)
                hasC|=(1u<<idx);
        }

        int diffPos=-1;
        freq0=freq, hasC0=hasC;
        for (int i=0; i<n; i++) {
            int largestC=31-countl_zero(hasC0);
            int idx=target[i]-'a';
            if (largestC < idx) break;
            if (largestC > idx) diffPos=i;
            if (freq0[idx]>0) {
                if (--freq0[idx]==0) hasC0 &=~(1u<<idx);
            } 
            else break;
        }
    //    cout<<diffPos<<endl;
        if (diffPos==-1) return "";

        // rebuild s up to diffPos
        for (int j=0; j<diffPos; j++) {
            int idx=target[j]-'a';
            s[j]=target[j];
            if (--freq[idx]==0) hasC &=~(1u<<idx);
        }

        // increase at diffPos
        int shift=target[diffPos]-'a'+1;
        unsigned higher=hasC>>shift;
        if (higher==0) return "";
        int idx=countr_zero(higher)+shift;
        s[diffPos]='a'+idx;
        if (--freq[idx]==0) hasC&=~(1u<<idx); 

        // fill remaining with smallest
        for (int j=diffPos+1; j<n; j++) {
            idx=countr_zero(hasC);
            if (!hasC) return "";
            s[j]='a'+idx;
            if (--freq[idx]==0) hasC&=~(1u<<idx);
        }

        return s;
    }
};