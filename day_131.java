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
    public String clearStars(String s) {
        int n = s.length();
        PriorityQueue<int[]> minh = new PriorityQueue<>((a, b) -> {
            if (a[0] != b[0]) return a[0] - b[0];
            return a[1] - b[1];
        });
        boolean[] deleted = new boolean[n];
        for (int i = 0; i < n; i++) {
            char c = s.charAt(i);
            if (c == '*') {
                int[] top = minh.poll();
                deleted[-top[1]] = true;
            } else {
                minh.offer(new int[]{c, -i});
            }
        }
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < n; i++) {
            if (deleted[i] || s.charAt(i) == '*') continue;
            res.append(s.charAt(i));
        }
        return res.toString();
    }
}