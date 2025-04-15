// 3272. Find the Count of Good Integers

// You are given two positive integers n and k.

// An integer x is called k-palindromic if:

// x is a palindrome.
// x is divisible by k.
// An integer is called good if its digits can be rearranged to form a k-palindromic integer. For example, for k = 2, 2020 can be rearranged to form the k-palindromic integer 2002, whereas 1010 cannot be rearranged to form a k-palindromic integer.

// Return the count of good integers containing n digits.

// Note that any integer must not have leading zeros, neither before nor after rearrangement. For example, 1010 cannot be rearranged to form 101.

 

// Example 1:

// Input: n = 3, k = 5

// Output: 27

// Explanation:

// Some of the good integers are:

// 551 because it can be rearranged to form 515.
// 525 because it is already k-palindromic.
// Example 2:

// Input: n = 1, k = 4

// Output: 2

// Explanation:

// The two good integers are 4 and 8.

// Example 3:

// Input: n = 5, k = 6

// Output: 2468

 

// Constraints:

// 1 <= n <= 10
// 1 <= k <= 9


// Solution: 

import java.util.*;

class Solution {
    public long countGoodIntegers(int n, int k) {
        Set<String> dict = new HashSet<>();
        int base = (int) Math.pow(10, (n - 1) / 2);
        int skip = n % 2;

        // Step 1: Generate k-palindromic integers
        for (int i = base; i < base * 10; i++) {
            String left = Integer.toString(i);
            StringBuilder right = new StringBuilder(left).reverse();
            if (skip == 1) {
                right.deleteCharAt(0);
            }
            String full = left + right.toString();
            long num = Long.parseLong(full);
            
            if (num % k == 0) {
                char[] arr = full.toCharArray();
                Arrays.sort(arr);
                dict.add(new String(arr));
            }
        }

        // Step 2: Precompute factorials up to n
        long[] factorial = new long[n + 1];
        factorial[0] = 1;
        for (int i = 1; i <= n; i++) {
            factorial[i] = factorial[i - 1] * i;
        }

        long ans = 0;

        // Step 3: Calculate valid permutations for each unique digit combination
        for (String s : dict) {
            int[] count = new int[10];
            for (char c : s.toCharArray()) {
                count[c - '0']++;
            }

            long total = (n - count[0]) * factorial[n - 1];
            for (int c : count) {
                total /= factorial[c];
            }

            ans += total;
        }

        return ans;
    }
}


