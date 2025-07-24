// // 1957. Delete Characters to Make Fancy String

// A fancy string is a string where no three consecutive characters are equal.

// Given a string s, delete the minimum possible number of characters from s to make it fancy.

// Return the final string after the deletion. It can be shown that the answer will always be unique.

 

// Example 1:

// Input: s = "leeetcode"
// Output: "leetcode"
// Explanation:
// Remove an 'e' from the first group of 'e's to create "leetcode".
// No three consecutive characters are equal, so return "leetcode".
// Example 2:

// Input: s = "aaabaaaa"
// Output: "aabaa"
// Explanation:
// Remove an 'a' from the first group of 'a's to create "aabaaaa".
// Remove two 'a's from the second group of 'a's to create "aabaa".
// No three consecutive characters are equal, so return "aabaa".
// Example 3:

// Input: s = "aab"
// Output: "aab"
// Explanation: No three consecutive characters are equal, so return "aab".
 

// Constraints:

// 1 <= s.length <= 105
// s consists only of lowercase English letters.


// Solution :

//𝓟𝓵𝓮𝓪𝓼𝓮 𝓤𝓟𝓥𝓞𝓣𝓔 𝓲𝓯 𝓲𝓽 𝓱𝓮𝓵𝓹𝓼👍🏻
class Solution {
    public String makeFancyString(String s) {
        char[] chars = s.toCharArray();
        char last = chars[0];
        int count = 1;
        int pos = 1;

        for (int i = 1; i < chars.length; i++) {
            if (chars[i] != last) {
                last = chars[i];
                count = 0;
            }

            if (++count > 2) continue;

            chars[pos++] = chars[i];
        }

        return new String(chars, 0, pos);
    }
}
