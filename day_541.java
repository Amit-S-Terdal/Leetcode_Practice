// 3518. Smallest Palindromic Rearrangement II

// You are given a palindromic string s and an integer k.

// Return the k-th lexicographically smallest palindromic permutation of s. If there are fewer than k distinct palindromic permutations, return an empty string.

// Note: Different rearrangements that yield the same palindromic string are considered identical and are counted once.

 

// Example 1:

// Input: s = "abba", k = 2

// Output: "baab"

// Explanation:

// The two distinct palindromic rearrangements of "abba" are "abba" and "baab".
// Lexicographically, "abba" comes before "baab". Since k = 2, the output is "baab".
// Example 2:

// Input: s = "aa", k = 2

// Output: ""

// Explanation:

// There is only one palindromic rearrangement: "aa".
// The output is an empty string since k = 2 exceeds the number of possible rearrangements.
// Example 3:

// Input: s = "bacab", k = 1

// Output: "abcba"

// Explanation:

// The two distinct palindromic rearrangements of "bacab" are "abcba" and "bacab".
// Lexicographically, "abcba" comes before "bacab". Since k = 1, the output is "abcba".
 

// Constraints:

// 1 <= s.length <= 10^4
// s consists of lowercase English letters.
// s is guaranteed to be palindromic.
// 1 <= k <= 10^6



// Solution: 



class Solution {
    private static final int INF = 1_000_001;
    private static final int N = 24;
    private static final int[][] C = new int[N][N];

    static {
        pascal();
    }

    private static void pascal() {
        if (C[0][0] == 1) return;

        C[0][0] = 1;
        for (int i = 1; i < N; i++) {
            C[i][0] = C[i][i] = 1;
            for (int j = 1; j <= i / 2; j++) {
                C[i][j] = C[i][i - j] = C[i - 1][j - 1] + C[i - 1][j];
            }
        }
    }

    private static int comb(int n, int k) {
        if (n < N) return C[n][k];

        if (2 * k > n) k = n - k;

        long ans = 1;
        for (int i = 1; i <= k; i++) {
            ans = ans * (n - i + 1) / i;
            if (ans >= INF) return INF;
        }
        return (int) ans;
    }

    private static int perm(int[] freq, int seen, int sz) {
        long ans = 1;

        while (seen != 0) {
            int bit = Integer.numberOfTrailingZeros(seen);
            int f = freq[bit];
            ans *= comb(sz, f);
            if (ans >= INF) return INF;
            sz -= f;
            seen &= (seen - 1);
        }

        return (int) ans;
    }

    public String smallestPalindrome(String s, int k) {
        int n = s.length();
        int half = n / 2;

        int[] freq = new int[26];
        int seen = 0;

        for (int i = 0; i < half; i++) {
            int idx = s.charAt(i) - 'a';
            freq[idx]++;
            seen |= (1 << idx);
        }

        int total = perm(freq, seen, half);
        if (k > total) return "";

        StringBuilder left = new StringBuilder();
        int sz = half;

        for (int i = 0; i < half; i++) {
            for (int c = 0; c < 26; c++) {
                if (freq[c] == 0) continue;

                freq[c]--;
                sz--;

                int cnt = perm(freq, seen, sz);

                if (cnt >= k) {
                    left.append((char) ('a' + c));
                    if (freq[c] == 0) {
                        seen &= ~(1 << c);
                    }
                    break;
                } else {
                    k -= cnt;
                    freq[c]++;
                    sz++;
                }
            }
        }

        StringBuilder ans = new StringBuilder(left);

        if ((n & 1) == 1) {
            ans.append(s.charAt(half));
        }

        ans.append(new StringBuilder(left).reverse());

        return ans.toString();
    }
}