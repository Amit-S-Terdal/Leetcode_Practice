// 3403. Find the Lexicographically Largest String From the Box I

// You are given a string word, and an integer numFriends.

// Alice is organizing a game for her numFriends friends. There are multiple rounds in the game, where in each round:

// word is split into numFriends non-empty strings, such that no previous round has had the exact same split.
// All the split words are put into a box.
// Find the lexicographically largest string from the box after all the rounds are finished.

 

// Example 1:

// Input: word = "dbca", numFriends = 2

// Output: "dbc"

// Explanation: 

// All possible splits are:

// "d" and "bca".
// "db" and "ca".
// "dbc" and "a".
// Example 2:

// Input: word = "gggg", numFriends = 4

// Output: "g"

// Explanation: 

// The only possible split is: "g", "g", "g", and "g".

 

// Constraints:

// 1 <= word.length <= 5 * 10^3
// word consists only of lowercase English letters.
// 1 <= numFriends <= word.length

// Solution:


class Solution {
    public String answerString(String word, int numFriends) {
        if (numFriends == 1) return word;
        String res = "";
        int length = word.length() - numFriends + 1;
        for (int i = 0; i < word.length(); i++) {
            String temp;
            if (i + length <= word.length())
                temp = word.substring(i, i + length);
            else
                temp = word.substring(i);
            if (temp.compareTo(res) > 0)
                res = temp;
        }
        return res;
    }
}