// 3306. Count of Substrings Containing Every Vowel and K Consonants II

// You are given a string word and a non-negative integer k.

// Return the total number of substrings of word that contain every vowel ('a', 'e', 'i', 'o', and 'u') at least once and exactly k consonants.

 

// Example 1:

// Input: word = "aeioqq", k = 1

// Output: 0

// Explanation:

// There is no substring with every vowel.

// Example 2:

// Input: word = "aeiou", k = 0

// Output: 1

// Explanation:

// The only substring with every vowel and zero consonants is word[0..4], which is "aeiou".

// Example 3:

// Input: word = "ieaouqqieaouqq", k = 1

// Output: 3

// Explanation:

// The substrings with every vowel and one consonant are:

// word[0..5], which is "ieaouq".
// word[6..11], which is "qieaou".
// word[7..12], which is "ieaouq".
 

// Constraints:

// 5 <= word.length <= 2 * 105
// word consists only of lowercase English letters.
// 0 <= k <= word.length - 5


// Solution:

class Solution {
    public long countOfSubstrings(String word, int k) {
        int[][] frequencies = new int[2][128];
        frequencies[0]['a'] = 1;
        frequencies[0]['e'] = 1;
        frequencies[0]['i'] = 1;
        frequencies[0]['o'] = 1;
        frequencies[0]['u'] = 1;

        long response = 0;
        int currentK = 0, vowels = 0, extraLeft = 0;

        for (int right = 0, left = 0; right < word.length(); right++) {
            char rightChar = word.charAt(right);

            if (frequencies[0][rightChar] == 1) {
                if (++frequencies[1][rightChar] == 1) vowels++;
            } else {
                currentK++;
            }

            while (currentK > k) {
                char leftChar = word.charAt(left);
                if (frequencies[0][leftChar] == 1) {
                    if (--frequencies[1][leftChar] == 0) vowels--;
                } else {
                    currentK--;
                }
                left++;
                extraLeft = 0;
            }

            while (vowels == 5 && currentK == k && left < right && frequencies[0][word.charAt(left)] == 1 && frequencies[1][word.charAt(left)] > 1) {
                extraLeft++;
                frequencies[1][word.charAt(left)]--;
                left++;
            }

            if (currentK == k && vowels == 5) {
                response += (1 + extraLeft);
            }
        }

        return response;
    }
}

