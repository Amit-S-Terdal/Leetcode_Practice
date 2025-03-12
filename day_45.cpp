
// 1358. Number of Substrings Containing All Three Characters

// Hint
// Given a string s consisting only of characters a, b and c.

// Return the number of substrings containing at least one occurrence of all these characters a, b and c.

 

// Example 1:

// Input: s = "abcabc"
// Output: 10
// Explanation: The substrings containing at least one occurrence of the characters a, b and c are "abc", "abca", "abcab", "abcabc", "bca", "bcab", "bcabc", "cab", "cabc" and "abc" (again). 
// Example 2:

// Input: s = "aaacb"
// Output: 3
// Explanation: The substrings containing at least one occurrence of the characters a, b and c are "aaacb", "aacb" and "acb". 
// Example 3:

// Input: s = "abc"
// Output: 1
 

// Constraints:

// 3 <= s.length <= 5 x 10^4
// s only consists of a, b or c characters.

// Solution:



class Solution {
    public:
        int numberOfSubstrings(string s) {
            vector<int> lastSeen(3, -1);
            int count = 0;
    
            for (int i = 0; i < s.size(); i++) {
                lastSeen[s[i] - 'a'] = i;
                if (lastSeen[0] != -1 && lastSeen[1] != -1 && lastSeen[2] != -1) {
                    count += 1 + min({lastSeen[0], lastSeen[1], lastSeen[2]});
                }
            }
            return count;
        }
    };
                                                                                            