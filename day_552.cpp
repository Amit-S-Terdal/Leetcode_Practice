// 3302. Find the Lexicographically Smallest Valid Sequence

// You are given two strings word1 and word2.

// A string x is called almost equal to y if you can change at most one character in x to make it identical to y.

// A sequence of indices seq is called valid if:

// The indices are sorted in ascending order.
// Concatenating the characters at these indices in word1 in the same order results in a string that is almost equal to word2.
// Return an array of size word2.length representing the lexicographically smallest valid sequence of indices. If no such sequence of indices exists, return an empty array.

// Note that the answer must represent the lexicographically smallest array, not the corresponding string formed by those indices.

 

// Example 1:

// Input: word1 = "vbcca", word2 = "abc"

// Output: [0,1,2]

// Explanation:

// The lexicographically smallest valid sequence of indices is [0, 1, 2]:

// Change word1[0] to 'a'.
// word1[1] is already 'b'.
// word1[2] is already 'c'.
// Example 2:

// Input: word1 = "bacdc", word2 = "abc"

// Output: [1,2,4]

// Explanation:

// The lexicographically smallest valid sequence of indices is [1, 2, 4]:

// word1[1] is already 'a'.
// Change word1[2] to 'b'.
// word1[4] is already 'c'.
// Example 3:

// Input: word1 = "aaaaaa", word2 = "aaabc"

// Output: []

// Explanation:

// There is no valid sequence of indices.

// Example 4:

// Input: word1 = "abc", word2 = "ab"

// Output: [0,1]

 

// Constraints:

// 1 <= word2.length < word1.length <= 3 * 10^5
// word1 and word2 consist only of lowercase English letters.


// Solution: 




constexpr int N=3e5;
int last[N]; 
class Solution {
public:
    vector<int> validSequence(string word1, string word2) {
        const int n1=word1.size(), n2=word2.size();
        memset(last, -1, n2*sizeof(int)); 
        last[n2]=n1;
        for (int i=n2-1, j=n1-1; i>=0; i--) {
            const char c2=word2[i];
            while (j>=0 && c2!=word1[j]) j--;
            if (j<0) break;
            last[i]=j--;
        }
        
        vector<int> ans(n2);
        bool skip=0;
        int j=0; // index in word2
        
        for (int i=0; i<n1 && j<n2; i++) {
            const bool same=(word1[i]==word2[j]);
            const bool can_change=(!skip && i<last[j+1]);
            
            if (same | can_change) {
                ans[j++]=i;
                skip|=!same;
            }
        }
        
        return (j==n2)?ans:vector<int>();
    }
};
auto init = []() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);
    cout.tie(nullptr);
    return 'c';
}();