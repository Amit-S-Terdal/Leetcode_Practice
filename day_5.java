// 2185. Counting Words With a Given Prefix

// You are given an array of strings words and a string pref.

// Return the number of strings in words that contain pref as a prefix.

// A prefix of a string s is any leading contiguous substring of s.

 

// Example 1:

// Input: words = ["pay","attention","practice","attend"], pref = "at"
// Output: 2
// Explanation: The 2 strings that contain "at" as a prefix are: "attention" and "attend".
// Example 2:

// Input: words = ["leetcode","win","loops","success"], pref = "code"
// Output: 0
// Explanation: There are no strings that contain "code" as a prefix.
 

// Constraints:

// 1 <= words.length <= 100
// 1 <= words[i].length, pref.length <= 100
// words[i] and pref consist of lowercase English letters.

// SOLUTION:

import java.util.*;

public class Solution {
    public int prefixCount(String[] words, String pref) {
        int count = 0;
        int prefLength = pref.length();

        for (String word : words) {
            // Check if the word starts with the prefix
            if (word.length() >= prefLength && word.substring(0, prefLength).equals(pref)) {
                count++;
            }
        }

        return count;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();

        // Example 1
        String[] words1 = {"pay", "attention", "practice", "attend"};
        String pref1 = "at";
        System.out.println("Example 1 Output: " + solution.prefixCount(words1, pref1)); // Output: 2

        // Example 2
        String[] words2 = {"leetcode", "win", "loops", "success"};
        String pref2 = "code";
        System.out.println("Example 2 Output: " + solution.prefixCount(words2, pref2)); // Output: 0
    }
}
