// 1871. Jump Game VII

// You are given a 0-indexed binary string s and two integers minJump and maxJump. In the beginning, you are standing at index 0, which is equal to '0'. You can move from index i to index j if the following conditions are fulfilled:

// i + minJump <= j <= min(i + maxJump, s.length - 1), and
// s[j] == '0'.
// Return true if you can reach index s.length - 1 in s, or false otherwise.

 

// Example 1:

// Input: s = "011010", minJump = 2, maxJump = 3
// Output: true
// Explanation:
// In the first step, move from index 0 to index 3. 
// In the second step, move from index 3 to index 5.
// Example 2:

// Input: s = "01101110", minJump = 2, maxJump = 3
// Output: false
 

// Constraints:

// 2 <= s.length <= 10^5
// s[i] is either '0' or '1'.
// s[0] == '0'
// 1 <= minJump <= maxJump < s.length

// Solution: 



class Solution {
    static int[] q = new int[100000];
    static int front, back;

    public boolean canReach(String s, int minJump, int maxJump) {
        int n = s.length();

        if (s.charAt(n - 1) == '1') {
            return false;
        }

        // reset queue
        front = 0;
        back = 0;

        q[back++] = 0;

        int i = 0;

        for (int far = 0; front < back; far = Math.max(far, i + maxJump)) {

            i = q[front++]; // pop front

            int j0 = Math.max(far + 1, i + minJump);
            int jM = Math.min(i + maxJump, n - 1);

            for (int j = j0; j <= jM; j++) {
                if (s.charAt(j) == '0') {

                    if (j == n - 1) {
                        return true;
                    }

                    q[back++] = j;
                }
            }
        }

        return false;
    }
}