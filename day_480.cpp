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



int q[100000];
int front, back;
class Solution {
public:
    static bool canReach(string& s, int minJ, int maxJ) {
        const int n=s.size();
        if (s[n-1]=='1') return 0;

        front=back=0;// reset q
        q[back++]=0;
        
        int i=0;
        for(int far=0; front<back; far=max(far, i+maxJ)) {
            i=q[front++];// pop the front
            // Start from j0=max(far+1, i+minJ)
            int j0=max(far+1, i+minJ), jM=min(i+maxJ, n-1);
            for (int j=j0; j<=jM; j++) {
                if (s[j]=='0') {
                    if (j==n-1) return 1;
                    q[back++]=j;
                }
            }
        }
        return 0;
    }
};