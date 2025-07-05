// 3333. Find the Original Typed String II

// Alice is attempting to type a specific string on her computer. However, she tends to be clumsy and may press a key for too long, resulting in a character being typed multiple times.

// You are given a string word, which represents the final output displayed on Alice's screen. You are also given a positive integer k.

// Return the total number of possible original strings that Alice might have intended to type, if she was trying to type a string of size at least k.

// Since the answer may be very large, return it modulo 109 + 7.

 

// Example 1:

// Input: word = "aabbccdd", k = 7

// Output: 5

// Explanation:

// The possible strings are: "aabbccdd", "aabbccd", "aabbcdd", "aabccdd", and "abbccdd".

// Example 2:

// Input: word = "aabbccdd", k = 8

// Output: 1

// Explanation:

// The only possible string is "aabbccdd".

// Example 3:

// Input: word = "aaabbb", k = 3

// Output: 8

 

// Constraints:

// 1 <= word.length <= 5 * 10^5
// word consists only of lowercase English letters.
// 1 <= k <= 2000


// Solution:


public class Solution {
    private static final int MOD = (int)1e9 + 7;

    public int possibleStringCount(String word, int k) {
        if (word.isEmpty()) return 0;

        List<Integer> groups = new ArrayList<>();
        int count = 1;
        for (int i = 1; i < word.length(); i++) {
            if (word.charAt(i) == word.charAt(i - 1)) count++;
            else {
                groups.add(count);
                count = 1;
            }
        }
        groups.add(count);

        long total = 1;
        for (int num : groups) total = (total * num) % MOD;

        if (k <= groups.size()) return (int)total;

        int[] dp = new int[k];
        dp[0] = 1;
        for (int num : groups) {
            int[] newDp = new int[k];
            long sum = 0;
            for (int s = 0; s < k; s++) {
                if (s > 0) sum = (sum + dp[s - 1]) % MOD;
                if (s > num) sum = (sum - dp[s - num - 1] + MOD) % MOD;
                newDp[s] = (int)sum;
            }
            dp = newDp;
        }
        long invalid = 0;
        for (int s = groups.size(); s < k; s++) invalid = (invalid + dp[s]) % MOD;

        return (int)((total - invalid + MOD) % MOD);
    }
}