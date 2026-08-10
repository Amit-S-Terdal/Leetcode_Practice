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




class Solution {
    public int[] validSequence(String word1, String word2) {
        int n1 = word1.length();
        int n2 = word2.length();

        int[] last = new int[n2 + 1];

        // Equivalent to memset(last, -1, ...)
        java.util.Arrays.fill(last, -1);

        last[n2] = n1;

        int j = n1 - 1;

        for (int i = n2 - 1; i >= 0; i--) {
            char c2 = word2.charAt(i);

            while (j >= 0 && c2 != word1.charAt(j)) {
                j--;
            }

            if (j < 0) {
                break;
            }

            last[i] = j--;
        }

        int[] ans = new int[n2];

        boolean skip = false;
        j = 0; // index in word2

        for (int i = 0; i < n1 && j < n2; i++) {
            boolean same = word1.charAt(i) == word2.charAt(j);
            boolean canChange = (!skip && i < last[j + 1]);

            if (same || canChange) {
                ans[j++] = i;

                if (!same) {
                    skip = true;
                }
            }
        }

        return (j == n2) ? ans : new int[0];
    }
}