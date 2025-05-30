// 2131. Longest Palindrome by Concatenating Two Letter Words

// You are given an array of strings words. Each element of words consists of two lowercase English letters.

// Create the longest possible palindrome by selecting some elements from words and concatenating them in any order. Each element can be selected at most once.

// Return the length of the longest palindrome that you can create. If it is impossible to create any palindrome, return 0.

// A palindrome is a string that reads the same forward and backward.

 

// Example 1:

// Input: words = ["lc","cl","gg"]
// Output: 6
// Explanation: One longest palindrome is "lc" + "gg" + "cl" = "lcggcl", of length 6.
// Note that "clgglc" is another longest palindrome that can be created.
// Example 2:

// Input: words = ["ab","ty","yt","lc","cl","ab"]
// Output: 8
// Explanation: One longest palindrome is "ty" + "lc" + "cl" + "yt" = "tylcclyt", of length 8.
// Note that "lcyttycl" is another longest palindrome that can be created.
// Example 3:

// Input: words = ["cc","ll","xx"]
// Output: 2
// Explanation: One longest palindrome is "cc", of length 2.
// Note that "ll" is another longest palindrome that can be created, and so is "xx".
 

// Constraints:

// 1 <= words.length <= 10^5
// words[i].length == 2
// words[i] consists of lowercase English letters.


// Solution:


import java.util.HashMap;
import java.util.Map;

class Solution {
    public int longestPalindrome(String[] words) {
        Map<String, Integer> count = new HashMap<>();
        int length = 0;
        boolean hasCenter = false;

        for (String word : words) {
            count.put(word, count.getOrDefault(word, 0) + 1);
        }

        for (String word : count.keySet()) {
            String reversed = new StringBuilder(word).reverse().toString();

            if (!word.equals(reversed)) {
                if (count.containsKey(reversed)) {
                    int pairs = Math.min(count.get(word), count.get(reversed));
                    length += pairs * 4;
                    count.put(word, count.get(word) - pairs);
                    count.put(reversed, count.get(reversed) - pairs);
                }
            } else {
                int pairs = count.get(word) / 2;
                length += pairs * 4;
                count.put(word, count.get(word) - pairs * 2);
            }
        }

        // Check for one remaining word with same letters to use in center
        for (String word : count.keySet()) {
            if (word.charAt(0) == word.charAt(1) && count.get(word) > 0) {
                length += 2;
                break;
            }
        }

        return length;
    }
}
