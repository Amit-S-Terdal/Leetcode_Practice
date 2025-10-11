// 3186. Maximum Total Damage With Spell Casting

// A magician has various spells.

// You are given an array power, where each element represents the damage of a spell. Multiple spells can have the same damage value.

// It is a known fact that if a magician decides to cast a spell with a damage of power[i], they cannot cast any spell with a damage of power[i] - 2, power[i] - 1, power[i] + 1, or power[i] + 2.

// Each spell can be cast only once.

// Return the maximum possible total damage that a magician can cast.

 

// Example 1:

// Input: power = [1,1,3,4]

// Output: 6

// Explanation:

// The maximum possible damage of 6 is produced by casting spells 0, 1, 3 with damage 1, 1, 4.

// Example 2:

// Input: power = [7,1,6,6]

// Output: 13

// Explanation:

// The maximum possible damage of 13 is produced by casting spells 1, 2, 3 with damage 1, 6, 6.

 

// Constraints:

// 1 <= power.length <= 10^5
// 1 <= power[i] <= 10^9

// Solution:




import java.util.*;

class Solution {

    // Helper method for radix sort
    private void radixSort(int[] nums, int n) {
        final int N = 256, mask = 255, bshift = 8;
        int[] output = new int[n];  // buffer
        int M = Arrays.stream(nums).max().getAsInt();
        int Mround = Math.max(1, (31 - Integer.numberOfLeadingZeros(M) + bshift) / bshift);
        int[] in = nums;
        int[] out = output;
        boolean swapped = false;

        for (int round = 0; round < Mround; round++) {
            int shift = round * bshift;
            int[] freq = new int[N];

            // Counting frequency
            for (int i = 0; i < n; i++)
                freq[(in[i] >> shift) & mask]++;

            // Partial sum
            for (int i = 1; i < N; i++)
                freq[i] += freq[i - 1];

            // Sorting step
            for (int i = n - 1; i >= 0; i--) {
                int val = in[i];
                int x = (val >> shift) & mask;
                out[--freq[x]] = val;
            }

            // Swap references
            int[] temp = in;
            in = out;
            out = temp;
            swapped = !swapped;
        }

        // If needed, copy back
        if (swapped)
            System.arraycopy(in, 0, nums, 0, n);
    }

    public long maximumTotalDamage(int[] power) {
        int n = power.length;
        radixSort(power, n);

        List<Map.Entry<Integer, Long>> spell = new ArrayList<>();
        spell.add(new AbstractMap.SimpleEntry<>(power[0], (long) power[0]));

        for (int i = 1; i < n; i++) {
            int x = power[i];
            if (x != power[i - 1]) {
                spell.add(new AbstractMap.SimpleEntry<>(x, (long) x));
            } else {
                spell.get(spell.size() - 1).setValue(spell.get(spell.size() - 1).getValue() + x);
            }
        }

        int sz = spell.size();
        long[] dp = new long[sz + 1];

        for (int i = sz - 1; i >= 0; i--) {
            long notake = dp[i + 1];
            long take = 0;
            int j = i + 1;
            int x = spell.get(i).getKey();
            while (j < sz && spell.get(j).getKey() <= x + 2) {
                j++;
            }

            take = spell.get(i).getValue() + dp[j];
            dp[i] = Math.max(take, notake);
        }

        return dp[0];
    }
}
