// 1625. Lexicographically Smallest String After Applying Operations

// You are given a string s of even length consisting of digits from 0 to 9, and two integers a and b.

// You can apply either of the following two operations any number of times and in any order on s:

// Add a to all odd indices of s (0-indexed). Digits post 9 are cycled back to 0. For example, if s = "3456" and a = 5, s becomes "3951".
// Rotate s to the right by b positions. For example, if s = "3456" and b = 1, s becomes "6345".
// Return the lexicographically smallest string you can obtain by applying the above operations any number of times on s.

// A string a is lexicographically smaller than a string b (of the same length) if in the first position where a and b differ, string a has a letter that appears earlier in the alphabet than the corresponding letter in b. For example, "0158" is lexicographically smaller than "0190" because the first position they differ is at the third letter, and '5' comes before '9'.

 

// Example 1:

// Input: s = "5525", a = 9, b = 2
// Output: "2050"
// Explanation: We can apply the following operations:
// Start:  "5525"
// Rotate: "2555"
// Add:    "2454"
// Add:    "2353"
// Rotate: "5323"
// Add:    "5222"
// Add:    "5121"
// Rotate: "2151"
// Add:    "2050"​​​​​
// There is no way to obtain a string that is lexicographically smaller than "2050".
// Example 2:

// Input: s = "74", a = 5, b = 1
// Output: "24"
// Explanation: We can apply the following operations:
// Start:  "74"
// Rotate: "47"
// ​​​​​​​Add:    "42"
// ​​​​​​​Rotate: "24"​​​​​​​​​​​​
// There is no way to obtain a string that is lexicographically smaller than "24".
// Example 3:

// Input: s = "0011", a = 4, b = 2
// Output: "0011"
// Explanation: There are no sequence of operations that will give us a lexicographically smaller string than "0011".
 

// Constraints:

// 2 <= s.length <= 100
// s.length is even.
// s consists of digits from 0 to 9 only.
// 1 <= a <= 9
// 1 <= b <= s.length - 1



// Solution: 

import java.util.*;

class Solution {
    public String findLexSmallestString(String s, int a, int b) {
        int n = s.length();
        int a0 = gcd(a, 10);
        int b0 = gcd(n, b);
        int A = 10 / a0;
        int B = n / b0;

        char[] chars = s.toCharArray();
        char[] best = new char[n];
        Arrays.fill(best, '9');

        if (b0 % 2 == 0) {
            for (int j = 0; j < B; j++) {
                rotate(chars, b0);
                minAddOdd(chars, a0, A);
                if (compare(chars, best) < 0)
                    System.arraycopy(chars, 0, best, 0, n);
            }
        } else {
            for (int j = 0; j < B; j++) {
                rotate(chars, b0);
                minAddOdd(chars, a0, A);
                minAddEven(chars, a0, A);
                if (compare(chars, best) < 0)
                    System.arraycopy(chars, 0, best, 0, n);
            }
        }
        return new String(best);
    }

    private void addOdd(char[] s, int a) {
        for (int i = 1; i < s.length; i += 2) {
            int d = (s[i] - '0' + a) % 10;
            s[i] = (char) ('0' + d);
        }
    }

    private void minAddOdd(char[] s, int a, int A) {
        int d = s[1] - '0';
        if (d < a) return;
        int steps = (10 - d / a) * a % 10;
        addOdd(s, steps);
    }

    private void addEven(char[] s, int a) {
        for (int i = 0; i < s.length; i += 2) {
            int d = (s[i] - '0' + a) % 10;
            s[i] = (char) ('0' + d);
        }
    }

    private void minAddEven(char[] s, int a, int A) {
        int d = s[0] - '0';
        if (d < a) return;
        int steps = (10 - d / a) * a % 10;
        addEven(s, steps);
    }

    private void rotate(char[] s, int b) {
        reverse(s, 0, s.length - 1);
        reverse(s, 0, b - 1);
        reverse(s, b, s.length - 1);
    }

    private void reverse(char[] arr, int l, int r) {
        while (l < r) {
            char tmp = arr[l];
            arr[l] = arr[r];
            arr[r] = tmp;
            l++;
            r--;
        }
    }

    private int compare(char[] a, char[] b) {
        for (int i = 0; i < a.length; i++) {
            if (a[i] != b[i]) return a[i] - b[i];
        }
        return 0;
    }

    private int gcd(int a, int b) {
        return b == 0 ? a : gcd(b, a % b);
    }
}

