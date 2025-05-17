// 3337. Total Characters in String After Transformations II

// You are given a string s consisting of lowercase English letters, an integer t representing the number of transformations to perform, and an array nums of size 26. In one transformation, every character in s is replaced according to the following rules:

// Replace s[i] with the next nums[s[i] - 'a'] consecutive characters in the alphabet. For example, if s[i] = 'a' and nums[0] = 3, the character 'a' transforms into the next 3 consecutive characters ahead of it, which results in "bcd".
// The transformation wraps around the alphabet if it exceeds 'z'. For example, if s[i] = 'y' and nums[24] = 3, the character 'y' transforms into the next 3 consecutive characters ahead of it, which results in "zab".
// Return the length of the resulting string after exactly t transformations.

// Since the answer may be very large, return it modulo 109 + 7.

 

// Example 1:

// Input: s = "abcyy", t = 2, nums = [1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,2]

// Output: 7

// Explanation:

// First Transformation (t = 1):

// 'a' becomes 'b' as nums[0] == 1
// 'b' becomes 'c' as nums[1] == 1
// 'c' becomes 'd' as nums[2] == 1
// 'y' becomes 'z' as nums[24] == 1
// 'y' becomes 'z' as nums[24] == 1
// String after the first transformation: "bcdzz"
// Second Transformation (t = 2):

// 'b' becomes 'c' as nums[1] == 1
// 'c' becomes 'd' as nums[2] == 1
// 'd' becomes 'e' as nums[3] == 1
// 'z' becomes 'ab' as nums[25] == 2
// 'z' becomes 'ab' as nums[25] == 2
// String after the second transformation: "cdeabab"
// Final Length of the string: The string is "cdeabab", which has 7 characters.

// Example 2:

// Input: s = "azbk", t = 1, nums = [2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2]

// Output: 8

// Explanation:

// First Transformation (t = 1):

// 'a' becomes 'bc' as nums[0] == 2
// 'z' becomes 'ab' as nums[25] == 2
// 'b' becomes 'cd' as nums[1] == 2
// 'k' becomes 'lm' as nums[10] == 2
// String after the first transformation: "bcabcdlm"
// Final Length of the string: The string is "bcabcdlm", which has 8 characters.

 

// Constraints:

// 1 <= s.length <= 10^5
// s consists only of lowercase English letters.
// 1 <= t <= 10^9
// nums.length == 26
// 1 <= nums[i] <= 25


// Solution:


class Solution {
    static final int L = 26;
    static final int MOD = 1_000_000_007;

    static class Matrix {
        int[][] a = new int[L][L];

        Matrix() {
            for (int i = 0; i < L; i++) {
                for (int j = 0; j < L; j++) {
                    a[i][j] = 0;
                }
            }
        }
    }

    private Matrix multiply(Matrix m1, Matrix m2) {
        Matrix result = new Matrix();
        for (int i = 0; i < L; i++) {
            for (int j = 0; j < L; j++) {
                for (int k = 0; k < L; k++) {
                    result.a[i][j] = (int) ((result.a[i][j] + 1L * m1.a[i][k] * m2.a[k][j]) % MOD);
                }
            }
        }
        return result;
    }

    private Matrix identity() {
        Matrix id = new Matrix();
        for (int i = 0; i < L; i++) {
            id.a[i][i] = 1;
        }
        return id;
    }

    private Matrix matrixPower(Matrix base, int exp) {
        Matrix result = identity();
        while (exp > 0) {
            if ((exp & 1) == 1) {
                result = multiply(result, base);
            }
            base = multiply(base, base);
            exp >>= 1;
        }
        return result;
    }

    public int lengthAfterTransformations(String s, int t, List<Integer> nums) {
        Matrix T = new Matrix();
        for (int i = 0; i < L; i++) {
            int count = nums.get(i);
            for (int j = 1; j <= count; j++) {
                T.a[(i + j) % L][i] = 1;
            }
        }

        Matrix Texp = matrixPower(T, t);

        int[] freq = new int[L];
        for (char c : s.toCharArray()) {
            freq[c - 'a']++;
        }

        int result = 0;
        for (int i = 0; i < L; i++) {
            for (int j = 0; j < L; j++) {
                result = (int) ((result + 1L * Texp.a[i][j] * freq[j]) % MOD);
            }
        }

        return result;
    }
}
