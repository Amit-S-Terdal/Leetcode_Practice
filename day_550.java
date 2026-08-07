// 3348. Smallest Divisible Digit Product II

// You are given a string num which represents a positive integer, and an integer t.

// A number is called zero-free if none of its digits are 0.

// Return a string representing the smallest zero-free number greater than or equal to num such that the product of its digits is divisible by t. If no such number exists, return "-1".

 

// Example 1:

// Input: num = "1234", t = 256

// Output: "1488"

// Explanation:

// The smallest zero-free number that is greater than 1234 and has the product of its digits divisible by 256 is 1488, with the product of its digits equal to 256.

// Example 2:

// Input: num = "12355", t = 50

// Output: "12355"

// Explanation:

// 12355 is already zero-free and has the product of its digits divisible by 50, with the product of its digits equal to 150.

// Example 3:

// Input: num = "11111", t = 26

// Output: "-1"

// Explanation:

// No number greater than 11111 has the product of its digits divisible by 26.

 

// Constraints:

// 2 <= num.length <= 2 * 10^5
// num consists only of digits in the range ['0', '9'].
// num does not contain leading zeros.
// 1 <= t <= 10^14



// Solution:



class Solution {
    int[] allowedPrimes = {2, 3, 5, 7};

    // contrib[d][i] = contribution of digit d to prime i:
    // i = 0 -> 2
    // i = 1 -> 3
    // i = 2 -> 5
    // i = 3 -> 7
    int[][] contrib = {
        {0, 0, 0, 0},
        {0, 0, 0, 0},
        {1, 0, 0, 0},
        {0, 1, 0, 0},
        {2, 0, 0, 0},
        {0, 0, 1, 0},
        {1, 1, 0, 0},
        {0, 0, 0, 1},
        {3, 0, 0, 0},
        {0, 2, 0, 0}
    };

    int maxE2, maxE3, maxE5, maxE7;
    int[][][][] dp;

    void buildDP(int E2, int E3, int E5, int E7) {
        maxE2 = E2;
        maxE3 = E3;
        maxE5 = E5;
        maxE7 = E7;

        dp = new int[E2 + 1][E3 + 1][E5 + 1][E7 + 1];

        for (int e2 = 0; e2 <= E2; e2++) {
            for (int e3 = 0; e3 <= E3; e3++) {
                for (int e5 = 0; e5 <= E5; e5++) {
                    for (int e7 = 0; e7 <= E7; e7++) {
                        dp[e2][e3][e5][e7] = Integer.MAX_VALUE;
                    }
                }
            }
        }

        dp[0][0][0][0] = 0;

        // Iterate by increasing total exponent sum
        for (int s = 1; s <= E2 + E3 + E5 + E7; s++) {
            for (int e2 = 0; e2 <= E2; e2++) {
                for (int e3 = 0; e3 <= E3; e3++) {
                    for (int e5 = 0; e5 <= E5; e5++) {
                        for (int e7 = 0; e7 <= E7; e7++) {

                            if (e2 + e3 + e5 + e7 != s)
                                continue;

                            int best = Integer.MAX_VALUE;

                            for (int d = 2; d <= 9; d++) {
                                int ne2 = Math.max(0, e2 - contrib[d][0]);
                                int ne3 = Math.max(0, e3 - contrib[d][1]);
                                int ne5 = Math.max(0, e5 - contrib[d][2]);
                                int ne7 = Math.max(0, e7 - contrib[d][3]);

                                if (dp[ne2][ne3][ne5][ne7] != Integer.MAX_VALUE) {
                                    best = Math.min(
                                        best,
                                        1 + dp[ne2][ne3][ne5][ne7]
                                    );
                                }
                            }

                            dp[e2][e3][e5][e7] = best;
                        }
                    }
                }
            }
        }
    }

