// 1415. The k-th Lexicographical String of All Happy Strings of Length n

// Hint
// A happy string is a string that:

// consists only of letters of the set ['a', 'b', 'c'].
// s[i] != s[i + 1] for all values of i from 1 to s.length - 1 (string is 1-indexed).
// For example, strings "abc", "ac", "b" and "abcbabcbcb" are all happy strings and strings "aa", "baa" and "ababbc" are not happy strings.

// Given two integers n and k, consider a list of all happy strings of length n sorted in lexicographical order.

// Return the kth string of this list or return an empty string if there are less than k happy strings of length n.

 

// Example 1:

// Input: n = 1, k = 3
// Output: "c"
// Explanation: The list ["a", "b", "c"] contains all happy strings of length 1. The third string is "c".
// Example 2:

// Input: n = 1, k = 4
// Output: ""
// Explanation: There are only 3 happy strings of length 1.
// Example 3:

// Input: n = 3, k = 9
// Output: "cab"
// Explanation: There are 12 different happy string of length 3 ["aba", "abc", "aca", "acb", "bab", "bac", "bca", "bcb", "cab", "cac", "cba", "cbc"]. You will find the 9th string = "cab"
 

// Constraints:

// 1 <= n <= 10
// 1 <= k <= 100

// Solution: 

class Solution {
    private int count = 0; // To keep track of the number of happy strings generated
    private String result = ""; // To store the k-th happy string

    public String getHappyString(int n, int k) {
        generateHappyStrings(n, k, new StringBuilder());
        return result;
    }

    private void generateHappyStrings(int n, int k, StringBuilder current) {
        // Base case: if the current string is of length n
        if (current.length() == n) {
            count++; // Increment the count of happy strings
            if (count == k) {
                result = current.toString(); // If this is the k-th string, store it
            }
            return;
        }

        // Try appending 'a', 'b', and 'c' to the current string
        for (char ch : new char[]{'a', 'b', 'c'}) {
            // Ensure the new character is not the same as the last character in the current string
            if (current.length() == 0 || current.charAt(current.length() - 1) != ch) {
                current.append(ch); // Append the character
                generateHappyStrings(n, k, current); // Recurse
                current.deleteCharAt(current.length() - 1); // Backtrack
                if (count >= k) {
                    return; // Early termination if we've found the k-th string
                }
            }
        }
    }
}