// 1189. Maximum Number of Balloons

// Given a string text, you want to use the characters of text to form as many instances of the word "balloon" as possible.

// You can use each character in text at most once. Return the maximum number of instances that can be formed.

 

// Example 1:



// Input: text = "nlaebolko"
// Output: 1
// Example 2:



// Input: text = "loonbalxballpoon"
// Output: 2
// Example 3:

// Input: text = "leetcode"
// Output: 0
 

// Constraints:

// 1 <= text.length <= 10^4
// text consists of lower case English letters only.





// Solution:




class Solution {
    public int maxNumberOfBalloons(String text) {
        int[] freq = new int[26];

        for (char c : text.toCharArray()) {
            freq[c - 'a']++;
        }

        return Math.min(
            Math.min(freq[0], freq[1]),
            Math.min(
                Math.min(freq['l' - 'a'] / 2, freq['o' - 'a'] / 2),
                Math.min(freq['n' - 'a'], Integer.MAX_VALUE)
            )
        );
    }
}