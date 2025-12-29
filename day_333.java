// 756. Pyramid Transition Matrix

// You are stacking blocks to form a pyramid. Each block has a color, which is represented by a single letter. Each row of blocks contains one less block than the row beneath it and is centered on top.

// To make the pyramid aesthetically pleasing, there are only specific triangular patterns that are allowed. A triangular pattern consists of a single block stacked on top of two blocks. The patterns are given as a list of three-letter strings allowed, where the first two characters of a pattern represent the left and right bottom blocks respectively, and the third character is the top block.

// For example, "ABC" represents a triangular pattern with a 'C' block stacked on top of an 'A' (left) and 'B' (right) block. Note that this is different from "BAC" where 'B' is on the left bottom and 'A' is on the right bottom.
// You start with a bottom row of blocks bottom, given as a single string, that you must use as the base of the pyramid.

// Given bottom and allowed, return true if you can build the pyramid all the way to the top such that every triangular pattern in the pyramid is in allowed, or false otherwise.

 

// Example 1:


// Input: bottom = "BCD", allowed = ["BCC","CDE","CEA","FFF"]
// Output: true
// Explanation: The allowed triangular patterns are shown on the right.
// Starting from the bottom (level 3), we can build "CE" on level 2 and then build "A" on level 1.
// There are three triangular patterns in the pyramid, which are "BCC", "CDE", and "CEA". All are allowed.
// Example 2:


// Input: bottom = "AAAA", allowed = ["AAB","AAC","BCD","BBE","DEF"]
// Output: false
// Explanation: The allowed triangular patterns are shown on the right.
// Starting from the bottom (level 4), there are multiple ways to build level 3, but trying all the possibilites, you will get always stuck before building level 1.
 

// Constraints:

// 2 <= bottom.length <= 6
// 0 <= allowed.length <= 216
// allowed[i].length == 3
// The letters in all input strings are from the set {'A', 'B', 'C', 'D', 'E', 'F'}.
// All the values of allowed are unique.



// Solution: 



import java.util.*;

class Solution {
    private static int N = 117649; // 7^6
    private static int[] pattern = new int[36];
    private static BitSet[] BAD = new BitSet[7]; // memo per row length

    // Helper method to encode the string as an integer
    private static int encode(String s) {
        int ans = 0;
        for (char c : s.toCharArray()) {
            ans = ans * 7 + (c - 'A');
        }
        return ans;
    }

    // Helper method to check if the string is valid
    private static boolean check(String cur, int sz) {
        for (int i = 0; i < sz - 1; i++) {
            if (cur.charAt(i) == 'G') return false;
            int key = (cur.charAt(i) - 'A') * 6 + (cur.charAt(i + 1) - 'A');
            if (pattern[key] == 0) return false;
        }
        return true;
    }

    // Add allowed patterns to the pattern array
    private static void addPattern(List<String> allowed) {
        for (String s : allowed) {
            int idx = (s.charAt(0) - 'A') * 6 + (s.charAt(1) - 'A');
            pattern[idx] |= 1 << (s.charAt(2) - 'A');
        }
    }

    // DFS to try building the pyramid
    private static boolean dfs(String cur, String next, int i, int sz) {
        if (i == sz - 1) {
            if (sz == 2) return true;
            // pruning check
            if (!check(next, sz - 1)) return false;
            int idx = encode(next);
            if (BAD[sz - 1].get(idx)) return false;

            String up = new String(new char[sz - 1]).replace('\0', 'G');
            if (!dfs(next, up, 0, sz - 1)) {
                BAD[sz - 1].set(idx);
                return false;
            }
            return true;
        }

        int key = (cur.charAt(i) - 'A') * 6 + (cur.charAt(i + 1) - 'A');
        int mask = pattern[key];

        while (mask != 0) {
            int bit = mask & -mask;
            mask -= bit;

            int c = Integer.numberOfTrailingZeros(bit);
            next = next.substring(0, i) + (char) ('A' + c) + next.substring(i + 1);

            if (dfs(cur, next, i + 1, sz)) return true;
        }
        return false;
    }

    public boolean pyramidTransition(String bottom, List<String> allowed) {
        // Initialize pattern and BAD
        Arrays.fill(pattern, 0);
        for (int i = 1; i <= 6; i++) BAD[i] = new BitSet();

        // Add allowed patterns
        addPattern(allowed);

        // Start DFS
        String next = new String(new char[bottom.length() - 1]).replace('\0', 'G');
        return dfs(bottom, next, 0, bottom.length());
    }
}
