// 2434. Using a Robot to Print the Lexicographically Smallest String

// You are given a string s and a robot that currently holds an empty string t. Apply one of the following operations until s and t are both empty:

// Remove the first character of a string s and give it to the robot. The robot will append this character to the string t.
// Remove the last character of a string t and give it to the robot. The robot will write this character on paper.
// Return the lexicographically smallest string that can be written on the paper.

 

// Example 1:

// Input: s = "zza"
// Output: "azz"
// Explanation: Let p denote the written string.
// Initially p="", s="zza", t="".
// Perform first operation three times p="", s="", t="zza".
// Perform second operation three times p="azz", s="", t="".
// Example 2:

// Input: s = "bac"
// Output: "abc"
// Explanation: Let p denote the written string.
// Perform first operation twice p="", s="c", t="ba". 
// Perform second operation twice p="ab", s="c", t="". 
// Perform first operation p="ab", s="", t="c". 
// Perform second operation p="abc", s="", t="".
// Example 3:

// Input: s = "bdda"
// Output: "addb"
// Explanation: Let p denote the written string.
// Initially p="", s="bdda", t="".
// Perform first operation four times p="", s="", t="bdda".
// Perform second operation four times p="addb", s="", t="".
 

// Constraints:

// 1 <= s.length <= 10^5
// s consists of only English lowercase letters.



// Solution:


import java.util.*;

class Solution {
    public String robotWithString(String s) {
        int n = s.length();
        char[] minSuffix = new char[n];
        
        // Step 1: Compute the minimum suffix characters
        minSuffix[n - 1] = s.charAt(n - 1);
        for (int i = n - 2; i >= 0; i--) {
            minSuffix[i] = (char)Math.min(s.charAt(i), minSuffix[i + 1]);
        }

        StringBuilder result = new StringBuilder();
        Deque<Character> stack = new ArrayDeque<>();

        // Step 2: Simulate the robot operations
        for (int i = 0; i < n; i++) {
            stack.push(s.charAt(i));

            // While the top of the stack is <= the smallest character remaining in `s`
            while (!stack.isEmpty() && (i == n - 1 || stack.peek() <= minSuffix[i + 1])) {
                result.append(stack.pop());
            }
        }

        return result.toString();
    }
}
