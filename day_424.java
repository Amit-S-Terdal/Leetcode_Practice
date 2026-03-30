// 2840. Check if Strings Can be Made Equal With Operations II

// You are given two strings s1 and s2, both of length n, consisting of lowercase English letters.

// You can apply the following operation on any of the two strings any number of times:

// Choose any two indices i and j such that i < j and the difference j - i is even, then swap the two characters at those indices in the string.
// Return true if you can make the strings s1 and s2 equal, and false otherwise.

 

// Example 1:

// Input: s1 = "abcdba", s2 = "cabdab"
// Output: true
// Explanation: We can apply the following operations on s1:
// - Choose the indices i = 0, j = 2. The resulting string is s1 = "cbadba".
// - Choose the indices i = 2, j = 4. The resulting string is s1 = "cbbdaa".
// - Choose the indices i = 1, j = 5. The resulting string is s1 = "cabdab" = s2.
// Example 2:

// Input: s1 = "abe", s2 = "bea"
// Output: false
// Explanation: It is not possible to make the two strings equal.
 

// Constraints:

// n == s1.length == s2.length
// 1 <= n <= 10^5
// s1 and s2 consist only of lowercase English letters.



// Solution:




class Solution {
    public boolean checkStrings(String s1, String s2) {
        int[][] freq = new int[2][26];
        int n = s1.length();

        for (int i = 0; i < n; i++) {
            int iOdd = i & 1;
            freq[iOdd][s1.charAt(i) - 'a']++;
            freq[iOdd][s2.charAt(i) - 'a']--;
        }

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 26; j++) {
                if (freq[i][j] != 0) {
                    return false;
                }
            }
        }

        return true;
    }
}