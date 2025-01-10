// 916. Word Subsets

// You are given two string arrays words1 and words2.

// A string b is a subset of string a if every letter in b occurs in a including multiplicity.

// For example, "wrr" is a subset of "warrior" but is not a subset of "world".
// A string a from words1 is universal if for every string b in words2, b is a subset of a.

// Return an array of all the universal strings in words1. You may return the answer in any order.

 

// Example 1:

// Input: words1 = ["amazon","apple","facebook","google","leetcode"], words2 = ["e","o"]
// Output: ["facebook","google","leetcode"]
// Example 2:

// Input: words1 = ["amazon","apple","facebook","google","leetcode"], words2 = ["l","e"]
// Output: ["apple","google","leetcode"]
 

// Constraints:

// 1 <= words1.length, words2.length <= 104
// 1 <= words1[i].length, words2[i].length <= 10
// words1[i] and words2[i] consist only of lowercase English letters.
// All the strings of words1 are unique.

// SOLUTION:

#include <vector>
#include <string>
#include <unordered_map>
#include <algorithm>

class Solution {
public:
    vector<string> wordSubsets(vector<string>& words1, vector<string>& words2) {
        vector<string> result;
        
        // Step 1: Calculate the maximum frequency requirement from words2
        unordered_map<char, int> required_freq;
        
        for (const string& word : words2) {
            unordered_map<char, int> word_freq;
            for (char c : word) {
                word_freq[c]++;
            }
            // Update the maximum frequency requirement for each character
            for (const auto& [c, count] : word_freq) {
                required_freq[c] = max(required_freq[c], count);
            }
        }
        
        // Step 2: Check each word in words1 to see if it satisfies the required frequencies
        for (const string& word : words1) {
            unordered_map<char, int> word_freq;
            for (char c : word) {
                word_freq[c]++;
            }
            
            // Check if the word satisfies all the frequency requirements
            bool is_universal = true;
            for (const auto& [c, count] : required_freq) {
                if (word_freq[c] < count) {
                    is_universal = false;
                    break;
                }
            }
            
            if (is_universal) {
                result.push_back(word);
            }
        }
        
        return result;
    }
};
