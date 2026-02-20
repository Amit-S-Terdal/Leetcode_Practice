// 761. Special Binary String

// Special binary strings are binary strings with the following two properties:

// The number of 0's is equal to the number of 1's.
// Every prefix of the binary string has at least as many 1's as 0's.
// You are given a special binary string s.

// A move consists of choosing two consecutive, non-empty, special substrings of s, and swapping them. Two strings are consecutive if the last character of the first string is exactly one index before the first character of the second string.

// Return the lexicographically largest resulting string possible after applying the mentioned operations on the string.

 

// Example 1:

// Input: s = "11011000"
// Output: "11100100"
// Explanation: The strings "10" [occuring at s[1]] and "1100" [at s[3]] are swapped.
// This is the lexicographically largest string possible after some number of swaps.
// Example 2:

// Input: s = "10"
// Output: "10"
 

// Constraints:

// 1 <= s.length <= 50
// s[i] is either '0' or '1'.
// s is a special binary string.




// Solution: 




class Solution {
public:
    string makeLargestSpecial(string s) {
        int count = 0, i = 0;
        vector<string> res;
        
        for (int j = 0; j < s.length(); j++) {
            // Track balance: +1 for '1', -1 for '0'
            count += (s[j] == '1') ? 1 : -1;
            
            // Found a balanced chunk when count returns to 0
            if (count == 0) {
                // Recursively maximize inner part, wrap with 1...0
                res.push_back('1' + makeLargestSpecial(s.substr(i + 1, j - i - 1)) + '0');
                i = j + 1; // Move to next potential chunk
            }
        }
        
        // Sort chunks in descending order for largest arrangement
        sort(res.begin(), res.end(), greater<string>());
        
        string result;
        for (const string& str : res) {
            result += str;
        }
        return result;
    }
};