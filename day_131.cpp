// 3170. Lexicographically Minimum String After Removing Stars

// You are given a string s. It may contain any number of '*' characters. Your task is to remove all '*' characters.

// While there is a '*', do the following operation:

// Delete the leftmost '*' and the smallest non-'*' character to its left. If there are several smallest characters, you can delete any of them.
// Return the lexicographically smallest resulting string after removing all '*' characters.

 

// Example 1:

// Input: s = "aaba*"

// Output: "aab"

// Explanation:

// We should delete one of the 'a' characters with '*'. If we choose s[3], s becomes the lexicographically smallest.

// Example 2:

// Input: s = "abc"

// Output: "abc"

// Explanation:

// There is no '*' in the string.

 

// Constraints:

// 1 <= s.length <= 10^5
// s consists only of lowercase English letters and '*'.
// The input is generated such that it is possible to delete all '*' characters.



// Solution:


class Solution {
    public:
        string clearStars(string s) {
        priority_queue<pair<char,int>, vector<pair<char,int>>, greater<pair<char,int>>> minh;
        unordered_map<int,bool> mpp;
            for (int i = 0; i < s.size(); i++) {
                if(s[i] == '*') {
                    mpp[-minh.top().second] = true;
                    minh.pop();
                } else {
                    minh.push({s[i], -i});
                }
            }
            string res;
            for(int i = 0; i < s.size(); i++) {
                if(mpp.count(i) || s[i] == '*') continue;
                res += s[i];
            }
            return res;
        }
    };