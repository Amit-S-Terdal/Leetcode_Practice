// 1400. Construct K Palindrome Strings

// Given a string s and an integer k, return true if you can use all the characters in s to construct k palindrome strings or false otherwise.

 

// Example 1:

// Input: s = "annabelle", k = 2
// Output: true
// Explanation: You can construct two palindromes using all characters in s.
// Some possible constructions "anna" + "elble", "anbna" + "elle", "anellena" + "b"
// Example 2:

// Input: s = "leetcode", k = 3
// Output: false
// Explanation: It is impossible to construct 3 palindromes using all the characters of s.
// Example 3:

// Input: s = "true", k = 4
// Output: true
// Explanation: The only possible solution is to put each character in a separate string.
 

// Constraints:

// 1 <= s.length <= 105
// s consists of lowercase English letters.
// 1 <= k <= 105

// SOLUTION:


#include <unordered_map>
#include <string>
using namespace std;
class Solution {
public:
    bool canConstruct(string s, int k) {
        // Early exits
        if (k > s.length()) return false;
        if (k == s.length()) return true;

        // Count character frequencies using a fixed-size array
        vector<int> freq(26, 0);
        for (char c : s) {
            freq[c - 'a']++;
        }

        // Count characters with odd frequencies
        int oddCount = 0;
        for (int count : freq) {
            if (count % 2 != 0) {
                oddCount++;
            }
        }

        // At most `oddCount` palindromes are needed for odd characters
        return oddCount <= k;
    }
};
