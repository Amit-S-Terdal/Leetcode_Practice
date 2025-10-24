// 2048. Next Greater Numerically Balanced Number

// An integer x is numerically balanced if for every digit d in the number x, there are exactly d occurrences of that digit in x.

// Given an integer n, return the smallest numerically balanced number strictly greater than n.

 

// Example 1:

// Input: n = 1
// Output: 22
// Explanation: 
// 22 is numerically balanced since:
// - The digit 2 occurs 2 times. 
// It is also the smallest numerically balanced number strictly greater than 1.
// Example 2:

// Input: n = 1000
// Output: 1333
// Explanation: 
// 1333 is numerically balanced since:
// - The digit 1 occurs 1 time.
// - The digit 3 occurs 3 times. 
// It is also the smallest numerically balanced number strictly greater than 1000.
// Note that 1022 cannot be the answer because 0 appeared more than 0 times.
// Example 3:

// Input: n = 3000
// Output: 3133
// Explanation: 
// 3133 is numerically balanced since:
// - The digit 1 occurs 1 time.
// - The digit 3 occurs 3 times.
// It is also the smallest numerically balanced number strictly greater than 3000.
 

// Constraints:

// 0 <= n <= 10^6



// Solution:



import java.util.*;

class Solution {
    static List<Integer> Bal = new ArrayList<>();
    static int[] tens = {1, 10, 100, 1000, 10000, 100000, 1000000};

    // Helper: generate all permutations of digits and push valid numbers
    static void permDigits(List<Integer> digit) {
        int dz = digit.size();
        if (dz == 1) { // O(|digit|)
            int x = 0, d = digit.get(0);
            for (int i = 0; i < d; i++) x = 10 * x + d;
            Bal.add(x);
            return;
        }

        // Build a string with each digit repeated 'digit' times
        StringBuilder sb = new StringBuilder();
        for (int d : digit) {
            for (int i = 0; i < d; i++) sb.append((char) ('0' + d));
        }

        char[] arr = sb.toString().toCharArray();
        Arrays.sort(arr); // to generate permutations lexicographically

        do {
            int x = Integer.parseInt(new String(arr));
            if (x <= 1224444) Bal.add(x);
        } while (nextPermutation(arr));
    }

    // Helper: generate all balanced numbers once
    static void genBalanced() {
        if (!Bal.isEmpty()) return;

        for (int mask = 1; mask < (1 << 6); mask++) {
            int len = 0;
            List<Integer> digit = new ArrayList<>();
            for (int d = 0; d < 6; d++) {
                if ((mask & (1 << d)) != 0) {
                    len += d + 1;
                    if (len > 7) break;
                    digit.add(d + 1);
                }
            }
            if (len <= 7)
                permDigits(digit);
        }

        Collections.sort(Bal);
    }

    public int nextBeautifulNumber(int n) {
        genBalanced();
        for (int x : Bal) {
            if (x > n) return x;
        }
        return -1; // shouldn't happen for valid inputs
    }

    // Java equivalent of std::next_permutation
    private static boolean nextPermutation(char[] arr) {
        int i = arr.length - 2;
        while (i >= 0 && arr[i] >= arr[i + 1]) i--;
        if (i < 0) return false;
        int j = arr.length - 1;
        while (arr[j] <= arr[i]) j--;
        swap(arr, i, j);
        reverse(arr, i + 1, arr.length - 1);
        return true;
    }

    private static void swap(char[] arr, int i, int j) {
        char tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    private static void reverse(char[] arr, int i, int j) {
        while (i < j) swap(arr, i++, j--);
    }
}
