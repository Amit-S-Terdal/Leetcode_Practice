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

import java.util.*;

class Solution {
    public List<String> wordSubsets(String[] words1, String[] words2) {
        // Step 1: Calculate the maximum frequency of each character required by words2
        int[] maxFreq = new int[26];
        for (String word : words2) {
            int[] wordFreq = countFrequency(word);
            for (int i = 0; i < 26; i++) {
                maxFreq[i] = Math.max(maxFreq[i], wordFreq[i]);
            }
        }

        // Step 2: Filter words1 based on whether they satisfy the maxFreq condition
        List<String> result = new ArrayList<>();
        for (String word : words1) {
            int[] wordFreq = countFrequency(word);
            boolean isUniversal = true;
            for (int i = 0; i < 26; i++) {
                if (wordFreq[i] < maxFreq[i]) {
                    isUniversal = false;
                    break;
                }
            }
            if (isUniversal) {
                result.add(word);
            }
        }

        return result;
    }

    // Helper method to count the frequency of each character in a string
    private int[] countFrequency(String word) {
        int[] freq = new int[26];
        for (char c : word.toCharArray()) {
            freq[c - 'a']++;
        }
        return freq;
    }
}
