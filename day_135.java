// 3445. Maximum Difference Between Even and Odd Frequency II

// You are given a string s and an integer k. Your task is to find the maximum difference between the frequency of two characters, freq[a] - freq[b], in a substring subs of s, such that:

// subs has a size of at least k.
// Character a has an odd frequency in subs.
// Character b has an even frequency in subs.
// Return the maximum difference.

// Note that subs can contain more than 2 distinct characters.

 

// Example 1:

// Input: s = "12233", k = 4

// Output: -1

// Explanation:

// For the substring "12233", the frequency of '1' is 1 and the frequency of '3' is 2. The difference is 1 - 2 = -1.

// Example 2:

// Input: s = "1122211", k = 3

// Output: 1

// Explanation:

// For the substring "11222", the frequency of '2' is 3 and the frequency of '1' is 2. The difference is 3 - 2 = 1.

// Example 3:

// Input: s = "110", k = 3

// Output: -1

 

// Constraints:

// 3 <= s.length <= 3 * 1064
// s consists only of digits '0' to '4'.
// The input is generated that at least one substring has a character with an even frequency and a character with an odd frequency.
// 1 <= k <= s.length


// Solution: 


class Solution {

    public int maxDifference(String s, int k) {
        int n = s.length();
        // Initialize answer to a very small number.
        int ans = Integer.MIN_VALUE;

        // Step 1: Iterate through all possible pairs of distinct characters (a, b).
        for (char a = '0'; a <= '4'; ++a) {
            for (char b = '0'; b <= '4'; ++b) {
                if (a == b) {
                    continue;
                }

                // --- Start of logic for a single (a, b) pair ---

                // best[status] stores the minimum (prev_a - prev_b) for a prefix
                // with the parity state 'status'. Initialize with a large value.
                int[] best = new int[4];
                Arrays.fill(best, Integer.MAX_VALUE);

                // cnt_a, cnt_b: Prefix counts for the 'right' pointer (s[0...right]).
                int cnt_a = 0, cnt_b = 0;
                // prev_a, prev_b: Prefix counts for the 'left' pointer (s[0...left]).
                int prev_a = 0, prev_b = 0;
                // 'left' tracks the end of the prefix we are recording in the 'best' array.
                int left = -1;

                // The main loop iterating through the string with the 'right' pointer.
                for (int right = 0; right < n; ++right) {
                    // Update prefix counts for the current 'right' position.
                    cnt_a += (s.charAt(right) == a) ? 1 : 0;
                    cnt_b += (s.charAt(right) == b) ? 1 : 0;

                    // This loop updates the 'best' array. It advances the 'left' pointer
                    // and records the state of the prefix ending at 'left'.
                    // The conditions ensure that any substring starting at 'left + 1'
                    // will have a length of at least 'k'.
                    // Also, `cnt_b - prev_b >= 2` since b appears an even number of times in the substring,
                    // but zero occurrences must be excluded (and 1 must also be excluded since it's odd, obviously)
                    while (right - left >= k && cnt_b - prev_b >= 2) {
                        // Get the parity state for the prefix ending at 'left'.
                        int left_status = getStatus(prev_a, prev_b);

                        // Update the 'best' array with the minimum value of (prev_a - prev_b)
                        // for this specific state.
                        best[left_status] = Math.min(
                                best[left_status],
                                prev_a - prev_b
                        );

                        // Advance the left pointer and its corresponding prefix counts.
                        ++left;
                        prev_a += (s.charAt(left) == a) ? 1 : 0;
                        prev_b += (s.charAt(left) == b) ? 1 : 0;
                    }

                    // Now, calculate the potential answer for the current 'right' pointer.
                    // 1. Get the parity state for the prefix ending at 'right'.
                    int right_status = getStatus(cnt_a, cnt_b);

                    // 2. Determine the required state for the start-prefix.
                    // We need `left_status = right_status XOR 10` (binary).
                    int required_status = right_status ^ 0b10;

                    // 3. If we have seen a valid starting prefix with the required state...
                    if (best[required_status] != Integer.MAX_VALUE) {
                        // Calculate the difference: (cnt_a-cnt_b) - min(prev_a-prev_b).
                        // This maximizes the expression.
                        ans = Math.max(
                                ans,
                                cnt_a - cnt_b - best[required_status]
                        );
                    }
                }
            }
        }
        return ans;
    }

    /**
     * Helper function to calculate the 2-bit parity state.
     * Bit 1: parity of cnt_a. Bit 0: parity of cnt_b.
     */
    private int getStatus(int cnt_a, int cnt_b) {
        // (cnt_a & 1) is 1 if cnt_a is odd, 0 if even.
        // << 1 shifts it to the second bit position.
        // | (cnt_b & 1) puts the parity of cnt_b in the first bit position.
        return ((cnt_a & 1) << 1) | (cnt_b & 1);
    }
}