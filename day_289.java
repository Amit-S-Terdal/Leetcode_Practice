// 3234. Count the Number of Substrings With Dominant Ones

// You are given a binary string s.

// Return the number of substrings with dominant ones.

// A string has dominant ones if the number of ones in the string is greater than or equal to the square of the number of zeros in the string.

 

// Example 1:

// Input: s = "00011"

// Output: 5

// Explanation:

// The substrings with dominant ones are shown in the table below.

// i	j	s[i..j]	Number of Zeros	Number of Ones
// 3	3	1	0	1
// 4	4	1	0	1
// 2	3	01	1	1
// 3	4	11	0	2
// 2	4	011	1	2
// Example 2:

// Input: s = "101101"

// Output: 16

// Explanation:

// The substrings with non-dominant ones are shown in the table below.

// Since there are 21 substrings total and 5 of them have non-dominant ones, it follows that there are 16 substrings with dominant ones.

// i	j	s[i..j]	Number of Zeros	Number of Ones
// 1	1	0	1	0
// 4	4	0	1	0
// 1	4	0110	2	2
// 0	4	10110	2	3
// 1	5	01101	2	3
 

// Constraints:

// 1 <= s.length <= 4 * 10^4
// s consists only of characters '0' and '1'.


// Solution: 


class Solution {
    public int numberOfSubstrings(String s) {
        int n = s.length();
        int Zmax = (int)((Math.sqrt(1.0 + 4.0 * n) - 1) / 2);

        // Allocate array to store positions of '0'
        int[] p0 = new int[n + 1]; 
        int front = 0, back = 0;

        // Fill p0[] with indices of '0'
        for (int i = 0; i < n; i++) {
            if (s.charAt(i) == '0') {
                p0[back++] = i;
            }
        }
        p0[back++] = n; // sentinel

        int ans = 0;

        for (int l = 0; l < n; l++) {
            while (front < back && p0[front] < l) front++;

            int prev = l;

            for (int p = front; p < back; p++) {
                int cnt0 = p - front;
                if (cnt0 > Zmax) break;

                int r = p0[p];
                int minLen = Math.max(prev - l + 1, cnt0 * (cnt0 + 1));

                ans += Math.max(0, (r - l) - minLen + 1);

                prev = r;
            }
        }

        return ans;
    }
}