    int minDigits(int e2, int e3, int e5, int e7) {
        return dp[
            Math.min(e2, maxE2)
        ][
            Math.min(e3, maxE3)
        ][
            Math.min(e5, maxE5)
        ][
            Math.min(e7, maxE7)
        ];
    }

    void applyDigit(int[] freq, int d) {
        freq[2] = Math.max(0, freq[2] - contrib[d][0]);
        freq[3] = Math.max(0, freq[3] - contrib[d][1]);
        freq[5] = Math.max(0, freq[5] - contrib[d][2]);
        freq[7] = Math.max(0, freq[7] - contrib[d][3]);
    }

    boolean isReqMet(int[] freq) {
        for (int p : allowedPrimes) {
            if (freq[p] > 0)
                return false;
        }
        return true;
    }

    // Smallest suffix of exactly length L satisfying freq
    String greedyFill(int[] freq, int L) {
        StringBuilder res = new StringBuilder(L);

        for (int pos = 0; pos < L; pos++) {
            int slotsAfter = L - pos - 1;

            for (int d = 1; d <= 9; d++) {
                int[] nf = freq.clone();

                applyDigit(nf, d);

                if (minDigits(nf[2], nf[3], nf[5], nf[7]) <= slotsAfter) {
                    freq = nf;
                    res.append((char) ('0' + d));
                    break;
                }
            }
        }

        return res.toString();
    }

    public String smallestNumber(String num, long t) {

        int[] freqFull = new int[10];

        // Factorize t using only 2, 3, 5, 7
        for (int p : allowedPrimes) {
            while (t % p == 0) {
                freqFull[p]++;
                t /= p;
            }
        }

        // Contains a prime factor >= 11
        if (t > 1)
            return "-1";

        buildDP(
            freqFull[2],
            freqFull[3],
            freqFull[5],
            freqFull[7]
        );

        int len = num.length();

        boolean hasZero = false;

        for (int i = 0; i < len; i++) {
            if (num.charAt(i) == '0') {
                hasZero = true;
                break;
            }
        }

        // Case 1: num itself already works
        if (!hasZero) {
            int[] freq = freqFull.clone();

            for (int i = 0; i < len; i++) {
                applyDigit(freq, num.charAt(i) - '0');
            }

            if (isReqMet(freq))
                return num;
        }

        // prefixFreq[i] =
        // remaining prime factors before placing digit i
        int[][] prefixFreq = new int[len + 1][10];

        prefixFreq[0] = freqFull.clone();

        for (int i = 0; i < len; i++) {
            prefixFreq[i + 1] = prefixFreq[i].clone();

            if (num.charAt(i) != '0') {
                applyDigit(
                    prefixFreq[i + 1],
                    num.charAt(i) - '0'
                );
            }
        }

        int limit;

        if (hasZero) {
            limit = num.indexOf('0');
        } else {
            limit = len - 1;
        }

        String answer = "";

        // Try to increase the number at the latest possible position
        for (int pos = limit; pos >= 0 && answer.isEmpty(); pos--) {

            int[] freqBefore = prefixFreq[pos];

            int origDigit = num.charAt(pos) - '0';

            for (int d = origDigit + 1; d <= 9; d++) {

                int[] nf = freqBefore.clone();

                applyDigit(nf, d);

                int slotsAfter = len - pos - 1;

                if (minDigits(
                        nf[2],
                        nf[3],
                        nf[5],
                        nf[7]
                    ) <= slotsAfter) {

                    answer =
                        num.substring(0, pos)
                        + (char) ('0' + d)
                        + greedyFill(nf, slotsAfter);

                    break;
                }
            }
        }

        if (!answer.isEmpty())
            return answer;

        // No answer with the same length.
        // Increase the length.
        int totalNeeded = minDigits(
            freqFull[2],
            freqFull[3],
            freqFull[5],
            freqFull[7]
        );

        int L = Math.max(len + 1, totalNeeded);

        return greedyFill(freqFull.clone(), L);
    }
}