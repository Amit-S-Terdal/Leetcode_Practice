// 3734. Lexicographically Smallest Palindromic Permutation Greater Than Target

// You are given two strings s and target, each of length n, consisting of lowercase English letters.

// Return the lexicographically smallest string that is both a palindromic permutation of s and strictly greater than target. If no such permutation exists, return an empty string.

 

// Example 1:

// Input: s = "baba", target = "abba"

// Output: "baab"

// Explanation:

// The palindromic permutations of s (in lexicographical order) are "abba" and "baab".
// The lexicographically smallest permutation that is strictly greater than target is "baab".
// Example 2:

// Input: s = "baba", target = "bbaa"

// Output: ""

// Explanation:

// The palindromic permutations of s (in lexicographical order) are "abba" and "baab".
// None of them is lexicographically strictly greater than target. Therefore, the answer is "".
// Example 3:

// Input: s = "abc", target = "abb"

// Output: ""

// Explanation:

// s has no palindromic permutations. Therefore, the answer is "".

// Example 4:

// Input: s = "aac", target = "abb"

// Output: "aca"

// Explanation:

// The only palindromic permutation of s is "aca".
// "aca" is strictly greater than target. Therefore, the answer is "aca".
 

// Constraints:

// 1 <= n == s.length == target.length <= 300
// s and target consist of only lowercase English letters.



// Solution: 




class Solution {
int[] dp = new int[151];
int[] freq = new int[26];
int n, half;
int oddIdx = -1;


boolean revLeftIsGrR(String t) {
    for (int i = 0; i < half; i++) {
        if (t.charAt(half - 1 - i) > t.charAt(half + (n & 1) + i))
            return true;
        if (t.charAt(half - 1 - i) < t.charAt(half + (n & 1) + i))
            return false;
    }
    return false;
}

boolean canPlace(int i, String target, int hasC) {
    if (dp[i] != -1)
        return dp[i] == 1;

    int idx = target.charAt(i) - 'a';

    if (freq[idx] == 0) {
        dp[i] = 0;
        return false;
    }

    int hasC1 = hasC;

    freq[idx]--;

    if (freq[idx] == 0)
        hasC1 &= ~(1 << idx);

    boolean ans;

    if (i == half - 1) {
        if ((n & 1) != 0) {
            char middle = (char) ('a' + oddIdx);

            if (middle != target.charAt(half))
                ans = middle > target.charAt(half);
            else
                ans = revLeftIsGrR(target);
        } else {
            ans = revLeftIsGrR(target);
        }
    } else {
        int nxt = target.charAt(i + 1) - 'a';

        if ((hasC1 >>> (nxt + 1)) != 0) {
            ans = true;
        } else if (((hasC1 >>> nxt) & 1) == 0) {
            ans = false;
        } else {
            ans = canPlace(i + 1, target, hasC1);
        }
    }

    freq[idx]++;
    dp[i] = ans ? 1 : 0;

    return ans;
}

String buildPalindrome(String ans) {
    StringBuilder pal = new StringBuilder(ans);

    if ((n & 1) != 0)
        pal.append((char) ('a' + oddIdx));

    StringBuilder rev = new StringBuilder(ans);
    rev.reverse();

    pal.append(rev);

    return pal.toString();
}

public String lexPalindromicPermutation(String s, String target) {
    n = s.length();
    half = n >> 1;

    java.util.Arrays.fill(dp, -1);
    java.util.Arrays.fill(freq, 0);

    int parity = 0;
    int hasC = 0;

    for (char c : s.toCharArray()) {
        int idx = c - 'a';

        freq[idx]++;
        hasC |= 1 << idx;
        parity ^= 1 << idx;
    }

    if (Integer.bitCount(parity) > 1)
        return "";

    if ((n & 1) != 0)
        oddIdx = Integer.numberOfTrailingZeros(parity);

    for (int mask = hasC; mask != 0; mask &= (mask - 1)) {
        int idx = Integer.numberOfTrailingZeros(mask);

        freq[idx] >>= 1;

        if (freq[idx] == 0)
            hasC &= ~(1 << idx);
    }

    StringBuilder ans = new StringBuilder();

    for (int i = 0; i < half; i++) {
        int tIdx = target.charAt(i) - 'a';

        if (freq[tIdx] > 0 && canPlace(i, target, hasC)) {
            ans.append(target.charAt(i));

            freq[tIdx]--;

            if (freq[tIdx] == 0)
                hasC &= ~(1 << tIdx);
        } else {
            int higher = hasC >>> (tIdx + 1);

            if (higher == 0)
                return "";

            int choice =
                Integer.numberOfTrailingZeros(higher) + tIdx + 1;

            freq[choice]--;

            if (freq[choice] == 0)
                hasC &= ~(1 << choice);

            ans.append((char) ('a' + choice));

            for (int j = i + 1; j < half; j++) {
                int idx = Integer.numberOfTrailingZeros(hasC);

                ans.append((char) ('a' + idx));

                freq[idx]--;

                if (freq[idx] == 0)
                    hasC &= ~(1 << idx);
            }

            return buildPalindrome(ans.toString());
        }
    }

    String pal = buildPalindrome(ans.toString());

    return pal.compareTo(target) > 0 ? pal : "";
}

}
