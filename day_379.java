// 3714. Longest Balanced Substring II

// You are given a string s consisting only of the characters 'a', 'b', and 'c'.

// A substring of s is called balanced if all distinct characters in the substring appear the same number of times.

// Return the length of the longest balanced substring of s.

 

// Example 1:

// Input: s = "abbac"

// Output: 4

// Explanation:

// The longest balanced substring is "abba" because both distinct characters 'a' and 'b' each appear exactly 2 times.

// Example 2:

// Input: s = "aabcc"

// Output: 3

// Explanation:

// The longest balanced substring is "abc" because all distinct characters 'a', 'b' and 'c' each appear exactly 1 time.

// Example 3:

// Input: s = "aba"

// Output: 2

// Explanation:

// One of the longest balanced substrings is "ab" because both distinct characters 'a' and 'b' each appear exactly 1 time. Another longest balanced substring is "ba".

 

// Constraints:

// 1 <= s.length <= 10^5
// s contains only the characters 'a', 'b', and 'c'.




// Solution: 


import java.util.*;

class Solution {

    private static int has1(String s) {
        int best = 1, len = 1;
        for (int i = 1; i < s.length(); i++) {
            if (s.charAt(i) == s.charAt(i - 1)) {
                len++;
            } else {
                best = Math.max(best, len);
                len = 1;
            }
        }
        return Math.max(best, len);
    }

    private static class Pair {
        int x, y;
        Pair(int x, int y) { this.x = x; this.y = y; }

        @Override
        public int hashCode() {
            return 31 * x + y;
        }

        @Override
        public boolean equals(Object o) {
            Pair p = (Pair) o;
            return x == p.x && y == p.y;
        }
    }

    public int longestBalanced(String s) {
        int n = s.length();
        if (n == 0) return 0;

        int ans = has1(s);

        Map<Pair, Integer> abc = new HashMap<>();
        Map<Pair, Integer> ab  = new HashMap<>();
        Map<Pair, Integer> bc  = new HashMap<>();
        Map<Pair, Integer> ca  = new HashMap<>();

        abc.put(new Pair(0, 0), -1);
        ab.put(new Pair(0, 0), -1);
        bc.put(new Pair(0, 0), -1);
        ca.put(new Pair(0, 0), -1);

        int A = 0, B = 0, C = 0;

        for (int i = 0; i < n; i++) {
            char ch = s.charAt(i);
            if (ch == 'a') A++;
            else if (ch == 'b') B++;
            else C++;

            // A = B = C
            Pair key = new Pair(B - A, C - A);
            Integer prev = abc.putIfAbsent(key, i);
            if (prev != null) ans = Math.max(ans, i - prev);

            // A = B and no C
            key = new Pair(A - B, C);
            prev = ab.putIfAbsent(key, i);
            if (prev != null) ans = Math.max(ans, i - prev);

            // B = C and no A
            key = new Pair(B - C, A);
            prev = bc.putIfAbsent(key, i);
            if (prev != null) ans = Math.max(ans, i - prev);

            // C = A and no B
            key = new Pair(C - A, B);
            prev = ca.putIfAbsent(key, i);
            if (prev != null) ans = Math.max(ans, i - prev);
        }

        return ans;
    }
}
